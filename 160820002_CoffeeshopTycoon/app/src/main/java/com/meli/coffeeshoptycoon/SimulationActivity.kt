package com.meli.coffeeshoptycoon

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_simulation.*

class SimulationActivity : AppCompatActivity() {
    override fun onBackPressed() {
        Toast.makeText(this, "There is no back action", Toast.LENGTH_LONG).show()
        return
    }

    companion object{
        val SOLDCUP = "SOLDCUP"
        val PRICEACUP = "PRICEACUP"
        val EXPENSES = "EXPENSES"
    }

    //initial variable
    var soldCalculation = 0
    var soldCup = 0
    var availableCup = 0
    var cup = 0
    var custInfo = ""
    var timeInfo = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_simulation)

        Global.simulation.clear()

        val totalCup = intent.getStringExtra(PreparationActivity.TOTALCUP).toString().toInt()
        val priceACup = intent.getStringExtra(PreparationActivity.SELLINGPRICE).toString().toInt()
        val totalCost = intent.getStringExtra(PreparationActivity.TOTALCOSTS).toString().toInt()
        val weather = intent.getStringExtra(PreparationActivity.WEATHER)

        txtDaySimul.text = "DAY " + Global.day
        txtWeatherSimul.text = weather.toString()

        availableCup = totalCup
        cup = totalCup

        for (hour in 7 .. 18){
            if (hour >= 10){
                timeInfo = "$hour.00"
            } else {
                timeInfo = "0$hour.00"
            }
            if (soldCup <= cup){
                if (availableCup == 0){
                    custInfo = "Out of Stock"
                }
                else if(availableCup != 0){
                    if (soldCalculation != cup){
                        soldCup = (0 .. availableCup).shuffled().random()
                        soldCalculation += soldCup
                        availableCup -= soldCup
                        if (soldCup != 0){
                            custInfo = "$soldCup Customer"
                        } else {
                            custInfo = "No Customer"
                        }
                    }
                }
            }
            Global.simulation.add(Simulation(timeInfo, custInfo))
        }

        recyclerSimul.adapter = SimulAdaptor(this)

        btnResult.setOnClickListener(){
            val intent = Intent(this, ResultActivity::class.java)
            intent.putExtra("SOLDCUP", soldCalculation).toString()
            intent.putExtra("PRICEACUP", priceACup).toString()
            intent.putExtra("EXPENSES", totalCost).toString()
            startActivity(intent)
            finish()
        }
    }
}