package com.street.fox.composable

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.street.fox.ui.theme.FoxTheme

@Composable
fun HomeScreen() {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {

    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    FoxTheme {
        HomeScreen()
    }
}
