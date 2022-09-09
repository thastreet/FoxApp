package com.street.fox.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import com.street.fox.ui.theme.FoxTheme
import com.street.fox.viewmodel.HomeViewModel
import com.street.fox.viewmodel.HomeViewModelPreview

@Composable
fun HomeScreen(viewModel: HomeViewModel) {
    val currentViewModel by viewModel.viewData.collectAsState()

    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = currentViewModel.profileImageUrl,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .aspectRatio(1f)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )

        Text(currentViewModel.name, color = Color.Black)
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    FoxTheme {
        HomeScreen(HomeViewModelPreview())
    }
}
