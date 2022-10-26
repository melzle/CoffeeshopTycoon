package com.meli.coffeeshoptycoon

object Global {
    var day = 1
    var balance = 350000
    var playername = ""

    var weather:List<Weather> = listOf(
        Weather("Thunderstorm"),
        Weather("Rainy Day"),
        Weather("Sunny Day")
    )

    var location:Array<Locations> = arrayOf(
        Locations(1, "University", 100000),
        Locations(2, "Business District", 350000),
        Locations(3, "Beach", 500000)
    )

    val simulation:ArrayList<Simulation> = ArrayList(
        listOf()
    )

    val recipes:ArrayList<Recipes> = ArrayList(
        listOf(
            Recipes("-- Select Recipe --", "0","0","0"),
            Recipes("Caffe Latte", "8", "6", "4"),
            Recipes("Macchiato", "12", "5", "4")
        )
    )
}