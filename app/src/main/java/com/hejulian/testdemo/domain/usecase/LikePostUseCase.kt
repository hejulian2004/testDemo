package com.hejulian.testdemo.domain.usecase

import com.hejulian.testdemo.domain.model.FeedNotification
import com.hejulian.testdemo.domain.model.FeedUser
import com.hejulian.testdemo.domain.repository.FeedRepository
import kotlinx.coroutines.flow.first
import java.util.UUID

class LikePostUseCase(private val repository: FeedRepository) {
    suspend operator fun invoke(postId: String, user: FeedUser): String {
        val result = repository.likePost(postId, user)
        val post = repository.getFeedPost(postId).first()
        if (post != null) {
            repository.addNotification(
                FeedNotification(
                    id = UUID.randomUUID().toString(),
                    post = post,
                    user = user,
                    isLikeNotification = true
                )
            )
        }
        return result
    }
}
