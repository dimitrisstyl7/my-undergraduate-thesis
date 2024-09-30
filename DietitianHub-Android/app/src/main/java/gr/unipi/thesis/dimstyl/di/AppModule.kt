package gr.unipi.thesis.dimstyl.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import gr.unipi.thesis.dimstyl.data.sources.local.JwtTokenManager
import gr.unipi.thesis.dimstyl.data.sources.remote.AuthApiService
import gr.unipi.thesis.dimstyl.data.sources.remote.OkHttpClientBuilder
import gr.unipi.thesis.dimstyl.data.sources.remote.RetrofitBuilder
import gr.unipi.thesis.dimstyl.data.sources.remote.interceptors.AccessTokenInterceptor
import gr.unipi.thesis.dimstyl.data.sources.remote.interceptors.LoggingInterceptor
import gr.unipi.thesis.dimstyl.domain.repositories.AuthRepository
import gr.unipi.thesis.dimstyl.domain.usecases.CheckTokenValidityUseCase
import gr.unipi.thesis.dimstyl.domain.usecases.LoginUseCase

interface AppModule {

    val okHttpClientBuilder:OkHttpClientBuilder
    val retrofitBuilder: RetrofitBuilder
    val dataStore: DataStore<Preferences>
    val jwtTokenManager: JwtTokenManager
    val loggingInterceptor: LoggingInterceptor
    val accessTokenInterceptor: AccessTokenInterceptor
    val authApiService: AuthApiService
    val authRepository: AuthRepository
    val loginUseCase: LoginUseCase
    val checkTokenValidityUseCase: CheckTokenValidityUseCase

}