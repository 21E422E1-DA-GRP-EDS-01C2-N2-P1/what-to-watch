package br.infnet.projeto_bloco_abbj.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.infnet.projeto_bloco_abbj.R
import br.infnet.projeto_bloco_abbj.data.model.Item
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.livro_item.view.*

class FavoritosAdapter(val livros: List<Item>,
                       val deleteItem: (Item) -> Unit,
                       val detalhaLivro: (Item) -> Unit,
                       val abreAnotacoes: (Item) -> Unit)
    : RecyclerView.Adapter<FavoritosAdapter.FavoritosAdapterViewHolder>() {
    lateinit var context: Context

    class FavoritosAdapterViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val imgFoto = itemView.findViewById<ImageView>(R.id.favorito_item_imagem)
        val lblTitulo = itemView.findViewById<TextView>(R.id.favorito_item_lbl_titulo)
        val lblAutor = itemView.findViewById<TextView>(R.id.favorito_item_lbl_autor)
        val btnDelete = itemView.findViewById<ImageButton>(R.id.favorito_item_btn_delete)
        val btnEdit = itemView.findViewById<ImageButton>(R.id.favorito_item_btn_editar)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
    : FavoritosAdapterViewHolder {
        context = parent.context
        val view =LayoutInflater
            .from(context)
            .inflate(R.layout.favorito_item, parent, false)
        return FavoritosAdapterViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavoritosAdapterViewHolder, position: Int) {
        val livro = livros[position]
        holder.lblTitulo.text = livro.volumeInfo?.title
        Picasso.with(context)
            .load(livro.volumeInfo?.imageLinks?.smallThumbnail)
            .into(holder.imgFoto)
        livro.volumeInfo?.authors?.forEach { autor ->
            holder.lblAutor.text = autor
        }
        holder.btnDelete.setOnClickListener {
            deleteItem(livro)
        }
        holder.btnEdit.setOnClickListener {
            abreAnotacoes(livro)
        }
        holder.itemView.setOnClickListener {
            detalhaLivro(livro)
        }
    }

    override fun getItemCount(): Int = livros.size


}