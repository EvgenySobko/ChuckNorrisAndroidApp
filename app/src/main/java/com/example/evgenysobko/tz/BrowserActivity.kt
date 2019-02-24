package com.example.evgenysobko.tz

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.webkit.WebView
import kotlinx.android.synthetic.main.activity_browser.*
import kotlinx.android.synthetic.main.app_bar_browser.*
import android.webkit.WebResourceRequest
import android.os.Build
import android.annotation.TargetApi
import android.webkit.WebViewClient


class BrowserActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private var webView: WebView? = null

    private inner class MyWebViewClient : WebViewClient() {
        @TargetApi(Build.VERSION_CODES.N)
        override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
            view.loadUrl(request.url.toString())
            return true
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_browser)
        setSupportActionBar(toolbar)

        webView = findViewById(R.id.webView)
        webView!!.settings.javaScriptEnabled
        webView!!.loadUrl("http://www.icndb.com/api/")
        webView!!.webViewClient = MyWebViewClient()

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
    }

    override fun onBackPressed() {
        if (webView!!.canGoBack()) {
            webView!!.goBack()
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.browser, menu)
        return true
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_jokes -> {
                startActivity(Intent(this, MainActivity::class.java))
            }
            R.id.nav_info -> {
                startActivity(Intent(this, BrowserActivity::class.java))
            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}
