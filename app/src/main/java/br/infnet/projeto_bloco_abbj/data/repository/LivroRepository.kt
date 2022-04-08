package br.infnet.projeto_bloco_abbj.data.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.infnet.projeto_bloco_abbj.data.api.ILivroApi
import br.infnet.projeto_bloco_abbj.data.api.ServiceBuilder
import br.infnet.projeto_bloco_abbj.data.database.RoomViewModelApp
import br.infnet.projeto_bloco_abbj.data.model.ApiResponse
import br.infnet.projeto_bloco_abbj.data.model.Item
import kotlinx.coroutines.*

class LivroRepository(context: Context) {

    private lateinit var context: Context
    private var obterLivrosdaApiJob: CompletableJob? = null
    private var inserirLivronaDbJob: CompletableJob? = null

    init {
        this.context = context
    }


    fun carregarListaLivros(
        titulo_livro: String,
        autor_livro: String
    ): MutableLiveData<ApiResponse> {
        obterLivrosdaApiJob = Job()
        return object : MutableLiveData<ApiResponse>() {
            override fun onActive() {
                super.onActive()
                obterLivrosdaApiJob?.let { theJob ->
                    CoroutineScope(Dispatchers.IO + theJob).launch {
                        try {
                            val livros = ServiceBuilder.buildService(ILivroApi::class.java)
                                .obterLivrosdaApi(titulo_livro, autor_livro)
                            withContext(Dispatchers.Main) {
                                value = livros
                                theJob.complete()
                            }
                        } catch (e: Exception) {
                            Log.d("Erro ao carregar a lista", e.toString())
                        }
                    }
                }
            }
        }
    }

    fun cancelarJobs() {
        obterLivrosdaApiJob?.cancel()
    }

    suspend fun inserirLivroDB(livro: Item) {
        RoomViewModelApp.provideDao(context)
            .livroDao()
            .inserirLivro(livro)
    }

    suspend fun apagarLivroDB(livro: Item) {
        RoomViewModelApp.provideDao(context)
            .livroDao()
            .apagarLivro(livro)
    }

    fun obterListaLivrosDB(): LiveData<List<Item>> {
        return RoomViewModelApp.provideDao(context)
            .livroDao()
            .obterLivrosdaDB()
    }

}