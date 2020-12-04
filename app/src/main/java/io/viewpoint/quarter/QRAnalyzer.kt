package io.viewpoint.quarter

import android.annotation.SuppressLint
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.google.zxing.BinaryBitmap
import com.google.zxing.PlanarYUVLuminanceSource
import com.google.zxing.Result
import com.google.zxing.common.HybridBinarizer
import com.google.zxing.qrcode.QRCodeReader
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch


class QRAnalyzer(
    private val lifecycleOwner: LifecycleOwner
) : ImageAnalysis.Analyzer {
    private val reader: QRCodeReader = QRCodeReader()

    private var reusedBytes: ByteArray = ByteArray(0)

    private val resultChannel = Channel<Result?>()

    @SuppressLint("UnsafeExperimentalUsageError")
    override fun analyze(image: ImageProxy) {
        val rawImage = image.image ?: return

        val buffer = rawImage.planes.getOrNull(0)?.buffer ?: return
        val capacity = buffer.capacity()
        if (reusedBytes.size < capacity) {
            reusedBytes = ByteArray(capacity)
        }
        buffer.get(reusedBytes)
        val source = PlanarYUVLuminanceSource(
            reusedBytes,
            rawImage.width,
            rawImage.height,
            0,
            0,
            rawImage.width,
            rawImage.height,
            false
        )
        val bitmap = BinaryBitmap(HybridBinarizer(source))

        try {
            sendResult(reader.decode(bitmap))
        } catch (t: Throwable) {
            sendResult(null)
        } finally {
            image.close()
        }
    }

    private fun sendResult(result: Result?) {
        lifecycleOwner.lifecycleScope.launch {
            resultChannel.send(result)
        }
    }

    fun receiveAsFlow(): Flow<Result?> = resultChannel.receiveAsFlow()
}