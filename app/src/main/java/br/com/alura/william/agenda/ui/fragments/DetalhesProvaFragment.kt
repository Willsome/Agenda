package br.com.alura.william.agenda.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import br.com.alura.william.agenda.R
import br.com.alura.william.agenda.model.Prova
import kotlinx.android.synthetic.main.fragment_detalhes_prova.view.*


class DetalhesProvaFragment : Fragment() {

    lateinit var mvView: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        mvView = inflater.inflate(R.layout.fragment_detalhes_prova, container, false)

        val parametros = arguments
        if (arguments != null) {
            val prova = parametros?.getSerializable("prova") as Prova
            populaCamposCom(prova)
        }

        return mvView
    }

    fun populaCamposCom(prova: Prova) {
        mvView.tv_detalhes_prova_materia?.text = prova.materia
        mvView.tv_detalhes_prova_data?.text = prova.data

        val adapter = ArrayAdapter<String>(
                context,
                android.R.layout.simple_list_item_1,
                prova.topicos
        )

        mvView.lv_detalhes_prova?.adapter = adapter
    }
}
