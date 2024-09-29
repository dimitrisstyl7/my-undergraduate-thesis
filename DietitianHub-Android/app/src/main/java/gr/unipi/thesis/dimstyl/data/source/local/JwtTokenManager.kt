package gr.unipi.thesis.dimstyl.data.source.local

interface JwtTokenManager {

    suspend fun saveAccessToken(token: String)
    suspend fun getAccessToken(): String?
    suspend fun clearAccessToken()

}