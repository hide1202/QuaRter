package io.viewpoint.quarter

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.SystemClock
import android.text.method.LinkMovementMethod
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import io.viewpoint.quarter.databinding.ActivityMainBinding
import io.viewpoint.quarter.extensions.clickableIfWebUrl
import io.viewpoint.quarter.extensions.throttleFirst
import io.viewpoint.quarter.util.Cameras
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.concurrent.Executors
import kotlin.time.seconds

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val qrAnalyzer = QRAnalyzer(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        if (allPermissionsGranted()) {
            startCamera()
        } else {
            ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS)
        }

        binding.result.movementMethod = LinkMovementMethod()

        lifecycleScope.launch {
            var lastVisibleResultTime = 0L
            qrAnalyzer.receiveAsFlow()
                .throttleFirst(100L)
                .collect {
                    val currentTime = SystemClock.uptimeMillis()
                    if (it != null) {
                        lastVisibleResultTime = currentTime
                    } else if (currentTime - lastVisibleResultTime < DURATION_VISIBLE_QR_RESULT.inMicroseconds) {
                        return@collect
                    }
                    binding.resultText = it?.text?.clickableIfWebUrl()
                }
        }
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted()) {
                startCamera()
            } else {
                Toast.makeText(
                    this,
                    getString(R.string.permission_denied_message),
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            }
        }
    }

    private fun startCamera() {
        lifecycleScope.launch {
            val preview = Preview.Builder()
                .build()
                .apply {
                    setSurfaceProvider(binding.preview.surfaceProvider)
                }

            val analysis = ImageAnalysis.Builder()
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .build()
                .apply {
                    setAnalyzer(Executors.newSingleThreadExecutor(), qrAnalyzer)
                }

            Cameras.bindCamera(this@MainActivity, preview, analysis)
        }
    }

    companion object {
        private val DURATION_VISIBLE_QR_RESULT = 1.seconds

        private const val REQUEST_CODE_PERMISSIONS = 0x10
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
    }
}