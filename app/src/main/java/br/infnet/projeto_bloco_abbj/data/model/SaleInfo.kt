package br.infnet.projeto_bloco_abbj.data.model

import java.io.Serializable

data class SaleInfo(
    val country: String?,
    val saleability: String?,
    val isEbook: Boolean?,
    val buyLink: String?
) : Serializable