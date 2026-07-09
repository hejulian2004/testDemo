package com.hejulian.testdemo.presentation.components

import androidx.compose.foundation.excludeFromSystemGesture
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import coil3.compose.AsyncImage

@Composable
fun Avatar(
    url: String,
    size: Dp,
    onClick: () -> Unit
){
    Surface(
        modifier = Modifier
            .size(size)
            .clip(CircleShape),
        shape = CircleShape,
        color = Color.Gray,
        onClick = {
            onClick()
        }
    ) {
        AsyncImage(
            model = url,
            contentDescription = "头像",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
    }
}