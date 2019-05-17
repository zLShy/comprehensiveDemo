package com.shy.zlread

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import kotlinx.android.synthetic.main.activity_md_details.*

class FruitDetailsActivity : AppCompatActivity() {
    private var mImageId = 0
    private lateinit var mName: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_md_details)

        setSupportActionBar(toolbar_detail)
        mImageId = intent.getIntExtra("id", mImageId)

        mName = intent.getStringExtra("name")

        collapsing_toolbar.title = mName
        image_detail.setImageResource(mImageId)
        details_tv.text = generateFruitContent(mName)
    }


    private fun generateFruitContent(fruitName: String): String {
        val fruitContent = StringBuilder()
        for (i in 0..499) {
            fruitContent.append(fruitName)
        }
        return fruitContent.toString()
    }
}
