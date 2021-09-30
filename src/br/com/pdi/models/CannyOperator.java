package br.com.pdi.models;

import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

public class CannyOperator {
	public static Mat edgeDetection(Mat source){
		Mat gray = new Mat();
		Mat canny = new Mat();
		Imgproc.cvtColor(source, gray, Imgproc.COLOR_BGR2GRAY);
		Imgproc.Canny(gray, canny, 60, 152);
		return canny;
	}
}
