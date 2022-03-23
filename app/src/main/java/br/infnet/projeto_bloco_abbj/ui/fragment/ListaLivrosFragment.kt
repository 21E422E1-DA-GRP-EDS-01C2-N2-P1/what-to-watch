package br.infnet.projeto_bloco_abbj.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import br.infnet.projeto_bloco_abbj.R
import br.infnet.projeto_bloco_abbj.data.AUTOR_LIVRO_REQUEST
import br.infnet.projeto_bloco_abbj.data.EXTRA_LIVRO
import br.infnet.projeto_bloco_abbj.data.TITULO_LIVRO_REQUEST
import br.infnet.projeto_bloco_abbj.data.model.Item
import br.infnet.projeto_bloco_abbj.ui.adapter.LivroAdapter
import br.infnet.projeto_bloco_abbj.ui.factory.LivrosViewModelFactory
import br.infnet.projeto_bloco_abbj.ui.viewmodel.LivrosViewModel
import br.infnet.projeto_bloco_abbj.utils.Utils

class ListaLivrosFragment : Fragment() {

    private lateinit var viewModel: LivrosViewModel
    private lateinit var swiperRefresh: SwipeRefreshLayout
    private lateinit var recyclerListaLivros: RecyclerView
    private lateinit var listaVazia: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_lista_livros, container, false)
        setupWidgets(view)

        setupViewModel()

        //Ativar o swipe para atualizar a tela
        swiperRefresh.isRefreshing = true

        //Obter título e autor inseridos pelo usuário
        val tituloLivro =
            arguments?.getString(TITULO_LIVRO_REQUEST)//intent.getStringExtra(TITULO_LIVRO_REQUEST)
        val autorLivro = arguments?.getString(AUTOR_LIVRO_REQUEST)

        // Verificando a conexão com a internet
        if (Utils.isNetworkConnected(requireContext())) {
            viewModel.obterLivrosdaApi(tituloLivro!!, autorLivro!!).observe(this,
                Observer {
                    listaLivrosRecyclerView(it.items)
                })
        } else {
            makeToast(getString(R.string.no_internet_error))
        }

        return view
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.cancelarJobs()
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                requireActivity().finish()
                return true
            }
        }
        return super.onContextItemSelected(item)
    }

    private fun listaLivrosRecyclerView(livros: List<Item>) {
        val frag = this
        if (livros.isEmpty()) listaVazia.visibility = View.VISIBLE
        else {
            recyclerListaLivros.apply {
                setHasFixedSize(true)
                swiperRefresh.isRefreshing = false
                layoutManager = LinearLayoutManager(context)
                adapter = livros.let {
                    LivroAdapter(it, frag::detalhaLivro, frag::listItemClicked)
                }
            }
        }
    }

    private fun listItemClicked(livro: Item) {
        //val exists = dao.exists(book.id)
        //if(!exists){
        viewModel.inserirLivroNaDB(livro)
        makeToast(getString(R.string.salvar_livro_em_favoritos))
        /*} else {
            Toast.makeText(this, "Tá lá já", Toast.LENGTH_LONG)
            .show()
        }*/
    }

    fun detalhaLivro(livro: Item) {
        findNavController().navigate(R.id.livroDetalhesFragment, bundleOf(EXTRA_LIVRO to livro))
    }

    private fun makeToast(msg: String) {
        Toast.makeText(requireContext(), msg, Toast.LENGTH_LONG).show()
    }

    private fun setupWidgets(view: View) {
        recyclerListaLivros = view.findViewById(R.id.recycler_view_lista_livros)
        listaVazia = view.findViewById(R.id.lista_vazia)
        swiperRefresh = view.findViewById(R.id.swipe_refresh_list_book)
    }

    private fun setupViewModel() {
        //instanciando a ViewModel e Factory
        val vmFactory = LivrosViewModelFactory(requireContext())
        viewModel = ViewModelProvider(this, vmFactory)
            .get(LivrosViewModel::class.java)
    }
}