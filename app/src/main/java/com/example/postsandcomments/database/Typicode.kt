package com.example.postsandcomments.database

import com.example.postsandcomments.model.CommentModel
import com.example.postsandcomments.model.PostModel
import retrofit2.Call
import retrofit2.http.GET

interface TypicodeApi {
    @GET("posts")
    fun getPosts(): Call<List<PostModel?>?>?

    @GET("comments")
    fun getComments(): Call<List<CommentModel?>?>?
}