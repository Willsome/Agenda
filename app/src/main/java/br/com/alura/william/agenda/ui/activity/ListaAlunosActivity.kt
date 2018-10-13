package br.com.alura.william.agenda.ui.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import br.com.alura.william.agenda.R
import br.com.alura.william.agenda.dao.AlunoDao
import br.com.alura.william.agenda.model.Aluno
import kotlinx.android.synthetic.main.activity_lista_alunos.*

class ListaAlunosActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_alunos)

        registerForContextMenu(lv_lista_alunos)

        bt_lista_alunos_novo_aluno.setOnClickListener {
            val vaiProFormulario = Intent(
                    this,
                    FormularioActivity::class.java
            )
            startActivity(vaiProFormulario)
        }
    }

    override fun onResume() {
        super.onResume()
        carregaAlunos()
    }

    private fun carregaAlunos() {
        val alunos = AlunoDao(this).buscaAlunos()

        val adapter = ArrayAdapter<Aluno>(
                this,
                android.R.layout.simple_list_item_1,
                alunos
        )

        lv_lista_alunos.adapter = adapter

        lv_lista_alunos.setOnItemClickListener { parent, view, position, id ->
            val aluno = lv_lista_alunos.getItemAtPosition(position) as Aluno
            val intent = Intent(this, FormularioActivity::class.java)
            intent.putExtra("aluno", aluno)
            startActivity(intent)
        }
    }

    override fun onCreateContextMenu(menu: ContextMenu?, menuItem: View?, menuInfo: ContextMenu.ContextMenuInfo?) {

        val info = menuInfo as AdapterView.AdapterContextMenuInfo

        val aluno = lv_lista_alunos.getItemAtPosition(info.position) as Aluno

        val menuDeletar = menu?.add("Deletar")
        menuDeletar?.setOnMenuItemClickListener(
                MenuItem.OnMenuItemClickListener {
                    AlunoDao(this).deleta(aluno)
                    carregaAlunos()
                    return@OnMenuItemClickListener false
                }
        )
    }
}
