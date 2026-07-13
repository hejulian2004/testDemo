package com.hejulian.testdemo.domain.usecase

import com.hejulian.testdemo.domain.repository.FeedRepository

class RefreshFeedUseCase(private val repository: FeedRepository) {
    suspend operator fun invoke() = repository.refreshFeed()
}
