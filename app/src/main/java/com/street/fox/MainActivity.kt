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
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import com.street.fox.home.HomeViewModel
import com.street.fox.ui.theme.FoxTheme
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
    private val homeViewModel: HomeViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FoxTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                    Greeting(homeViewModel)
                }
            }
        }

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
fun Greeting(viewModel: HomeViewModel) {
    val username: String = viewModel.username.collectAsState("").value

    Text(text = username)
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    FoxTheme {
        //Greeting()
    }
}
