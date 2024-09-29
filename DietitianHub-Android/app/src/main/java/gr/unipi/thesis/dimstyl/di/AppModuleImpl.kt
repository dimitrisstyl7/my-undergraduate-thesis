package gr.unipi.thesis.dimstyl.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import gr.unipi.thesis.dimstyl.data.repository.AuthRepositoryImpl
import gr.unipi.thesis.dimstyl.data.source.local.JwtTokenDataStore
import gr.unipi.thesis.dimstyl.data.source.local.JwtTokenManager
import gr.unipi.thesis.dimstyl.data.source.remote.AuthApiService
import gr.unipi.thesis.dimstyl.data.source.remote.OkHttpClientBuilder
import gr.unipi.thesis.dimstyl.data.source.remote.RetrofitBuilder
import gr.unipi.thesis.dimstyl.data.source.remote.interceptor.LoggingInterceptor
import gr.unipi.thesis.dimstyl.domain.repository.AuthRepository
import gr.unipi.thesis.dimstyl.domain.usecase.LoginUseCase

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "jwt_tokens")

class AppModuleImpl(private val appContext: Context) : AppModule {

    override val okHttpClientBuilder: OkHttpClientBuilder by lazy {
        OkHttpClientBuilder()
    }
    override val retrofitBuilder: RetrofitBuilder by lazy {
        RetrofitBuilder()
    }
    override val dataStore: DataStore<Preferences> by lazy {
        appContext.dataStore
    }
    override val jwtTokenManager: JwtTokenManager by lazy {
        JwtTokenDataStore(dataStore)
    }
    override val loggingInterceptor: LoggingInterceptor by lazy {
        LoggingInterceptor()
    }
    override val authApiService: AuthApiService by lazy {
        retrofitBuilder.build(
            okHttpClientBuilder.build(
                listOf(loggingInterceptor)
            )
        ).create(AuthApiService::class.java)
    }
    override val authRepository: AuthRepository by lazy {
        AuthRepositoryImpl(authApiService, jwtTokenManager)
    }
    override val loginUseCase: LoginUseCase by lazy {
        LoginUseCase(authRepository)
    }

}