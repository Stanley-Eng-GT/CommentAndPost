package com.example.postsandcomments.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.postsandcomments.database.CommentDao
import com.example.postsandcomments.database.PostDao
import com.example.postsandcomments.repository.CommentRepository
import com.example.postsandcomments.repository.PostRepository
import kotlinx.coroutines.launch

class PostViewModel(private val postDao: PostDao, private val commentDao: CommentDao): ViewModel() {
    val postRepo = PostRepository(postDao)
    val commentRepo = CommentRepository(commentDao)

    val postLiveData = postRepo.posts
    fun updatePost()= viewModelScope.launch{
        postRepo.postApiCallCopyToDB()
    }

    fun updateComment()= viewModelScope.launch{
        commentRepo.commentApiCallCopyToDB()
    }
}