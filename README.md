# **Android Accelerometer Data Logger and Predictor**

This Android application captures accelerometer data (X, Y, Z axes) in real-time, stores the data in a local SQLite database, and visualizes it using line charts using the MPAndroidChart library.

## **Features**

- **Real-time Sensor Data**: Displays accelerometer values (X, Y, Z) on the screen in real-time.
- **Data Logging**: Stores the accelerometer data along with timestamps in a local SQLite database using Room Persistence Library.
- **Graphical Visualization**: Plots line charts for X, Y, Z accelerometer data using the MPAndroidChart library.
- **Asynchronous Data Handling**: Uses coroutines for background database operations and graph plotting.

## **Setup and Configuration**

1. **Clone the Repository**:
    
    ```bash
    git clone https://github.com/0oAVIRALo0/accelerometer-logger-predictor.git
    ```
    
2. **Open in Android Studio**:
    - Launch Android Studio.
    - Click on **`Open an existing Android Studio project`**.
    - Navigate to the cloned repository and select the **`assignment3`** directory.
3. **Run the Application**:
    - Connect an Android device or start an emulator.
    - Click on the **`Run`** button in Android Studio to build and run the application on the selected device/emulator.

## **Usage**

1. **View Sensor Data**:
    - Launch the application on your Android device/emulator.
    - The main screen will display real-time accelerometer data (X, Y, Z values) as you move the device.
2. **Data Logging**:
    - The accelerometer data, along with timestamps, is automatically logged into the local SQLite database.
3. **Graph Visualization**:
    - Line charts for X, Y, Z accelerometer data are plotted using the MPAndroidChart library.
    - Each chart represents the variation of accelerometer values over time.

## **Libraries Used**

- **MPAndroidChart**: Used to plot line charts for accelerometer data visualization.
- **Room Persistence Library**: Provides an abstraction layer over SQLite for database operations.
- **Kotlin Coroutines**: Used for asynchronous and non-blocking database operations and UI updates.

## **Screenshots**

*Insert screenshots of the application demonstrating real-time sensor data and plotted graphs.*

## **Troubleshooting**

- **Sensor Unavailable**: If the app displays "Accelerometer not available", ensure that the device supports the accelerometer sensor.
- **Graphs Not Updating**: If the graphs are not updating, check the data insertion process and ensure the database operations are handled asynchronously.

## **Future Improvements**

- Implement data export functionality to CSV or other formats.
- Enhance UI with more interactive features for data visualization.
- Add settings to customize sensor sampling rate and other parameters.
