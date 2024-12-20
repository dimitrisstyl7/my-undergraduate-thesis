package gr.unipi.thesis.dimstyl.data.sources.remote.builders

import okhttp3.Interceptor
import okhttp3.OkHttpClient

class OkHttpClientBuilder {

    fun build(interceptors: List<Interceptor>): OkHttpClient {
        val clientBuilder = OkHttpClient().newBuilder()
        interceptors.forEach { clientBuilder.addInterceptor(it) }
        return clientBuilder.build()
    }

}