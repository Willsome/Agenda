package br.com.alura.william.agenda.helper

import br.com.alura.william.agenda.model.Aluno
import br.com.alura.william.agenda.ui.activity.FormularioActivity
import kotlinx.android.synthetic.main.activity_formulario.*

class AlunoHelper(val activity: FormularioActivity) {

    fun pegaAluno(): Aluno {

        val aluno = Aluno()
        aluno.nome = activity.et_formulario_nome.text.toString()
        aluno.endereco = activity.et_formulario_endereco.text.toString()
        aluno.telefone = activity.et_formulario_telefone.text.toString()
        aluno.site = activity.et_formulario_site.text.toString()
        aluno.nota = activity.rb_formulario_nota.progress.toDouble()

        return aluno
    }
}
