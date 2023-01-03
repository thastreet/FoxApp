package com.street.fox

import android.app.Application
import com.street.fox.repository.TokenRepository
import com.street.fox.utils.createEncryptedSharedPreferences
import com.street.fox.utils.createHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.qualifier.named
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
                    single(qualifier = named("strava")) {
                        createHttpClient(
                            "strava", {
                                get<TokenRepository>().getStravaToken()
                            },
                            {
                                get<TokenRepository>().setStravaToken(it)
                            }
                        )
                    }
                    single(qualifier = named("spotify")) {
                        createHttpClient(
                            "spotify", {
                                get<TokenRepository>().getSpotifyToken()
                            },
                            {
                                get<TokenRepository>().setSpotifyToken(it)
                            }
                        )
                    }
                    single { createEncryptedSharedPreferences(this@App) }
                },
                AppModule().module
            )
        }
    }
}
