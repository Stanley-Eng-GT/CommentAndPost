package com.example.postsandcomments

import android.content.Context
import android.net.ConnectivityManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.example.postsandcomments.database.SqliteDatabase
import com.example.postsandcomments.ui.theme.PostsAndCommentsTheme
import com.example.postsandcomments.viewmodel.PostViewModel
import java.time.format.TextStyle

class MainActivity : ComponentActivity() {
    private val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            SqliteDatabase::class.java,
            "contacts.db"
        ).build()
    }

    private val viewModel by viewModels<PostViewModel>(
        factoryProducer = {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return PostViewModel(db.postDAO, db.commentDAO) as T
                }
            }
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

            if (checkWifi(this)) {
                viewModel.updatePost()
                viewModel.updateComment()
            } else {
                Toast.makeText(this, "No wifi- DB not updated", Toast.LENGTH_SHORT).show()
            }
        setContent {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally // Align children horizontally
            ) {

                searchView()
                Text("Posts")
                val sizeOfList = 100
                val fibItems = generateFibItems(sizeOfList)

                addFibonacciList(fibItems)

            }

        }
    }
}

@Composable
fun searchView() {
    var searchText by remember{ mutableStateOf("") }
    Column(
        modifier = Modifier
            .wrapContentSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp), // Space between items
        horizontalAlignment = Alignment.CenterHorizontally // Align children horizontally
    ) {
        Text("Posts")
        TextField(
            value = searchText,
            onValueChange = { searchText = it },
            label = { Text("Search") },
            modifier = Modifier.fillMaxWidth()
        )


    }
}

@Composable
private fun PostItem(
    text: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        fontSize = 14.sp,
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    )
}

@Composable
private fun addFibonacciList(fibItems: List<Int>) {
    LazyColumn() {
        items(count = fibItems.size) { pos ->
            Text(
                text = fibItems[pos].toString(),
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.LightGray)
                    .padding(vertical = 12.dp),

                textAlign = TextAlign.Center
            )
            Divider(color = Color.Gray)
        }
    }


}




@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PostsAndCommentsTheme {
        Greeting("Android")
    }
}

private fun generateFibItems(sizeOfList: Int): List<Int> {
    var fibItems = mutableListOf<Int>()


    // first 2 items
    if (sizeOfList >= 1) fibItems.add(0)
    if (sizeOfList >= 2) fibItems.add(1)

    if (sizeOfList >= 3)  {
        for (i in 2 until sizeOfList) {
            fibItems.add(fibItems.last() + fibItems[fibItems.size -2])
        }
    }
    return fibItems
}
@RequiresApi(Build.VERSION_CODES.M)
private fun checkWifi(context: Context): Boolean {
    val manager = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val networkInfo = manager.activeNetwork
    return networkInfo != null
}


