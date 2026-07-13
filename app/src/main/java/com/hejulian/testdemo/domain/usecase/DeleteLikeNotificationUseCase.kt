package com.hejulian.testdemo.domain.usecase

import com.hejulian.testdemo.domain.model.FeedNotification
import com.hejulian.testdemo.domain.repository.FeedRepository

class DeleteLikeNotificationUseCase(private val repository: FeedRepository) {
    suspend operator fun invoke(feedNotification: FeedNotification) {
        repository.deleteLikeNotification(feedNotification)
    }
}
