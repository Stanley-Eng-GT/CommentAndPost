package com.example.postsandcomments.repository

import android.util.Log
import com.example.postsandcomments.database.CommentDao
import com.example.postsandcomments.database.TypicodeApi
import com.example.postsandcomments.model.CommentModel
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CommentRepository  (
    private val commentDAO: CommentDao
) {
    val comments = commentDAO.getAllComments()
    val BASE_URL = "https://jsonplaceholder.typicode.com/"
    val TAG = "Post- PostRepository"

    suspend fun commentApiCallCopyToDB() {
        val gson = Gson()
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(BASE_URL)
            .build()

        val restApi = retrofit.create<TypicodeApi>(TypicodeApi::class.java)
        restApi.getComments()?.enqueue(object : Callback<List<CommentModel?>?> {
            override fun onFailure(call: Call<List<CommentModel?>?>, t: Throwable) {
                Log.d(TAG, "OOPS!! something went wrong..")
            }

            override fun onResponse(
                call: Call<List<CommentModel?>?>,
                response: Response<List<CommentModel?>?>
            ) {
                var comments = response.body()
                GlobalScope.launch {
                    commentDAO.deleteAll()
                    comments?.forEach {
                        if (it != null) {
                            commentDAO.insertComment(it)
                        }
                    }
                }
            }
        })
    }
}