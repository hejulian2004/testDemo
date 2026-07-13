package com.hejulian.testdemo.domain.usecase

import com.hejulian.testdemo.domain.model.FeedPost
import com.hejulian.testdemo.domain.repository.FeedRepository
import kotlinx.coroutines.flow.Flow

class GetFeedPostsUseCase(private val repository: FeedRepository) {
    operator fun invoke(): Flow<List<FeedPost>> = repository.getFeedPosts()
}
