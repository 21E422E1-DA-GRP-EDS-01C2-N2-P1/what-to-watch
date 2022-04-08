package br.infnet.projeto_bloco_abbj.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.infnet.projeto_bloco_abbj.R
import br.infnet.projeto_bloco_abbj.data.model.Anotacao
import java.text.SimpleDateFormat

class AnotacaoAdapter(
    val anotacoes: List<Anotacao>,
    val deleteItem: (Int) -> Unit,
    val detalhaAnotacao: (String) -> Unit)
    : RecyclerView.Adapter<AnotacaoAdapter.AnotacaoAdapterViewHolder>() {

    class AnotacaoAdapterViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val lblData = itemView.findViewById<TextView>(R.id.anotacao_item_lbl_data)
        val lblTitulo = itemView.findViewById<TextView>(R.id.anotacao_item_lbl_titulo)
        val btnDelete = itemView.findViewById<ImageButton>(R.id.anotacao_item_btn_delete)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AnotacaoAdapterViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.anotacao_item, parent, false)
        return AnotacaoAdapterViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: AnotacaoAdapterViewHolder,
        position: Int
    ) {
        val anotacao = anotacoes[position]
        val dataString = SimpleDateFormat("dd/MM/yy").format(anotacao.data.toDate())
        holder.lblData.text = dataString
        holder.lblTitulo.text = anotacao.titulo
        holder.btnDelete.setOnClickListener {
            deleteItem(position)
        }
        holder.itemView.setOnClickListener {
            if(!anotacao.id.isNullOrBlank()) detalhaAnotacao(anotacao.id)
        }

    }

    override fun getItemCount(): Int = anotacoes.size
}