package gr.unipi.thesis.dimstyl.di

import android.app.DownloadManager
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import gr.unipi.thesis.dimstyl.data.sources.local.JwtTokenManager
import gr.unipi.thesis.dimstyl.data.sources.remote.builders.OkHttpClientBuilder
import gr.unipi.thesis.dimstyl.data.sources.remote.builders.RetrofitBuilder
import gr.unipi.thesis.dimstyl.data.sources.remote.interceptors.AccessTokenInterceptor
import gr.unipi.thesis.dimstyl.data.sources.remote.interceptors.LoggingInterceptor
import gr.unipi.thesis.dimstyl.data.sources.remote.services.AnnouncementApiService
import gr.unipi.thesis.dimstyl.data.sources.remote.services.AppointmentApiService
import gr.unipi.thesis.dimstyl.data.sources.remote.services.ArticleApiService
import gr.unipi.thesis.dimstyl.data.sources.remote.services.AuthApiService
import gr.unipi.thesis.dimstyl.data.sources.remote.services.DietPlanApiService
import gr.unipi.thesis.dimstyl.data.sources.remote.services.HomeApiService
import gr.unipi.thesis.dimstyl.data.sources.remote.services.ProfileApiService
import gr.unipi.thesis.dimstyl.domain.repositories.AnnouncementRepository
import gr.unipi.thesis.dimstyl.domain.repositories.AppointmentRepository
import gr.unipi.thesis.dimstyl.domain.repositories.ArticleRepository
import gr.unipi.thesis.dimstyl.domain.repositories.AuthRepository
import gr.unipi.thesis.dimstyl.domain.repositories.DietPlanRepository
import gr.unipi.thesis.dimstyl.domain.repositories.HomeRepository
import gr.unipi.thesis.dimstyl.domain.repositories.ProfileRepository
import gr.unipi.thesis.dimstyl.domain.usecases.CancelAppointmentUseCase
import gr.unipi.thesis.dimstyl.domain.usecases.CheckTokenValidityUseCase
import gr.unipi.thesis.dimstyl.domain.usecases.CreateAppointmentUseCase
import gr.unipi.thesis.dimstyl.domain.usecases.DownloadDietPlanUseCase
import gr.unipi.thesis.dimstyl.domain.usecases.FetchAnnouncementsUseCase
import gr.unipi.thesis.dimstyl.domain.usecases.FetchAppointmentsUseCase
import gr.unipi.thesis.dimstyl.domain.usecases.FetchArticlesUseCase
import gr.unipi.thesis.dimstyl.domain.usecases.FetchDietPlansUseCase
import gr.unipi.thesis.dimstyl.domain.usecases.FetchHomeDataUseCase
import gr.unipi.thesis.dimstyl.domain.usecases.FetchProfileDataUseCase
import gr.unipi.thesis.dimstyl.domain.usecases.LoginUseCase
import gr.unipi.thesis.dimstyl.domain.usecases.LogoutUseCase
import gr.unipi.thesis.dimstyl.domain.usecases.UpdateProfileDataUseCase

interface AppModule {

    val downloadManager: DownloadManager

    val okHttpClientBuilder: OkHttpClientBuilder
    val retrofitBuilder: RetrofitBuilder

    val dataStore: DataStore<Preferences>
    val jwtTokenManager: JwtTokenManager

    val loggingInterceptor: LoggingInterceptor
    val accessTokenInterceptor: AccessTokenInterceptor

    val authApiService: AuthApiService
    val homeApiService: HomeApiService
    val appointmentApiService: AppointmentApiService
    val articleApiService: ArticleApiService
    val dietPlanApiService: DietPlanApiService
    val announcementApiService: AnnouncementApiService
    val profileApiService: ProfileApiService

    val authRepository: AuthRepository
    val homeRepository: HomeRepository
    val appointmentRepository: AppointmentRepository
    val articleRepository: ArticleRepository
    val dietPlanRepository: DietPlanRepository
    val announcementRepository: AnnouncementRepository
    val profileRepository: ProfileRepository

    val loginUseCase: LoginUseCase
    val checkTokenValidityUseCase: CheckTokenValidityUseCase
    val logoutUseCase: LogoutUseCase
    val fetchHomeDataUseCase: FetchHomeDataUseCase
    val fetchAppointmentsUseCase: FetchAppointmentsUseCase
    val createAppointmentUseCase: CreateAppointmentUseCase
    val cancelAppointmentUseCase: CancelAppointmentUseCase
    val fetchArticlesUseCase: FetchArticlesUseCase
    val fetchDietPlansUseCase: FetchDietPlansUseCase
    val downloadDietPlanUseCase: DownloadDietPlanUseCase
    val fetchAnnouncementsUseCase: FetchAnnouncementsUseCase
    val fetchProfileDataUseCase: FetchProfileDataUseCase
    val updateProfileDataUseCase: UpdateProfileDataUseCase

}