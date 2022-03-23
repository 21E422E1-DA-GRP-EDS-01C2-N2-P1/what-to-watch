package br.infnet.projeto_bloco_abbj.ui.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.text.HtmlCompat
import br.infnet.projeto_bloco_abbj.R
import br.infnet.projeto_bloco_abbj.data.EXTRA_LIVRO
import br.infnet.projeto_bloco_abbj.data.model.Item
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso


class LivroDetalhesFragment : Fragment() {

    private lateinit var btnComprar: Button
    private lateinit var tituloLivro: TextView
    private lateinit var autorLivro: TextView
    private lateinit var livroInfo: TextView
    private lateinit var descricaoLivro: TextView
    private lateinit var imgCapa: ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_livro_detalhes, container, false)
        setupWidgets(view)
        val livro = arguments?.getSerializable(EXTRA_LIVRO) as Item

        bindComponents(livro)

        btnComprar = view.findViewById(R.id.btnComprar)
        btnComprar.setOnClickListener {
            val url = livro.saleInfo?.buyLink
            if (url != null) {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
            } else {
                Snackbar.make(view, "Livro indisponivel", Snackbar.LENGTH_SHORT)
                    .show()
            }

        }
        return view
    }

    private fun bindComponents(livro: Item) {
        Picasso.with(requireContext())
            .load(livro.volumeInfo?.imageLinks?.smallThumbnail)
            .into(imgCapa)
        tituloLivro.text = livro.volumeInfo?.title
        livro.volumeInfo?.authors?.forEach { author -> autorLivro.text = author }
        livroInfo.text = livro.searchInfo?.textSnippet?.let {
            HtmlCompat.fromHtml(
                it,
                HtmlCompat.FROM_HTML_MODE_LEGACY
            )
        }
        descricaoLivro.text = livro.volumeInfo?.description
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        Log.d("item id", item.itemId.toString())
        Log.d("android.R.id.home", android.R.id.home.toString())
        when (item.itemId) {
            android.R.id.home -> {
                requireActivity().finish()
                return true
            }
        }
        return super.onContextItemSelected(item)
    }

    private fun setupWidgets(view: View) {
        tituloLivro = view.findViewById(R.id.tituloLivro)
        autorLivro = view.findViewById(R.id.autorLivro)
        livroInfo = view.findViewById(R.id.livroInfo)
        descricaoLivro = view.findViewById(R.id.descricaoLivro)
        imgCapa = view.findViewById(R.id.imgCapa)
    }
}