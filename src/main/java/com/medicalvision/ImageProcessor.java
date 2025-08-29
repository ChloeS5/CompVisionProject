package com.medicalvision;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.bytedeco.opencv.global.opencv_core;

public class ImageProcessor {
    static {
        opencv_core.getVersionString();
    }

    public BufferedImage processImage(BufferedImage image) {
        // Convert BufferedImage to OpenCV Mat
        Mat mat = bufferedImageToMat(image);
        
        // Apply grayscale conversion
        Mat gray = new Mat();
        Imgproc.cvtColor(mat, gray, Imgproc.COLOR_BGR2GRAY);
        
        // Apply Gaussian blur
        Mat blurred = new Mat();
        Imgproc.GaussianBlur(gray, blurred, new org.opencv.core.Size(5, 5), 0);
        
        // Apply Canny edge detection
        Mat edges = new Mat();
        Imgproc.Canny(blurred, edges, 50, 150);
        
        // Convert back to BufferedImage
        return matToBufferedImage(edges);
    }

    private Mat bufferedImageToMat(BufferedImage image) {
        // Convert to 3-channel BGR format if needed
        if (image.getType() != BufferedImage.TYPE_3BYTE_BGR) {
            BufferedImage converted = new BufferedImage(
                image.getWidth(), image.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
            converted.getGraphics().drawImage(image, 0, 0, null);
            image = converted;
        }
        
        byte[] pixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        Mat mat = new Mat(image.getHeight(), image.getWidth(), CvType.CV_8UC3);
        mat.put(0, 0, pixels);
        return mat;
    }


    private BufferedImage matToBufferedImage(Mat mat) {
        MatOfByte mob = new MatOfByte();
        Imgcodecs.imencode(".jpg", mat, mob);
        byte[] byteArray = mob.toArray();
        try {
            return javax.imageio.ImageIO.read(new java.io.ByteArrayInputStream(byteArray));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
