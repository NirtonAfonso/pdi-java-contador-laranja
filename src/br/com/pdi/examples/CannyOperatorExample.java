package br.com.pdi.examples;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

import br.com.pdi.models.CannyOperator;
import br.com.pdi.util.ImShow;

public class CannyOperatorExample {

	static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}

	public static void main(String[] args) {
		
		Mat source = Imgcodecs.imread("resources/images/oranges.jfif");
		Mat image = CannyOperator.edgeDetection(source);
		
		new ImShow("original").showImage(source);
		new ImShow("canny").showImage(image);
	}

}
