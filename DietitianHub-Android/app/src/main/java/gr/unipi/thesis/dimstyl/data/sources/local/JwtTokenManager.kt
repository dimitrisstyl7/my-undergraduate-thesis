package gr.unipi.thesis.dimstyl.data.sources.local

interface JwtTokenManager {

    suspend fun saveAccessToken(token: String)
    suspend fun getAccessToken(): String?
    suspend fun clearAccessToken()

}