package br.infnet.projeto_bloco_abbj.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import br.infnet.projeto_bloco_abbj.R

@Suppress("DEPRECATION")
class SplashActivity : AppCompatActivity() {

    // This is the loading time of the splash screen
    private val timedelay:Long = 3000 // 1 sec

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide() // Para remover a title bar
        setContentView(R.layout.activity_splash)

        Handler().postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 3000) // 3000 is the delayed time in milliseconds.
    }
}