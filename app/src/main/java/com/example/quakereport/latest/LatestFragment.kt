package com.example.quakereport.latest

import android.content.Intent
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
import com.example.quakereport.screens.MapActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton

class LatestFragment : Fragment(),QuakeItemClicked{
    lateinit var mAdapter: LatestListAdapter
    val quakeArray = ArrayList<QuakeList>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_latest, container, false)

        // Set up RecyclerView and its LayoutManager
        val recyclerView = view.findViewById<RecyclerView>(R.id.latest_recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        fetchData()
        mAdapter = LatestListAdapter(this)
        recyclerView.adapter = mAdapter


//        //HANDLING ONCLICK OF FLOATING ACTION BUTTON
//        val fabViewMap = view.findViewById<FloatingActionButton>(R.id.view_map)
//        fabViewMap.setOnClickListener {
//            // Create an Intent to start the NewMapActivity
//            val intent = Intent(requireContext(), MapActivity::class.java)
//            intent.putExtra("quakeArray", quakeArray)
//            // Start the activity
//            startActivity(intent)
//        }

        return view
    }

    private fun fetchData() {
        val url = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime=2023-09-01&endtime=2023-11-02&minfelt=50&minmagnitude=3"

        //https://newsapi.org/v2/everything?q=earthquake AND magnitude&sources=bbc-news,time,abc-news,cbc-news&apiKey=3798bbe662c0440f8e3263114f32a11b
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET,
            url,
            null,
            {
                val earthQuakeJsonArray = it.getJSONArray("features")
                for(i in 0 until earthQuakeJsonArray.length()) {
                    val quakeJsonObject = earthQuakeJsonArray.getJSONObject(i)
                    val sourceJsonObject = quakeJsonObject.getJSONObject("properties")

                    val mag = sourceJsonObject.getString("mag")
                    val place = sourceJsonObject.optString("place")

                    // Check if place is not null or empty before adding it to the quakeArray
                    if (place != "null") {
                        val news = QuakeList(
                            mag,
                            place,
                            sourceJsonObject.getString("url")
                        )
                        quakeArray.add(news)
                    }
                }
                mAdapter.updateQuakeList(quakeArray)
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

    override fun onItemClicked(item: QuakeList) {
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
        customTabsIntent.launchUrl(requireContext(), Uri.parse(item.url))
    }

}