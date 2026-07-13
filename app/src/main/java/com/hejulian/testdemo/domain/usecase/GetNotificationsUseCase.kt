package com.hejulian.testdemo.domain.usecase

import com.hejulian.testdemo.domain.model.FeedNotification
import com.hejulian.testdemo.domain.repository.FeedRepository
import kotlinx.coroutines.flow.Flow

class GetNotificationsUseCase(private val repository: FeedRepository) {
    operator fun invoke(): Flow<List<FeedNotification>> = repository.getNotifications()
}
