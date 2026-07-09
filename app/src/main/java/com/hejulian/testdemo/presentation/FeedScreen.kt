package com.hejulian.testdemo.presentation

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hejulian.testdemo.data.FeedRepositoryImpl
import com.hejulian.testdemo.data.model.FeedUser
import com.hejulian.testdemo.presentation.components.BottomSheet
import com.hejulian.testdemo.presentation.components.FeedPostItem
import com.hejulian.testdemo.presentation.components.FeedTopBar
import com.hejulian.testdemo.presentation.components.TextPublishScreen
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeedScreen(
    viewModel: FeedViewModel
) {
    val uiState by viewModel.uiState.collectAsState()

    val snackbarHostState = remember{
        SnackbarHostState()
    }

    var snackbarJob by remember {
        mutableStateOf<Job?>(null)
    }

    var showBottomSheet by remember{
        mutableStateOf(false)
    }

    val bottomSheetState = rememberModalBottomSheetState()

    var commentPostId by remember{
        mutableStateOf<String?>(null)
    }

    var commentContent by remember {
        mutableStateOf<String?>(null)
    }

    var showTextPublish by remember {
        mutableStateOf(false)
    }

    var currentTime by remember {
        mutableLongStateOf(System.currentTimeMillis())
    }

    var pendingDeletePostId by remember {
        mutableStateOf<String?>(null)
    }

    val lazyListState = rememberLazyListState()

    LaunchedEffect(Unit) {
        while (true) {
            currentTime = System.currentTimeMillis()
            delay((60 * 1000L).milliseconds)
        }
    }

    LaunchedEffect(Unit){
        viewModel.effect.collect {effect ->
            when(effect){
                is FeedEffect.ShowMessage ->{
                    snackbarJob?.cancel()
                    snackbarHostState.currentSnackbarData?.dismiss()

                    snackbarJob = launch {
                        snackbarHostState.showSnackbar(
                            message = effect.message,
                            duration = SnackbarDuration.Short
                        )
                    }
                }

                is FeedEffect.OpenComment ->{
                    commentPostId = effect.postId
                }

                is FeedEffect.ScrollToIndex ->{ //发帖后回到顶部,默认回到index=0
                    lazyListState.animateScrollToItem(effect.index)
                }

            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            FeedTopBar(
                onShortClickCreatePost = {
                    showBottomSheet = true
                },
                onLongClickCreatePost = {
                    showTextPublish = true
                }
            )
        }
    ) { innerPadding ->
        PullToRefreshBox(
            isRefreshing = uiState.isLoading,
            onRefresh = {
                viewModel.handelIntent(FeedIntent.Refresh)
            },
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ){
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                state = lazyListState
            ) {
                if(!uiState.isLoading&&uiState.posts.isEmpty()){
                    item {
                        Box(
                            modifier = Modifier.fillParentMaxSize(),
                            contentAlignment = Alignment.Center
                        ){
                            Text("暂无动态")
                        }
                    }
                }

                items(
                    items = uiState.posts,
                    key = { post -> post.id },
                ){post ->
                    FeedPostItem(
                        post = post,
                        currentUser = uiState.currentUser,
                        onClick = {viewModel.handelIntent(FeedIntent.ShowMessage("item被点击了"))},
                        onNameClick = {viewModel.handelIntent(FeedIntent.ShowMessage(post.postUser.name))},
                        onLikeClick = {
                            if(!post.isLiked)viewModel.handelIntent(FeedIntent.LikePost(post.id, uiState.currentUser))
                            else viewModel.handelIntent(FeedIntent.UnlikePost(post.id, uiState.currentUser))
                        },
                        onAddCommentClick = {
                            commentPostId = post.id
                            commentContent = ""
                        },
                        onDeleteCommentClick = {

                        },
                        onDeletePostClick = {
                            pendingDeletePostId = post.id
                        },
                        onAvatarClick = {
                            viewModel.handelIntent(FeedIntent.ShowMessage(post.postUser.toString()))
                        },
                        currentTime = currentTime,
                    )
                    HorizontalDivider(
                        thickness = 0.5.dp,
                        color = Color.LightGray
                    )
                }
            }

            if(showBottomSheet){
                ModalBottomSheet(
                    onDismissRequest = {showBottomSheet = false},
                    sheetState = bottomSheetState,
                    dragHandle = {
                        BottomSheetDefaults.DragHandle()
                    }
                ) {
                    BottomSheet(
                        onShootClick = {

                        },
                        onChooseClick = {

                        },
                        onCancelClick = {
                            showBottomSheet = false
                        }
                    )
                }
            }

            //删除自己的帖子
            if (pendingDeletePostId != null) {
                AlertDialog(
                    onDismissRequest = {
                        pendingDeletePostId = null
                    },
                    title = {
                        Text(text = "确认删除")
                    },
                    text = {
                        Text(text = "确定要删除这条动态吗？删除后不可恢复。")
                    },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                val postId = pendingDeletePostId
                                if (postId != null) {
                                    viewModel.handelIntent(
                                        FeedIntent.DeletePost(
                                            postId = postId
                                        )
                                    )
                                }
                                pendingDeletePostId = null
                            }
                        ) {
                            Text(
                                text = "删除",
                                color = Color.Red
                            )
                        }
                    },
                    dismissButton = {
                        TextButton(
                            onClick = {
                                pendingDeletePostId = null
                            }
                        ) {
                            Text(text = "取消")
                        }
                    }
                )
            }
        }

    }

    //发布文字内容
    AnimatedVisibility(
        visible = showTextPublish,
        enter = slideInVertically(initialOffsetY = {it}),
        exit = slideOutVertically(targetOffsetY = {it})
    ) {
        TextPublishScreen(
            onCancelClick = {
                showTextPublish = false
            },
            onPostClick = { textContent ->
                viewModel.handelIntent(
                    FeedIntent.CreatePost(
                        user = uiState.currentUser,
                        content = textContent,
                        mediaList = emptyList()
                    )
                )
                showTextPublish = false
            }
        )
    }
}

@Preview
@Composable
fun FeedScreenPreview(){
    val repository = FeedRepositoryImpl()
    val user = FeedUser(
        id = "1",
        name = "何聚敛",
        avatarUrl = ""
    )

    FeedScreen(
        viewModel = FeedViewModel(
            feedRepository = repository,
            currentUser = user
        )
    )
}