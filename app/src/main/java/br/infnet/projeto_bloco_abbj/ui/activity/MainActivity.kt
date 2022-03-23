package br.infnet.projeto_bloco_abbj.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.infnet.projeto_bloco_abbj.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //carregaAnuncios()

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