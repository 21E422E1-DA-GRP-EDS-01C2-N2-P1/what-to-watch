package br.infnet.projeto_bloco_abbj.data.database

import android.app.Application
import android.content.Context

object RoomViewModelApp  : Application() {

    var database: LivroDb? = null

    fun provideDao(context: Context): LivroDb {
        return LivroDb.getInstance(context)
    }
}