package com.hejulian.testdemo.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hejulian.testdemo.data.model.FeedPost
import com.hejulian.testdemo.data.model.FeedUser
import java.util.UUID

@Composable
fun FeedPostItem(
    post: FeedPost,
    currentUser: FeedUser,
    onClick: () -> Unit,//整体被点击
    onNameClick: () -> Unit,//名字被点击
    onLikeClick: () -> Unit,//点赞
    onAddCommentClick: () -> Unit,//添加评论
    onDeleteCommentClick: () -> Unit,//删除评论
    onDeletePostClick: () -> Unit,//删除按钮
    onPostAvatarClick:() -> Unit,
    onLikedAvatarClick: (FeedUser) -> Unit,
    currentTime: Long
) {
    Row(
        modifier = Modifier
            .background(Color.White)
            .fillMaxWidth()
            .padding(12.dp)
            .clickable{
                onClick()
            }
    ) {
        Avatar(
            url = post.postUser.avatarUrl,
            size = 48.dp,
            onClick = {
                onPostAvatarClick()
            }
        )

        Spacer(modifier = Modifier.width(12.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                modifier = Modifier.
                    clickable{
                        onNameClick()
                    },
                text = post.postUser.name,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )

            if(!post.content.isEmpty()){
                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = post.content,
                    fontWeight = FontWeight.Normal,
                    fontSize = 17.sp,
                    color = Color.Black
                )
            }

            Spacer(modifier = Modifier.height(6.dp))

            FeedActionBar(
                post = post,
                currentUser = currentUser,
                onLikeClick = onLikeClick,
                onAddCommentClick = onAddCommentClick,
                onDeletePostClick = onDeletePostClick,
                currentTime = currentTime
            )

            FeedLikedUserAvatarBar(
                likedUserList = post.likedUsers,
                onUserClick = { user ->
                    onLikedAvatarClick(user)
                }
            )

        }
    }

}

@Preview
@Composable
fun FeedPostItemPreview(){
    val uuid = UUID.randomUUID().toString()
    FeedPostItem(
        post = FeedPost(
            id = uuid,
            postUser = FeedUser(
                id = uuid,
                name = "何聚敛",
                avatarUrl = "https://i.pravatar.cc/300"
            ),
            content = "这是一条测试朋友圈内容",
        ),
        currentUser = FeedUser(
            id = uuid,
            name = "何聚敛",
            avatarUrl = "https://i.pravatar.cc/300"
        ),
        onClick = {  },
        onNameClick = {},
        onLikeClick = {  },
        onAddCommentClick =  {  },
        onDeleteCommentClick =  {  },
        onDeletePostClick =  {  },
        onPostAvatarClick = {  },
        onLikedAvatarClick = {},
        currentTime = System.currentTimeMillis()
    )
}