package com.meli.coffeeshoptycoon

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences

class MainActivity : AppCompatActivity() {
    override fun onBackPressed() {
        Toast.makeText(this, "There is no back action", Toast.LENGTH_LONG).show()
        return
    }

    @SuppressLint("CommitPrefEdits")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sharedPreferences = getSharedPreferences("160820002_CoffeshopTycoon", Context.MODE_PRIVATE)

        val editor = sharedPreferences.edit()
        editor.putString("username", "meli")
        editor.putString("password", "123")
        editor.putString("playername", "Meliyana")
        editor.apply()

        btnLogin.setOnClickListener {
            val username = txtUsername.text.toString()
            val password = txtPassword.text.toString()
            val getUsername = sharedPreferences.getString("username", "error")
            val getPassword = sharedPreferences.getString("password", "error")
            val getPlayername= sharedPreferences.getString("playername", "error")

            if(username == ""){
                Toast.makeText(this, "please fill in your username", Toast.LENGTH_SHORT).show()
            }
            else if (password == ""){
                Toast.makeText(this, "please fill in your password", Toast.LENGTH_SHORT).show()
            }
            else{
                if (username == getUsername && password == getPassword){
                    Global.playername = getPlayername.toString()
                    val intent = Intent(this, PreparationActivity::class.java)
                    intent.putExtra("USERNAME", getPlayername.toString())
                    startActivity(intent)
                    finish()
                }
                else {
                    Toast.makeText(this, "Your username or password is not match in our records", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}