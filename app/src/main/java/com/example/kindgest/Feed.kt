package com.example.kindgest

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Check if the fragment is being recreated


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.feed, container, false)
    }
}

class DashboardFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.create_post, container, false)
    }
}

class ProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.profile, container, false)
    }
}

class Feed : AppCompatActivity() {
    private var currentFragment: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.feed)
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNav)

        bottomNav.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_home -> {
                    if (currentFragment !is HomeFragment) {
                        currentFragment = HomeFragment()
                        loadFragment(currentFragment!!)
                    }
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.nav_dashboard -> {
                    if (currentFragment !is DashboardFragment) {
                        currentFragment = DashboardFragment()
                        loadFragment(currentFragment!!)
                    }
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.nav_profile -> {
                    if (currentFragment !is ProfileFragment) {
                        currentFragment = ProfileFragment()
                        loadFragment(currentFragment!!)
                    }
                    return@setOnNavigationItemSelectedListener true
                }
                else -> false
            }
        }

        // Load the initial fragment
//        if (savedInstanceState == null) {
//            currentFragment = HomeFragment()
//            loadFragment(currentFragment!!)
//        }
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
            .addToBackStack(null)
            .commit()
    }

//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        val inflater: MenuInflater = menuInflater
//        inflater.inflate(R.menu.bottom_navigation_menu, menu)
//        return true
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        when (item.itemId) {
//            R.id.create_post -> {
//                val intent = Intent(this, CreatePost::class.java)
//                startActivity(intent)
//                return true
//            }
//
//            R.id.feed -> {
//                // Do nothing or handle differently, since you are already on the Feed activity
//                return true
//            }
//            else -> return super.onOptionsItemSelected(item)
//        }
//    }
}

