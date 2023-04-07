package com.example.retrofit

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alexyuzefovich.stacklayoutmanager.StackLayoutManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    lateinit var adapter: NewsAdapter
    private var articles = mutableListOf<Article>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        adapter = NewsAdapter(this@MainActivity, articles)
        val newsList = findViewById<RecyclerView>(R.id.newsList)
        newsList.adapter = adapter
        newsList.layoutManager = LinearLayoutManager(this@MainActivity)
        GetNews()
    }

    private fun GetNews() {
        val news: Call<News> = NewsSrevice.newsInstance.getHeadlines("in", 1)
        news.enqueue(object : Callback<News> {
            override fun onResponse(call: Call<News>, response: Response<News>) {
                val news: News? = response.body()
                if (news != null) {
                    Log.d("pawan", news.toString())
                    articles.addAll(news.articles)
                    adapter.notifyDataSetChanged()


                }
            }

            override fun onFailure(call: Call<News>, t: Throwable) {
                Log.d("Pawan", "Error featching News")
            }
        })
    }
}


