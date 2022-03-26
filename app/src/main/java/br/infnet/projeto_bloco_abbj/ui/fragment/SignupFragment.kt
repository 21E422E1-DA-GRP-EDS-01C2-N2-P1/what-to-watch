package br.infnet.projeto_bloco_abbj.ui.fragment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import br.infnet.projeto_bloco_abbj.R
import br.infnet.projeto_bloco_abbj.ui.viewmodel.SignupViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SignupFragment : Fragment() {

    companion object {
        fun newInstance() = SignupFragment()
    }

    private lateinit var editTextSignupEmail: EditText
    private lateinit var editTextSignupSenha: EditText
    private lateinit var editTextSignupRepitaSenha: EditText
    private lateinit var btnSignupCadastrar: Button
    private lateinit var textViewSignupLogar: TextView
    private lateinit var viewModel: SignupViewModel
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var view = inflater.inflate(R.layout.signup_fragment, container, false)
        auth = Firebase.auth

        editTextSignupEmail = view.findViewById(R.id.editTextSignupEmail)
        editTextSignupSenha = view.findViewById(R.id.editTextSignupSenha)
        editTextSignupRepitaSenha = view.findViewById(R.id.editTextSignupRepitaSenha)
        btnSignupCadastrar = view.findViewById(R.id.btnSignupCadastrar)
        textViewSignupLogar = view.findViewById(R.id.textViewSignupLogar)

        viewModel = ViewModelProvider(this).get(SignupViewModel::class.java)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        textViewSignupLogar.setOnClickListener{
            findNavController().navigate(R.id.signinFragment)
        }

        btnSignupCadastrar.setOnClickListener{
            var email = editTextSignupEmail.text.toString()
            var senha = editTextSignupSenha.text.toString()
            var repetirSenha = editTextSignupRepitaSenha.text.toString()

            if (senha.compareTo(repetirSenha) == 0){
                var task = auth.createUserWithEmailAndPassword(email, senha)
                task.addOnCompleteListener {
                    if (it.isSuccessful && it.result != null){
                        Log.d("FirebaseAuth", it.result?.user.toString())
                        showSnackbar(view, "Cadastrado com sucesso! Seja bem-vindo!")
                        findNavController().navigate(R.id.signinFragment)

                    } else{
                        Log.d("FirebaseAuth", it.exception?.message.toString())
                        showSnackbar(view, it.exception?.message.toString())
                    }
                }
            }else{
                Log.d("FirebaseAuth", "Senhas não conferem")
                showSnackbar(view, "Senhas não conferem")
            }
        }


    }

    private fun showSnackbar(view: View, msg: String) {
        Snackbar
            .make(view, msg, Snackbar.LENGTH_LONG)
            .show()
    }





}