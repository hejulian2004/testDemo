package com.hejulian.testdemo.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
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

    var isShowMore by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = TimeUtils.formatTime(post.createTime),
            fontSize = 14.sp,
            color = Color.Gray,
        )

        Spacer(modifier = Modifier.weight(1f))

        if(isShowMore&&post.postUser.id == currentUser.id) {
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

        if(isShowMore) {
            Text(
                modifier = Modifier
                    .clickable {
                        onLikeClick()
                    },
                text = "点赞",
                fontSize = 12.sp,
            )
            Spacer(modifier = Modifier.width(10.dp))
        }

        if(isShowMore) {
            Text(
                modifier = Modifier
                    .clickable {
                        onAddCommentClick()
                    },
                text = "评论",
                fontSize = 12.sp,
            )
            Spacer(modifier = Modifier.width(10.dp))
        }

        if(!isShowMore){
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                contentDescription = "更多",
                modifier = Modifier
                    .clickable{
                        isShowMore = true
                    }
                    .size(20.dp),
                tint = Color.Gray
            )
        }

        if(isShowMore){
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = "折叠",
                modifier = Modifier
                    .clickable{
                        isShowMore = false
                    }
                    .size(20.dp),
                tint = Color.Gray
            )
        }
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