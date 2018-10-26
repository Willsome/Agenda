package br.com.alura.william.agenda.adapter

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import br.com.alura.william.agenda.R
import br.com.alura.william.agenda.model.Aluno
import kotlinx.android.synthetic.main.layout_adapter_lista_alunos.view.*

class ListaAlunosAdapter(
        var context: Context,
        var alunos: List<Aluno>

) : BaseAdapter() {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        var view = convertView
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.layout_adapter_lista_alunos, parent, false)
        }

        val caminhoFoto = alunos[position].caminhoFoto
        if (caminhoFoto != null) {
            val bitmap = BitmapFactory.decodeFile(caminhoFoto)
            val bitmapReduzido = Bitmap.createScaledBitmap(bitmap, 150, 150, true)
            view?.iv_lista_aluno_adapter_foto?.setImageBitmap(bitmapReduzido)
            view?.iv_lista_aluno_adapter_foto?.setTag(caminhoFoto)
            view?.iv_lista_aluno_adapter_foto?.scaleType = ImageView.ScaleType.FIT_XY
        }

        view?.tv_lista_aluno_adapter_nome?.text = alunos[position].nome
        view?.tv_lista_aluno_adapter_telefone?.text = alunos[position].telefone

        if (view?.tv_lista_aluno_adapter_endereco != null && view.tv_lista_aluno_adapter_site != null) {
            view.tv_lista_aluno_adapter_endereco?.text = alunos[position].endereco
            view.tv_lista_aluno_adapter_site?.text = alunos[position].site
        }

        return view!!
    }

    override fun getItem(position: Int): Any {
        return alunos[position]
    }

    override fun getItemId(position: Int): Long {
        return alunos[position].id!!
    }

    override fun getCount(): Int {
        return alunos.size
    }

}