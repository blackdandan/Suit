package com.alone.suit

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.webkit.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.work.*
import com.alone.suit.worker.TestWork
import com.google.android.material.navigation.NavigationView
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    /**
     * LOG TAG
     */
    companion object {
        private const val TAG = "MainActivity"
    }

    var appBarConfiguration: AppBarConfiguration? = null

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initNavHost()
        val workRequest = PeriodicWorkRequestBuilder<TestWork>(PeriodicWorkRequest.MIN_PERIODIC_INTERVAL_MILLIS, TimeUnit.MILLISECONDS).build()
        WorkManager.getInstance(this).enqueue(workRequest)
    }

    private fun initNavHost() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
        val navController = navHostFragment.navController
        val drawerLayout = findViewById<DrawerLayout>(R.id.drawer)
        val navigationView = findViewById<NavigationView>(R.id.navigation)
        appBarConfiguration =
            AppBarConfiguration.Builder(navController.graph).setDrawerLayout(drawerLayout).build()
        val toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.app_name, R.string.app_name)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        NavigationUI.setupWithNavController(navigationView, navController)
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration!!)
    }


    override fun onBackPressed() {
        Log.d(TAG, "onBackPressed: ")
        super.onBackPressed()
    }

    override fun navigateUpTo(upIntent: Intent?): Boolean {
        Log.d(TAG, "navigateUpTo: ")
        return super.navigateUpTo(upIntent)
    }

    override fun onSupportNavigateUp(): Boolean {
        Log.d(TAG, "onSupportNavigateUp: ")
        val navController = findNavController(R.id.nav_host)
        return navController.navigateUp(appBarConfiguration!!) || super.onSupportNavigateUp()
    }
}