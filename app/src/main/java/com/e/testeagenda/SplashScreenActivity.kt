package com.e.testeagenda

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity

class SplashScreenActivity : AppCompatActivity() {
    // Tempo da splash screen
    //private static final int SPLASH_TIME_OUT = 3000;
    /*private var progressBar: ProgressBar? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_screen_splash)
        progressBar = findViewById(R.id.progress_splash_bar)
        val maxSlashTime = 3000
        var progressSplash = 0
        val percentResult = maxSlashTime / 100

        // Mostrar a ProgressBar na tela
        for (i in 0..100) {
            Handler().postDelayed({progressBar.getProgress() }, progressSplash.toLong())
            progressSplash += percentResult
        }

        // Exibindo o splash com o timer
        Handler().postDelayed({


            // Esse metodo sera executado sempre que o timer acabar
            // E inicia a activity principal
            val intent = Intent(this@SplashScreenActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, maxSlashTime.toLong())
    }*/
}