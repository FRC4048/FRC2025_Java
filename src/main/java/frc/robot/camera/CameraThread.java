package frc.robot.camera;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.cscore.CvSink;
import edu.wpi.first.cscore.CvSource;
import edu.wpi.first.cscore.UsbCamera;
import org.littletonrobotics.junction.Logger;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

public class CameraThread {

  private static final int WIDTH = 640 / 4;
  private static final int HEIGHT = 480 / 4;

  private static final String LOGGING_PREFIX = "DriverCam";

  // Leaving this commented out now, may bring back double lines

  private static final double HORIZ_LINE = 85.0;

  public CameraThread() {
    CameraRunner runner = new CameraRunner();
    Thread cameraThread = new Thread(runner, "CameraThread");
    cameraThread.setDaemon(true);
    cameraThread.start();
  }

  private static class CameraRunner implements Runnable {
    @Override
    public void run() {
      try {
        processImage();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

    private void processImage() {
      UsbCamera camera = CameraServer.startAutomaticCapture();
      // Get the USB Camera from the camera server
      camera.setResolution(WIDTH, HEIGHT);

      // Get a CvSink. This will capture Mats from the Camera
      // Setup a CvSource. This will send images back to the dashboard
      CvSink cvSink = CameraServer.getVideo();
      CvSource outputStream = CameraServer.putVideo(LOGGING_PREFIX, WIDTH, HEIGHT);

      // Mats are very expensive. Let's reuse this Mat.
      Mat cameraMat = new Mat();

      int errorCount = 0;

      Mat rotatedMat = Mat.zeros(HEIGHT, WIDTH, 0);

      while (true) {

        long startTime = System.currentTimeMillis();
        // Tell the CvSink to grab a frame from the camera and put it
        // in the source mat.  If there is an error notify the output.
        if (cvSink.grabFrame(cameraMat) == 0) {
          errorCount++;
          Logger.recordOutput(LOGGING_PREFIX + "/errorCount", errorCount);
          // Send the output the error.
          outputStream.notifyError(cvSink.getError());
          // skip the rest of the current iteration
          continue;
        }

        long mark1 = System.currentTimeMillis();
        Logger.recordOutput(LOGGING_PREFIX + "/mark1", (mark1 - startTime));

        drawLines(cameraMat);

        Core.flip(cameraMat, cameraMat, 0);
        Core.transpose(cameraMat, rotatedMat);

        // Give the output stream a new image to display
        outputStream.putFrame(rotatedMat);
        long endTime = System.currentTimeMillis();
        Logger.recordOutput(LOGGING_PREFIX + "/frameProcessingTimeMS", (endTime - startTime));
      }
    }

    private void drawLines(Mat mat) {

      Imgproc.line(
          mat, new Point(HORIZ_LINE, 0), new Point(HORIZ_LINE, HEIGHT), new Scalar(20, 97, 255));
      Imgproc.line(
          mat,
          new Point(0, (double) HEIGHT / 2),
          new Point(WIDTH, (double) HEIGHT / 2),
          new Scalar(20, 97, 255));

      // Leaving this commented out right now, may bring back two lines
    }
  }
}
