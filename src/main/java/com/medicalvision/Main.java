package com.medicalvision;

// Triggers native load by touching any global class:
import org.bytedeco.opencv.global.opencv_core; // fixes org.bytedeco issue

public class Main {
    public static void main(String[] args) {
        // Force class init -> loads bundled natives for the current OS/arch
        opencv_core.getNumThreads(); //doesn't work becuase of bytedeco import.

        GUI gui = new GUI();
        gui.createAndShowGUI();
    }
}
