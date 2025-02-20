package com.medicalvision;

import java.util.ArrayList;
import java.util.List;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

public class MedicalImageAnalyzer {

    private Mat bufferedImgToMat(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        
        // Convert BufferedImage to byte array
        byte[] pixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        
        // Create Mat with correct type based on image format
        Mat mat = new Mat(height, width, CvType.CV_8UC3);
        
        // Convert image data to 3-channel format if needed
        if (image.getType() == BufferedImage.TYPE_3BYTE_BGR) {
            mat.put(0, 0, pixels);
        } else {
            // Convert other image types to 3-channel format
            BufferedImage convertedImage = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
            convertedImage.getGraphics().drawImage(image, 0, 0, null);
            byte[] convertedPixels = ((DataBufferByte) convertedImage.getRaster().getDataBuffer()).getData();
            mat.put(0, 0, convertedPixels);
        }
        
        return mat;
    }

    private BufferedImage matToBufferedImage(Mat mat) {
        int width = mat.width();
        int height = mat.height();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
        byte[] pixels = new byte[width * height * 3];
        mat.get(0, 0, pixels);
        image.getRaster().setDataElements(0, 0, width, height, pixels);
        return image;
    }

    public BufferedImage analyzeImage(BufferedImage image) {
        Mat src = bufferedImgToMat(image);
        
        // Convert source image to grayscale
        Mat gray = new Mat();
        Imgproc.cvtColor(src, gray, Imgproc.COLOR_BGR2GRAY);

        // Apply binary threshold
        Mat binary = new Mat();
        Imgproc.threshold(gray, binary, 100, 255, Imgproc.THRESH_BINARY);

        // Find contours
        List<MatOfPoint> contours = new ArrayList<>();
        Mat hierarchy = new Mat();
        Imgproc.findContours(binary, contours, hierarchy, Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);
        
        // Draw contours on original image
        Imgproc.drawContours(src, contours, -1, new Scalar(0, 0, 255), 2);

        // Calculate contour areas to find the anomaly
        int numLargeContours = 0;

        double minArea = 500;
        List<MatOfPoint> largeContours = new ArrayList<>();

        // Loop to iterate through contours, filter for largest areas (likely tumor/abnormal growth)
        for (MatOfPoint contour : contours) {
            double area = Imgproc.contourArea(contour);
            if (area > minArea) {
                largeContours.add(contour);
            }
        }
    

        //TODO Add print out to Graphic UI, not terminal
        System.out.println("Number of anomalies suspected: " + largeContours.size());

        return matToBufferedImage(src);
    }
}
