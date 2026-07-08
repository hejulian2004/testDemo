package com.hejulian.testdemo.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Comment
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Comment
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
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
    onOpenMoreMenuClick: () -> Unit,
    onLikeClick: () -> Unit,
    onAddCommentClick: () -> Unit,
    onDeletePostClick: () -> Unit,
    currentTime: Long
) {

    var isShowMore by remember { mutableStateOf(true) }

    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = showTime(post.createTime, currentTime),
            fontSize = 14.sp,
            color = Color.Gray,
        )

        Spacer(modifier = Modifier.weight(1f))

        if(isShowMore&&post.postUser.id == currentUser.id) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "删除",
                Modifier.size(15.dp)
            )
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
            Icon(
                imageVector = Icons.Default.FavoriteBorder,
                contentDescription = "删除",
                Modifier.size(15.dp)
            )
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
            Icon(
                imageVector = Icons.AutoMirrored.Filled.Comment,
                contentDescription = "删除",
                Modifier.size(15.dp)
            )
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

fun showTime(postTime: Long, currentTime: Long): String {
    val timeGap = currentTime - postTime
    return if (timeGap < 60_000L) { //一分钟以内
        "刚刚"
    } else if (timeGap < 3_600_000L) { //一小时以内
        "${timeGap / 60_000L}分钟前"
    } else if (timeGap < 86_400_000L) { //一天以内
        "${timeGap / 3_600_000L}小时前"
    } else if (timeGap < 604_800_000L) { //一周以内
        "${timeGap / 86_400_000L}天前"
    } else {
        TimeUtils.formatTime(postTime, "yyyy-MM-dd")
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
        onOpenMoreMenuClick = { },
        onLikeClick = { },
        onAddCommentClick = { },
        onDeletePostClick = { },
        currentTime = System.currentTimeMillis()
    )
}