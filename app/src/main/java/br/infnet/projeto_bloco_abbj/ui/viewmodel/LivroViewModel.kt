package br.infnet.projeto_bloco_abbj.ui.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.infnet.projeto_bloco_abbj.data.model.ApiResponse
import br.infnet.projeto_bloco_abbj.data.model.Item
import br.infnet.projeto_bloco_abbj.data.repository.LivroRepository
import kotlinx.coroutines.launch

class LivroViewModel(context: Context) : ViewModel() {

    private lateinit var context: Context
    private lateinit var repositorio: LivroRepository

    init {
        this.context = context
        repositorio = LivroRepository(this.context)
    }

    fun obterLivrosdaApi(titulo_livro: String, autor_livro: String): MutableLiveData<ApiResponse> {
        return repositorio.carregarListaLivros(titulo_livro, autor_livro)
    }

    fun cancelarJobs() {
        repositorio.cancelarJobs()
    }

    fun inserirLivroNaDB(livro: Item) {
        viewModelScope.launch {
            repositorio.inserirLivroDB(livro)
        }
    }

    fun apagarLivroDaDB(livro: Item) {
        viewModelScope.launch {
            repositorio.apagarLivroDB(livro)
        }
    }

    fun listarLivrosDaDB(): LiveData<List<Item>> {
        return repositorio.obterListaLivrosDB()
    }

}