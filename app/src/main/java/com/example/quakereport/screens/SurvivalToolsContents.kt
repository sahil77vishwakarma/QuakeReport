package com.example.quakereport.screens

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import com.example.quakereport.R
import com.example.quakereport.measures.MeasuresFragment

class SurvivalToolsContents : AppCompatActivity() {
    private lateinit var mainLayout: LinearLayout
    private lateinit var firstAidLayout: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.survival_tools_contents)

        mainLayout = findViewById(R.id.survival_tool_parentView)
        firstAidLayout = findViewById(R.id.firstAidLayout_childview)

        //Handling the click of Whistle Alert button
        val whistleCard = mainLayout.findViewById<CardView>(R.id.whistle_Alert)
        whistleCard.setOnClickListener{
            val intent = Intent(this, AlertsActivity::class.java)
            startActivity(intent)
        }


        //Handling the click of FlashLight button
        val flashLightCard = mainLayout.findViewById<CardView>(R.id.flashLightCardid)
        flashLightCard.setOnClickListener{
            val intent = Intent(this, FlashLightActivity::class.java)
            startActivity(intent)
        }


        // Handling the layout switch
        val firstAidCard = mainLayout.findViewById<CardView>(R.id.firstAidCardid)
        firstAidCard.setOnClickListener {
            toggleLayouts()
        }

        val childLayoutBackButton = firstAidLayout.findViewById<ImageView>(R.id.backButton)
        childLayoutBackButton.setOnClickListener {
            toggleLayouts()
        }
    }

    private fun toggleLayouts() {
        if (mainLayout.visibility == View.VISIBLE) {
            mainLayout.visibility = View.GONE
            firstAidLayout.visibility = View.VISIBLE
        } else {
            mainLayout.visibility = View.VISIBLE
            firstAidLayout.visibility = View.GONE
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}
