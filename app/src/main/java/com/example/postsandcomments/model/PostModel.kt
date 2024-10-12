package com.example.postsandcomments.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Post")
data class PostModel(
    @PrimaryKey
    val id: Int,
    val title: String,
    val body: String,
    val userId: Int
)