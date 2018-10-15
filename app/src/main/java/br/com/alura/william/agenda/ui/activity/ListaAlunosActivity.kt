package br.com.alura.william.agenda.ui.activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import br.com.alura.william.agenda.R
import br.com.alura.william.agenda.adapter.ListaAlunosAdapter
import br.com.alura.william.agenda.dao.AlunoDao
import br.com.alura.william.agenda.model.Aluno
import kotlinx.android.synthetic.main.activity_lista_alunos.*

class ListaAlunosActivity : AppCompatActivity() {

    val CODIGO_PERMISSAO_SMS = 312

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_alunos)

        pegaPermissaoSMS()

        registerForContextMenu(lv_lista_alunos)

        bt_lista_alunos_novo_aluno.setOnClickListener {
            val vaiProFormulario = Intent(
                    this,
                    FormularioActivity::class.java
            )
            startActivity(vaiProFormulario)
        }
    }

    private fun pegaPermissaoSMS() {
        if (ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(
                    this@ListaAlunosActivity,
                    arrayOf(Manifest.permission.RECEIVE_SMS),
                    CODIGO_PERMISSAO_SMS
            )
        }
    }

    override fun onResume() {
        super.onResume()
        carregaAlunos()
    }

    private fun carregaAlunos() {
        val alunos = AlunoDao(this).buscaAlunos()

        val adapter = ListaAlunosAdapter(
                this,
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

        val menuLigar = menu?.add("Ligar")
        menuLigar?.setOnMenuItemClickListener(
                MenuItem.OnMenuItemClickListener {

                    //                    if(ActivityCompat.checkSelfPermission(
//                                    this,
//                                    android.Manifest.permission.CALL_PHONE)
//                        != PackageManager.PERMISSION_GRANTED
//                    ) {
//                        ActivityCompat.requestPermissions(
//                                this@ListaAlunosActivity,
//                                arrayOf(android.Manifest.permission.CALL_PHONE),
//                                69
//                        )
//
//                    } else {
//                        val intentLigar = Intent(Intent.ACTION_CALL)
//                        intentLigar.data = Uri.parse("tel:" + aluno.telefone)
//                        startActivity(intentLigar)
//                    }

                    val intentLigar = Intent(Intent.ACTION_CALL)
                    intentLigar.data = Uri.parse("tel:" + aluno.telefone)
                    startActivity(intentLigar)

                    return@OnMenuItemClickListener false
                }
        )

        val menuIrParaSite = menu?.add("Ir para Site")
        val intentSite = Intent(Intent.ACTION_VIEW)
        var site = aluno.site
        if (!site!!.startsWith("http://")) {
            site = "http://" + site
        }
        intentSite.data = Uri.parse(site)
        menuIrParaSite?.intent = intentSite

        val menuEnviarSMS = menu?.add("SMS")
        val intentSMS = Intent(Intent.ACTION_VIEW)
        intentSMS.data = Uri.parse("sms:" + aluno.telefone)
        menuEnviarSMS?.intent = Intent(Intent.ACTION_VIEW)

        val menuIrParaMapa = menu?.add("Vai Para Mapa")
        val intentMapa = Intent(Intent.ACTION_VIEW)
        intentMapa.data = Uri.parse("geo:0,0?z=14&q=" + aluno.endereco)
        menuIrParaMapa?.intent = Intent(Intent.ACTION_VIEW)

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
