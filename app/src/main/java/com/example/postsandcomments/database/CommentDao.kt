package com.example.postsandcomments.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.postsandcomments.model.CommentModel

@Dao
interface CommentDao {
    @Query("SELECT * FROM Comments")
    fun getAllComments(): LiveData<List<CommentModel>>

    //return number of rows deleted
    @Query("DELETE FROM Comments")
    suspend fun deleteAll() : Int

    //return id, -1 if nothing is inserted
    @Insert
    suspend fun insertComment(comment: CommentModel): Long
}