package br.infnet.projeto_bloco_abbj.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.infnet.projeto_bloco_abbj.R
import br.infnet.projeto_bloco_abbj.data.AUTOR_LIVRO_REQUEST
import br.infnet.projeto_bloco_abbj.data.TITULO_LIVRO_REQUEST
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        loadListeners()
    }

    private fun loadListeners() {
        btn_buscar.setOnClickListener {
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
}