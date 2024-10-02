package gr.unipi.thesis.dimstyl.data.sources.remote.builders

import gr.unipi.thesis.dimstyl.utils.Constants.BaseUrl.LOCALHOST
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitBuilder {

    fun build(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(LOCALHOST)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

}