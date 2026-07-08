package com.hejulian.testdemo.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hejulian.testdemo.data.FeedRepository
import com.hejulian.testdemo.data.model.FeedMedia
import com.hejulian.testdemo.data.model.FeedUser
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FeedViewModel(
    private val feedRepository: FeedRepository,
    currentUser: FeedUser
) : ViewModel(){
    private val _uiState = MutableStateFlow(
        FeedUiState(
            isLoading = true,
            currentUser = currentUser
        )
    )
    val uiState: StateFlow<FeedUiState> = _uiState.asStateFlow()

    private val _effect = MutableSharedFlow<FeedEffect>();
    val effect = _effect.asSharedFlow()

    init {
        observeFeeds()
        refreshFeed(showSuccessMessage = false)
    }

    fun handelIntent(feedIntent: FeedIntent){
        when(feedIntent){
            FeedIntent.Refresh -> {
                refreshFeed(showSuccessMessage = true)
            }

            is FeedIntent.CreatePost ->{
                createPost(
                    postUser = feedIntent.user,
                    content = feedIntent.content,
                    mediaList = feedIntent.mediaList
                )
            }

            is FeedIntent.UpdatePost ->{
                updatePost(
                    postId = feedIntent.postId,
                    content = feedIntent.content,
                    mediaList = feedIntent.mediaList
                )
            }

            is FeedIntent.DeletePost ->{
                deletePost(feedIntent.postId)
            }

            is FeedIntent.OpenComment ->{
                openComment(feedIntent.postId)
            }

            is FeedIntent.AddComment -> {
                addComment(
                    feedIntent.postId,
                    feedIntent.user,
                    feedIntent.content
                )
            }

            is FeedIntent.DeleteComment -> {
                deleteComment(
                    feedIntent.postId,
                    feedIntent.commentId
                )
            }

            is FeedIntent.LikePost -> {
                likePost(
                    feedIntent.postId,
                    feedIntent.user
                )
            }

            is FeedIntent.UnlikePost -> {
                unlikePost(
                    feedIntent.postId,
                    feedIntent.user
                )
            }

            is FeedIntent.ShowMessage ->{
                showMessage(feedIntent.message)
            }
        }
    }

    private fun observeFeeds() {
        viewModelScope.launch {
            feedRepository.getFeedPosts().collect { newPosts ->
                _uiState.update {
                    it.copy(
                        posts = newPosts,
                        isLoading = false
                    )
                }
            }
        }
    }

    private fun loadFeed(){
        viewModelScope.launch {
            feedRepository.refreshFeed()
            feedRepository.getFeedPosts().collect { newPosts ->
                _uiState.update {
                    it.copy(posts = newPosts, isLoading = false)
                }
            }
        }
    }

    private fun refreshFeed(showSuccessMessage: Boolean){
        viewModelScope.launch {
            _uiState.update {
                it.copy(isLoading = true)
            }
            runCatching {
                feedRepository.refreshFeed()
            }.onSuccess {
                _uiState.update {
                    it.copy(isLoading = false)
                }
                if (showSuccessMessage) {
                    _effect.emit(FeedEffect.ShowMessage("刷新成功"))
                }
            }.onFailure {
                _uiState.update { it.copy(isLoading = false) }
                _effect.emit(FeedEffect.ShowMessage("刷新失败"))
            }
        }
    }

    private fun createPost(postUser: FeedUser, content: String, mediaList: List<FeedMedia>){
        viewModelScope.launch {
            feedRepository.createPost(
                user = postUser,
                content = content,
                mediaList = mediaList
            )
            _effect.emit(FeedEffect.ShowMessage("发布成功"))
        }
    }

    private fun updatePost(postId: String,content: String, mediaList: List<FeedMedia>){
        viewModelScope.launch {
            feedRepository.updatePost(
                postId = postId,
                content = content,
                mediaList = mediaList
            )
            _effect.emit(FeedEffect.ShowMessage("修改成功"))
        }
    }

    private fun deletePost(postId: String){
        viewModelScope.launch {
            feedRepository.deletePost(postId = postId)
            _effect.emit(FeedEffect.ShowMessage("删除成功"))
        }
    }

    private fun likePost(postId: String, user: FeedUser) {
        viewModelScope.launch {
            feedRepository.likePost(
                postId = postId,
                user = user
            )
        }
    }

    private fun unlikePost(postId: String, user: FeedUser) {
        viewModelScope.launch {
            feedRepository.unlikePost(
                postId = postId,
                user = user
            )
        }
    }

    private fun openComment(postId: String) {
        viewModelScope.launch {
            _effect.emit(FeedEffect.OpenComment(postId))
        }
    }

    private fun addComment(postId: String, user: FeedUser, content: String) {
        viewModelScope.launch {
            feedRepository.addComment(
                postId = postId,
                commentUser = user,
                content = content
            )
            _effect.emit(FeedEffect.ShowMessage("评论成功"))
        }
    }

    private fun deleteComment(postId: String, commentId: String) {
        viewModelScope.launch {
            feedRepository.deleteComment(postId = postId, commentId = commentId)
            _effect.emit(FeedEffect.ShowMessage("删除评论成功"))
        }
    }

    private fun showMessage(message: String){
        viewModelScope.launch {
            _effect.emit(FeedEffect.ShowMessage(message))
        }
    }
}