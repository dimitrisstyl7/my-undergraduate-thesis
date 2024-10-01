package gr.unipi.thesis.dimstyl.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import gr.unipi.thesis.dimstyl.data.repositories.AppointmentRepositoryImpl
import gr.unipi.thesis.dimstyl.data.repositories.AuthRepositoryImpl
import gr.unipi.thesis.dimstyl.data.repositories.HomeRepositoryImpl
import gr.unipi.thesis.dimstyl.data.sources.local.JwtTokenDataStore
import gr.unipi.thesis.dimstyl.data.sources.local.JwtTokenManager
import gr.unipi.thesis.dimstyl.data.sources.remote.builders.OkHttpClientBuilder
import gr.unipi.thesis.dimstyl.data.sources.remote.builders.RetrofitBuilder
import gr.unipi.thesis.dimstyl.data.sources.remote.interceptors.AccessTokenInterceptor
import gr.unipi.thesis.dimstyl.data.sources.remote.interceptors.LoggingInterceptor
import gr.unipi.thesis.dimstyl.data.sources.remote.services.AppointmentApiService
import gr.unipi.thesis.dimstyl.data.sources.remote.services.AuthApiService
import gr.unipi.thesis.dimstyl.data.sources.remote.services.HomeApiService
import gr.unipi.thesis.dimstyl.domain.repositories.AppointmentRepository
import gr.unipi.thesis.dimstyl.domain.repositories.AuthRepository
import gr.unipi.thesis.dimstyl.domain.repositories.HomeRepository
import gr.unipi.thesis.dimstyl.domain.usecases.CancelAppointmentUseCase
import gr.unipi.thesis.dimstyl.domain.usecases.CheckTokenValidityUseCase
import gr.unipi.thesis.dimstyl.domain.usecases.CreateAppointmentUseCase
import gr.unipi.thesis.dimstyl.domain.usecases.FetchAppointmentsUseCase
import gr.unipi.thesis.dimstyl.domain.usecases.FetchHomeDataUseCase
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
    override val homeApiService: HomeApiService by lazy {
        retrofitBuilder.build(
            okHttpClientBuilder.build(
                listOf(loggingInterceptor, accessTokenInterceptor)
            )
        ).create(HomeApiService::class.java)
    }
    override val appointmentApiService: AppointmentApiService by lazy {
        retrofitBuilder.build(
            okHttpClientBuilder.build(
                listOf(loggingInterceptor, accessTokenInterceptor)
            )
        ).create(AppointmentApiService::class.java)
    }

    override val authRepository: AuthRepository by lazy {
        AuthRepositoryImpl(authApiService, jwtTokenManager)
    }
    override val homeRepository: HomeRepository by lazy {
        HomeRepositoryImpl(homeApiService)
    }
    override val appointmentRepository: AppointmentRepository by lazy {
        AppointmentRepositoryImpl(appointmentApiService)
    }

    override val loginUseCase: LoginUseCase by lazy {
        LoginUseCase(authRepository)
    }
    override val checkTokenValidityUseCase: CheckTokenValidityUseCase by lazy {
        CheckTokenValidityUseCase(authRepository)
    }
    override val fetchHomeDataUseCase: FetchHomeDataUseCase by lazy {
        FetchHomeDataUseCase(homeRepository)
    }
    override val fetchAppointmentsUseCase: FetchAppointmentsUseCase by lazy {
        FetchAppointmentsUseCase(appointmentRepository)
    }
    override val createAppointmentUseCase: CreateAppointmentUseCase by lazy {
        CreateAppointmentUseCase(appointmentRepository)
    }
    override val cancelAppointmentUseCase: CancelAppointmentUseCase by lazy {
        CancelAppointmentUseCase(appointmentRepository)
    }

}