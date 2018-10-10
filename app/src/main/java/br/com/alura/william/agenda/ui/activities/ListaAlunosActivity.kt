package br.com.alura.william.agenda.ui.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import br.com.alura.william.agenda.R
import kotlinx.android.synthetic.main.activity_lista_alunos.*

class ListaAlunosActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_alunos)

        val alunos = arrayOf("William", "Victor", "Dennis", "Mariaelena")

        val adapter = ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                alunos
        )

        lv_lista_alunos.adapter = adapter

        bt_lista_alunos_novo_aluno.setOnClickListener {
            val vaiProFormulario = Intent(
                    this,
                    FormularioActivity::class.java
            )
            startActivity(vaiProFormulario)
        }
    }
}
