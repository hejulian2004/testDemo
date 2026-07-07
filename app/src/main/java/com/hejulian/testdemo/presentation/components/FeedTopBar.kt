package com.hejulian.testdemo.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun FeedTopBar (
    onRefresh: () -> Unit,
    onCreatePost: () -> Unit,
){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.White)
            .height(56.dp)
            .padding(horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "朋友圈")

        Spacer(modifier = Modifier.weight(1f))

        TextButton(
            onClick = onRefresh
        ) {
            Text(text = "刷新")
        }

        TextButton(
            onClick = onCreatePost
        ) {
            Text("发布")
        }
    }
}

@Preview
@Composable
fun FeedTopBarPreview(){
    FeedTopBar(
        onRefresh = {},
        onCreatePost = {}
    )
}