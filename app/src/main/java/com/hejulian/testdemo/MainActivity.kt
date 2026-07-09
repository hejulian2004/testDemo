package com.hejulian.testdemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hejulian.testdemo.data.FeedRepositoryImpl
import com.hejulian.testdemo.data.model.FeedUser
import com.hejulian.testdemo.presentation.FeedScreen
import com.hejulian.testdemo.presentation.FeedViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val feedViewModel: FeedViewModel = viewModel{
                FeedViewModel(
                    feedRepository = FeedRepositoryImpl(),
                    currentUser = FeedUser(id = "test", name="何聚敛", avatarUrl = "https://i.pravatar.cc/30")
                )
            }
            FeedScreen(feedViewModel)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    val feedViewModel: FeedViewModel = viewModel{
        FeedViewModel(
            feedRepository = FeedRepositoryImpl(),
            currentUser = FeedUser(id = "test", name="何聚敛", avatarUrl = "https://i.pravatar.cc/30")
        )
    }
    FeedScreen(feedViewModel)
}