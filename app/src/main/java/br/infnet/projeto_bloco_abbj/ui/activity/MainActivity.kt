package br.infnet.projeto_bloco_abbj.ui.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import br.infnet.projeto_bloco_abbj.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    lateinit var btnNav: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //carregaAnuncios()

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



    /*private fun carregaAnuncios(){

        val btn_Anuncio = this.findViewById<Button>(R.id.btn_buscar)

        btn_Anuncio.setOnClickListener{

            val adRequest = AdRequest.Builder().build()

            InterstitialAd.load(this,
                "ca-app-pub-3940256099942544/1033173712",
                adRequest,
                object: InterstitialAdLoadCallback() {

                    override fun onAdLoaded(interstitialAd: InterstitialAd) {
                        interstitialAd.show(this@MainActivity)
                    }

                    //override fun onAdFailedToLoad(p0: LoadAdError) {}

                })
        }

        //MobileAds.initialize(this){}

        //val mAdView = this.findViewById<AdView>(R.id.btn_buscar)
        //mAdView.loadAd(adRequest)

    }*/
}