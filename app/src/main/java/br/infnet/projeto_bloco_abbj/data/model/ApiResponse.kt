package br.infnet.projeto_bloco_abbj.data.model

data class ApiResponse(
    val kind: String?,
    val totalItems: Int?,
    val items: List<Item>
)