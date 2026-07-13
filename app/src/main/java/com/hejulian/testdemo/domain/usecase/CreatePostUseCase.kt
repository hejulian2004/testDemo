package com.hejulian.testdemo.domain.usecase

import com.hejulian.testdemo.domain.model.FeedMedia
import com.hejulian.testdemo.domain.model.FeedUser
import com.hejulian.testdemo.domain.repository.FeedRepository

class CreatePostUseCase(private val repository: FeedRepository) {
    suspend operator fun invoke(user: FeedUser, content: String, mediaList: List<FeedMedia>) {
        repository.createPost(user, content, mediaList)
    }
}
