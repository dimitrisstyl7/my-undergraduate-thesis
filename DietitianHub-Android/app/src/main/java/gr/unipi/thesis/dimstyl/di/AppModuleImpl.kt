package gr.unipi.thesis.dimstyl.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import gr.unipi.thesis.dimstyl.data.repositories.AuthRepositoryImpl
import gr.unipi.thesis.dimstyl.data.sources.local.JwtTokenDataStore
import gr.unipi.thesis.dimstyl.data.sources.local.JwtTokenManager
import gr.unipi.thesis.dimstyl.data.sources.remote.AuthApiService
import gr.unipi.thesis.dimstyl.data.sources.remote.OkHttpClientBuilder
import gr.unipi.thesis.dimstyl.data.sources.remote.RetrofitBuilder
import gr.unipi.thesis.dimstyl.data.sources.remote.interceptors.AccessTokenInterceptor
import gr.unipi.thesis.dimstyl.data.sources.remote.interceptors.LoggingInterceptor
import gr.unipi.thesis.dimstyl.domain.repositories.AuthRepository
import gr.unipi.thesis.dimstyl.domain.usecases.CheckTokenValidityUseCase
import gr.unipi.thesis.dimstyl.domain.usecases.LoginUseCase

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
    override val accessTokenInterceptor: AccessTokenInterceptor by lazy {
        AccessTokenInterceptor(jwtTokenManager)
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
    override val checkTokenValidityUseCase: CheckTokenValidityUseCase by lazy {
        CheckTokenValidityUseCase(authRepository)
    }

}