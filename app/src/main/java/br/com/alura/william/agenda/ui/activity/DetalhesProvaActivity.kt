package br.com.alura.william.agenda.ui.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import br.com.alura.william.agenda.R
import br.com.alura.william.agenda.model.Prova
import kotlinx.android.synthetic.main.activity_detalhes_prova.*

class DetalhesProvaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalhes_prova)

        val prova = intent.getSerializableExtra("prova") as Prova

        val adapter = ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                prova.topicos
        )

        lv_detalhes_prova.adapter = adapter

        tv_detalhes_prova_materia.text = prova.materia
        tv_detalhes_prova_data.text = prova.data
    }
}
