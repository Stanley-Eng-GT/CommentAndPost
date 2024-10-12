package com.example.postsandcomments

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.postsandcomments.model.PostModel
import com.example.postsandcomments.ui.theme.PostsAndCommentsTheme
import com.example.postsandcomments.viewmodel.PostViewModel

@Composable
fun PostScreen(viewModel:PostViewModel) {
    Column(
    horizontalAlignment = Alignment.CenterHorizontally // Align children horizontally
    ) {
        searchView()
        Text("Posts")
        val posts = viewModel.postLiveData.observeAsState().value
        if (posts != null) {
            addPostData(posts)
        }

    }
}
@Composable
fun searchView() {
    var searchText by remember{ mutableStateOf("") }
    Column(
        modifier = Modifier
            .wrapContentSize()
            .padding(16.dp)
            .padding(top = 40.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp), // Space between items
        horizontalAlignment = Alignment.CenterHorizontally // Align children horizontally
    ) {
        TextField(
            value = searchText,
            onValueChange = { searchText = it },
            label = { Text("Search") },
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun addPostData(postItems: List<PostModel>) {
    LazyColumn() {
        items(postItems) { post ->
            Column(
                modifier = Modifier.clickable {}
            ) {
                Text(
                    text = "id: ${post.id}, user id: ${post.userId}",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(6.dp),

                    textAlign = TextAlign.Start
                )
                Text(
                    text = "Title: ${post.title}",
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(6.dp),

                    textAlign = TextAlign.Start
                )
                Text(
                    text = post.body,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(6.dp),

                    textAlign = TextAlign.Start
                )
                Divider(color = Color.Gray)

            }

        }
    }
}