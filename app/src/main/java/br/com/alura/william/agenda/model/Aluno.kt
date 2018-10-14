package br.com.alura.william.agenda.model

import java.io.Serializable

class Aluno : Serializable {

    var id: Long? = null
    var nome: String? = null
    var endereco: String? = null
    var telefone: String? = null
    var site: String? = null
    var nota: Double? = null
    var caminhoFoto: String? = null

    override fun toString(): String {
        return "${id} - ${nome}"
    }
}