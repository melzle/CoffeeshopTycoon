package com.meli.coffeeshoptycoon

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_simulation.*

class SimulationActivity : AppCompatActivity() {
    companion object{
        val SELLCUP = "SELLCUP"
        val PRICEPERCUP = "PRICEPERCUP"
        val EXPENSES = "EXPENSES"
    }

    var perhitunganTerjual = 0
    var cupTerjual = 0
    var cupTersedia = 0
    var dummyCup = 0

    var cusMessage = ""
    var timeMessage = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_simulation)

        Global.simulation.clear()

        var totalCupsCoffee = intent.getStringExtra(PreparationActivity.SELLING).toString().toInt()
        var priceAllIngridientCoffee = intent.getStringExtra(PreparationActivity.PRICEALL).toString().toInt()
        var priceSellCoffee = intent.getStringExtra(PreparationActivity.PRICESELL).toString().toInt()
        var totalAllPrice = intent.getStringExtra(PreparationActivity.TOTAL).toString().toInt()
        var weather = intent.getStringExtra(PreparationActivity.WEATHER)
        var location = intent.getStringExtra(PreparationActivity.LOCATION)
        var locationPrice = intent.getStringExtra(PreparationActivity.LOCATIONPRICE).toString().toInt()

        var expenses = totalAllPrice

        txtDaySimul.text = "DAY " + Global.day

        txtWeatherSimul.text = weather.toString()

        cupTersedia = totalCupsCoffee
        dummyCup = totalCupsCoffee

        cupTersedia = totalCupsCoffee
        dummyCup = totalCupsCoffee

        for(i in 7 .. 18){
            if(cupTerjual <= dummyCup){
                if (cupTersedia != 0){
                    if(perhitunganTerjual != dummyCup){
                        cupTerjual = (0 .. cupTersedia).shuffled().random()
                        perhitunganTerjual += cupTerjual
                        cupTersedia -= cupTerjual
                        if(cupTerjual == 0){
                            cusMessage = "No Customer"
                        }else{
                            cusMessage = "$cupTerjual Customer"
                        }
                    }
                }
                else if(cupTersedia == 0){
                    cusMessage = "Out of Stock"
                }
            }
            if(i>=10){
                timeMessage="$i.00"
            }else{
                timeMessage="0$i.00"
            }
            Global.simulation.add(Simulation(cusMessage, timeMessage))
        }
        val lm: LinearLayoutManager = LinearLayoutManager(this)
        recyclerSimul.layoutManager = lm
        recyclerSimul.setHasFixedSize(true)
        recyclerSimul.adapter = SimulAdaptor(this)

        btnResult.setOnClickListener(){
            var intent = Intent(this, ResultActivity::class.java)
            intent.putExtra("SELLCUP", perhitunganTerjual).toString()
            intent.putExtra("PRICEPERCUP", priceSellCoffee).toString()
            intent.putExtra("EXPENSES", expenses).toString()

            startActivity(intent)

            finish()
        }
    }
}