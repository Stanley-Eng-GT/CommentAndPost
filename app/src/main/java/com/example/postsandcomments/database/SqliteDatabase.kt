package com.example.postsandcomments.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.postsandcomments.model.CommentModel
import com.example.postsandcomments.model.PostModel

/*** SQLite DB */
@Database(entities = [PostModel::class, CommentModel::class], version = 1)
abstract class SqliteDatabase : RoomDatabase() {
    abstract val postDAO: PostDao
    abstract val commentDAO: CommentDao

    companion object {
        @Volatile
        private var INSTANCE: SqliteDatabase? = null
        fun getInstance(context: Context): SqliteDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        SqliteDatabase::class.java,
                        "sql_database"
                    ).build()
                }
                return instance
            }
        }
    }
}