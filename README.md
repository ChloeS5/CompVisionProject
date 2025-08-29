## Computer Vision Project: Web App for Tumor Detection in Medical Imagery
**Purpose**: This healthcare-focused application uses object detection techniques to identify tumor-resembling anomalies in MRI scans, X-Rays, ultrasounds, and other medical images.

## Setup
1. Install Java 17 or 21 (check that java -version in terminal shows 17 or 21)
3. Run ./gradlew clean shadowJar in terminal -> this gets build/libs/CompVisionProject.jar
4. On Mac run java -jar CompVisionProject.jar OR on Windows run java -jar CompVisionProject.jar

## Feature
- Upload image for analysis
- Returns contrast-enhanced image with "hotspot(s)" that improves tumors visibility

## License
This app is licensed under GNU General Public License v2.0
