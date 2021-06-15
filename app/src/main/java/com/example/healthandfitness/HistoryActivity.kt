package com.example.healthandfitness

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_final.*
import kotlinx.android.synthetic.main.activity_history.*

class HistoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        setSupportActionBar(historyActivityToolbar)
        val actionbar = supportActionBar
        if (actionbar != null)
            actionbar.setDisplayHomeAsUpEnabled(true)
        historyActivityToolbar.setNavigationOnClickListener {
            onBackPressed()
        }
        getAllDates()
    }

    private fun getAllDates()
    {
        val dbHandler = SqliteOpenHelper(this,null)
        val datesList = dbHandler.getDates()

        if(datesList.size > 0)
        {
            historyRecyclerView.visibility = View.VISIBLE
            textViewHistory.visibility = View.GONE
            NoDataAvailable.visibility = View.GONE

            historyRecyclerView.layoutManager = LinearLayoutManager(this)
            val historyAdapter = HistoryAdapter(this,datesList)
            historyRecyclerView.adapter = historyAdapter
        }
        else
        {
            historyRecyclerView.visibility = View.GONE
            textViewHistory.visibility = View.VISIBLE
            NoDataAvailable.visibility = View.VISIBLE
        }
    }
}