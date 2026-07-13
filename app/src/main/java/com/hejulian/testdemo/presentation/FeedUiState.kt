package com.hejulian.testdemo.presentation

import com.hejulian.testdemo.domain.model.FeedNotification
import com.hejulian.testdemo.domain.model.FeedPost
import com.hejulian.testdemo.domain.model.FeedUser

enum class Screen {
    Feed, Notification, Publish
}

data class FeedUiState(
    val currentUser: FeedUser,
    val currentScreen: Screen = Screen.Feed,
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val posts: List<FeedPost> = emptyList(),
    val notifications: List<FeedNotification> = emptyList(),
    val unreadNotificationCount: Int = 0,
    val errorMessage: String? = null
)
