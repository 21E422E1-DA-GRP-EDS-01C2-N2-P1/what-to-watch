package br.infnet.projeto_bloco_abbj.ui.activity

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import br.infnet.projeto_bloco_abbj.R
import br.infnet.projeto_bloco_abbj.data.EXTRA_LIVRO
import br.infnet.projeto_bloco_abbj.data.model.Item
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_livro_detalhes.*
import kotlinx.android.synthetic.main.content_livros_detalhes.*

class LivroDetalhesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_livro_detalhes)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
//        Récupérer l'objet Book
        val livro = intent.getSerializableExtra(EXTRA_LIVRO) as Item

        bindComponents(livro)
    }

    private fun bindComponents(livro: Item) {
        Picasso.with(this)
            .load(livro.volumeInfo?.imageLinks?.smallThumbnail)
            .into(imgCapa)
        tituloLivro.text = livro.volumeInfo?.title
        livro.volumeInfo?.authors?.forEach { author -> autorLivro.text = author }
        livroInfo.text = livro.searchInfo?.textSnippet?.let {
            HtmlCompat.fromHtml(
                it,
                HtmlCompat.FROM_HTML_MODE_LEGACY
            )
        }
        descricaoLivro.text = livro.volumeInfo?.description
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        Log.d("item id", item.itemId.toString())
        Log.d("android.R.id.home", android.R.id.home.toString())
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onContextItemSelected(item)
    }
}