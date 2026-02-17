package com.example.laba11_mobprinew_kotlin.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.laba11_mobprinew_kotlin.data.model.Post
import com.example.laba11_mobprinew_kotlin.data.remote.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun PostListScreen() {
    var posts by remember { mutableStateOf<List<Post>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    LaunchedEffect(Unit) {
        isLoading = true
        errorMessage = null
        try {
            val list = withContext(Dispatchers.IO) {
                RetrofitClient.postApi.getPosts(page = 1, limit = 20)
            }
            posts = list
        } catch (e: Exception) {
            errorMessage = e.message ?: "Ошибка загрузки"
        } finally {
            isLoading = false
        }
    }
    when {
        isLoading -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center)
            {
                CircularProgressIndicator()
            }
        }
        errorMessage != null -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center)
            {
                Text(text = errorMessage!!, color =
                    MaterialTheme.colorScheme.error)
            }
        }
        else -> {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(16.dp)
            ) {
                items(posts, key = { it.id }) { post ->
                    PostItem(post = post)
                }
            }
        }
    }
}
@Composable
fun PostItem(post: Post) { //убрал модификатор доступа private  чтобы можно было вызвать в коде экрана PostListPagingScreen
    Card(
    modifier = Modifier.fillMaxWidth(),
    colors = CardDefaults.cardColors(containerColor =
        MaterialTheme.colorScheme.surfaceVariant)
) {
    Column(Modifier.padding(12.dp)) {
        Text(text = post.title, style =
            MaterialTheme.typography.titleMedium)
        Text(text = post.body, style =
            MaterialTheme.typography.bodySmall, maxLines = 2)
    }
}
}