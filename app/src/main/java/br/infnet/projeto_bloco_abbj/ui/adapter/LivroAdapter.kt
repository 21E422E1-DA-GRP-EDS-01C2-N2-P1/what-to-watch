package br.infnet.projeto_bloco_abbj.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.RecyclerView
import br.infnet.projeto_bloco_abbj.R
import br.infnet.projeto_bloco_abbj.data.model.Item
import br.infnet.projeto_bloco_abbj.data.repository.LivroRepository
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.livro_item.view.*

class LivroAdapter(
    val listaLivros: List<Item>,
    val detalhaLivro: (Item) -> Unit,
    val clickListener: (Item) -> Unit
) :
    RecyclerView.Adapter<LivroAdapter.LivroViewHolder>() {
    lateinit var context: Context
    lateinit var livroRepositorio: LivroRepository

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LivroViewHolder {
        context = parent.context
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.livro_item, parent, false)
        return LivroViewHolder(view)
    }

    class LivroViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(livro: Item, clickListener: (Item) -> Unit) {
            Picasso.with(itemView.context)
                .load(livro.volumeInfo?.imageLinks?.smallThumbnail)
                .into(itemView.imgCapa)
            itemView.tituloLivro.text = livro.volumeInfo?.title
            //itemView.tituloLivro.setText(livro.volumeInfo?.title)
            livro.volumeInfo?.authors?.forEach { autor ->
                //itemView.autorLivro.setText(author)
                itemView.autorLivro.text = autor
            }
            itemView.livroInfo.text =
                livro.searchInfo?.textSnippet?.let {
                    HtmlCompat.fromHtml(
                        it,
                        HtmlCompat.FROM_HTML_MODE_LEGACY
                    )
                }
            itemView.descricaoLivro.text = livro.volumeInfo?.description
            itemView.star.setOnClickListener {
                clickListener(livro)
            }
        }
    }

    override fun getItemCount(): Int {
        return listaLivros.size ?: 0
    }

    override fun onBindViewHolder(holder: LivroViewHolder, position: Int) {
        val livroExibido = listaLivros?.get(position)
        return livroExibido.let {
            holder.bind(it, clickListener)
            holder.itemView.setOnClickListener {
                detalhaLivro(livroExibido)
                /*val intent = Intent(context, LivroDetalhesActivity::class.java)
                intent.putExtra(EXTRA_LIVRO, livroExibido)
                context.startActivity(intent)*/
            }
        }
    }
}