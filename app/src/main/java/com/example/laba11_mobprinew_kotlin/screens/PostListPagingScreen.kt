package com.example.laba11_mobprinew_kotlin.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.app.NotificationCompat.MessagingStyle.Message
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.laba11_mobprinew_kotlin.data.PostPagingProvider

@Composable
fun PostListPagingScreen() {
    val posts = PostPagingProvider.getPostsFlow().collectAsLazyPagingItems()


    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(16.dp)

    ) {

        when (val refresh = posts.loadState.refresh) {
            is LoadState.Loading -> {
                item {
                    Box(
                        modifier = Modifier.fillMaxWidth().height(200.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }
            is LoadState.Error -> {
                item {
                    ErrorItem(
                        message = (refresh as LoadState.Error).error.message ?:
                        "Ошибка загрузки",
                        onRetry = { posts.retry() }
                    )
                }
            }
            else -> {}
        }

        items(
            count = posts.itemCount,
            key = { index -> posts[index]?.id ?: index }
        ) { index ->
            val post = posts[index]
            if (post != null) {
                PostItem(post = post)
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        }
        if (posts.loadState.append is LoadState.Loading) {
            item {
                Box(Modifier.fillMaxWidth().padding(16.dp), contentAlignment =
                    Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
        } //добавил состояние append для Error
        if (posts.loadState.append is LoadState.Error) {
            item {
               ErrorItem (
                   message = (posts.loadState.append as LoadState.Error).error.message ?:
               "Ошибка загрузки",
                   onRetry = { posts.retry() })
            }
        }
    }
}
@Composable
private fun ErrorItem(message: String, onRetry: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = message, color = MaterialTheme.colorScheme.error)
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = onRetry) { Text("Повторить") }
    }
}