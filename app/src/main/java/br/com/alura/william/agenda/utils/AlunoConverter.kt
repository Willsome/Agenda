package br.com.alura.william.agenda.utils

import br.com.alura.william.agenda.model.Aluno
import org.json.JSONException
import org.json.JSONStringer

class AlunoConverter {

    fun toJSON(alunos: List<Aluno>): String {
        try {
            val jsonStringer = JSONStringer()

            jsonStringer
                    .`object`().key("list")
                    .array()
                    .`object`().key("aluno")
                    .array()

            for (aluno in alunos) {
                jsonStringer
                        .`object`()
                        .key("id").value(aluno.id)
                        .key("nome").value(aluno.nome)
                        .key("telefone").value(aluno.telefone)
                        .key("endereco").value(aluno.endereco)
                        .key("site").value(aluno.site)
                        .key("site").value(aluno.nota)
                        .endObject()
            }

            jsonStringer
                    .endArray()
                    .endObject()
                    .endArray()
                    .endObject()

            return jsonStringer.toString()

        } catch (ex: JSONException) {
            ex.printStackTrace()
        }

        return ""
    }
}