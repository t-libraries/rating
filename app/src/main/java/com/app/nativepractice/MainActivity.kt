package com.app.nativepractice

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.android.reviewdialog.ReviewDialog
import com.app.nativepractice.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var bind: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bind.root)

        ReviewDialog.showRateusDialog(
            this,
            arrayListOf(
                getDrawable(R.drawable.ic_profile1),
                getDrawable(R.drawable.ic_profile1),
                getDrawable(R.drawable.ic_profile1)
            ),
            arrayListOf("adflkjd", "adflkajsdf", "adflasjdf"),
            arrayListOf("sdfjlaskdjf" , "alsdkfjaodfi", "sdfjlsdfjihtrhyadsfh")
        )

    }

}