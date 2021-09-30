package br.com.pdi.models;

import java.util.Arrays;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfFloat;
import org.opencv.core.MatOfInt;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.core.TermCriteria;
import org.opencv.highgui.HighGui;
import org.opencv.imgproc.Imgproc;
import org.opencv.video.Video;
import org.opencv.videoio.VideoCapture;

public class HoughCircles {

	public void run(Mat src) {
		if (src.empty()) {
			System.out.println("Error opening image!");
			System.exit(-1);
		}
		Mat hsv = new Mat();
		// Mat sharpness = new Mat();
		// Mat contrast = new Mat();
		// Mat threshold = new Mat();
		Mat circles = new Mat();

		//Imgproc.cvtColor(src, gray, Imgproc.COLOR_BGR2GRAY);
		Imgproc.cvtColor(src, hsv, Imgproc.COLOR_BGR2HSV);
        Mat thresh = new Mat();
        Core.inRange(hsv, new Scalar(0, 158, 133),
                new Scalar(93, 255, 255), hsv);
        //update(hsv, thresh);
        HighGui.imshow("hsv", hsv);

		
		  /*Imgproc.threshold(gray, gray, 165, 255, 4);
		  
		  
		  Imgproc.GaussianBlur(gray, gray, new Size(0,0), 4); Core.addWeighted(gray,
		  1.5, gray, -0.5, 0, gray); //HighGui.imshow("sharpness", sharpness);
		  //
		  
		  Imgproc.equalizeHist(gray, gray); //HighGui.imshow("contrast", contrast);
		  
		  Imgproc.medianBlur(gray, gray, 3); //HighGui.imshow("mediumBlur", gray);
		  
		  HighGui.imshow("threshold", gray);
		  
		  Imgproc.erode(gray, gray, Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(1,1))); 
		  Imgproc.dilate(gray, gray,Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(1, 1)));*/
		 

		Imgproc.HoughCircles(hsv, circles, Imgproc.HOUGH_GRADIENT, 2.0, 85, // change this value to detect circles with
																				// different distances to each other
				200, 50, 1, 100); // change the last two parameters
		// (min_radius & max_radius) to detect larger circles
		for (int x = 0; x < circles.cols(); x++) {
			double[] c = circles.get(0, x);
			Point center = new Point(Math.round(c[0]), Math.round(c[1]));
			// circle center
			Imgproc.circle(src, center, 1, new Scalar(0, 100, 100), 3, 8, 0);
			// circle outline
			int radius = (int) Math.round(c[2]);
			Imgproc.circle(src, center, radius, new Scalar(255, 0, 255), 3, 8, 0);
		}

		HighGui.imshow("detected circles", src);
		HighGui.waitKey();
		System.exit(0);
	}
}
