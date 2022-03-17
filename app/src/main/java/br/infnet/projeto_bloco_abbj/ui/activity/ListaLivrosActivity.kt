package br.infnet.projeto_bloco_abbj.ui.activity

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import br.infnet.projeto_bloco_abbj.R
import br.infnet.projeto_bloco_abbj.data.AUTOR_LIVRO_REQUEST
import br.infnet.projeto_bloco_abbj.data.TITULO_LIVRO_REQUEST
import br.infnet.projeto_bloco_abbj.data.database.LivroDao
import br.infnet.projeto_bloco_abbj.data.model.Item
import br.infnet.projeto_bloco_abbj.ui.adapter.LivroAdapter
import br.infnet.projeto_bloco_abbj.ui.viewmodel.LivroViewModel
import br.infnet.projeto_bloco_abbj.utils.Utils
import kotlinx.android.synthetic.main.activity_lista_livros.*
import kotlinx.android.synthetic.main.content_lista_livros.*

class ListaLivrosActivity : AppCompatActivity() {
    lateinit var livroViewModel: LivroViewModel
    lateinit var dao: LivroDao
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_livros)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
//        Ativar o swipe para atualizar a tela
        swipe_refresh_list_book.isRefreshing = true
//        Obter título e autor inseridos pelo usuário
        val titulo_livro = intent.getStringExtra(TITULO_LIVRO_REQUEST)
        val autor_livro = intent.getStringExtra(AUTOR_LIVRO_REQUEST)
//        Instanciando a ViewModel
        livroViewModel = LivroViewModel(applicationContext)
        // Verificando a conexão com a internet
        if (Utils.isNetworkConnected(this)) {
            livroViewModel.obterLivrosdaApi(titulo_livro!!, autor_livro!!).observe(this,
                Observer {
                    listaLivrosRecyclerView(it.items)
                })
        } else {
            Toast.makeText(
                this,
                getString(R.string.no_internet_error),
                Toast.LENGTH_LONG
            ).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        livroViewModel.cancelarJobs()
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
                        it
                    ) { selectedItem: Item -> listItemClicked(selectedItem) }
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun listItemClicked(livro: Item) {
        //val exists = dao.exists(book.id)
        //if(!exists){
        livroViewModel.inserirLivroNaDB(livro)
            Toast.makeText(this, getString(R.string.salvar_livro_em_favoritos), Toast.LENGTH_LONG)
                .show()
        /*} else {
            Toast.makeText(this, "Tá lá já", Toast.LENGTH_LONG)
            .show()
        }*/
    }

}