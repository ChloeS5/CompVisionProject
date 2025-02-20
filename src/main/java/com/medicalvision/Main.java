package com.medicalvision;

public class Main {
    static {
        System.load("/Users/chloesepulveda/opencv/build/lib/libopencv_java4120.dylib");
    }

    public static void main(String[] args) {
        // Initialize the GUI
        GUI gui = new GUI();
        gui.createAndShowGUI();
    }
}
