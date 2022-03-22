package br.infnet.projeto_bloco_abbj.ui.activity

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import br.infnet.projeto_bloco_abbj.R
import br.infnet.projeto_bloco_abbj.data.AD_UNIT_ID
import br.infnet.projeto_bloco_abbj.data.model.Item
import br.infnet.projeto_bloco_abbj.ui.adapter.LivroAdapter
import br.infnet.projeto_bloco_abbj.ui.viewmodel.LivroViewModel
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import kotlinx.android.synthetic.main.activity_favoritos.*
import kotlinx.android.synthetic.main.content_lista_livros.*

class FavoritosActivity : AppCompatActivity() {
    lateinit var livroViewModel: LivroViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favoritos)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        swipe_refresh_list_book.isEnabled = false

        /*val adRequest = AdRequest.Builder().build()

        InterstitialAd.load(this,
            //"ca-app-pub-3940256099942544/1033173712",
            AD_UNIT_ID,
            adRequest,
            object: InterstitialAdLoadCallback() {

                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    interstitialAd.show(this@FavoritosActivity)
                }

                //override fun onAdFailedToLoad(p0: LoadAdError) {}

            })*/

        livroViewModel = LivroViewModel(this)
        livroViewModel.listarLivrosDaDB()
            .observe(
                this,
                Observer {
                    listaLivrosRecyclerView(it)
                }
            )
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onContextItemSelected(item)
    }

    private fun listaLivrosRecyclerView(livros: List<Item>) {
        if (livros.isEmpty()) lista_vazia.visibility = View.VISIBLE
        else {
            recycler_view_lista_livros.apply {
                setHasFixedSize(true)
                swipe_refresh_list_book.isRefreshing = false
                layoutManager = LinearLayoutManager(context)
                adapter = livros.let {
                    LivroAdapter(
                        it,
                        { selectedItem: Item -> listItemClicked(selectedItem) })
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun listItemClicked(book: Item) {
        livroViewModel.apagarLivroDaDB(book)
        finish();
        overridePendingTransition(0, 0);
        startActivity(getIntent());
        overridePendingTransition(0, 0);
        Toast.makeText(this, "Livro removido :(", Toast.LENGTH_LONG).show()
    }
}
