package cn.shui.movieguide.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.net.URL

/**
 * Created by shui on 7/6/21
 */
object HttpUtil {
    fun getBitmapFromUrl(src: String): Bitmap? {
        return try {
            val url = URL(src)
            val input = url.openStream()
            BitmapFactory.decodeStream(input)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}