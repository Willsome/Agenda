package br.com.alura.william.agenda.ui.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import br.com.alura.william.agenda.R
import br.com.alura.william.agenda.model.Prova
import br.com.alura.william.agenda.ui.activity.ProvasActivity
import kotlinx.android.synthetic.main.fragment_lista_provas.view.*


class ListaProvasFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_lista_provas, container, false)

        val topicosPort = listOf("Adjetivo", "Objeto direto", "Objeto indireto")
        val topicosMat = listOf("Trigonometria", "Função quadrática do 2º grau")

        val provaPort = Prova("Português", "25/10/2018", topicosPort)
        val provaMat = Prova("Matemática", "29/10/2018", topicosMat)

        val provas = listOf(provaPort, provaMat)

        val adapter = ArrayAdapter<Prova>(
                context,
                android.R.layout.simple_list_item_1,
                provas
        )

        view?.lv_provas?.adapter = adapter

        view?.lv_provas?.setOnItemClickListener { parent, viewFromList, position, id ->
            val prova = parent.getItemAtPosition(position) as Prova
            (activity as ProvasActivity).selecionaProva(prova)
        }

        return view
    }


}
