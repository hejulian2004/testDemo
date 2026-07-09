package com.hejulian.testdemo.data

import com.hejulian.testdemo.data.model.FeedComment
import com.hejulian.testdemo.data.model.FeedMedia
import com.hejulian.testdemo.data.model.FeedPost
import com.hejulian.testdemo.data.model.FeedUser
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import java.util.ListResourceBundle
import java.util.UUID
import kotlin.time.Duration.Companion.milliseconds

class FeedRepositoryImpl : FeedRepository{
    private val _feedPosts = MutableStateFlow<List<FeedPost>>(emptyList())

    override fun getFeedPosts(): Flow<List<FeedPost>> {
        return _feedPosts.map{ posts ->
            posts.sortedByDescending { it.createTime }
        }
    }

    override fun getFeedPost(postId: String): Flow<FeedPost?> {
        return _feedPosts.map{ posts ->
            posts.find { it.id == postId }
        }
    }

    override suspend fun refreshFeed() {
        delay(1000.milliseconds)
        _feedPosts.value = createFakeData().sortedByDescending { it.createTime }
    }

    override suspend fun likePost(
        postId: String,
        user: FeedUser
    ): String {
        val post = _feedPosts.value.find { it.id == postId }
        if( post == null){
            return "点赞失败，找不到该帖子"
        }
        _feedPosts.update { posts ->
            posts.map{
                if(it.id == postId){
                    it.copy(
                        isLiked = true,
                        likedUsers = it.likedUsers + user
                    )
                } else it
            }
        }
        return "点赞成功"
    }

    override suspend fun getLikedUsers(postId: String): List<FeedUser> {
        val post = _feedPosts.value.find { it.id == postId }
        return post?.likedUsers ?: emptyList()
    }

    override suspend fun unlikePost(
        postId: String,
        user: FeedUser
    ): String {
        val post = _feedPosts.value.find { it.id == postId }
        if( post == null){
            return "取消点赞失败，找不到该帖子"
        }
        _feedPosts.update { posts ->
            posts.map{ post->
                if(post.id == postId){
                    post.copy(
                        isLiked = false,
                        likedUsers = post.likedUsers.filterNot { likedUser ->
                            likedUser.id  == user.id
                        }
                    )
                } else post
            }
        }
        return "取消点赞成功"
    }

    override suspend fun addComment(
        postId: String,
        commentUser: FeedUser,
        content: String
    ): String {
        val newComment = createFakeComment(
            postId = postId,
            commentUser = commentUser,
            content = content
        ).copy()
        _feedPosts.update { posts ->
            posts.map {
                if(it.id == postId){
                    it.copy(commentsList = it.commentsList+newComment)
                } else it
            }
        }
        return "评论发布成功"
    }

    override suspend fun getComments(postId: String): List<FeedComment> {
        return _feedPosts.value.find { it.id == postId }?.commentsList ?: emptyList()
    }

    override suspend fun deleteComment(postId: String, commentId: String): String {
        _feedPosts.update { posts ->
            posts.map { post ->
                if(post.id == postId){
                    post.copy(commentsList = post.commentsList.filter { it.id!=commentId })
                }else post
            }
        }
        return "评论删除成功"
    }

    override suspend fun createPost(
        user: FeedUser,
        content: String,
        mediaList: List<FeedMedia>
    ) {
        val newPost = FeedPost(
            id = UUID.randomUUID().toString(),
            postUser = user,
            content = content,
            mediaList = mediaList,
        )
        _feedPosts.update { posts ->
            posts + newPost
        }

    }

    override suspend fun deletePost(postId: String) {
        _feedPosts.update { posts ->
            posts.filterNot { it.id == postId }
        }
    }

    override suspend fun updatePost(
        postId: String,
        content: String,
        mediaList: List<FeedMedia>
    ) {
        _feedPosts.update { posts ->
            posts.map { post ->
                if (post.id == postId) {
                    post.copy(content = content, mediaList = mediaList)
                } else post
            }
        }
    }

}

private fun createFakeData(): List<FeedPost> {
    val user = FeedUser(id = "1", name = "何聚敛1", avatarUrl = "https://i.pravatar.cc/300?t="+ System.currentTimeMillis())
    return listOf(
        createFakePost(user),
        createFakePost(user.copy(id = "2", name = "何聚敛2", avatarUrl = "https://i.pravatar.cc/300?t=1"+ System.currentTimeMillis())),
        createFakePost(user.copy(id = "3",name = "何聚敛3", avatarUrl = "https://i.pravatar.cc/300?t=2"+ System.currentTimeMillis())),
        createFakePost(user.copy(id = "4",name = "何聚敛4", avatarUrl = "https://i.pravatar.cc/300?t=3"+ System.currentTimeMillis())),
        createFakePost(user.copy(id = "5",name = "何聚敛5", avatarUrl = "https://i.pravatar.cc/300?t=4"+ System.currentTimeMillis())),
        createFakePost(user.copy(id = "6",name = "何聚敛6", avatarUrl = "https://i.pravatar.cc/300?t=5"+ System.currentTimeMillis())),
    )
}

private fun createFakeComment(postId : String, commentUser: FeedUser, content: String): FeedComment{
    return FeedComment(
        id = UUID.randomUUID().toString(),
        postId = postId,
        commentUser = commentUser,
        content = "这是一条模拟评论"+ System.currentTimeMillis()
    )
}


private fun createFakePost(user: FeedUser): FeedPost{
    val fakeLikedUser: List<FeedUser> = listOf(
        FeedUser(
            id = "11",
            name = "张三",
            avatarUrl = "https://i.pravatar.cc/300?img=1"
        ),
        FeedUser(
            id = "22",
            name = "李四",
            avatarUrl = "https://i.pravatar.cc/300?img=2"
        ),
        FeedUser(
            id = "33",
            name = "王五",
            avatarUrl = "https://i.pravatar.cc/300?img=3"
        ),
        FeedUser(
            id = "44",
            name = "张三2",
            avatarUrl = "https://i.pravatar.cc/300?img=4"
        ),
        FeedUser(
            id = "55",
            name = "李四2",
            avatarUrl = "https://i.pravatar.cc/300?img=5"
        ),
        FeedUser(
            id = "66",
            name = "王五2",
            avatarUrl = "https://i.pravatar.cc/300?img=6"
        )
    )
    return FeedPost(
        id = UUID.randomUUID().toString(),
        postUser = user,
        content = "这是一个测试内容-----------------------------------------------\n---\n---\n---\n"+ System.currentTimeMillis(),
        likedUsers = fakeLikedUser
    )
}