package com.example.quakereport.news

import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.browser.customtabs.CustomTabColorSchemeParams
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.example.quakereport.screens.MySingleton
import com.example.quakereport.R


class NewsFragment : Fragment(), NewsItemClicked {
    lateinit var mAdapter: NewsListAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_news, container, false)

        // Set up RecyclerView and its LayoutManager
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        fetchData()
        mAdapter = NewsListAdapter(this)
        recyclerView.adapter = mAdapter

        return view
    }

    private fun fetchData() {
        val url = "https://sahil77vishwakarma.github.io/apitest/sample.json"

            //"https://saurav.tech/NewsAPI/top-headlines/category/health/in.json"

        //https://newsapi.org/v2/everything?q=earthquake AND magnitude&sources=bbc-news,time,abc-news,cbc-news&apiKey=3798bbe662c0440f8e3263114f32a11b
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET,
            url,
            null,
            {
                val newsJsonArray = it.getJSONArray("articles")
                val newsArray = ArrayList<News>()
                for(i in 0 until newsJsonArray.length()) {
                    val newsJsonObject = newsJsonArray.getJSONObject(i)

                    val title = newsJsonObject.getString("title")
                    var author = newsJsonObject.getString("author")
                    val url = newsJsonObject.getString("url")
                    val urlToImage = newsJsonObject.getString("urlToImage")

                    if(author == "null"){
                        author = "https://earthquake.usgs.gov"
                    }

                    val news = News(title,author,url,urlToImage)
                    newsArray.add(news)
                }
                mAdapter.updateNews(newsArray)
            },
            {
                // Log error for debugging purposes
                Log.e("NewsFragment", "Volley Error: $it")

                // Display user feedback using Toast
                Toast.makeText(requireContext(), "Failed to fetch news", Toast.LENGTH_SHORT).show()
            }
        )
        MySingleton.getInstance(requireContext()).addToRequestQueue(jsonObjectRequest)
    }

    override fun onItemClicked(item: News) {
        val myCustomCloseIcon = ContextCompat.getDrawable(requireContext(), R.drawable.baseline_arrow_back_24)
        val colorPrimaryLight = ContextCompat.getColor(requireContext(), R.color.lightred)

        val builder = CustomTabsIntent.Builder()
            .setUrlBarHidingEnabled(true)
            .setShowTitle(true)
            .setCloseButtonIcon(myCustomCloseIcon?.toBitmap() ?: Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888))
            .setDefaultColorSchemeParams(
            CustomTabColorSchemeParams.Builder()
                .setToolbarColor(colorPrimaryLight)
                .build()

        )
        val customTabsIntent = builder.build()
        customTabsIntent.launchUrl(requireContext(),Uri.parse(item.url))
    }

}