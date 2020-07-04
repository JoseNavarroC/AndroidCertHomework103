package com.android.cert103

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_details.*

class DetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        intent.extras?.let {
            tvDetailsContent?.text = it.getString(EXTRA_DATA) ?: ""
        }
    }

    companion object {
        const val EXTRA_DATA = "extra.data"
    }

}