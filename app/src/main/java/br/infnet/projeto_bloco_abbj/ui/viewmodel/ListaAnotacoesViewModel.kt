package br.infnet.projeto_bloco_abbj.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.infnet.projeto_bloco_abbj.data.database.AnotacaoDao
import br.infnet.projeto_bloco_abbj.data.model.Anotacao

class ListaAnotacoesViewModel : ViewModel() {
    private val _anotacoes = MutableLiveData<List<Anotacao>>()
    val anotacoes: LiveData<List<Anotacao>> = _anotacoes

    private val _msg = MutableLiveData<String>()
    val msg: LiveData<String> = _msg

    fun setupListener(userId: String, livroId: String) {
        AnotacaoDao
            .listWhereUserIdAndBook(userId, livroId)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    _msg.value = error.message
                } else if (snapshot != null && !snapshot.isEmpty) {
                    _anotacoes.value = snapshot.toObjects(Anotacao::class.java)
                }
            }
    }

    fun deleteItem(index: Int) {
        try {
            val anotacao = _anotacoes.value?.get(index)
            AnotacaoDao.delete(anotacao)
        } catch (e: Exception) {
            _msg.value = e.message
        }
    }
}