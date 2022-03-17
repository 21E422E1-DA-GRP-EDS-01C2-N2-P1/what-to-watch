package br.infnet.projeto_bloco_abbj.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import br.infnet.projeto_bloco_abbj.data.DB_NOME
import br.infnet.projeto_bloco_abbj.data.model.Item
import br.infnet.projeto_bloco_abbj.utils.StringListConverter

@Database(entities = [Item::class], version = 1, exportSchema = false)
@TypeConverters(StringListConverter::class)
abstract class LivroDb  : RoomDatabase() {

    abstract fun livroDao(): LivroDao

    companion object {
        @Volatile
        private var instance: LivroDb? = null

        fun getInstance(context: Context) = instance ?: synchronized(this) {
            instance ?: buildDataBase(context).also { instance = it }
        }

        private fun buildDataBase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                LivroDb::class.java,
                DB_NOME
            )
                .build()
    }

}