package br.infnet.projeto_bloco_abbj.data.model

import androidx.room.Embedded
import br.infnet.projeto_bloco_abbj.data.model.ImageLinks
import java.io.Serializable

data class VolumeInfo(
    val title: String?,
    val authors: List<String>?,
    val publishedDate: String?,
    val printType: String?,
    val description: String?,
    val maturityRating: String?,
    val allowAnonLogging: Boolean?,
    val contentVersion: String?,
    @Embedded val imageLinks: ImageLinks?,
    val language: String?,
    val previewLink: String?,
    val infoLink: String?,
    val canonicalVolumeLink: String?,
    val subtitle: String?,
    val pageCount: Int?,
) : Serializable