package io.viewpoint.quarter

import android.annotation.SuppressLint
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.zxing.BinaryBitmap
import com.google.zxing.PlanarYUVLuminanceSource
import com.google.zxing.Result
import com.google.zxing.common.HybridBinarizer
import com.google.zxing.qrcode.QRCodeReader


class QRAnalyzer(
    private val listener: (Result?) -> Unit
) : ImageAnalysis.Analyzer {
    private val reader: QRCodeReader = QRCodeReader()

    @SuppressLint("UnsafeExperimentalUsageError")
    override fun analyze(image: ImageProxy) {
        val rawImage = image.image ?: return

        val buffer = rawImage.planes.getOrNull(0)?.buffer ?: return
        val capacity = buffer.capacity()
        val bytes = ByteArray(capacity)
        buffer.get(bytes)
        val source = PlanarYUVLuminanceSource(
            bytes,
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
            listener(reader.decode(bitmap))
        } catch (t: Throwable) {
            listener(null)
        } finally {
            image.close()
        }
    }
}