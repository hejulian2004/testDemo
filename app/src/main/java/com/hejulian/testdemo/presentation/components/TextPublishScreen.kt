package com.hejulian.testdemo.presentation.components

import androidx.compose.animation.core.EaseOutExpo
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TextPublishScreen(
    modifier: Modifier = Modifier,
    onCancelClick: () -> Unit,
    onPostClick: (String) -> Unit
) {

    var textContent by remember { mutableStateOf("") }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFFF5F5F5))
            .statusBarsPadding()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            Box(
                modifier = Modifier
                    .background(color = Color(0xFFF5F5F5))
                    .padding(horizontal = 12.dp)
                    .height(56.dp)
                    .fillMaxWidth()
            ){
                Text(
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .clickable {
                            onCancelClick()
                        },
                    text = "取消",
                    fontSize = 18.sp,
                    color = Color.Gray
                )
                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = "发表文字",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                TextButton(
                    enabled = textContent.isNotBlank(),
                    modifier = Modifier.align(Alignment.CenterEnd),
                    onClick = {
                        onPostClick(textContent)
                    }
                ) {
                    Text(
                        text = "发布",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = if(textContent.isEmpty()) Color.Gray else Color.Black
                    )
                }
            }

            TextField(
                value = textContent,
                onValueChange = {textContent = it},
                placeholder = {Text("这一刻的想法...")},
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )
        }
    }
}

@Preview
@Composable
fun TextPublishScreenPreview(){
    TextPublishScreen(
        onCancelClick = {},
        onPostClick = {}
    )
}