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
import br.infnet.projeto_bloco_abbj.ui.viewmodel.DetalhaAnotacaoViewModel
import br.infnet.projeto_bloco_abbj.R
import br.infnet.projeto_bloco_abbj.data.ANOTACAO_ID_REQUEST
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat

class DetalhaAnotacaoFragment : Fragment() {

    private lateinit var viewModel: DetalhaAnotacaoViewModel
    private lateinit var txtTitulo: EditText
    private lateinit var txtCorpo: EditText
    private lateinit var btnSalvar: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.detalha_anotacao_fragment, container, false)
        setupWidgets(view)
        val docId = arguments?.getString(ANOTACAO_ID_REQUEST)
        viewModel = ViewModelProvider(this).get(DetalhaAnotacaoViewModel::class.java)
        if (!docId.isNullOrBlank()) viewModel.getAnotacao(docId)
        viewModel.anotacao.observe(viewLifecycleOwner) {
            txtTitulo.setText(it.titulo)
            txtCorpo.setText(it.corpo)
        }
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
        btnSalvar.setOnClickListener {
            if (!txtTitulo.text.isNullOrBlank() && !txtCorpo.text.isNullOrBlank()) {
                val titulo = txtTitulo.text.toString()
                val corpo = txtCorpo.text.toString()
                viewModel.salvar(titulo, corpo)
                findNavController().popBackStack()
             }
        }
    }


    private fun setupWidgets(view: View) {
        txtTitulo = view.findViewById(R.id.detalha_anotacao_fragment_txt_titulo)
        txtCorpo = view.findViewById(R.id.detalha_anotacao_fragment_txt_corpo)
        btnSalvar = view.findViewById(R.id.detalha_anotacao_fragment_btn_salvar)
    }

}