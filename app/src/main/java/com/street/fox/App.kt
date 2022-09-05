package com.street.fox

import android.app.Application
import com.street.fox.utils.createEncryptedSharedPreferences
import com.street.fox.utils.createHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.dsl.module
import org.koin.ksp.generated.module

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(
                module {
                    single {
                        createHttpClient({
                            get<TokenRepository>().getToken()
                        }, {
                            get<TokenRepository>().setToken(it)
                        })
                    }
                    single { createEncryptedSharedPreferences(this@App) }
                },
                AppModule().module
            )
        }
    }
}
