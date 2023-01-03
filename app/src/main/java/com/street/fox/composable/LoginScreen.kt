package com.street.fox.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.street.fox.R
import com.street.fox.ui.theme.FoxTheme

@Composable
fun LoginScreen(stravaLoginAction: () -> Unit, spotifyLoginAction: () -> Unit) {
    Box(
        Modifier.fillMaxSize()
    ) {
        Column(
            Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)
        ) {
            Button(
                onClick = stravaLoginAction
            ) {
                Text(text = "Login with ")
                Image(
                    painter = painterResource(id = R.drawable.ic_strava_logo_word_white),
                    contentDescription = null,
                    modifier = Modifier.height(10.dp),
                    contentScale = ContentScale.FillHeight
                )
            }
            Image(
                painter = painterResource(id = R.drawable.ic_spotify_login),
                contentDescription = null,
                modifier = Modifier
                    .height(42.dp)
                    .clickable {
                        spotifyLoginAction()
                    },
                contentScale = ContentScale.FillHeight
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    FoxTheme {
        LoginScreen({}, {})
    }
}
