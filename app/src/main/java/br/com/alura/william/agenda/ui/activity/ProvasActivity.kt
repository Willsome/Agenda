package br.com.alura.william.agenda.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.com.alura.william.agenda.R
import br.com.alura.william.agenda.model.Prova
import br.com.alura.william.agenda.ui.fragments.DetalhesProvaFragment
import br.com.alura.william.agenda.ui.fragments.ListaProvasFragment

class ProvasActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_provas)

        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fl_provas, ListaProvasFragment())
        if (modoPaisagem()) fragmentTransaction.replace(R.id.fl_detalhe_provas, DetalhesProvaFragment())
        fragmentTransaction.commit()
    }

    private fun modoPaisagem() = resources.getBoolean(R.bool.modoPaisagem)

    fun selecionaProva(prova: Prova) {

        if (!modoPaisagem()) {
            val detalhesProvaFragment = DetalhesProvaFragment()
            val parametros = Bundle()
            parametros.putSerializable("prova", prova)
            detalhesProvaFragment.arguments = parametros
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fl_provas, detalhesProvaFragment)
                    .addToBackStack(null)
                    .commit()

        } else {
            val detalhesProvaFragment = supportFragmentManager
                    .findFragmentById(R.id.fl_detalhe_provas) as DetalhesProvaFragment
            detalhesProvaFragment.populaCamposCom(prova)
        }
    }
}
