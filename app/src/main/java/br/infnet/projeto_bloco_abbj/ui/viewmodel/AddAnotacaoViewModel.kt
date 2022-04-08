package br.infnet.projeto_bloco_abbj.ui.viewmodel

import androidx.lifecycle.ViewModel
import br.infnet.projeto_bloco_abbj.data.database.AnotacaoDao
import br.infnet.projeto_bloco_abbj.data.model.Anotacao

class AddAnotacaoViewModel : ViewModel() {
    fun addAnotacao(titulo: String, corpo: String, userId: String, livroId: String) {
        val anotacao = Anotacao(userId, livroId, titulo, corpo)
        AnotacaoDao.insert(anotacao)
    }
}