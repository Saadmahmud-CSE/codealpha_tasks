package com.saad.cse.randomquotegenerator

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.saad.cse.randomquotegenerator.ui.about.AboutFragment
import com.saad.cse.randomquotegenerator.ui.quote.QuoteFragment

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main) // This points to the new layout with DrawerLayout

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        drawerLayout = findViewById(R.id.drawer_layout)
        val navigationView: NavigationView = findViewById(R.id.nav_view)

        val toggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar,
            R.string.nav_header_desc, // Use a string for "Open navigation drawer"
            R.string.nav_header_desc // Use a string for "Close navigation drawer"
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        navigationView.setNavigationItemSelectedListener(this)

        // Load the initial fragment
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, QuoteFragment())
                .commit()
            navigationView.setCheckedItem(R.id.nav_quotes)
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
            R.id.nav_quotes -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, QuoteFragment())
                    .commit()
            }
            R.id.nav_about -> {
                // Example: Load an About Fragment or show an AlertDialog
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, AboutFragment()) // You'll create this
                    .commit()
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
}