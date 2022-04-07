package br.infnet.projeto_bloco_abbj.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.infnet.projeto_bloco_abbj.data.model.Item

class FavoritosViewModel {
    private val _livros = MutableLiveData<List<Item>>()
    val livros: LiveData<List<Item>> = _livros


}