package frc.robot.camera;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.cscore.CvSink;
import edu.wpi.first.cscore.CvSource;
import edu.wpi.first.cscore.UsbCamera;
import java.util.ArrayList;
import java.util.List;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

public class CameraThread {

  private static final int WIDTH = 640;
  private static final int HEIGHT = 480;

  public CameraThread() {
    CameraRunner runner = new CameraRunner();
    Thread cameraThread = new Thread(runner, "CameraThread");
    cameraThread.setDaemon(true);
    cameraThread.start();
  }

  private class CameraRunner implements Runnable {
    @Override
    public void run() {

      UsbCamera camera = CameraServer.startAutomaticCapture();
      // Get the USB Camera from the camera server
      camera.setResolution(640, 480);

      // Get a CvSink. This will capture Mats from the Camera
      CvSink cvSink = CameraServer.getVideo();
      // Setup a CvSource. This will send images back to the dashboard
      CvSource outputStream = CameraServer.putVideo("TestStream", WIDTH, HEIGHT);

      // Mats are very expensive. Let's reuse this Mat.
      Mat cameraMat = new Mat();

      // CvType.CV_8UC3;
      Mat overlayMat = new Mat(new Size(WIDTH, HEIGHT), CvType.CV_8UC3);
      addOverlay(overlayMat);

      Mat finalMat = Mat.zeros(WIDTH, HEIGHT, 0);

      Mat rotatedMat = Mat.ones(HEIGHT, WIDTH, 0);

      while (true) {
        // Tell the CvSink to grab a frame from the camera and put it
        // in the source mat.  If there is an error notify the output.
        if (cvSink.grabFrame(cameraMat) == 0) {
          System.err.println("ERROR GRABBING FRAME");
          // Send the output the error.
          outputStream.notifyError(cvSink.getError());
          // skip the rest of the current iteration
          continue;
        }

        try {
          double alpha = 0.4; // Transparency of the overlay
          Core.addWeighted(overlayMat, alpha, cameraMat, 1 - alpha, 0, finalMat);

          Core.transpose(finalMat, rotatedMat);
          Core.flip(rotatedMat, rotatedMat, 1);
        } catch (Exception e) {
          e.printStackTrace();
        }

        // Give the output stream a new image to display
        outputStream.putFrame(rotatedMat);
      }
    }

    private void addOverlay(Mat overlayMat) {
      // "Top" rect polygon
      Point[] points = new Point[4];
      points[0] = new Point(0, 0);
      points[1] = new Point(WIDTH, 0);
      points[2] = new Point(WIDTH, 100);
      points[3] = new Point(0, 150);
      MatOfPoint pointMat = new MatOfPoint();
      pointMat.fromArray(points);
      List<MatOfPoint> rectBlockList = new ArrayList<MatOfPoint>();
      rectBlockList.add(pointMat);

      // "Bottom" rect polygon
      Point[] secondPoints = new Point[4];
      secondPoints[0] = new Point(0, 350);
      secondPoints[1] = new Point(WIDTH, 380);
      secondPoints[2] = new Point(WIDTH, HEIGHT);
      secondPoints[3] = new Point(0, HEIGHT);
      MatOfPoint secondPointMat = new MatOfPoint();
      secondPointMat.fromArray(secondPoints);
      List<MatOfPoint> secondRectBlockList = new ArrayList<MatOfPoint>();
      secondRectBlockList.add(secondPointMat);

      // Imgproc.fillPoly(overlayMat, baseList, new Scalar(255, 255, 255));
      Imgproc.fillPoly(overlayMat, rectBlockList, new Scalar(0, 0, 150));
      Imgproc.fillPoly(overlayMat, secondRectBlockList, new Scalar(0, 0, 150));
    }
  }
}
