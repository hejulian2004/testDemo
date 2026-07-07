package com.hejulian.testdemo.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hejulian.testdemo.data.model.FeedPost
import com.hejulian.testdemo.data.model.FeedUser
import com.hejulian.testdemo.utils.TimeUtils
import java.util.UUID

@Composable
fun FeedActionBar(
    post: FeedPost,
    currentUser: FeedUser,
    onOpenMOreMenuClick: () -> Unit,
    onLikeClick: () -> Unit,
    onAddCommentClick: () -> Unit,
    onDeletePostClick: () -> Unit,
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            text = TimeUtils.formatTime(post.createTime),
            fontSize = 12.sp,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.weight(1f))

        if (post.postUser.id == currentUser.id) {
            Text(
                modifier = Modifier
                    .clickable {
                        onDeletePostClick()
                    },
                text = "删除",
                fontSize = 12.sp,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.width(10.dp))
        }

        Text(
            modifier = Modifier
                .clickable {
                    onOpenMOreMenuClick()
                },
            text = "更多",
            fontSize = 12.sp
        )
    }

}

@Preview(showBackground = true)
@Composable
private fun FeedActionBarPreview() {
    val uuid = UUID.randomUUID().toString()
    FeedActionBar(
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
        onOpenMOreMenuClick = { },
        onLikeClick = { },
        onAddCommentClick = { },
        onDeletePostClick = { }
    )
}