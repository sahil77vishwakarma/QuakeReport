package com.example.quakereport

import android.content.Intent
import com.example.quakereport.news.NewsFragment
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.quakereport.latest.LatestFragment
import com.example.quakereport.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView
import com.example.quakereport.measures.MeasuresFragment
import com.example.quakereport.screens.PredictionFragment
import com.example.quakereport.screens.ProfileImagePicker

class HomeScreen : AppCompatActivity(),NavigationView.OnNavigationItemSelectedListener {

    private lateinit var fragmentManager: FragmentManager
    private lateinit var binding: ActivityMainBinding
    lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        openFragment(NewsFragment())                                                                /// to open the default news section
        setSupportActionBar(binding.toolbar)


        toggle = ActionBarDrawerToggle(this,binding.drawerLayout, binding.toolbar,
            R.string.nav_open,
            R.string.nav_close
        )
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        binding.navigationDrawer.setNavigationItemSelectedListener(this)


        /// Handling the clicks on bottom Navigation app bar
        binding.bottomNavigation.setOnItemSelectedListener {item ->
            when(item.itemId){
                R.id.bottom_news -> {
                    openFragment(NewsFragment())
                    binding.navigationDrawer.menu.findItem(R.id.nav_news)?.isChecked = true
                }
                R.id.bottom_latest ->{
                    openFragment(LatestFragment())
                    binding.navigationDrawer.menu.findItem(R.id.nav_latest)?.isChecked = true
                }
                R.id.bottom_prediction -> {
                    openFragment(PredictionFragment())
                    binding.navigationDrawer.menu.findItem(R.id.nav_prediction)?.isChecked = true
                }
                R.id.bottom_measures -> {
                    openFragment(MeasuresFragment())
                    binding.navigationDrawer.menu.findItem(R.id.nav_measures)?.isChecked = true
                }
            }
            true
        }

    }

    //Handling the onclick of the Toolbar item
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.refresh -> {
                // Get the instance of LatestFragment

            }
            R.id.filter -> {
                // Perform action for menu item 2
                return true
            }
            R.id.more -> {
                // Perform action for menu item 2
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }


    //// Handling the on clicks of the Navigation drawer
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_latest -> {
                openFragment(LatestFragment())
                binding.bottomNavigation.selectedItemId =
                    R.id.bottom_latest                         // Highlight the corresponding item in BottomNavigationView
            }
            R.id.nav_prediction -> {
                openFragment(PredictionFragment())
                binding.bottomNavigation.selectedItemId = R.id.bottom_prediction
            }
            R.id.nav_measures -> {
                openFragment(MeasuresFragment())
                binding.bottomNavigation.selectedItemId = R.id.bottom_measures
            }
            R.id.nav_news -> {
                openFragment(NewsFragment())
                binding.bottomNavigation.selectedItemId = R.id.bottom_news
            }
            R.id.nav_setting -> {
                //Open the Account page

            }
            R.id.nav_about -> {
                //Open the About page

            }

        }

        binding.drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    //   HANDLING THE OPENING OF THE FRAGMENTS
    private fun openFragment(fragment: Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container, fragment)
        fragmentTransaction.commit()
    }

    // HANDLING THE OPENING MENU LIST IN APPBAR
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.tool_bar_menu_item, menu)
        return true
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.optAccount -> {
                showToast("You clicked 'Account'")
                val intent = Intent(this, ProfileImagePicker::class.java)
                startActivity(intent)
                true
            }

            R.id.optShare -> {
                showToast("You clicked 'Share'")
                true
            }

            R.id.optAbout -> {
                showToast("You clicked 'About'")
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    private fun showToast(s: String) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }


    //Handling the backPressed button and drawer
    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if(binding.drawerLayout.isDrawerOpen(GravityCompat.START)){
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        }else{
            super.getOnBackPressedDispatcher().onBackPressed()
        }
    }

}