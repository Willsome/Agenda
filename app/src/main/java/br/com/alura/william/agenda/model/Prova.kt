package br.com.alura.william.agenda.model

import java.io.Serializable

class Prova(
        var materia: String,
        var data: String,
        var topicos: List<String>

) : Serializable {

    override fun toString(): String {
        return materia
    }
}