package com.hejulian.testdemo.domain.usecase

import com.hejulian.testdemo.domain.model.FeedMedia
import com.hejulian.testdemo.domain.repository.FeedRepository

class UpdatePostUseCase(private val repository: FeedRepository) {
    suspend operator fun invoke(postId: String, content: String, mediaList: List<FeedMedia>) {
        repository.updatePost(postId, content, mediaList)
    }
}
