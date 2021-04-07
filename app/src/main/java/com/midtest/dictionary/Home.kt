package com.midtest.dictionary

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.viewpager.widget.ViewPager
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.midtest.dictionary.adapter.ViewPagerAdapter
import com.midtest.dictionary.model.Menu

class Home : Activity() {
    private lateinit var viewPager: ViewPager
    private lateinit var readPage: Button
    private lateinit var username: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        username = intent.getStringExtra("username").toString()
        readPage = findViewById(R.id.home_readmore)
        readPage.setOnClickListener(View.OnClickListener {
            val intent = Intent(this@Home, Translate::class.java)
            intent.putExtra("username", username)
            startActivity(intent)
        })
        ViewPagerData()
    }

    fun ViewPagerData() {
        val allMenu = ArrayList<Menu>()
        allMenu.add(Menu(R.drawable.ic_i_trans, title="Translate", desc="Translate words from one language to another"))
        allMenu.add(Menu(R.drawable.ic_sand, title="History", desc="Beautiful journey from translation word by word in your life story"))
        allMenu.add(Menu(R.drawable.ic_prof, title="Profile", desc="See a wonderful user account in this apps"))

        viewPager = findViewById(R.id.home_viewpager)
        val viewPagerAdapter = ViewPagerAdapter(allMenu, this)
        viewPager.adapter = viewPagerAdapter
        viewPager.setPadding(50, 0, 50, 0)
        var viewPagerPageChangeListener: ViewPager.OnPageChangeListener = object : ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(state: Int) { }
            override fun onPageScrolled(position: Int,positionOffset: Float,positionOffsetPixels: Int) {

            }
            override fun onPageSelected(position: Int) {
                when(position) {
                    0 -> {
                        readPage.text = "Lets Translate"
                        readPage.setOnClickListener(View.OnClickListener {
                            val intent = Intent(this@Home, Translate::class.java)
                            intent.putExtra("username", username)
                            startActivity(intent)
                        })
                    }
                    1 -> {
                        readPage.text = "See History"
                        readPage.setOnClickListener(View.OnClickListener {
                            val intent = Intent(this@Home, HistoryTranslate::class.java)
                            intent.putExtra("username", username)
                            startActivity(intent)
                        })
                    }
                    2 -> {
                        readPage.text = "Jump to Profile"
                        readPage.setOnClickListener(View.OnClickListener {
                            val intent = Intent(this@Home, UserDetail::class.java)
                            intent.putExtra("username", username)
                            startActivity(intent)
                        })
                    }
                }
            }
        }
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener)
    }

}

