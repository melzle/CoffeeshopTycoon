package com.meli.coffeeshoptycoon

data class Recipes(val name:String, val coffee:String, val milk:String, val water:String){
    override fun toString(): String {
        return name
    }
}
