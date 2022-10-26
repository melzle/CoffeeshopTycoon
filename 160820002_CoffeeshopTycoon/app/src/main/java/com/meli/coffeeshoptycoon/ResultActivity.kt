package com.meli.coffeeshoptycoon

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_result.*

class ResultActivity : AppCompatActivity() {
    override fun onBackPressed() {
        Toast.makeText(this, "There is no back action", Toast.LENGTH_LONG).show()
        return
    }

    companion object{
        val BALANCEUPDATE = "BALANCEUPDATE"
        val DAYUPDATE = "DAYUPDATE"
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        var sellCup = intent.getIntExtra(SimulationActivity.SELLCUP,0).toString().toInt()
        var pricePerCup = intent.getIntExtra(SimulationActivity.PRICEPERCUP,0).toString().toInt()
        var expenses = intent.getIntExtra(SimulationActivity.EXPENSES,0).toString().toInt()

        var revenues = sellCup * pricePerCup

        var profit = revenues - expenses

        Global.balance = Global.balance+revenues

        txtResultDay.text = "DAY ${Global.day} RESULT"

        txtCupTotal.text = "${sellCup.toString()} cup of coffee"
        txtPricePerCup.text = "@IDR ${pricePerCup.toString()}"
        txtSellingTotal.text = "IDR ${revenues.toString()}"

        txtLocIngredientCost.text = "IDR ${expenses.toString()}"

        txtProfit.text ="IDR ${profit.toString()}"

        btnStartNewDay.setOnClickListener(){
            if(Global.balance < 101700){
                val builder = AlertDialog.Builder(this)
                builder.setMessage("You have gone bankrupt")
                builder.setPositiveButton("PLAY AGAIN", DialogInterface.OnClickListener { dialogInterface, i ->
                    Global.balance = 350000
                    Global.day = 1
                    var intent = Intent(this,PreparationActivity::class.java)

                    startActivity(intent)
                })
                builder.setNegativeButton("EXIT", DialogInterface.OnClickListener { dialogInterface, i ->
                    finish()
                })
                builder.create().show()
            }
            else{
                var intent = Intent(this,PreparationActivity::class.java)

                Global.day++

                startActivity(intent)
            }
        }
    }
}