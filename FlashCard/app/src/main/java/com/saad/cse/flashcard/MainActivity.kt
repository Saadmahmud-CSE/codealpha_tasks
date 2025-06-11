package com.saad.cse.flashcard

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        drawerLayout = findViewById(R.id.drawer_layout)
        navigationView = findViewById(R.id.nav_view)
        progressBar = findViewById(R.id.progressBar)

        val toggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar,
            R.string.nav_header_desc,
            R.string.nav_header_desc
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        navigationView.setNavigationItemSelectedListener(this)

        progressBar.visibility = View.GONE

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, FlashcardQuizFragment())
                .commit()
            navigationView.setCheckedItem(R.id.nav_quiz)
        }
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_quiz -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, FlashcardQuizFragment())
                    .commit()
            }
            R.id.nav_add_flashcard -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, AddFlashcardFragment())
                    .commit()
            }
            R.id.nav_score_history -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, ScoreHistoryFragment())
                    .commit()
            }
            R.id.nav_about -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, AboutFragment())
                    .commit()
            }
        }

        drawerLayout.closeDrawer(GravityCompat.START)

        return true
    }
}
