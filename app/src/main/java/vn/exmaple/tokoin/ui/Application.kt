package vn.exmaple.tokoin.ui

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import vn.exmaple.tokoin.module.appModule
import vn.exmaple.tokoin.module.viewModelModule

class Application : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@Application)
            modules(appModule, viewModelModule)
        }
    }
}