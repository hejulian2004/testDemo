package com.hejulian.testdemo.presentation.components

import android.graphics.Bitmap
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.hejulian.testdemo.domain.model.FeedMedia
import java.io.File
import java.io.FileOutputStream

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PublishScreen(
    initialMediaList: List<FeedMedia>,
    onCancelClick: () -> Unit,
    onPostClick: (String, List<FeedMedia>) -> Unit,
    modifier: Modifier = Modifier,
    isTextOnly: Boolean = false
) {
    val context = LocalContext.current
    var textContent by remember { mutableStateOf("") }
    var selectedMedia by remember { mutableStateOf(initialMediaList) }
    var mediaToDelete by remember { mutableStateOf<FeedMedia?>(null) }

    // 发布页内部用于添加更多照片/视频的启动器
    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickMultipleVisualMedia(9)
    ) { uris ->
        if (uris.isNotEmpty()) {
            val newMedia = uris.map { uri ->
                val isVideo = context.contentResolver.getType(uri)?.startsWith("video") == true
                if (isVideo) {
                    FeedMedia.Video(coverUrl = uri.toString(), videoUrl = uri.toString())
                } else {
                    FeedMedia.Image(url = uri.toString())
                }
            }
            selectedMedia = (selectedMedia + newMedia).take(9)
        }
    }

    val takePictureLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap ->
        if (bitmap != null) {
            val file = File(context.cacheDir, "captured_image_${System.currentTimeMillis()}.jpg")
            FileOutputStream(file).use { out ->
                bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out)
            }
            val newMedia = FeedMedia.Image(url = Uri.fromFile(file).toString())
            selectedMedia = (selectedMedia + newMedia).take(9)
        }
    }

    var showAddMoreBottomSheet by remember { mutableStateOf(false) }

    Surface(
        modifier = modifier
            .fillMaxSize()
            .background(color = Color(0xFFF5F5F5))
            .statusBarsPadding()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            // 顶部栏
            Box(
                modifier = Modifier
                    .background(color = Color(0xFFF5F5F5))
                    .padding(horizontal = 12.dp)
                    .height(56.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .clickable { onCancelClick() }
                        .padding(8.dp),
                    text = "取消",
                    fontSize = 17.sp,
                    color = Color.Black
                )
                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = if (isTextOnly) "发表文字" else "发表",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                TextButton(
                    enabled = textContent.isNotBlank() || (!isTextOnly && selectedMedia.isNotEmpty()),
                    modifier = Modifier.align(Alignment.CenterEnd),
                    onClick = {
                        onPostClick(textContent, selectedMedia)
                    }
                ) {
                    Text(
                        text = "发表",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (textContent.isBlank() && (isTextOnly || selectedMedia.isEmpty())) Color.LightGray else Color(0xFF07C160)
                    )
                }
            }

            // 可滚动区域内容
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp)
            ) {
                // 文本输入框
                TextField(
                    value = textContent,
                    onValueChange = { textContent = it },
                    placeholder = { Text("这一刻的想法...", color = Color.Gray, fontSize = 16.sp) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black
                    )
                )

                if (!isTextOnly) {
                    Spacer(modifier = Modifier.height(16.dp))

                    // 媒体网格（3列）
                    val spacing = 8.dp
                    val imageSize = 90.dp
                    val totalItems = selectedMedia.size + (if (selectedMedia.size < 9) 1 else 0)
                    val itemsList = selectedMedia.toMutableList()

                    val rows = if (selectedMedia.size < 9) {
                        (selectedMedia + null).chunked(3)
                    } else {
                        selectedMedia.chunked(3)
                    }

                    Column(
                        verticalArrangement = Arrangement.spacedBy(spacing),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        rows.forEach { rowItems ->
                            Row(horizontalArrangement = Arrangement.spacedBy(spacing)) {
                                rowItems.forEach { media ->
                                    if (media == null) {
                                        // “+” 添加按钮
                                        Box(
                                            modifier = Modifier
                                                .size(imageSize)
                                                .clip(RoundedCornerShape(4.dp))
                                                .background(Color(0xFFF7F7F7))
                                                .clickable {
                                                    showAddMoreBottomSheet = true
                                                },
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.Add,
                                                contentDescription = "添加",
                                                tint = Color.Gray,
                                                modifier = Modifier.size(36.dp)
                                            )
                                        }
                                    } else {
                                        // 媒体文件预览
                                        Box(
                                            modifier = Modifier
                                                .size(imageSize)
                                                .clip(RoundedCornerShape(4.dp))
                                                .background(Color(0xFFF5F5F5))
                                                .clickable {
                                                    mediaToDelete = media
                                                }
                                        ) {
                                            val imageUrl = when (media) {
                                                is FeedMedia.Image -> media.url
                                                is FeedMedia.Video -> media.coverUrl
                                            }
                                            if (!imageUrl.isNullOrEmpty()) {
                                                AsyncImage(
                                                    model = imageUrl,
                                                    contentDescription = null,
                                                    modifier = Modifier.fillMaxSize(),
                                                    contentScale = ContentScale.Crop
                                                )
                                            }
                                            if (media is FeedMedia.Video) {
                                                Icon(
                                                    imageVector = Icons.Filled.PlayArrow,
                                                    contentDescription = "视频",
                                                    tint = Color.White,
                                                    modifier = Modifier
                                                        .size(28.dp)
                                                        .align(Alignment.Center)
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    // 删除确认弹窗
    if (mediaToDelete != null) {
        AlertDialog(
            onDismissRequest = { mediaToDelete = null },
            title = { Text("确认删除") },
            text = { Text("要删除这张照片吗？") },
            confirmButton = {
                TextButton(
                    onClick = {
                        selectedMedia = selectedMedia.filterNot { it == mediaToDelete }
                        mediaToDelete = null
                    }
                ) {
                    Text("删除", color = Color.Red)
                }
            },
            dismissButton = {
                TextButton(onClick = { mediaToDelete = null }) {
                    Text("取消")
                }
            }
        )
    }

    // 添加更多媒体底栏
    if (showAddMoreBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = { showAddMoreBottomSheet = false },
            dragHandle = { BottomSheetDefaults.DragHandle() }
        ) {
            com.hejulian.testdemo.presentation.components.BottomSheet(
                onShootClick = {
                    showAddMoreBottomSheet = false
                    takePictureLauncher.launch()
                },
                onChooseClick = {
                    showAddMoreBottomSheet = false
                    photoPickerLauncher.launch(
                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageAndVideo)
                    )
                },
                onCancelClick = {
                    showAddMoreBottomSheet = false
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PublishScreenPreview() {
    PublishScreen(
        initialMediaList = emptyList(),
        onCancelClick = {},
        onPostClick = { _, _ -> }
    )
}

@Preview(showBackground = true)
@Composable
fun PublishScreenTextOnlyPreview() {
    PublishScreen(
        initialMediaList = emptyList(),
        isTextOnly = true,
        onCancelClick = {},
        onPostClick = { _, _ -> }
    )
}
