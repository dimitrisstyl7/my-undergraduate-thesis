package gr.unipi.thesis.dimstyl.data.sources.remote.interceptors

import gr.unipi.thesis.dimstyl.data.sources.local.JwtTokenManager
import gr.unipi.thesis.dimstyl.utils.Constants.Authorization.HEADER_AUTHORIZATION
import gr.unipi.thesis.dimstyl.utils.Constants.Authorization.TOKEN_TYPE
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

class AccessTokenInterceptor(private val jwtTokenManager: JwtTokenManager) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val token = runBlocking { jwtTokenManager.getAccessToken() }
        val request = chain.request().newBuilder()
        request.addHeader(HEADER_AUTHORIZATION, "$TOKEN_TYPE $token")
        return chain.proceed(request.build())
    }

}