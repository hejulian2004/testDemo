package com.hejulian.testdemo.domain.repository

import com.hejulian.testdemo.domain.model.FeedComment
import com.hejulian.testdemo.domain.model.FeedMedia
import com.hejulian.testdemo.domain.model.FeedNotification
import com.hejulian.testdemo.domain.model.FeedPost
import com.hejulian.testdemo.domain.model.FeedUser
import kotlinx.coroutines.flow.Flow

interface FeedRepository {
    fun getFeedPosts(): Flow<List<FeedPost>>

    fun getFeedPost(postId: String): Flow<FeedPost?>

    suspend fun refreshFeed()

    suspend fun likePost(
        postId: String,
        user: FeedUser
    ): String

    suspend fun getLikedUsers(
        postId: String
    ): List<FeedUser>

    suspend fun unlikePost(
        postId: String,
        user: FeedUser
    ): String

    suspend fun addComment(
        postId: String,
        commentUser: FeedUser,
        content: String
    ): String

    suspend fun getComments(
        postId: String
    ): List<FeedComment>

    suspend fun deleteComment(
        comment: FeedComment
    ): String

    suspend fun createPost(
        user: FeedUser,
        content: String,
        mediaList: List<FeedMedia>
    )

    suspend fun deletePost(
        postId: String
    )

    suspend fun updatePost(
        postId: String,
        content: String,
        mediaList: List<FeedMedia>
    )

    suspend fun addNotification(
        feedNotification: FeedNotification
    )

    suspend fun deleteCommentNotification(
        feedNotification: FeedNotification
    )

    suspend fun deleteLikeNotification(
        feedNotification: FeedNotification
    )

    fun getNotifications(): Flow<List<FeedNotification>>

    suspend fun markAllNotificationsAsRead()
}
