package com.example.postsandcomments

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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.postsandcomments.model.CommentModel
import com.example.postsandcomments.model.PostModel
import com.example.postsandcomments.viewmodel.PostViewModel

@Composable
fun CommentScreen(navController: NavController, viewModel: PostViewModel) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally // Align children horizontally
    ) {
        val posts = viewModel.postLiveData.observeAsState().value
        if (posts  != null) {
            addPostOnTop(posts.filter {
                it.id == viewModel.selectedPostId
            })
        }

        Text("Comments",modifier = Modifier.padding(top = 40.dp) )
        val comments = viewModel.commentLiveData.observeAsState().value
        if (comments != null) {
            addCommentsData(comments.filter {
                it.postId == viewModel.selectedPostId
            })
        }

    }
}
@Composable
private fun addPostOnTop(postList: List<PostModel>) {
    val post = postList.first()

    Column() {
        Text(
            text = "id: ${post.id}, user id: ${post.userId}",
            modifier = Modifier
                .fillMaxWidth()
                .padding(6.dp)
                .padding(top = 50.dp),

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


@Composable
private fun addCommentsData(commentItems: List<CommentModel>) {
    LazyColumn() {
        items(commentItems) { comment ->
            Column {
                Text(
                    text = "id: ${comment.id}, post id: ${comment.postId}, name: ${comment.name}",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(6.dp),

                    textAlign = TextAlign.Start
                )
                Text(
                    text = comment.body,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(6.dp),

                    textAlign = TextAlign.Start
                )
                Text(
                    text = "Email: ${comment.email}",
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