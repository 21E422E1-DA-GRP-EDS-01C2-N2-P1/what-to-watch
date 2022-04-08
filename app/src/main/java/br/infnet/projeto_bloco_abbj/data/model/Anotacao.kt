package br.infnet.projeto_bloco_abbj.data.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId

data class Anotacao(
    val userId: String = "",
    val livroId: String = "",
    var titulo: String = "",
    var corpo: String = "",
    val data: Timestamp = Timestamp.now(),
    @DocumentId val id: String? = null
)
