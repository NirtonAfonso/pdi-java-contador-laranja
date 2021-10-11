/**
 * @author Nirton Afonso
 * @author Eliel Barros
 * @author Luigi de Luna
 * @author Max Yuri
 *
 */

package br.com.pdi.models;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.highgui.HighGui;
import org.opencv.imgproc.Imgproc;

/**
 * Classe Principal onde fazemos a analize das laranjas atraves dos valores do
 * HSV obtido na classe ThresholdInRange
 *
 */

public class HoughCircles {
	private int quantidade;

	public void run(Mat src) {
		if (src.empty()) {
			System.out.println("Error opening image!");
			System.exit(-1);
		}
		Mat hsv = new Mat();
		Mat circles = new Mat();

		Imgproc.cvtColor(src, hsv, Imgproc.COLOR_BGR2HSV);
		Mat thresh = new Mat();
		Core.inRange(hsv, new Scalar(0, 146, 102), new Scalar(20, 255, 255), thresh);

		// Aplicado os efeitos de Erosão e Dilatação para diminir a quantidade de ruido
		// da imagem
		Imgproc.erode(thresh, thresh, Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(1, 3)));
		Imgproc.dilate(thresh, thresh, Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(1, 3)));
		Imgproc.HoughCircles(thresh, circles, Imgproc.HOUGH_GRADIENT, 2.5, 30, 100, 49, 0, 100);

		for (quantidade = 0; quantidade < circles.cols(); quantidade++) {
			double[] c = circles.get(0, quantidade);
			Point center = new Point(Math.round(c[0]), Math.round(c[1]));
			// circle center
			Imgproc.circle(src, center, 1, new Scalar(0, 100, 100), 3, 8, 0);
			// circle outline
			int radius = (int) Math.round(c[2]);
			Imgproc.circle(src, center, radius, new Scalar(255, 0, 255), 3, 8, 0);
			// inserir texto na imagem contadno as laranjas individuais
			Imgproc.putText(src, Integer.toString(quantidade + 1), new Point(Math.round(c[0]), Math.round(c[1])),
					Core.FONT_HERSHEY_TRIPLEX, 0.7, new Scalar(255, 255, 255));

		}
		// inserindo na imagem a quantidade total de laranjas contadas
		Imgproc.putText(src, "Quantidade de Laranjas: " + quantidade, new Point(src.cols() / 40, src.rows() / 10),
				Core.FONT_HERSHEY_TRIPLEX, 1, new Scalar(255, 255, 255));
		HighGui.imshow("detected circles", src);
		HighGui.waitKey();
		System.exit(0);
	}
}
