package com.hejulian.testdemo.presentation.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.hejulian.testdemo.data.model.FeedComment
import com.hejulian.testdemo.data.model.FeedUser

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FeedCommentItem(
    comment: FeedComment,
    onCommentClick: (FeedComment) -> Unit,
    onCommentLongClick: (FeedComment) -> Unit,
    onUserClick: (FeedUser) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .combinedClickable(
                onClick = {
                    onCommentClick(comment)
                },
                onLongClick = {
                    onCommentLongClick(comment)
                }
            )
    ) {
        Text(
            text = "${comment.commentUser.name}:",
            color = Color(0xFF576B95),
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.clickable{
                onUserClick(comment.commentUser)
            }
        )

        Text(
            text = comment.content,
            fontSize = 14.sp,
            modifier = Modifier
                .weight(1f)
                .combinedClickable(
                    onClick = {
                        onUserClick(comment.commentUser)
                    },
                    onLongClick = {
                        onCommentLongClick(comment)
                    }
                )
        )
    }
}