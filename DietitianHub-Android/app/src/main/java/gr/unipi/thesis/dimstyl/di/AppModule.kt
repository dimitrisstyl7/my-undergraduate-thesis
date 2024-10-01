package gr.unipi.thesis.dimstyl.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
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
import gr.unipi.thesis.dimstyl.domain.usecases.FetchHomeDataUseCase
import gr.unipi.thesis.dimstyl.domain.usecases.LoginUseCase

interface AppModule {

    val okHttpClientBuilder: OkHttpClientBuilder
    val retrofitBuilder: RetrofitBuilder

    val dataStore: DataStore<Preferences>
    val jwtTokenManager: JwtTokenManager

    val loggingInterceptor: LoggingInterceptor
    val accessTokenInterceptor: AccessTokenInterceptor

    val authApiService: AuthApiService
    val homeApiService: HomeApiService
    val appointmentApiService: AppointmentApiService

    val authRepository: AuthRepository
    val homeRepository: HomeRepository
    val appointmentRepository: AppointmentRepository

    val loginUseCase: LoginUseCase
    val checkTokenValidityUseCase: CheckTokenValidityUseCase
    val fetchHomeDataUseCase: FetchHomeDataUseCase
    val cancelAppointmentUseCase: CancelAppointmentUseCase

}