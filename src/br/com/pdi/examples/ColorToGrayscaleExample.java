package br.com.pdi.examples;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

import br.com.pdi.models.ColorToGrayscale;
import br.com.pdi.util.ImShow;

public class ColorToGrayscaleExample {
	
	static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}

	public static void main(String[] args) {
		
		Mat source = Imgcodecs.imread("resources/images/oranges.jfif");
		Mat image = ColorToGrayscale.imageToGray(source);
		
		new ImShow("original").showImage(source);
		new ImShow("gray scale").showImage(image);
	}

}
