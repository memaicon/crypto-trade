package br.com.tecdev.btctrade.feature.guide

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.com.tecdev.btctrade.R
import kotlinx.android.synthetic.main.activity_guide.*

class GuideActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guide)
        setOnClick()
    }

    private fun setOnClick() {
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

}
