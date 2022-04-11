package br.infnet.projeto_bloco_abbj.ui.activity

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import br.infnet.projeto_bloco_abbj.R
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    lateinit var btnNav: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnNav = findViewById(R.id.bottomNavigationView)

    }

    override fun onStart() {
        super.onStart()

        var navController = findNavController(R.id.fragmentContainerView)
        btnNav.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            btnNav.visibility = if(destination.id == R.id.signinFragment || destination.id == R.id.signupFragment) {
                View.GONE
            } else {
                View.VISIBLE
            }
        }

    }

}