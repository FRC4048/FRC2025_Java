package frc.robot.camera;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.cscore.CvSink;
import edu.wpi.first.cscore.CvSource;
import edu.wpi.first.cscore.UsbCamera;
import edu.wpi.first.wpilibj.DriverStation;
import org.littletonrobotics.junction.Logger;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

public class CameraThread extends Thread {

  private static final int WIDTH = 640 / 4;
  private static final int HEIGHT = 480 / 4;

  private static final String LOGGING_PREFIX = "DriverCam";

  // Leaving this commented out now, may bring back double lines
  // private static final double TOP_Y = 45;
  // private static final double BOTTOM_Y = 72;

  private static final double HORIZ_LINE = 85.0;

  private UsbCamera camera;
  private CvSink cvSink;
  private CvSource outputStream;
  private Mat cameraMat;
  private Mat rotatedMat;
  private int errorCount;
  private boolean running = true;

  public CameraThread() {
    super("CameraThread");
    setDaemon(true);
  }

  @Override
  public void run() {
    if (!initializeCamera()){
      return;
    }
    while (running) {
      try {
        processImage();
      } catch (Exception e) {
        DriverStation.reportError("CameraServerException", true);
return;
      }
    }
  }

  private boolean initializeCamera() {
    camera = CameraServer.startAutomaticCapture();
    if (!camera.isValid() || !camera.isConnected()) {
      DriverStation.reportError("Driver Camera is not connected!", false);
      return false;
    }
    // Get the USB Camera from the camera server
    camera.setResolution(WIDTH, HEIGHT);

    // Get a CvSink. This will capture Mats from the Camera
    // Setup a CvSource. This will send images back to the dashboard
    cvSink = CameraServer.getVideo();
    outputStream = CameraServer.putVideo(LOGGING_PREFIX, WIDTH, HEIGHT);

    // Mats are very expensive. Let's reuse this Mat.
    cameraMat = new Mat();
    errorCount = 0;
    rotatedMat = Mat.zeros(HEIGHT, WIDTH, 0);
    return true;
  }

  private void processImage() {
    double startTime = System.nanoTime() / 1e-6;
    // Tell the CvSink to grab a frame from the camera and put it
    // in the source mat.  If there is an error notify the output.
    if (cvSink.grabFrame(cameraMat) == 0) {
      errorCount++;
      Logger.recordOutput(LOGGING_PREFIX + "/errorCount", errorCount);
      // Send the output the error.
      outputStream.notifyError(cvSink.getError());
      // skip the rest of the current iteration
      return;
    }

    double mark1 = System.nanoTime() / 1e-6;
    Logger.recordOutput(LOGGING_PREFIX + "/mark1", (mark1 - startTime));

    drawLines(cameraMat);

    Core.flip(cameraMat, cameraMat, 0);
    Core.transpose(cameraMat, rotatedMat);

    // Give the output stream a new image to display
    outputStream.putFrame(rotatedMat);
    double endTime = System.nanoTime() / 1e-6;
    Logger.recordOutput(LOGGING_PREFIX + "/frameProcessingTimeMS", (endTime - startTime));
  }

  private void drawLines(Mat mat) {
    Imgproc.line(
        mat, new Point(HORIZ_LINE, 0), new Point(HORIZ_LINE, HEIGHT), new Scalar(20, 97, 255));
    Imgproc.line(
        mat, new Point(0, HEIGHT / 2.0), new Point(WIDTH, HEIGHT / 2.0), new Scalar(20, 97, 255));

    // Leaving this commented out right now, may bring back two lines
    // Imgproc.line(mat, new Point(0, TOP_Y), new Point(WIDTH, TOP_Y), new Scalar(0, 255, 0));
    // Imgproc.line(mat, new Point(0, BOTTOM_Y), new Point(WIDTH, BOTTOM_Y), new Scalar(0, 255,
    // 0));
  }
}
