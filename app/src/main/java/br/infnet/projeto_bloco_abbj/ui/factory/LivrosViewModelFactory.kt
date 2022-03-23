package br.infnet.projeto_bloco_abbj.ui.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.infnet.projeto_bloco_abbj.ui.viewmodel.LivrosViewModel

class LivrosViewModelFactory(private val context: Context): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(LivrosViewModel::class.java)) {
            return LivrosViewModel(context) as T
        }
        throw IllegalArgumentException("ViewModel incompatível")
    }
}
