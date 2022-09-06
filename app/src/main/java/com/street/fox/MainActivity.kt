package com.street.fox

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import com.street.fox.repository.Token
import com.street.fox.repository.TokenRepository
import com.street.fox.ui.theme.FoxTheme
import com.street.fox.usecase.MainViewData
import com.street.fox.viewmodel.MainViewModel
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : ComponentActivity() {
    companion object {
        private const val RESULT_QUERY_PARAM = "result"
        private const val ACCESS_TOKEN_QUERY_PARAM = "access_token"
        private const val REFRESH_TOKEN_QUERY_PARAM = "refresh_token"
    }

    private val tokenRepository: TokenRepository by inject()
    private val mainViewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FoxTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                    val currentContent by mainViewModel.content.collectAsState(initial = MainViewData.Content.LoginScreen)
                    when (val content = currentContent) {
                        MainViewData.Content.LoginScreen -> Login(::login)
                        is MainViewData.Content.MainScreen -> Main(content)
                    }
                }
            }
        }
    }

    private fun login() {
        lifecycleScope.launch {
            val token: Token = tokenRepository.token.firstOrNull() ?: Token.default
            if (token is Token.NotSet) {
                Intent(Intent.ACTION_VIEW).let { intent ->
                    intent.data = Uri.parse("${Const.BASE_API_URL}/login")
                    startActivity(intent)
                }
            }
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

        val data = intent?.data ?: return

        when (val host = data.host) {
            "login" -> {
                val result = data.getQueryParameter(RESULT_QUERY_PARAM)
                val accessToken = data.getQueryParameter(ACCESS_TOKEN_QUERY_PARAM)
                val refreshToken = data.getQueryParameter(REFRESH_TOKEN_QUERY_PARAM)

                if (result == "success" && accessToken != null && refreshToken != null) {
                    tokenRepository.setToken(Token.Value(accessToken, refreshToken))
                }
            }
            else -> Log.w("onNewIntent", "Unhandled host: $host")
        }
    }
}

@Composable
fun Main(content: MainViewData.Content.MainScreen) {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = content.name)
    }
}

@Composable
fun Login(loginAction: () -> Unit) {
    Box(
        Modifier
            .fillMaxSize()
            .background(Color(0xFFFF00))
    ) {
        Column(
            Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Image(
                painter = painterResource(id = R.drawable.img_tails),
                contentDescription = null,
                modifier = Modifier.fillMaxWidth(0.5f)
            )

            Button(
                onClick = loginAction
            ) {
                Text(text = "Login with ")
                Image(
                    painter = painterResource(id = R.drawable.ic_strava_logo_word_white),
                    contentDescription = null,
                    modifier = Modifier.height(10.dp),
                    contentScale = ContentScale.FillHeight
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginPreview() {
    FoxTheme {
        Login {}
    }
}

@Preview(showBackground = true)
@Composable
fun MainPreview() {
    FoxTheme {
        Main(MainViewData.Content.MainScreen("Terry Fox"))
    }
}
