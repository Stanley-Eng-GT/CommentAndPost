package com.example.postsandcomments.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Comments")
data class CommentModel (
    @PrimaryKey
    val id: Int,
    val postId: Int,
    val name: String,
    val email: String,
    val body: String
)