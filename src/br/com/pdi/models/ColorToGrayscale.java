package br.com.pdi.models;

import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

public class ColorToGrayscale {

	public static Mat imageToGray(Mat source){
		Mat dst = new Mat();
		Imgproc.cvtColor(source, dst, Imgproc.COLOR_RGB2GRAY);
		return dst ;
	}

}
