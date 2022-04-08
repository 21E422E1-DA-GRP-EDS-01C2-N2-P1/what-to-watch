package br.infnet.projeto_bloco_abbj.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import br.infnet.projeto_bloco_abbj.R
import br.infnet.projeto_bloco_abbj.data.ANOTACAO_ID_REQUEST
import br.infnet.projeto_bloco_abbj.data.EXTRA_LIVRO
import br.infnet.projeto_bloco_abbj.data.model.Item
import br.infnet.projeto_bloco_abbj.ui.adapter.AnotacaoAdapter
import br.infnet.projeto_bloco_abbj.ui.viewmodel.ListaAnotacoesViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class ListaAnotacoesFragment : Fragment() {

    private lateinit var viewModel: ListaAnotacoesViewModel
    private lateinit var lblTitulo: TextView
    private lateinit var fabAddAnotacao: FloatingActionButton
    private lateinit var recyclerAnotacoes: RecyclerView
    private lateinit var livro: Item

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.lista_anotacoes_fragment, container, false)
        setupWidgets(view)
        val userId = Firebase.auth.currentUser?.uid
        livro = arguments?.getSerializable(EXTRA_LIVRO) as Item
        setupViewModel(userId, livro)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lblTitulo.text = livro.volumeInfo?.title
        fabAddAnotacao.setOnClickListener {
            findNavController().navigate(R.id.addAnotacaoFragment,
                bundleOf(EXTRA_LIVRO to livro))
        }
    }

    private fun setupViewModel(
        userId: String?,
        livro: Item
    ) {
        viewModel = ViewModelProvider(this).get(ListaAnotacoesViewModel::class.java)
        if (!userId.isNullOrBlank()) {
            viewModel.setupListener(userId, livro.id)
        }
        viewModel.anotacoes.observe(viewLifecycleOwner) {
            if (!it.isNullOrEmpty()) {
                recyclerAnotacoes.adapter = AnotacaoAdapter(it,
                    this::deletaAnotacao,
                    this::detalhaAnotacao
                )
            }
        }
        viewModel.msg.observe(viewLifecycleOwner) {
            if (!it.isNullOrBlank()) {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupWidgets(view: View) {
        lblTitulo = view.findViewById(R.id.lista_anotacoes_fragment_lbl_livro)
        fabAddAnotacao = view.findViewById(R.id.lista_anotacoes_fragment_fab_add_anotacao)
        recyclerAnotacoes = view.findViewById(R.id.lista_anotacoes_fragment_recycler_anotacoes)
    }

    private fun deletaAnotacao(position: Int) {
        viewModel.deleteItem(position)
    }

    private fun detalhaAnotacao(docId: String) {
        findNavController().navigate(
            R.id.detalhaAnotacaoFragment,
            bundleOf(ANOTACAO_ID_REQUEST to docId)
        )
    }

}