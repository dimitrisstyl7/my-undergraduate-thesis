package gr.unipi.thesis.dimstyl.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import gr.unipi.thesis.dimstyl.data.source.local.JwtTokenManager
import gr.unipi.thesis.dimstyl.data.source.remote.AuthApiService
import gr.unipi.thesis.dimstyl.data.source.remote.OkHttpClientBuilder
import gr.unipi.thesis.dimstyl.data.source.remote.RetrofitBuilder
import gr.unipi.thesis.dimstyl.data.source.remote.interceptor.LoggingInterceptor
import gr.unipi.thesis.dimstyl.domain.repository.AuthRepository
import gr.unipi.thesis.dimstyl.domain.usecase.LoginUseCase

interface AppModule {

    val okHttpClientBuilder:OkHttpClientBuilder
    val retrofitBuilder: RetrofitBuilder
    val dataStore: DataStore<Preferences>
    val jwtTokenManager: JwtTokenManager
    val loggingInterceptor: LoggingInterceptor
    val authApiService: AuthApiService
    val authRepository: AuthRepository
    val loginUseCase: LoginUseCase

}