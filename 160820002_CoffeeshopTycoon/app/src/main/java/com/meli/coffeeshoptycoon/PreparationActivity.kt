package com.meli.coffeeshoptycoon

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import kotlinx.android.synthetic.main.activity_preparation.*
import kotlin.random.Random

class PreparationActivity : AppCompatActivity() {
    override fun onBackPressed() {
        Toast.makeText(this, "There is no back action", Toast.LENGTH_LONG).show()
        return
    }

    companion object{
        val TOTALCUP = "TOTALCUP"
        val TOTALPRICE = "TOTALPRICE"
        val SELLINGPRICE = "SELLINGPRICE"
        val TOTALCOSTS = "TOTALCOSTS"
        val WEATHER = "WEATHER"
    }

    //price of each ingridient
    var coffeePrice = 500
    var milkPrice = 1000
    var waterPrice = 200

    //initial variable
    var numCoffee = 0
    var numMilk = 0
    var numWater = 0
    var rentCost = 0
    var totalCupCoffee = 0
    var totalSellCoffee = 0
    var sellingPrice = 0

    //calculate
    var priceOfCoffee = coffeePrice * numCoffee
    var priceOfMilk = milkPrice * numMilk
    var priceOfWater = waterPrice * numWater
    var totalPrice = priceOfCoffee + priceOfMilk + priceOfWater
    var totalCosts = rentCost + totalSellCoffee

    //weather
    var weather : Weather = Global.weather[0]
    var spinnIndex = ""

    //variable for recipe
    var recipeName = ""
    var inputCoffee = ""
    var inputMilk = ""
    var inputWater = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preparation)

        txtDay.text = "DAY ${Global.day}"

        //weather
        val weatherList : List<Weather> = Global.weather.toList()
        val randomArrWeather = weatherList.shuffled()
        val weatherIndex = Random.nextInt(until = 3)
        weather = randomArrWeather.get(weatherIndex)
        txtWeather.text = weather.name

        //welcome text
        txtWelcome.text = "Welcome, ${Global.playername}"

        //balance
        txtBalance.text = "Balance : IDR ${Global.balance.toString()}"

        //spinner
        val adapter = ArrayAdapter(this, R.layout.activity_spinner_layout, Global.location)
        adapter.setDropDownViewResource(R.layout.activity_spinner_item_layout)
        spinnerLocations.adapter = adapter

        spinnerLocations.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                txtLocRent.text = Global.location[p2].placeName
                txtRentCosts.text = "IDR " + Global.location[p2].price.toString()
                spinnIndex = Global.location[p2].placeName
                rentCost = Global.location[p2].price
                displayData()
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }

        addToSpinnerRecipes()

        //recipe
        btnSaveRecipe.setOnClickListener {
            if (txtRecipeName.text.toString() != ""){
                addToSpinnerRecipes()
                saveDataRecipe()
                txtRecipeName.setText("")
            } else{
                Toast.makeText(this, "Please input the recipes name", Toast.LENGTH_SHORT).show();
            }
        }

        spinnerRecipes.onItemSelectedListener = object:AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                txtNumCoffee.setText(Global.recipes[p2].coffee)
                txtNumMilk.setText(Global.recipes[p2].milk)
                txtNumWater.setText(Global.recipes[p2].water)
                displayData()
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }

        txtNumCoffee.addTextChangedListener(){
            if (txtNumCoffee.text.toString() != ""){
                numCoffee = txtNumCoffee.text.toString().toInt()
                priceOfCoffee = coffeePrice * numCoffee
                displayData()
            } else {
                numCoffee = 0
                priceOfCoffee = coffeePrice * numCoffee
                displayData()
            }
        }

        txtNumMilk.addTextChangedListener(){
            if (txtNumMilk.text.toString() != ""){
                numMilk = txtNumMilk.text.toString().toInt()
                priceOfMilk = milkPrice * numMilk
                displayData()
            } else {
                numMilk = 0
                priceOfMilk = milkPrice * numMilk
                displayData()
            }
        }

        txtNumWater.addTextChangedListener() {
            if (txtNumWater.text.toString() != ""){
                numWater = txtNumWater.text.toString().toInt()
                priceOfWater = waterPrice * numWater
                displayData()
            } else {
                numWater = 0
                priceOfWater = waterPrice * numWater
                displayData()
            }
        }

        txtCupToSell.addTextChangedListener(){
            if (txtCupToSell.text.toString() != ""){
                totalCupCoffee = txtCupToSell.text.toString().toInt()
                displayData()
            } else {
                totalCupCoffee = 0
                displayData()
            }
        }

        txtSellingPrice.addTextChangedListener() {
            if (txtSellingPrice.text.toString() != ""){
                sellingPrice = txtSellingPrice.text.toString().toInt()
            } else {
                Toast.makeText(this, "Please input the selling price at least 1", Toast.LENGTH_SHORT).show();
            }
        }

        //button up and down
        btnUpCoffee.setOnClickListener(){
            numCoffee += 1
            txtNumCoffee.setText(numCoffee.toString())
            displayData()
        }

        btnDownCoffee.setOnClickListener(){
            if (numCoffee >= 1){
                numCoffee -= 1
                txtNumCoffee.setText(numCoffee.toString())
                displayData()
            } else {
                Toast.makeText(this, "Please input the coffee bigger than or equal 1", Toast.LENGTH_SHORT).show();
            }
        }

        btnUpMilk.setOnClickListener(){
            numMilk += 1
            txtNumMilk.setText(numMilk.toString())
            displayData()
        }

        btnDownMilk.setOnClickListener(){
            if (numMilk >= 1){
                numMilk -= 1
                txtNumMilk.setText(numMilk.toString())
                displayData()
            } else {
                Toast.makeText(this, "Please input the milk bigger than or equal 1", Toast.LENGTH_SHORT).show();
            }
        }

        btnUpWater.setOnClickListener(){
            numWater += 1
            txtNumWater.setText(numWater.toString())
            displayData()
        }

        btnDownWater.setOnClickListener(){
            if (numWater >= 1){
                numWater -= 1
                txtNumWater.setText(numWater.toString())
                displayData()
            } else {
                Toast.makeText(this, "Please input the water bigger than or equal 1", Toast.LENGTH_SHORT).show();
            }
        }

        //start day
        btnStartDay.setOnClickListener(){
            if (totalCupCoffee > 0){
                if (sellingPrice > 0){
                    if (totalCosts <= Global.balance){
                        Global.balance = Global.balance - totalCosts
                        displayData()
                        openSimulation()
                    } else {
                        Toast.makeText(this, "Insufficient Balance", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "Please input the selling price at least 1", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Please input minimum 1 cup number to sell", Toast.LENGTH_SHORT).show();
            }
        }
    }

    fun displayData(){
        totalPrice = priceOfCoffee + priceOfMilk + priceOfWater
        priceOfCoffee = coffeePrice * numCoffee
        priceOfMilk = milkPrice * numMilk
        priceOfWater = waterPrice * numWater

        txtCostInfo.text = "Cost per cup of coffee is IDR $totalPrice How many cup do you want to sell ?"
        txtTotalCup.text = "$totalCupCoffee cup of coffee"
        txtPriceACup.text = "@IDR $totalPrice"
        txtTotalSell.text = "IDR " + calculateTotalSell(totalCupCoffee, totalPrice)
        txtTotalCosts.text = "IDR " + calculateTotalCosts(calculateTotalSell(totalCupCoffee, totalPrice), rentCost)
        txtBalance.text = "Balance : IDR ${Global.balance}"
    }

    fun calculateTotalSell(totalCup:Int, totalPrice:Int):Int{
        totalSellCoffee = totalCup * totalPrice
        return totalSellCoffee
    }

    fun calculateTotalCosts(total:Int, rentCost:Int):Int{
        totalCosts = total + rentCost
        return totalCosts
    }

    fun displayDataRecipe(){
        txtNumCoffee.setText(numCoffee.toString())
        txtNumMilk.setText(numMilk.toString())
        txtNumWater.setText(numWater.toString())
        displayData()
    }

    fun addToSpinnerRecipes(){
        val recipeAdapter = ArrayAdapter(this, R.layout.activity_spinner_layout, Global.recipes)
        recipeAdapter.setDropDownViewResource(R.layout.activity_spinner_item_layout)
        spinnerRecipes.adapter = recipeAdapter
    }

    fun saveDataRecipe(){
        displayDataRecipe()
        recipeName = txtRecipeName.text.toString()
        inputCoffee = txtNumCoffee.text.toString()
        inputMilk = txtNumMilk.text.toString()
        inputWater = txtNumWater.text.toString()
        Global.recipes.add(Recipes(recipeName, inputCoffee, inputMilk, inputWater))
        Toast.makeText(this, "Save Recipe Successful", Toast.LENGTH_SHORT).show();
        addToSpinnerRecipes()
        displayData()
    }

    fun openSimulation(){
        val intent = Intent(this, SimulationActivity::class.java)
        intent.putExtra("TOTALCUP", totalCupCoffee.toString())
        intent.putExtra("TOTALPRICE", totalPrice.toString())
        intent.putExtra("SELLINGPRICE", sellingPrice.toString())
        intent.putExtra("TOTALCOSTS", totalCosts.toString())
        intent.putExtra("WEATHER", weather.name.toString())

        startActivity(intent)
        finish()
    }

}