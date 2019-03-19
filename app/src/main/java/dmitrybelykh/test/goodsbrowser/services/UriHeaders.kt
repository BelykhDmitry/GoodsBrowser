package dmitrybelykh.test.goodsbrowser.services

import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import okhttp3.Credentials

object UriHeaders {
    var header: LazyHeaders? = null

    fun urlWithHeaders(url: String?): GlideUrl {
        return GlideUrl(url, header)
    }

    fun setAuthToken(login: String, pass: String) {
        val authToken = Credentials.basic(login, pass)
        header = LazyHeaders.Builder().addHeader("Authorization", authToken).build();
    }
}