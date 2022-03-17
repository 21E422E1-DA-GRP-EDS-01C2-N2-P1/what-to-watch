package br.infnet.projeto_bloco_abbj.data.model

import java.io.Serializable

data class SaleInfo(
    val country: String?, // FR
    val saleability: String?, // FREE
    val isEbook: Boolean?, // true
    val buyLink: String?
) : Serializable