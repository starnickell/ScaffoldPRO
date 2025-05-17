package com.scaffoldpro.app.features.armeasure

import android.os.Bundle
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.content.Context
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import com.google.ar.core.*
import com.google.ar.core.exceptions.CameraNotAvailableException
import com.google.ar.core.exceptions.UnavailableApkTooOldException
import com.google.ar.core.exceptions.UnavailableDeviceNotCompatibleException
import com.google.ar.core.exceptions.UnavailableSdkTooOldException
import com.google.ar.core.exceptions.UnavailableUserDeclinedInstallationException
import com.scaffoldpro.app.R // Assuming a layout file exists, e.g., activity_measure.xml
import com.gorisse.thomas.sceneform.ArSceneView
import com.gorisse.thomas.sceneform.scene.AnchorNode
import com.scaffoldpro.app.ui.theme.md_theme_light_primary
import com.scaffoldpro.app.ui.theme.md_theme_light_error
import com.scaffoldpro.app.ui.theme.md_theme_dark_primary
import com.scaffoldpro.app.ui.theme.md_theme_dark_error

class MeasureActivity : AppCompatActivity(), SensorEventListener {
    private lateinit var arSceneView: ArSceneView
    private var session: Session? = null
    private var shouldConfigureSession = false
    private lateinit var gestureDetector: GestureDetector
    private val placedAnchors = mutableListOf<Anchor>()
    private val placedAnchorNodes = mutableListOf<AnchorNode>()
    
    // Bubble level/inclinometer properties
    private lateinit var sensorManager: SensorManager
    private var accelerometer: Sensor? = null
    private var isInLevelMode = false
    
    // UI elements for bubble level (to be inflated dynamically)
    private lateinit var levelContainer: LinearLayout
    private lateinit var bubbleView: View
    private lateinit var xAngleText: TextView
    private lateinit var yAngleText: TextView
    private lateinit var statusText: TextView
    
    // Current orientation values
    private var xAngle = 0f
    private var yAngle = 0f
    private val LEVEL_THRESHOLD = 0.5f  // degrees threshold to consider "level"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_measure) // Assuming you have a layout file for this
        arSceneView = findViewById(R.id.arSceneView) // Assuming an ArSceneView with this ID in your layout
        
        // Keep screen on during ARCore session
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        // Initialize sensor manager and accelerometer for bubble level
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        
        // Initialize UI components for bubble level
        levelContainer = findViewById(R.id.levelContainer) // To be set in your layout
        bubbleView = findViewById(R.id.bubbleView) // Custom view for bubble visualization
        xAngleText = findViewById(R.id.xAngleText) // TextView to display X angle
        yAngleText = findViewById(R.id.yAngleText) // TextView to display Y angle
        statusText = findViewById(R.id.levelStatusText) // TextView for level status
        
        // Hide bubble level UI initially
        levelContainer.visibility = View.GONE

        // Initialize gesture detector for tap events
        gestureDetector = GestureDetector(this, object : GestureDetector.SimpleOnGestureListener() {
            override fun onSingleTapUp(e: MotionEvent): Boolean {
                if (!isInLevelMode) {
                    handleTap(e)
                }
                return true
            }
            override fun onDown(e: MotionEvent): Boolean {
                return true
            }
        })

        arSceneView.setOnTouchListener { _, event ->
            gestureDetector.onTouchEvent(event)
            true
        }
        
        // Set up mode switch button
        findViewById<View>(R.id.modeSwitchButton).setOnClickListener {
            toggleLevelMode()
        }

        // Setup ARCore session
        if (session == null) {
            try {
                session = Session(this)
            } catch (e: UnavailableArcoreNotInstalledException) {
                // Handle ARCore not installed
                Toast.makeText(this, "ARCore not installed.", Toast.LENGTH_LONG).show()
                finish()
                return
            } catch (e: UnavailableUserDeclinedInstallationException) {
                Toast.makeText(this, "Please install ARCore.", Toast.LENGTH_LONG).show()
                finish()
                return
            } catch (e: UnavailableApkTooOldException) {
                Toast.makeText(this, "Please update ARCore.", Toast.LENGTH_LONG).show()
                finish()
                return
            } catch (e: UnavailableSdkTooOldException) {
                Toast.makeText(this, "Please update this app.", Toast.LENGTH_LONG).show()
                finish()
                return
            } catch (e: UnavailableDeviceNotCompatibleException) {
                Toast.makeText(this, "This device does not support AR.", Toast.LENGTH_LONG).show()
                finish()
                return
            }
            shouldConfigureSession = true
        }

        if (shouldConfigureSession) {
            configureSession()
            shouldConfigureSession = false
            arSceneView.setupSession(session)
        }

        // Set up a listener for AR frame updates
        arSceneView.arFrame.observe(this) { frame ->
            // Handle frame updates if necessary, e.g., for continuous measurement updates
        }
    }

    private fun configureSession() {
        val config = Config(session)
        config.updateMode = Config.UpdateMode.LATEST_CAMERA_IMAGE
        session?.configure(config)
    }

    private fun handleTap(motionEvent: MotionEvent) {
        val frame = arSceneView.arFrame.value ?: return
        if (frame.camera.trackingState == TrackingState.TRACKING) {
            val hitResults = frame.hitTest(motionEvent)
            if (hitResults.isNotEmpty()) {
                val hitResult = hitResults.firstOrNull { it.trackable is Plane && (it.trackable as Plane).isPoseInPolygon(it.hitPose) }
                if (hitResult != null) {
                    // Create an anchor at the tapped location
                    val anchor = hitResult.createAnchor()
                    placedAnchors.add(anchor)

                    // Create an AnchorNode and attach it to the arSceneView's scene
                    val anchorNode = AnchorNode(anchor)
                    anchorNode.setParent(arSceneView.scene)
                    placedAnchorNodes.add(anchorNode)

                    // Add a visual representation (e.g., a small sphere or a marker)
                    // This part would require a 3D model or a simple shape to place.
                    // For simplicity, we'll just log it for now.
                    Log.d("ARMeasure", "Anchor placed at: ${anchor.pose}")

                    if (placedAnchors.size >= 2) {
                        calculateAndDisplayDistance()
                    }
                }
            }
        }
    }

    private fun calculateAndDisplayDistance() {
        if (placedAnchors.size < 2) return

        val anchor1 = placedAnchors[placedAnchors.size - 2]
        val anchor2 = placedAnchors.last()

        val pose1 = anchor1.pose
        val pose2 = anchor2.pose

        val dx = pose1.tx() - pose2.tx()
        val dy = pose1.ty() - pose2.ty()
        val dz = pose1.tz() - pose2.tz()

        val distance = kotlin.math.sqrt(dx * dx + dy * dy + dz * dz)

        // Display the distance. In a real app, you'd update a TextView or similar.
        // For this simulation, we'll use a Toast and logcat.
        val distanceMsg = "Distance: %.2f meters".format(distance)
        Toast.makeText(this, distanceMsg, Toast.LENGTH_LONG).show()
        Log.d("ARMeasure", distanceMsg)

        // Optional: Clear anchors for next measurement or allow multiple lines
        // For this example, let's clear them to measure a new distance next time.
        // To keep lines, you'd need a more complex state management.
        // clearAnchors()
    }

    private fun clearAnchors() {
        for (anchorNode in placedAnchorNodes) {
            arSceneView.scene.removeChild(anchorNode)
            anchorNode.anchor?.detach()
            anchorNode.isEnabled = false // Or other ways to remove/hide
        }
        placedAnchors.clear()
        placedAnchorNodes.clear()
    }

    override fun onResume() {
        super.onResume()
        if (session == null) {
            // Session creation and permission handling (simplified here)
        }
        try {
            session?.resume()
        } catch (e: CameraNotAvailableException) {
            // Handle exception
            session = null
            return
        }
        arSceneView.resume()
        
        // Register sensor listener for bubble level
        accelerometer?.let {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_UI)
        }
    }

    override fun onPause() {
        super.onPause()
        arSceneView.pause()
        session?.pause()
        
        // Unregister sensor listener to save battery
        sensorManager.unregisterListener(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        session?.close()
        session = null
        sensorManager.unregisterListener(this)
    }
    
    /**
     * Toggle between AR measuring mode and bubble level mode
     */
    private fun toggleLevelMode() {
        isInLevelMode = !isInLevelMode
        
        if (isInLevelMode) {
            // Switch to bubble level mode
            arSceneView.visibility = View.GONE
            levelContainer.visibility = View.VISIBLE
            
            // Make sure sensor is registered
            accelerometer?.let {
                sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_UI)
            }
        } else {
            // Switch to AR measurement mode
            arSceneView.visibility = View.VISIBLE
            levelContainer.visibility = View.GONE
        }
    }
    
    /**
     * Update bubble view position based on device orientation
     */
    private fun updateBubblePosition() {
        // Calculate bubble position based on angles (similar to iOS implementation)
        // Constrain movement within the bubble view bounds
        val maxOffset = 75
        val xOffset = (xAngle * 5).coerceIn(-maxOffset.toFloat(), maxOffset.toFloat())
        val yOffset = (yAngle * 5).coerceIn(-maxOffset.toFloat(), maxOffset.toFloat())
        
        // Update bubble position by changing its translation
        bubbleView.translationX = xOffset
        bubbleView.translationY = yOffset
        
        // Update angle text displays
        xAngleText.text = String.format("X-Axis: %.1f°", xAngle)
        yAngleText.text = String.format("Y-Axis: %.1f°", yAngle)
        
        // Check if device is level and update status
        val isLevel = Math.abs(xAngle) < LEVEL_THRESHOLD && Math.abs(yAngle) < LEVEL_THRESHOLD
        
        if (isLevel) {
            statusText.text = "Level!"
            statusText.setTextColor(ContextCompat.getColor(this, 
                if (resources.configuration.uiMode and android.content.res.Configuration.UI_MODE_NIGHT_MASK == 
                    android.content.res.Configuration.UI_MODE_NIGHT_YES) {
                    md_theme_dark_primary.toArgb()
                } else {
                    md_theme_light_primary.toArgb()
                }
            ))
            bubbleView.setBackgroundColor(ContextCompat.getColor(this, android.R.color.holo_green_light))
        } else {
            statusText.text = "Not Level"
            statusText.setTextColor(ContextCompat.getColor(this, 
                if (resources.configuration.uiMode and android.content.res.Configuration.UI_MODE_NIGHT_MASK == 
                    android.content.res.Configuration.UI_MODE_NIGHT_YES) {
                    md_theme_dark_error.toArgb()
                } else {
                    md_theme_light_error.toArgb()
                }
            ))
            bubbleView.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary)) // Assuming this resource exists
        }
    }
    
    // SensorEventListener implementation
    
    /**
     * Process sensor data when values change
     * This is called when the accelerometer reports new values
     */
    override fun onSensorChanged(event: SensorEvent) {
        if (event.sensor.type == Sensor.TYPE_ACCELEROMETER) {
            // Get the accelerometer values
            val x = event.values[0]
            val y = event.values[1]
            val z = event.values[2]
            
            // Calculate device tilt angles in degrees
            // Converting from radians to degrees
            xAngle = Math.toDegrees(Math.atan2(x, Math.sqrt((y * y + z * z).toDouble()))).toFloat()
            yAngle = Math.toDegrees(Math.atan2(y, Math.sqrt((x * x + z * z).toDouble()))).toFloat()
            
            // Update the bubble level UI if in level mode
            if (isInLevelMode) {
                updateBubblePosition()
            }
        }
    }
    
    /**
     * Handle sensor accuracy changes
     * Called when the accuracy of a sensor has changed
     */
    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {
        // We could notify the user if accuracy is low, but for now we'll just log it
        when (accuracy) {
            SensorManager.SENSOR_STATUS_UNRELIABLE -> 
                Log.w("LevelSensor", "Sensor accuracy unreliable")
            SensorManager.SENSOR_STATUS_ACCURACY_LOW -> 
                Log.i("LevelSensor", "Sensor accuracy low")
            SensorManager.SENSOR_STATUS_ACCURACY_MEDIUM -> 
                Log.i("LevelSensor", "Sensor accuracy medium")
            SensorManager.SENSOR_STATUS_ACCURACY_HIGH -> 
                Log.i("LevelSensor", "Sensor accuracy high")
        }
    }
}

