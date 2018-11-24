package ar.com.instafood.fragments

import android.graphics.Bitmap
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import ar.com.instafood.activities.R
import ar.com.instafood.adapters.MenuTabsAdapter
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import com.google.zxing.common.BitMatrix
import com.journeyapps.barcodescanner.BarcodeEncoder

/**
 * Created by mnavarro on 23/11/2018.
 */
class ShowQRFragment : Fragment() {

    private var showQRFragment = ShowQRFragment()
    internal var bitmap: Bitmap? = null
    private var iv: ImageView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        convertToImage("Pedido");
        goToOrder()

        return view
    }

    private fun convertToImage(text: String){
        bitmap = textToImageEncode(text)
        iv!!.setImageBitmap(bitmap)

    }

    @Throws(WriterException::class)
    private fun textToImageEncode(textToEncode: String): Bitmap? {
        var bitMatrix: BitMatrix
        var multiFormatWriter = MultiFormatWriter()
        var QRcodeWidth = 500
        try {
            bitMatrix = multiFormatWriter.encode(
                    textToEncode,
                    BarcodeFormat.QR_CODE,
                    QRcodeWidth, QRcodeWidth,null)

        } catch (Illegalargumentexception: IllegalArgumentException) {

            return null
        }

        var bitMatrixWidth = bitMatrix.getWidth()

        var bitMatrixHeight = bitMatrix.getHeight()

        var pixels = IntArray(bitMatrixWidth * bitMatrixHeight)

        for (y in 0 until bitMatrixHeight) {
            var offset = y * bitMatrixWidth

            for (x in 0 until bitMatrixWidth) {

                pixels[offset + x] = if (bitMatrix.get(x, y))
                    resources.getColor(R.color.black)
                else
                    resources.getColor(R.color.white)
            }
        }

        var bitmap = Bitmap.createBitmap(bitMatrixWidth, bitMatrixHeight, Bitmap.Config.ARGB_4444)

        bitmap.setPixels(pixels, 0, 500, 0, 0, bitMatrixWidth, bitMatrixHeight)
        return bitmap
    }

    private fun goToOrder(){
        val btn_ready = view?.findViewById<Button>(R.id.btn)
        btn_ready?.setOnClickListener { _ ->
            val transaction = activity!!.supportFragmentManager.beginTransaction()
            transaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
            var orderFragment = OrderFragment()
            transaction.replace(R.id.fragment_container, orderFragment).addToBackStack(null)
            transaction.commitAllowingStateLoss()
        }

    }


}