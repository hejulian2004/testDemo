package com.hejulian.testdemo.data

import com.hejulian.testdemo.data.model.FeedComment
import com.hejulian.testdemo.data.model.FeedMedia
import com.hejulian.testdemo.data.model.FeedPost
import com.hejulian.testdemo.data.model.FeedUser
import kotlinx.coroutines.flow.Flow

interface FeedRepository {
    fun getFeedPosts(): Flow<List<FeedPost>>

    fun getFeedPost(postId: String): Flow<FeedPost?>

    suspend fun refreshFeed()

    suspend fun likePost(
        postId: String,
        user: FeedUser
    )

    suspend fun getLikedUsers(
        postId: String
    ): List<FeedUser>

    suspend fun unlikePost(
        postId: String,
        user: FeedUser
    )

    suspend fun commentPost(
        postId: String,
        user: FeedUser,
        content: String
    )

    suspend fun getComments(
        postId: String
    ): List<FeedComment>

    suspend fun deleteComment(
        postId: String,
        commentId: String,
    )

    suspend fun createPost(
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
}