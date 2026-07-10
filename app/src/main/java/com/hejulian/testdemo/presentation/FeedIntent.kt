package com.hejulian.testdemo.presentation

import com.hejulian.testdemo.data.model.FeedComment
import com.hejulian.testdemo.data.model.FeedMedia
import com.hejulian.testdemo.data.model.FeedUser

sealed interface FeedIntent{
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
}