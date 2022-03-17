package br.infnet.projeto_bloco_abbj.data.api

import br.infnet.projeto_bloco_abbj.data.model.ApiResponse
import br.infnet.projeto_bloco_abbj.data.LIVRO_URL
import retrofit2.http.GET
import retrofit2.http.Query

interface ILivroApi {
    @GET(LIVRO_URL)
    suspend fun obterLivrosdaApi(
        @Query("q") titulo_livro: String,
        @Query("inauthor") autor_livro: String
    ): ApiResponse
}