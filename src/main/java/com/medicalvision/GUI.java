package com.medicalvision;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import com.medicalvision.ImageProcessor;
import com.medicalvision.MedicalImageAnalyzer;

// represents the graphical user interface
public class GUI {
    private JFrame frame;
    private JButton uploadButton;
    private JButton analyzeButton;
    private JLabel imageLabel;
    private JTextArea resultArea;
    private BufferedImage currentImage;
    private ImageProcessor imageProcessor;
    private MedicalImageAnalyzer medicalAnalyzer;



    // EFFECTS: creates the graphic user interface
    public void createAndShowGUI() {
        // Initialize components
        imageProcessor = new ImageProcessor();
        medicalAnalyzer = new MedicalImageAnalyzer();
        
        // Create the main frame
        frame = new JFrame("Medical Image Analyzer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        // Create the upload button
        uploadButton = new JButton("Upload Image");
        uploadButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    try {
                        currentImage = ImageIO.read(selectedFile);
                        ImageIcon icon = new ImageIcon(currentImage);
                        imageLabel.setIcon(icon);
                        analyzeButton.setEnabled(true);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(frame, "Error loading image");
                    }
                }
            }
        });

        // Create the analyze button
        analyzeButton = new JButton("Analyze Image");
        analyzeButton.setEnabled(false);
        analyzeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (currentImage != null) {
                    BufferedImage processedImage = imageProcessor.processImage(currentImage);
                    BufferedImage analyzedImage = medicalAnalyzer.analyzeImage(processedImage);
                    ImageIcon resultIcon = new ImageIcon(analyzedImage);
                    imageLabel.setIcon(resultIcon);
                    resultArea.setText("Analysis complete. See processed image above.");
                }
            }
        });

        // Create the image display area
        imageLabel = new JLabel("Uploaded image will appear here", SwingConstants.CENTER);
        imageLabel.setPreferredSize(new Dimension(600, 400));

        // Create the result area
        resultArea = new JTextArea();
        resultArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultArea);
        scrollPane.setPreferredSize(new Dimension(600, 100));

        // Add components to the frame
        frame.getContentPane().setLayout(new BorderLayout());
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(uploadButton);
        buttonPanel.add(analyzeButton);
        
        frame.getContentPane().add(butt