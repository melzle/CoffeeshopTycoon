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

    fun reset(){
        Global.balance = 350000
        Global.day = 1
    }

    fun setResultData(){
        val soldCup = intent.getIntExtra(SimulationActivity.SOLDCUP,0).toString().toInt()
        val priceACup = intent.getIntExtra(SimulationActivity.PRICEACUP,0).toString().toInt()
        val expenses = intent.getIntExtra(SimulationActivity.EXPENSES,0).toString().toInt()

        val revenues = soldCup * priceACup
        val profit = revenues - expenses

        Global.balance += revenues
        txtResultDay.text = "DAY ${Global.day} RESULT"
        txtCupTotal.text = "${soldCup.toString()} cup of coffee"
        txtPricePerCup.text = "@IDR ${priceACup.toString()}"
        txtSellingTotal.text = "IDR ${revenues.toString()}"
        txtLocIngredientCost.text = "IDR ${expenses.toString()}"
        txtProfit.text ="IDR ${profit.toString()}"
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        setResultData()

        // minimimumCost = 100000 + 1000 + 500 + 200
        val minimumCost = 101700

        btnStartNewDay.setOnClickListener(){
            if (Global.balance < minimumCost) {
                val builder = AlertDialog.Builder(this)
                builder.setMessage("Game Over! Your Coffeeshop have gone bankrupt")
                builder.setPositiveButton("Play Again", DialogInterface.OnClickListener { dialogInterface, i ->
                    reset()
                    val intent = Intent(this,PreparationActivity::class.java)
                    startActivity(intent)
                })
                builder.setNegativeButton("Exit", DialogInterface.OnClickListener { dialogInterface, i ->
                    finish()
                })
                builder.create().show()
            }
            else{
                val intent = Intent(this,PreparationActivity::class.java)
                Global.day++
                startActivity(intent)
            }
        }
    }
}