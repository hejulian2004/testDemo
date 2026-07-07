package com.hejulian.testdemo.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImagePainter
import com.hejulian.testdemo.data.FeedRepository
import com.hejulian.testdemo.data.FeedRepositoryImpl
import com.hejulian.testdemo.data.model.FeedUser
import com.hejulian.testdemo.presentation.components.FeedPostItem
import com.hejulian.testdemo.presentation.components.FeedTopBar
import java.util.UUID

@Composable
fun FeedScreen(
    viewModel: FeedViewModel
) {
    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = remember{
        SnackbarHostState()
    }

    var showCreateDialog by remember{
        mutableStateOf(false)
    }

    var commentPostId by remember{
        mutableStateOf<String?>(null)
    }

    LaunchedEffect(Unit){
        viewModel.effect.collect {effect ->
            when(effect){
                is FeedEffect.ShowMessage ->{
                    snackbarHostState.showSnackbar(effect.message)
                }

                is FeedEffect.OpenMoreMenu ->{

                }

                is FeedEffect.OpenComment ->{
                    commentPostId = effect.postId
                }
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            FeedTopBar(
                onRefresh = {
                    viewModel.handelIntent(FeedIntent.Refresh)
                },
                onCreatePost = {
                    showCreateDialog = true
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ){
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                if(uiState.isLoading&&uiState.posts.isEmpty()){
                    item {
                        Box(
                            modifier = Modifier.fillParentMaxSize(),
                            contentAlignment = Alignment.Center
                        ){
                            CircularProgressIndicator()
                        }
                    }
                }
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
                        onLikeClick = {

                        },
                        onAddCommentClick = {

                        },
                        onDeleteCommentClick = {

                        },
                        onOpenMoreMenuClick = {

                        },
                        onDeletePostClick = {

                        }
                    )
                }
            }
        }

    }
}

@Preview
@Composable
fun FeedScreenPreview(){
    val repository = FeedRepositoryImpl()
    val user = FeedUser(
        id = UUID.randomUUID().toString(),
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