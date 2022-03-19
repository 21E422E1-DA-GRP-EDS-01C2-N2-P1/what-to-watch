package br.infnet.projeto_bloco_abbj.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.infnet.projeto_bloco_abbj.R
import br.infnet.projeto_bloco_abbj.data.AUTOR_LIVRO_REQUEST
import br.infnet.projeto_bloco_abbj.data.TITULO_LIVRO_REQUEST
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        loadListeners()

        //carregaAnuncios()

    }

    private fun loadListeners() {
        btn_buscar.setOnClickListener {

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

            if (tituloLivro.text.isNotEmpty() || autorLivro.text.isNotEmpty()) {
                val mIntent = Intent(this@MainActivity, ListaLivrosActivity::class.java)
                mIntent.putExtra(TITULO_LIVRO_REQUEST, tituloLivro.text.toString())
                mIntent.putExtra(AUTOR_LIVRO_REQUEST, autorLivro.text.toString())
                startActivity(mIntent)
            } else {
                Toast.makeText(
                    this,
                    getString(R.string.field_required),
                    Toast.LENGTH_LONG
                ).show()
            }

        }

        btn_favoritos.setOnClickListener {
            val mIntent = Intent(this@MainActivity, FavoritosActivity::class.java)
            startActivity(mIntent)
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