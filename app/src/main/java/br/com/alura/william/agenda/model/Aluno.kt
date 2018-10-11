package br.com.alura.william.agenda.model

class Aluno {

    var id: Long? = null
    var nome: String? = null
    var endereco: String? = null
    var telefone: String? = null
    var site: String? = null
    var nota: Double? = null

    override fun toString(): String {
        return "${id} - ${nome}"
    }
}