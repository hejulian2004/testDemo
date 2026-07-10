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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hejulian.testdemo.data.createFakePost
import com.hejulian.testdemo.data.model.FeedComment
import com.hejulian.testdemo.data.model.FeedPost
import com.hejulian.testdemo.data.model.FeedUser
import java.util.UUID

@Composable
fun FeedPostItem(
    post: FeedPost,
    currentUser: FeedUser,
    onClick: (FeedPost) -> Unit,//整体被点击
    onNameClick: () -> Unit,//名字被点击
    onLikeClick: () -> Unit,//点赞
    onAddCommentClick: () -> Unit,//添加评论
    onCommentClick: (FeedComment) -> Unit, //点击评论
    onCommentUserClick: (FeedUser) -> Unit,
    onDeleteCommentClick: (FeedComment) -> Unit,//长按删除评论
    onDeletePostClick: (FeedPost) -> Unit,//删除帖子按钮
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
                onClick(post)
            }
    ) {
        Avatar(
            url = post.postUser.avatarUrl,
            size = 40.dp,
            onClick = {
                onPostAvatarClick()
            }
        )

        Spacer(modifier = Modifier.width(12.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                modifier = Modifier
                    .clickable{
                        onNameClick()
                    },
                text = post.postUser.name,
                fontSize = 18.sp,
                color = Color(0xFF576B95)
            )

            if(!post.content.isEmpty()){
                Spacer(modifier = Modifier.height(4.dp))
                SelectionContainer {
                    Text(
                        text = post.content,
                        fontWeight = FontWeight.Normal,
                        fontSize = 17.sp,
                        color = Color.Black
                    )
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            FeedActionBar(
                post = post,
                currentUser = currentUser,
                onLikeClick = onLikeClick,
                onAddCommentClick = onAddCommentClick,
                onDeletePostClick = { post ->
                    onDeletePostClick(post)
                },
                currentTime = currentTime
            )

            Column(
                modifier = Modifier
                    .background(
                        color = Color(0xFFF3F3F3),
                        shape = RoundedCornerShape(4.dp)
                    )
            ) {
                FeedLikedUserAvatarBar(
                    likedUserList = post.likedUsers,
                    onUserClick = { user ->
                        onLikedAvatarClick(user)
                    }
                )

                if(post.likedUsers.isNotEmpty()&&post.commentsList.isNotEmpty()){
                    HorizontalDivider(
                        thickness = 0.5.dp,
                        color = Color.LightGray
                    )
                }

                FeedCommentList(
                    currentUser = currentUser,
                    commentsList = post.commentsList,
                    onCommentClick = { comment ->
                        onCommentClick(comment)
                    },
                    onDeleteCommentClick = { comment ->
                        onDeleteCommentClick(comment)
                    },
                    onCommentUserClick = { user ->
                        onCommentUserClick(user)
                    }
                )

            }
        }
    }

}

@Preview
@Composable
fun FeedPostItemPreview(){
    val uuid = UUID.randomUUID().toString()
    val user = FeedUser(
        id = uuid,
        name = "何聚敛",
        avatarUrl = "https://i.pravatar.cc/300"
    )
    FeedPostItem(
        post = createFakePost(user),
        currentUser = user,
        onClick = { },
        onNameClick = {},
        onLikeClick = { },
        onAddCommentClick = { },
        onDeleteCommentClick = { },
        onDeletePostClick = { },
        onPostAvatarClick = { },
        onLikedAvatarClick = {},
        currentTime = System.currentTimeMillis(),
        onCommentClick = {},
        onCommentUserClick = {}
    )
}