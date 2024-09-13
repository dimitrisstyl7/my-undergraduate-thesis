package gr.unipi.thesis.dimstyl

import android.app.Application
import gr.unipi.thesis.dimstyl.di.AppModule
import gr.unipi.thesis.dimstyl.di.AppModuleImpl

class App : Application() {

    companion object {
        lateinit var appModule: AppModule
    }

    override fun onCreate() {
        super.onCreate()
        appModule = AppModuleImpl(this)
    }

}