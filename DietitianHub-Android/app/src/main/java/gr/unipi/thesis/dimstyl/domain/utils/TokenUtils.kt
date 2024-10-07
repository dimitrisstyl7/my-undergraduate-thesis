package gr.unipi.thesis.dimstyl.domain.utils

import android.util.Base64
import org.json.JSONObject

object TokenUtils {

    fun isTokenExpired(token: String): Boolean {
        val parts = token.split(".")
        if (parts.size != 3) return false

        val payload = String(Base64.decode(parts[1], Base64.URL_SAFE))
        val exp = JSONObject(payload).getLong("exp")
        val currentTime = System.currentTimeMillis() / 1000

        return currentTime >= exp
    }

}