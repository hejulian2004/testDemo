package com.hejulian.testdemo.domain.usecase

import com.hejulian.testdemo.domain.repository.FeedRepository

class DeletePostUseCase(private val repository: FeedRepository) {
    suspend operator fun invoke(postId: String) {
        repository.deletePost(postId)
    }
}
