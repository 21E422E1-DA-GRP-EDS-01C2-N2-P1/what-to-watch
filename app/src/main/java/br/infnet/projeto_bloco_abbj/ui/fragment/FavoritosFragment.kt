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
import br.infnet.projeto_bloco_abbj.data.EXTRA_LIVRO
import br.infnet.projeto_bloco_abbj.data.model.Item
import br.infnet.projeto_bloco_abbj.ui.adapter.LivroAdapter
import br.infnet.projeto_bloco_abbj.ui.factory.LivrosViewModelFactory
import br.infnet.projeto_bloco_abbj.ui.viewmodel.LivrosViewModel
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback

class FavoritosFragment : Fragment() {

    private lateinit var viewModel: LivrosViewModel
    private lateinit var swiperRefresh: SwipeRefreshLayout
    private lateinit var recyclerListaLivros: RecyclerView
    private lateinit var listaVazia: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_favoritos, container, false)
        setupWidgets(view)
        setupViewModel()
        loadAds()

        viewModel.listarLivrosDaDB()
            .observe(
                this,
                Observer {
                    listaLivrosRecyclerView(it)
                }
            )
        return view
    }

    private fun loadAds() {
        val adRequest = AdRequest.Builder().build()

        InterstitialAd.load(requireContext(),
            getString(R.string.ad_id_test),
            adRequest,
            object : InterstitialAdLoadCallback() {

                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    interstitialAd.show(requireActivity())
                }

                //override fun onAdFailedToLoad(p0: LoadAdError) {}

            })
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

    private fun listItemClicked(book: Item) {
        viewModel.apagarLivroDaDB(book)
        requireActivity().finish();
        requireActivity().overridePendingTransition(0, 0);
        /*startActivity(getIntent());
        overridePendingTransition(0, 0);
        */
        Toast.makeText(requireContext(), "Livro removido :(", Toast.LENGTH_LONG).show()
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

    fun detalhaLivro(livro: Item) {
        findNavController().navigate(R.id.livroDetalhesFragment, bundleOf(EXTRA_LIVRO to livro))
    }

}