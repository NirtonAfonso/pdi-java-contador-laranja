package br.com.pdi.examples;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

import br.com.pdi.models.HoughCircles;

public class HoughCirclesExample {

	public static void main(String[] args) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		
		Mat source = Imgcodecs.imread("resources/images/oranges.jfif");
		
        new HoughCircles().run(source);

	}

}
