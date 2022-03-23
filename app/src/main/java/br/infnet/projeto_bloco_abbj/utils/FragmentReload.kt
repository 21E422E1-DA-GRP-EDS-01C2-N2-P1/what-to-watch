package br.infnet.projeto_bloco_abbj.utils

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import br.infnet.projeto_bloco_abbj.R

object FragmentReload {
    fun refreshFragment(context: Context?){
        context?.let {
            val fragmentManager = (context as? AppCompatActivity)?.supportFragmentManager
            fragmentManager?.let {
                val currentFragment = fragmentManager.findFragmentById(R.id.favoritosFragment)
                currentFragment?.let{
                    val fragmentTransaction = fragmentManager.beginTransaction()
                    fragmentTransaction.detach(it)
                    fragmentTransaction.attach(it)
                    fragmentTransaction.commit()
                }
            }
        }
    }

}