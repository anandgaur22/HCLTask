package com.anandgaur.hcltask.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.anandgaur.hcltask.R
import com.anandgaur.hcltask.ui.home.HomeActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        CoroutineScope(Dispatchers.IO).launch {
            delay(2000)

            val intent = Intent(this@SplashActivity, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }



    }
}