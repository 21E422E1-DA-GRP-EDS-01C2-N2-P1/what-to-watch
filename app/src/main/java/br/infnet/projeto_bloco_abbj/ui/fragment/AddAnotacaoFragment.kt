package br.infnet.projeto_bloco_abbj.ui.fragment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.navigation.fragment.findNavController
import br.infnet.projeto_bloco_abbj.ui.viewmodel.AddAnotacaoViewModel
import br.infnet.projeto_bloco_abbj.R
import br.infnet.projeto_bloco_abbj.data.EXTRA_LIVRO
import br.infnet.projeto_bloco_abbj.data.model.Item
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class AddAnotacaoFragment : Fragment() {

    private lateinit var viewModel: AddAnotacaoViewModel
    private lateinit var txtTitulo: EditText
    private lateinit var txtCorpo: EditText
    private lateinit var btnAdicionar: Button
    private lateinit var livro: Item
    private val user = Firebase.auth.currentUser

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.add_anotacao_fragment, container, false)
        setupWidgets(view)
        livro = arguments?.getSerializable(EXTRA_LIVRO) as Item
        viewModel = ViewModelProvider(this).get(AddAnotacaoViewModel::class.java)
        return view
    }

    override fun onStart() {
        super.onStart()
        if (Firebase.auth.currentUser == null) {
            findNavController().navigate(R.id.signinFragment)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnAdicionar.setOnClickListener {
            if (!haCamposVazios()) {
                viewModel.addAnotacao(
                    txtTitulo.text.toString(),
                    txtCorpo.text.toString(),
                    user!!.uid,
                    livro.id)
                findNavController().popBackStack()
            }
        }

    }

    private fun setupWidgets(view: View) {
        txtTitulo = view.findViewById(R.id.add_anotacao_fragment_txt_titulo)
        txtCorpo = view.findViewById(R.id.add_anotacao_fragment_txt_corpo)
        btnAdicionar = view.findViewById(R.id.add_anotacao_fragment_btn_adicionar)
    }

    private fun haCamposVazios(): Boolean {
        if (txtTitulo.text.isNullOrBlank()) return true
        if (txtCorpo.text.isNullOrBlank()) return true
        if (user?.uid.isNullOrBlank()) return true
        return false
    }

}