package br.infnet.projeto_bloco_abbj.ui.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import br.infnet.projeto_bloco_abbj.R
import br.infnet.projeto_bloco_abbj.ui.viewmodel.SigninViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.io.File

class SigninFragment : Fragment() {

    companion object {
        fun newInstance() = SigninFragment()
    }

    private lateinit var viewModel: SigninViewModel
    private lateinit var editTextSigninEmail: EditText
    private lateinit var editTextSigninSenha: EditText
    private lateinit var checkboxSigninLembrar: CheckBox
    private lateinit var btnSigninEntrar: Button
    private lateinit var textViewSigninCadastrar: TextView
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.signin_fragment, container, false)
        editTextSigninEmail = view.findViewById(R.id.editTextSigninEmail)
        editTextSigninSenha = view.findViewById(R.id.editTextSigninSenha)
        checkboxSigninLembrar = view.findViewById(R.id.checkboxSigninLembrarSenha)
        btnSigninEntrar = view.findViewById(R.id.btnSigninEntrar)
        textViewSigninCadastrar = view.findViewById(R.id.textViewSigninCadastrar)
        auth = Firebase.auth // Instância de FirebaseAuth
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        verificarPreferencesUserEmail()
        verificarLogFile()

        btnSigninEntrar.setOnClickListener{
            val email = editTextSigninEmail.text.toString()
            val senha = editTextSigninSenha.text.toString()
            escreverLogFile("Tentativa de Acesso: \t${email}\n")
            val task = auth.signInWithEmailAndPassword(email, senha)

            task
                .addOnSuccessListener {
                    escreverLogFile("Tentativa de Acesso: \tOK\n")
                    if (checkboxSigninLembrar.isChecked) {
                        salvarPrefUserEmail(email)
                    }
                    findNavController().navigate(R.id.HomeFragment)
            }.addOnFailureListener {
                    showSnackbar(view, it.message.toString())
                    escreverLogFile("Tentativa de Acesso: \tUsuário inválido\n")
            }/*.addOnCanceledListener {

            }.addOnCompleteListener {
                    // em qualquer das situações
            }*/
        }

        textViewSigninCadastrar.setOnClickListener{
            findNavController().navigate(R.id.signupFragment)
        }
//        FirebaseAuth
        /*var currentUser = auth.currentUser
        if (currentUser == null) {
            findNavController().navigate(R.id.signinFragment)
        }*/
    }

    private fun verificarLogFile() {
        val filesDir = requireContext().filesDir
        val logFile = File(filesDir, "logsys.log")
        if (!logFile.exists()) logFile.createNewFile()
        if (logFile.canWrite())
            Log.d("Log File", "Arquivo de Log: OK")
        else
            Log.d("Log File", "Arquivo de Log: Permissão negada")
    }

    private fun escreverLogFile(msgLog: String) {
        val msgLogDateTime = "" + msgLog
        requireContext()
            .openFileOutput("logsys.log", Context.MODE_APPEND)
            .use {
                it.write(msgLog.toByteArray())
                it.close()
            }
    }

    private fun salvarPrefUserEmail(email: String) {
        val sharedPreferences = activity?.getPreferences(Context.MODE_PRIVATE) ?: return

        val editPreferences = sharedPreferences.edit()
        editPreferences.putString("user_email", email)
        Log.d("Email Preference", "set user_email")
        editPreferences.apply()
    }

    private fun verificarPreferencesUserEmail() {
        val sharedPreferences = activity?.getPreferences(Context.MODE_PRIVATE) ?: return

        val userEmail = sharedPreferences.getString("user_email", null)
        Log.d("Email Preference", userEmail ?: "null")
        if(!userEmail.isNullOrBlank()) editTextSigninEmail.setText(userEmail)
    }

    private fun showSnackbar(view: View, msg: String) {
        Snackbar
            .make(view, msg, Snackbar.LENGTH_LONG)
            .show()
    }

}