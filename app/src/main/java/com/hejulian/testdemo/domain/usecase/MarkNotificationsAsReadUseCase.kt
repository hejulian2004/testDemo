package com.hejulian.testdemo.domain.usecase

import com.hejulian.testdemo.domain.repository.FeedRepository

class MarkNotificationsAsReadUseCase(private val repository: FeedRepository) {
    suspend operator fun invoke() {
        repository.markAllNotificationsAsRead()
    }
}
