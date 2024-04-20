package com.example.assignment3

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet

class MainActivity : AppCompatActivity(), SensorEventListener {
    lateinit var sensorManager: SensorManager
    lateinit var sensorValues: TextView
    private lateinit var xChart: LineChart
    private lateinit var yChart: LineChart
    private lateinit var zChart: LineChart
    var accelerometer: Sensor? = null

    private lateinit var anglesRepository: AnglesRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sensorValues = findViewById(R.id.sensorValues)
        xChart = findViewById(R.id.xChart)
        yChart = findViewById(R.id.yChart)
        zChart = findViewById(R.id.zChart)
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager

        anglesRepository = AnglesRepository(AnglesDatabase.getInstance(this).anglesDao())

        // Check if accelerometer sensor is available
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        if (accelerometer == null) {
            sensorValues.text = "Accelerometer not available"
        } else {
            // Register sensor listener
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL)
        }

    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event != null && event.sensor.type == Sensor.TYPE_ACCELEROMETER) {
            val x = event.values[0]
            val y = event.values[1]
            val z = event.values[2]

            // Display sensor values in TextView
            sensorValues.text = "X: $x\nY: $y\nZ: $z"

            // Insert sensor values into database
            val currentTime = getCurrentTime()
            insertAnglesData(currentTime, x.toDouble(), y.toDouble(), z.toDouble())

            // Plot the graphs
            CoroutineScope(Dispatchers.IO).launch {
                val anglesDataList = anglesRepository.getAllAnglesData() // Assuming getAllAnglesData() retrieves all data

                // Extract x, y, z values from anglesDataList
                val xValues = anglesDataList.map { it.x.toFloat() }
                val yValues = anglesDataList.map { it.y.toFloat() }
                val zValues = anglesDataList.map { it.z.toFloat() }

                // Plot the graphs using extracted values
                runOnUiThread {
                    plotGraphs(xValues, yValues, zValues)
                }
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // Not used in this example
    }

    override fun onPause() {
        super.onPause()
        // Unregister the sensor listener to conserve battery
        sensorManager.unregisterListener(this)
    }

    private fun getCurrentTime(): String {
        val currentDateTime = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("HH:mm:ss")  // Format as desired (e.g., "HH:mm:ss a" for AM/PM)
        return currentDateTime.format(formatter)
    }

    private fun insertAnglesData(time: String, x: Double, y: Double, z: Double) {
        val anglesData = AnglesData(time = time, x = x, y = y, z = z)
        CoroutineScope(Dispatchers.IO).launch {
            anglesRepository.insertAnglesData(anglesData)
            Log.d("Database", "Data inserted successfully: $anglesData")
        }
    }

    private fun plotGraphs(xValues: List<Float>, yValues: List<Float>, zValues: List<Float>) {
        // Populate xChart with x values
        val xEntries = xValues.mapIndexed { index, value -> Entry(index.toFloat(), value) }
        val xDataSet = LineDataSet(xEntries, "X Values")
        xChart.data = LineData(xDataSet)
        xChart.invalidate()

        // Populate yChart with y values
        val yEntries = yValues.mapIndexed { index, value -> Entry(index.toFloat(), value) }
        val yDataSet = LineDataSet(yEntries, "Y Values")
        yChart.data = LineData(yDataSet)
        yChart.invalidate()

        // Populate zChart with z values
        val zEntries = zValues.mapIndexed { index, value -> Entry(index.toFloat(), value) }
        val zDataSet = LineDataSet(zEntries, "Z Values")
        zChart.data = LineData(zDataSet)
        zChart.invalidate()
    }
}
