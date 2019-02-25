package com.example.evgenysobko.tz

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.design.widget.TextInputLayout
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

open class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private val url = "http://api.icndb.com/jokes/random/"
    private var jokes: MutableList<Joke> = mutableListOf()
    private var count: Int = 0
    private var okHttpClient: OkHttpClient = OkHttpClient()
    private var inputtedText: TextInputLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        nav_view.setNavigationItemSelectedListener(this)

        inputtedText = findViewById(R.id.input_text)

        val recyclerView = findViewById<RecyclerView>(R.id.list_of_jokes)
        val linearLayoutManager = LinearLayoutManager(this)
        val adapter = Adapter(jokes)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = linearLayoutManager
        adapter.notifyDataSetChanged()

        button2.setOnClickListener {
            jokes.clear()
            val stringNum: String = inputtedText!!.editText!!.text.toString()
            if (stringNum == "" || stringNum == "0" ) {
                Toast.makeText(applicationContext, "Enter the correct number of jokes", Toast.LENGTH_LONG).show()
            } else {

                // Sometimes it crashes when you try to load a lot of jokes, sometimes it doesn't.
                // I dunno know why that's happening.

                count = inputtedText!!.editText!!.text.toString().toInt()
                for (i in 1..count) {
                    loadRandomFact()
                }
            }
        }
    }

    private fun loadRandomFact() {
        val request: Request = Request.Builder().url(url).build()
        okHttpClient.newCall(request).enqueue(object : Callback {

            override fun onFailure(call: Call?, e: IOException?) {}

            override fun onResponse(call: Call?, response: Response?) {
                val json = response?.body()?.string()
                val txt = (JSONObject(json).getJSONObject("value").get("joke")).toString()
                txt.replace("&quot;", "")
                jokes.add(Joke(txt))
            }
        })

    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
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
