package com.meli.coffeeshoptycoon

data class Locations(val id:Int, val placeName:String, val price:Int){
    override fun toString(): String {
        return placeName
        return price.toString()
    }
}
