package br.infnet.projeto_bloco_abbj.data.database

import br.infnet.projeto_bloco_abbj.data.model.Anotacao
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

object AnotacaoDao {
    val collection = Firebase.firestore.collection("anotacoes")

    fun insert(anotacao: Anotacao): Task<DocumentReference> {
        return collection.add(anotacao)
    }

    fun listWhereUserIdAndBook(userId: String, bookId: String): Query {
        return collection
            .whereEqualTo("userId", userId)
            .whereEqualTo("livroId", bookId)

    }

    fun delete(anotacao: Anotacao?): Task<Void> {
        return collection.document(anotacao?.id!!).delete()
    }

    fun read(docId: String):Task<DocumentSnapshot> {
        return collection.document(docId).get()
    }

    fun update(anotacao: Anotacao):Task<Void> {
        return collection.document(anotacao.id!!).set(anotacao)
    }
}