package com.hejulian.testdemo.presentation

import com.hejulian.testdemo.data.model.FeedPost
import com.hejulian.testdemo.data.model.FeedUser

data class FeedUiState(
    val currentUser: FeedUser,
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val posts: List<FeedPost> = emptyList(),
    val unreadNotificationCount: Int = 0,
    val errorMessage: String? = null
)
