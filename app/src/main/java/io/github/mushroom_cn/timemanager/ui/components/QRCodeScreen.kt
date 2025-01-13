package io.github.mushroom_cn.timemanager.ui.components

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.google.zxing.common.BitMatrix
import com.google.zxing.qrcode.QRCodeWriter


@Composable
fun QRCodeScreen(qrCodeText:String) {
    val qrCodeBitmap by remember { mutableStateOf<Bitmap?>(generateQRCode(qrCodeText, 300, 300)) }

    Column(
        modifier = Modifier
            .wrapContentWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        qrCodeBitmap?.let {
            Image(
                bitmap = it.asImageBitmap(),
                contentDescription = "QR Code",
                modifier = Modifier.size(200.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

    }
}

fun generateQRCode(text: String, width: Int, height: Int): Bitmap? {
    val qrCodeWriter = QRCodeWriter()
    return try {
        val bitMatrix: BitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height)
        val width = bitMatrix.width
        val height = bitMatrix.height
        val bmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        for (x in 0 until width) {
            for (y in 0 until height) {
                bmp.setPixel(x, y, if (bitMatrix[x, y]) 0xFF000000.toInt() else 0xFFFFFFFF.toInt())
            }
        }
        bmp
    } catch (e: WriterException) {
        e.printStackTrace()
        null
    }
}

@Composable
fun QRCodeScreenDemo(){
    QRCodeScreen(qrCodeText =  """
        {
          "ip": "192.168.1.1",
          "port": 8080,
          "data": {
            "id": 123,
            "today": "2025-01-12T07:02:08.296Z",
            "durationOfEachViewing": 30,
            "maxViewsCount": 5,
            "remainViewCount": 4
          }
        }
    """.trimIndent())
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun QRCodeScreenPreview(){
    QRCodeScreenDemo()
}