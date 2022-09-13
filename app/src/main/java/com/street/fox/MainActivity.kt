package com.street.fox

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.street.fox.composable.HomeScreen
import com.street.fox.composable.LoginScreen
import com.street.fox.extensions.popUpToStart
import com.street.fox.ui.theme.FoxTheme
import com.street.fox.viewmodel.HomeViewModelImpl
import com.street.fox.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {
    private val mainViewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            FoxTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                    val navController = rememberNavController()

                    LaunchedEffect(true) {
                        mainViewModel.navListener = object : MainViewModel.NavListener {
                            override fun navigateToHome() = navController.navigate(Screen.HOME.route) {
                                popUpToStart(navController)
                            }

                            override fun navigateToLogin() = navController.navigate(Screen.LOGIN.route) {
                                popUpToStart(navController)
                            }
                        }
                    }

                    NavHost(navController = navController, startDestination = mainViewModel.initialScreen.route) {
                        composable(Screen.LOGIN.route) {
                            LoginScreen(::launchLogin)
                        }
                        composable(Screen.HOME.route) {
                            HomeScreen(getViewModel<HomeViewModelImpl>())
                        }
                    }
                }
            }
        }
    }

    private fun launchLogin() {
        Intent(Intent.ACTION_VIEW).let { intent ->
            intent.data = Uri.parse("${Const.BASE_API_URL}/login")
            startActivity(intent)
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

        val data = intent?.data ?: return

        when (val host = data.host) {
            "login" -> mainViewModel.handleLoginUri(data)
            else -> Log.w("onNewIntent", "Unhandled host: $host")
        }
    }
}
