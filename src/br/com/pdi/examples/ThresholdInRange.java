package br.com.pdi.examples;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Image;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingWorker;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;
public class ThresholdInRange {
    private static int MAX_VALUE = 255;
    private static int MAX_VALUE_H = 360/2;
    private static final String WINDOW_NAME = "Thresholding Operations using inRange demo";
    private static final String LOW_H_NAME = "Low H";
    private static final String LOW_S_NAME = "Low S";
    private static final String LOW_V_NAME = "Low V";
    private static final String HIGH_H_NAME = "High H";
    private static final String HIGH_S_NAME = "High S";
    private static final String HIGH_V_NAME = "High V";
    private JSlider sliderLowH;
    private JSlider sliderHighH;
    private JSlider sliderLowS;
    private JSlider sliderHighS;
    private JSlider sliderLowV;
    private JSlider sliderHighV;
    private Mat matFrame = new Mat();
    private JFrame frame;
    private JLabel imgCaptureLabel;
    private JLabel imgDetectionLabel;
    private Mat src;
    Mat thresh = new Mat();
    Mat frameHSV = new Mat();
    Mat circles = new Mat();
    public ThresholdInRange(String[] args) {
    	String imagePath = "resources/images/oranges.jfif";
        if (args.length > 0) {
            imagePath = args[0];
        }
        // Load an image
        src = Imgcodecs.imread(imagePath);
        if (src.empty()) {
            System.out.println("Empty image: " + imagePath);
            System.exit(0);
        }
        
        Imgproc.cvtColor(src, frameHSV, Imgproc.COLOR_BGR2HSV);
        
        // Create and set up the window.
        frame = new JFrame(WINDOW_NAME);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
        });
        // Set up the content pane.
        Image img = HighGui.toBufferedImage(src);
        addComponentsToPane(frame.getContentPane(), img);
        // Use the content pane's default BorderLayout. No need for
        // setLayout(new BorderLayout());
        // Display the window.
        frame.pack();
        frame.setVisible(true);
    }
    
       
            
            
            
            
        
    
    private void addComponentsToPane(Container pane, Image img) {
        if (!(pane.getLayout() instanceof BorderLayout)) {
            pane.add(new JLabel("Container doesn't use BorderLayout!"));
            return;
        }
        JPanel sliderPanel = new JPanel();
        sliderPanel.setLayout(new BoxLayout(sliderPanel, BoxLayout.PAGE_AXIS));
        sliderPanel.add(new JLabel(LOW_H_NAME));
        sliderLowH = new JSlider(0, MAX_VALUE_H, 0);
        sliderLowH.setMajorTickSpacing(10);
        sliderLowH.setMinorTickSpacing(1);
        sliderLowH.setPaintTicks(true);
        sliderLowH.setPaintLabels(true);
        sliderPanel.add(sliderLowH);
        sliderPanel.add(new JLabel(HIGH_H_NAME));
        sliderHighH = new JSlider(0, MAX_VALUE_H, MAX_VALUE_H);
        sliderHighH.setMajorTickSpacing(10);
        sliderHighH.setMinorTickSpacing(1);
        sliderHighH.setPaintTicks(true);
        sliderHighH.setPaintLabels(true);
        sliderPanel.add(sliderHighH);
        sliderPanel.add(new JLabel(LOW_S_NAME));
        sliderLowS = new JSlider(0, MAX_VALUE, 0);
        sliderLowS.setMajorTickSpacing(10);
        sliderLowS.setMinorTickSpacing(1);
        sliderLowS.setPaintTicks(true);
        sliderLowS.setPaintLabels(true);
        sliderPanel.add(sliderLowS);
        sliderPanel.add(new JLabel(HIGH_S_NAME));
        sliderHighS = new JSlider(0, MAX_VALUE, MAX_VALUE);
        sliderHighS.setMajorTickSpacing(10);
        sliderHighS.setMinorTickSpacing(1);
        sliderHighS.setPaintTicks(true);
        sliderHighS.setPaintLabels(true);
        sliderPanel.add(sliderHighS);
        sliderPanel.add(new JLabel(LOW_V_NAME));
        sliderLowV = new JSlider(0, MAX_VALUE, 0);
        sliderLowV.setMajorTickSpacing(10);
        sliderLowV.setMinorTickSpacing(1);
        sliderLowV.setPaintTicks(true);
        sliderLowV.setPaintLabels(true);
        sliderPanel.add(sliderLowV);
        sliderPanel.add(new JLabel(HIGH_V_NAME));
        sliderHighV = new JSlider(0, MAX_VALUE, MAX_VALUE);
        sliderHighV.setMajorTickSpacing(10);
        sliderHighV.setMinorTickSpacing(1);
        sliderHighV.setPaintTicks(true);
        sliderHighV.setPaintLabels(true);
        sliderPanel.add(sliderHighV);
        sliderLowH.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                JSlider source = (JSlider) e.getSource();
                int valH = Math.min(sliderHighH.getValue()-1, source.getValue());
                sliderLowH.setValue(valH);
                update();
            }
        });
        sliderHighH.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                JSlider source = (JSlider) e.getSource();
                int valH = Math.max(source.getValue(), sliderLowH.getValue()+1);
                sliderHighH.setValue(valH);
                update();
            }
        });
        sliderLowS.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                JSlider source = (JSlider) e.getSource();
                int valS = Math.min(sliderHighS.getValue()-1, source.getValue());
                sliderLowS.setValue(valS);
                update();
            }
        });
        sliderHighS.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                JSlider source = (JSlider) e.getSource();
                int valS = Math.max(source.getValue(), sliderLowS.getValue()+1);
                sliderHighS.setValue(valS);
                update();
            }
        });
        sliderLowV.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                JSlider source = (JSlider) e.getSource();
                int valV = Math.min(sliderHighV.getValue()-1, source.getValue());
                sliderLowV.setValue(valV);
                update();
            }
        });
        sliderHighV.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                JSlider source = (JSlider) e.getSource();
                int valV = Math.max(source.getValue(), sliderLowV.getValue()+1);
                sliderHighV.setValue(valV);
                update();
            }
        });
        pane.add(sliderPanel, BorderLayout.PAGE_START);
        JPanel framePanel = new JPanel();
        imgCaptureLabel = new JLabel(new ImageIcon(img));
        framePanel.add(imgCaptureLabel);
        imgDetectionLabel = new JLabel(new ImageIcon(img));
        framePanel.add(imgDetectionLabel);
        pane.add(framePanel, BorderLayout.CENTER);
        
        
    }
    private void update() {
    	Core.inRange(frameHSV, new Scalar(sliderLowH.getValue(), sliderLowS.getValue(), sliderLowV.getValue()),
                new Scalar(sliderHighH.getValue(), sliderHighS.getValue(), sliderHighV.getValue()), thresh);
        imgCaptureLabel.setIcon(new ImageIcon(HighGui.toBufferedImage(src)));
        imgDetectionLabel.setIcon(new ImageIcon(HighGui.toBufferedImage(thresh)));
       
        
		/*Imgproc.HoughCircles(thresh, circles, Imgproc.HOUGH_GRADIENT, 2.0, 85, // change this value to detect circles with
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
		
		}*/
		
        frame.repaint();
    }
    public static void main(String[] args) {
        // Load the native OpenCV library
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        // Schedule a job for the event dispatch thread:
        // creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ThresholdInRange(args);
            }
        });
    }
}