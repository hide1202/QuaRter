package io.viewpoint.quarter

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.Color.BLACK
import android.graphics.Color.WHITE
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.google.zxing.*
import com.google.zxing.common.BitMatrix
import com.google.zxing.common.HybridBinarizer
import com.google.zxing.qrcode.QRCodeReader
import com.google.zxing.qrcode.QRCodeWriter
import io.viewpoint.quarter.extensions.clickableIfWebUrl
import io.viewpoint.quarter.model.UrlMetadata
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch


data class QRResult(
    val qrCode: Bitmap,
    val contents: CharSequence
) {
    suspend fun getUrlMetadata(): UrlMetadata? = UrlMetadata.from(contents)
}

class QRAnalyzer(
    private val lifecycleOwner: LifecycleOwner
) : ImageAnalysis.Analyzer {
    private val reader: QRCodeReader = QRCodeReader()

    private var reusedBytes: ByteArray = ByteArray(0)

    private val resultChannel = Channel<QRResult?>()

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
            val result = reader.decode(bitmap)
            sendResult(result.takeIf { it.barcodeFormat == BarcodeFormat.QR_CODE })
        } catch (t: Throwable) {
            sendResult(null)
        } finally {
            image.close()
        }
    }

    private fun sendResult(result: Result?) {
        lifecycleOwner.lifecycleScope.launch {
            resultChannel.send(generateQRResult(result))
        }
    }

    private fun generateQRResult(result: Result?): QRResult? = result?.let {
        val qrCode = encodeAsBitmap(it) ?: return@let null
        QRResult(
            qrCode = qrCode,
            contents = it.text.clickableIfWebUrl()
        )
    }

    private fun encodeAsBitmap(result: Result): Bitmap? = try {
        val bitMatrix: BitMatrix = QRCodeWriter()
            .encode(
                result.text,
                BarcodeFormat.QR_CODE,
                DEFAULT_QR_BITMAP_WIDTH,
                DEFAULT_QR_BITMAP_HEIGHT
            )

        val w = bitMatrix.width
        val h = bitMatrix.height
        val pixels = IntArray(w * h)
        for (y in 0 until h) {
            val offset = y * w
            for (x in 0 until w) {
                pixels[offset + x] = if (bitMatrix[x, y]) BLACK else WHITE
            }
        }
        val bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        bitmap.setPixels(pixels, 0, w, 0, 0, w, h)
        bitmap
    } catch (e: WriterException) {
        null
    }

    fun receiveAsFlow(): Flow<QRResult?> = resultChannel.receiveAsFlow()

    companion object {
        private const val DEFAULT_QR_BITMAP_WIDTH = 360
        private const val DEFAULT_QR_BITMAP_HEIGHT = 360
    }
}