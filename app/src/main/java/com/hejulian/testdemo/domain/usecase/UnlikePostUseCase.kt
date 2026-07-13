package com.hejulian.testdemo.domain.usecase

import com.hejulian.testdemo.domain.model.FeedUser
import com.hejulian.testdemo.domain.repository.FeedRepository
import kotlinx.coroutines.flow.first

class UnlikePostUseCase(private val repository: FeedRepository) {
    suspend operator fun invoke(postId: String, user: FeedUser): String {
        val result = repository.unlikePost(postId, user)
        val notifications = repository.getNotifications().first()
        val likeNotification = notifications.find {
            it.post.id == postId && it.user.id == user.id && it.isLikeNotification
        }
        if (likeNotification != null) {
            repository.deleteLikeNotification(likeNotification)
        }
        return result
    }
}
