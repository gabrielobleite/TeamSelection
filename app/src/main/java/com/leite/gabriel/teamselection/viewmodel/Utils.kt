package com.leite.gabriel.teamselection.viewmodel

import com.leite.gabriel.teamselection.model.Player

class ListSearch {
    fun ByRole(lst: List<Player>, role: String ) = lst.filter{ it.role == role }
}