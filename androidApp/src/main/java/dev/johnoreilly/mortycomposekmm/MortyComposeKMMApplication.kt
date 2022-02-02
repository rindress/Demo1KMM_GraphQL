package dev.johnoreilly.mortycomposekmm

import android.app.Application
import com.surrus.common.di.initKoin
import dev.johnoreilly.mortycomposekmm.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.logger.Level

class MortyComposeKMMApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        initKoin {
            // workaround for https://github.com/InsertKoinIO/koin/issues/1188
            androidLogger(if (BuildConfig.DEBUG) Level.ERROR else Level.NONE)
            androidContext(this@MortyComposeKMMApplication)
            modules(appModule)
        }
    }

}