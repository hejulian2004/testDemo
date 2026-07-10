package com.hejulian.testdemo.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun BottomSheet(
    modifier: Modifier = Modifier,
    onShootClick: () -> Unit,
    onChooseClick: () -> Unit,
    onCancelClick: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 32.dp)
    ) {
        BottomSheetItem(
            text = "拍摄（照片或视频）",
            onClick = {
                onShootClick()
            }
        )
        HorizontalDivider(
            thickness = 0.5.dp,
            color = Color.LightGray
        )
        BottomSheetItem(
            text = "从手机相机选择",
            onClick = {
                onChooseClick()
            }
        )
        HorizontalDivider(
            thickness = 0.5.dp,
            color = Color.LightGray
        )
        BottomSheetItem(
            text = "取消",
            onClick = {
                onCancelClick()
            }
        )
    }
}