package gr.unipi.thesis.dimstyl.di

import android.app.DownloadManager
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import gr.unipi.thesis.dimstyl.data.repositories.AnnouncementRepositoryImpl
import gr.unipi.thesis.dimstyl.data.repositories.AppointmentRepositoryImpl
import gr.unipi.thesis.dimstyl.data.repositories.ArticleRepositoryImpl
import gr.unipi.thesis.dimstyl.data.repositories.AuthRepositoryImpl
import gr.unipi.thesis.dimstyl.data.repositories.DietPlanRepositoryImpl
import gr.unipi.thesis.dimstyl.data.repositories.HomeRepositoryImpl
import gr.unipi.thesis.dimstyl.data.repositories.ProfileRepositoryImpl
import gr.unipi.thesis.dimstyl.data.sources.local.JwtTokenDataStore
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

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "jwt_tokens")

class AppModuleImpl(private val appContext: Context) : AppModule {

    override val downloadManager: DownloadManager by lazy {
        appContext.getSystemService(DownloadManager::class.java)
    }

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
    override val articleApiService: ArticleApiService by lazy {
        retrofitBuilder.build(
            okHttpClientBuilder.build(
                listOf(loggingInterceptor, accessTokenInterceptor)
            )
        ).create(ArticleApiService::class.java)
    }
    override val dietPlanApiService: DietPlanApiService by lazy {
        retrofitBuilder.build(
            okHttpClientBuilder.build(
                listOf(loggingInterceptor, accessTokenInterceptor)
            )
        ).create(DietPlanApiService::class.java)
    }
    override val announcementApiService: AnnouncementApiService by lazy {
        retrofitBuilder.build(
            okHttpClientBuilder.build(
                listOf(loggingInterceptor, accessTokenInterceptor)
            )
        ).create(AnnouncementApiService::class.java)
    }
    override val profileApiService: ProfileApiService by lazy {
        retrofitBuilder.build(
            okHttpClientBuilder.build(
                listOf(loggingInterceptor, accessTokenInterceptor)
            )
        ).create(ProfileApiService::class.java)
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
    override val articleRepository: ArticleRepository by lazy {
        ArticleRepositoryImpl(articleApiService)
    }
    override val dietPlanRepository: DietPlanRepository by lazy {
        DietPlanRepositoryImpl(dietPlanApiService, downloadManager, jwtTokenManager)
    }
    override val announcementRepository: AnnouncementRepository by lazy {
        AnnouncementRepositoryImpl(announcementApiService)
    }
    override val profileRepository: ProfileRepository by lazy {
        ProfileRepositoryImpl(profileApiService)
    }

    override val loginUseCase: LoginUseCase by lazy {
        LoginUseCase(authRepository)
    }
    override val checkTokenValidityUseCase: CheckTokenValidityUseCase by lazy {
        CheckTokenValidityUseCase(authRepository)
    }
    override val logoutUseCase: LogoutUseCase by lazy {
        LogoutUseCase(authRepository)
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
    override val fetchArticlesUseCase: FetchArticlesUseCase by lazy {
        FetchArticlesUseCase(articleRepository)
    }
    override val fetchDietPlansUseCase: FetchDietPlansUseCase by lazy {
        FetchDietPlansUseCase(dietPlanRepository)
    }
    override val downloadDietPlanUseCase: DownloadDietPlanUseCase by lazy {
        DownloadDietPlanUseCase(dietPlanRepository)
    }
    override val fetchAnnouncementsUseCase: FetchAnnouncementsUseCase by lazy {
        FetchAnnouncementsUseCase(announcementRepository)
    }
    override val fetchProfileDataUseCase: FetchProfileDataUseCase by lazy {
        FetchProfileDataUseCase(profileRepository)
    }
    override val updateProfileDataUseCase: UpdateProfileDataUseCase by lazy {
        UpdateProfileDataUseCase(profileRepository)
    }

}