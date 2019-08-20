package com.sk.broadband.socketdrawviewclientsample

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class EntryActivity : AppCompatActivity() {

    lateinit var ipEditText1: EditText
    lateinit var ipEditText2: EditText
    lateinit var ipEditText3: EditText
    lateinit var ipEditText4: EditText

    lateinit var inputButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_entry)

        ipEditText1 = findViewById(R.id.edit_text1)
        ipEditText2 = findViewById(R.id.edit_text2)
        ipEditText3 = findViewById(R.id.edit_text3)
        ipEditText4 = findViewById(R.id.edit_text4)

        inputButton = findViewById(R.id.input_button)

        inputButton.setOnClickListener {
            moveToMainActivity(getIpAddress())
        }
    }

    private fun getIpAddress(): String {
        return ipEditText1.text.toString() + "." +
                ipEditText2.text.toString() + "." +
                ipEditText3.text.toString() + "." +
                ipEditText4.text.toString()
    }

    private fun moveToMainActivity(data: String) {
        val nextIntent = Intent(this, MainActivity::class.java)
        nextIntent.putExtra("IP_ADDRESS", data)
        startActivity(nextIntent)
    }
}
