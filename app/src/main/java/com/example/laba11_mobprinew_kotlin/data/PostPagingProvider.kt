package com.example.laba11_mobprinew_kotlin.data

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.laba11_mobprinew_kotlin.data.model.Post
import com.example.laba11_mobprinew_kotlin.data.remote.PostPagingSource
import com.example.laba11_mobprinew_kotlin.data.remote.RetrofitClient
import kotlinx.coroutines.flow.Flow

object PostPagingProvider {
    private val api = RetrofitClient.postApi
    fun getPostsFlow(): Flow<PagingData<Post>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { PostPagingSource(api) }
        ).flow
    }
}
