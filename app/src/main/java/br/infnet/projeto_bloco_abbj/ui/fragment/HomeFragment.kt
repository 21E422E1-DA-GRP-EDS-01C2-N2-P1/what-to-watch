package br.infnet.projeto_bloco_abbj.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import br.infnet.projeto_bloco_abbj.R
import br.infnet.projeto_bloco_abbj.data.AD_UNIT_ID
import br.infnet.projeto_bloco_abbj.data.AUTOR_LIVRO_REQUEST
import br.infnet.projeto_bloco_abbj.data.TITULO_LIVRO_REQUEST
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback

class HomeFragment : Fragment() {

    private lateinit var btnBuscar: Button
    private lateinit var btnFavoritos: Button
    private lateinit var tituloLivro: EditText
    private lateinit var autorLivro: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        setupWidgets(view)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadListeners()
    }

    private fun loadListeners() {
        btnBuscar.setOnClickListener {

            val adRequest = AdRequest.Builder().build()

            InterstitialAd.load(requireContext(),
                //getString(R.string.ad_id_test),
                AD_UNIT_ID,
                adRequest,
                object: InterstitialAdLoadCallback() {

                    override fun onAdLoaded(interstitialAd: InterstitialAd) {
                        interstitialAd.show(requireActivity())
                    }

                    //override fun onAdFailedToLoad(p0: LoadAdError) {}

                })

            if (tituloLivro.text.isNotEmpty() || autorLivro.text.isNotEmpty()) {
                val bundle = bundleOf(TITULO_LIVRO_REQUEST to tituloLivro.text.toString(),
                    AUTOR_LIVRO_REQUEST to autorLivro.text.toString()
                )
                findNavController().navigate(R.id.listaLivrosFragment, bundle)
                /*val mIntent = Intent(requireActivity(), ListaLivrosActivity::class.java)
                mIntent.putExtra(TITULO_LIVRO_REQUEST, tituloLivro.text.toString())
                mIntent.putExtra(AUTOR_LIVRO_REQUEST, autorLivro.text.toString())
                startActivity(mIntent)*/
            } else {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.field_required),
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        btnFavoritos.setOnClickListener {
            findNavController().navigate(R.id.favoritosFragment)
        }
    }

    private fun setupWidgets(view: View) {
        btnBuscar = view.findViewById(R.id.btn_buscar)
        btnFavoritos = view.findViewById(R.id.btn_favoritos)
        tituloLivro = view.findViewById(R.id.tituloLivro)
        autorLivro = view.findViewById(R.id.autorLivro)
    }
}