package br.infnet.projeto_bloco_abbj.data.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "book_table")
data class Item(
    val kind: String?,
    @PrimaryKey
    val id: String,
    val etag: String?,
    val selfLink: String?,
    @Embedded val volumeInfo: VolumeInfo?,
    @Embedded val saleInfo: SaleInfo?,
//    @Embedded val accessInfo: AccessInfo?,
    @Embedded val searchInfo: SearchInfo?,
) : Serializable