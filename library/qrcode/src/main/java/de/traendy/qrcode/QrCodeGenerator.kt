package de.traendy.qrcode

import android.graphics.Bitmap
import com.google.zxing.BarcodeFormat
import com.journeyapps.barcodescanner.BarcodeEncoder

class QrCodeGenerator {
    fun createQrCodeBitMap(content: String, size:Int): Bitmap {
        val barcodeEncoder = BarcodeEncoder()
        return barcodeEncoder.encodeBitmap(content, BarcodeFormat.QR_CODE, size, size)
    }
}