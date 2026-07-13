package com.hejulian.testdemo.presentation

import com.hejulian.testdemo.domain.model.FeedComment
import com.hejulian.testdemo.domain.model.FeedMedia
import com.hejulian.testdemo.domain.model.FeedNotification
import com.hejulian.testdemo.domain.model.FeedUser

sealed interface FeedIntent {
    data object Refresh: FeedIntent

    data class LikePost(
        val postId: String,
        val user: FeedUser
    ): FeedIntent

    data class UnlikePost(
        val postId: String,
        val user: FeedUser
    ): FeedIntent

    data class AddComment(
        val postId: String,
        val user: FeedUser,
        val content: String,
    ): FeedIntent

    data class DeleteComment(
        val comment: FeedComment
    ): FeedIntent

    data class CreatePost(
        val user: FeedUser,
        val content: String,
        val mediaList: List<FeedMedia>
    ): FeedIntent

    data class UpdatePost(
        val postId: String,
        val content: String,
        val mediaList: List<FeedMedia>
    ): FeedIntent

    data class DeletePost(
        val postId: String
    ): FeedIntent

    data class ShowMessage(
        val message: String
    ): FeedIntent

    data class AddNotification(
        val feedNotification: FeedNotification
    ): FeedIntent

    data class DeleteCommentNotification(
        val feedNotification: FeedNotification
    ): FeedIntent

    data class DeleteLikeNotification(
        val feedNotification: FeedNotification
    ): FeedIntent

    data object ClearAllNotifications: FeedIntent

    data class NavigateTo(val screen: Screen): FeedIntent
}