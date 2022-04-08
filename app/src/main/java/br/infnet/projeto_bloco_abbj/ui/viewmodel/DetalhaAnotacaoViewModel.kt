package br.infnet.projeto_bloco_abbj.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.infnet.projeto_bloco_abbj.data.database.AnotacaoDao
import br.infnet.projeto_bloco_abbj.data.model.Anotacao

class DetalhaAnotacaoViewModel : ViewModel() {
    private val _anotacao = MutableLiveData<Anotacao>()
    var anotacao: LiveData<Anotacao> = _anotacao

    fun getAnotacao(docId: String) {
        val snapshot = AnotacaoDao.read(docId)
        snapshot.addOnSuccessListener {
            _anotacao.value = it.toObject(Anotacao::class.java)
        }
    }

    fun salvar(titulo: String, corpo: String) {
        _anotacao.value?.titulo = titulo
        _anotacao.value?.corpo = corpo
        AnotacaoDao.update(_anotacao.value!!)
    }
}