package br.com.pdi.models;

import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

public class ColorToBlackWhite {
	public static Mat imageToBlackWhite(Mat source){
		Mat dst = new Mat();
		Mat gray = ColorToGrayscale.imageToGray(source);
		Imgproc.threshold(gray, dst, 60.0, 255.0, Imgproc.THRESH_BINARY_INV);
		return dst;
	}
}
