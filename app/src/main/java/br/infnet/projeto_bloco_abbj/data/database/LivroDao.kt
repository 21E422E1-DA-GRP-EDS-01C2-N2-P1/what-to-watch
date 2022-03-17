package br.infnet.projeto_bloco_abbj.data.database

import androidx.lifecycle.LiveData
import androidx.room.*
import br.infnet.projeto_bloco_abbj.data.model.Item

@Dao
interface LivroDao {
    @Query("SELECT * FROM book_table")
    fun obterLivrosdaDB(): LiveData<List<Item>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun inserirLivro(livro: Item)

    @Delete
    suspend fun apagarLivro(livro: Item)

    @Query("SELECT EXISTS (SELECT 1 FROM book_table WHERE id = :id)")
    fun exists(id: String): Boolean
}