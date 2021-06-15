package com.example.healthandfitness

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_exercise.*
import kotlinx.android.synthetic.main.activity_final.*
import java.text.SimpleDateFormat
import java.util.*

class FinalActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_final)

        setSupportActionBar(finalActivityToolbar)
        val actionbar = supportActionBar
        if (actionbar != null)
            actionbar.setDisplayHomeAsUpEnabled(true)
        finalActivityToolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        finishButton.setOnClickListener {
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        calculateDate()
    }

    private fun calculateDate()
    {
        val calendar = Calendar.getInstance()
        val dateTime = calendar.time

        val sdf = SimpleDateFormat("dd MM yyyy HH:mm:ss",
            Locale.getDefault())
        val date = sdf.format(dateTime)

        val dbHandler = SqliteOpenHelper(this,null)
        dbHandler.addDate(date)

    }
}