package com.shy.zlread

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.design.widget.NavigationView
import android.support.design.widget.Snackbar
import android.support.v4.view.GravityCompat
import android.support.v7.widget.GridLayoutManager
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.shy.zlread.adapter.FruitAdapter
import com.shy.zlread.bean.Fruit
import kotlinx.android.synthetic.main.activity_md.*
import java.util.*

class MDActivity : AppCompatActivity() {
    private var fruits = arrayOf<Fruit>(Fruit("Apple", R.drawable.apple), Fruit("Banana", R.drawable.banana), Fruit("Orange", R.drawable.orange), Fruit("Watermelon", R.drawable.watermelon), Fruit("Pear", R.drawable.pear), Fruit("Grape", R.drawable.grape), Fruit("Pineapple", R.drawable.pineapple), Fruit("Strawberry", R.drawable.strawberry), Fruit("Cherry", R.drawable.cherry), Fruit("Mango", R.drawable.mango))

    private var fruitList = ArrayList<Fruit>()
    private lateinit var mAdapter: FruitAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_md)
        setSupportActionBar(toolbar_view)
        var actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu)
        }
        nav_view.setCheckedItem(R.id.call)
        nav_view.setNavigationItemSelectedListener(object : NavigationView.OnNavigationItemSelectedListener {
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                drawlayot_view.closeDrawers()
                return true
            }

        })
        initFruits()
        mAdapter = FruitAdapter(fruitList)
        var manger = GridLayoutManager(this, 2)
        recycler_view.layoutManager = manger
        recycler_view.adapter = mAdapter

        swiplayout.setOnRefreshListener {
            swiplayout.isRefreshing = false
        }

        fab.setOnClickListener { v ->
            Snackbar.make(v, "fab click~", Snackbar.LENGTH_SHORT).setAction("undo", object : View.OnClickListener {
                override fun onClick(v: View?) {
                    Toast.makeText(this@MDActivity, "Data restored", Toast.LENGTH_SHORT).show()

                }

            }).show()
        }

        bottom_nav.setOnNavigationItemSelectedListener(object : BottomNavigationView.OnNavigationItemSelectedListener {
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                when (item.itemId) {
                    R.id.home -> {
                        Toast.makeText(this@MDActivity, "home click~", Toast.LENGTH_SHORT).show()
                    }
                    R.id.seting -> {
                        Toast.makeText(this@MDActivity, "seting click~", Toast.LENGTH_SHORT).show()
                    }
                    R.id.news -> {
                        Toast.makeText(this@MDActivity, "news click~", Toast.LENGTH_SHORT).show()
                    }
                    R.id.center -> {
                        Toast.makeText(this@MDActivity, "center click~", Toast.LENGTH_SHORT).show()
                    }

                }
                return true
            }

        })
    }

    private fun initFruits() {
        fruitList.clear()
        for (i in 0..49) {
            var random = Random()
            var index = random.nextInt(fruits.size)
            fruitList.add(fruits[index])
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        menuInflater.inflate(R.menu.actionbar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                drawlayot_view.openDrawer(GravityCompat.START)
            }

            R.id.backup -> {
                Toast.makeText(this@MDActivity, "backup click~", Toast.LENGTH_SHORT).show()
            }
            R.id.delete -> {
                Toast.makeText(this@MDActivity, "delete click~", Toast.LENGTH_SHORT).show()

            }
            R.id.seting -> {
                Toast.makeText(this@MDActivity, "seting click~", Toast.LENGTH_SHORT).show()
            }
        }
        return true
    }
}
