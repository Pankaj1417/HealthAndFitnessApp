package com.example.healthandfitness

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_exercise.*
import java.util.*
import kotlin.collections.ArrayList

class ExerciseActivity : AppCompatActivity(),TextToSpeech.OnInitListener {
    private var restProgress = 0
    private var restTimer:CountDownTimer? = null
    private var exerciseProgress = 0
    private var exerciseTimer:CountDownTimer? = null
    private var exerciseList:ArrayList<ExerciseModel>? = null
    private var currentExercisePosition = -1
    private var tts:TextToSpeech? = null
    private var player:MediaPlayer? = null
    private var adapterObj:RecyclerViewAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercise)

        setSupportActionBar(toolbar)
        val actionbar = supportActionBar
        if (actionbar != null)
            actionbar.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
        tts = TextToSpeech(this,this)
        exerciseList = ExerciseController.exerciseFunction()
        setupRecyclerView()
        setupRestView()


    }

    override fun onDestroy() {
        if(restTimer != null)
        {
            restTimer!!.cancel()
            restProgress = 0
        }
        if(exerciseTimer != null)
        {
            exerciseTimer!!.cancel()
            exerciseProgress = 0
        }
        if (tts != null)
        {
            tts!!.stop()
            tts!!.shutdown()
        }
        if(player != null)
        {
            player!!.stop()
        }
        super.onDestroy()
    }

    fun setupRestProgressBar()
    {
        progressBar.progress = restProgress //0
        restTimer = object : CountDownTimer(10000,1000) {
            override fun onTick(millisUntilFinished : Long)
            {
                restProgress++
                progressBar.progress = 10-restProgress
                timer.text = (10-restProgress).toString()
            }
            override fun onFinish()
            {
                currentExercisePosition++

                exerciseList!![currentExercisePosition].isSelected = true
                adapterObj!!.notifyDataSetChanged()

                setupExerciseView()
            }
        }.start()
    }

    fun setupExerciseProgressBar()
    {
        ExerciseProgressBar.progress = exerciseProgress //0
        exerciseTimer = object : CountDownTimer(30000,
            1000) {
            override fun onTick(millisUntilFinished : Long)
            {
                exerciseProgress++
                ExerciseProgressBar.progress = 30-exerciseProgress
                ExerciseTimer.text = (30-exerciseProgress).toString()
            }
            override fun onFinish()
            {
                if (currentExercisePosition < exerciseList?.size!!-1) {
                    exerciseList!![currentExercisePosition].isSelected = false
                    exerciseList!![currentExercisePosition].isCompleted = true
                    adapterObj!!.notifyDataSetChanged()
                    setupRestView()
                }
                else {
                    val intent = Intent(
                        this@ExerciseActivity,
                        FinalActivity::class.java
                    )
                    startActivity(intent)
                    finish()
                }
            }
        }.start()
    }

    private fun setupRestView()
    {
        player = MediaPlayer.create(applicationContext,R.raw.gun)
        player!!.isLooping = false
        player!!.start()

        restLinearLayout.visibility = View.VISIBLE
        ExerciseLinearLayout.visibility = View.GONE

        if(restTimer != null)
        {
            restTimer!!.cancel()
            restProgress = 0
        }
        upcomingExerciseName.text = exerciseList!![currentExercisePosition+1].name
        setupRestProgressBar()
    }

    private fun setupExerciseView()
    {
        restLinearLayout.visibility = View.GONE
        ExerciseLinearLayout.visibility = View.VISIBLE

        if(exerciseTimer != null)
        {
            exerciseTimer!!.cancel()
            exerciseProgress = 0
        }
        saytheword(exerciseList!![currentExercisePosition].name)
        setupExerciseProgressBar()
        exerciseImage.setImageResource(exerciseList!![currentExercisePosition].image)
        exerciseName.text = exerciseList!![currentExercisePosition].name
    }

    override fun onInit(status: Int) {
        if(status == TextToSpeech.SUCCESS)
        {
            val result = tts!!.setLanguage(Locale.US)
            if (result == TextToSpeech.LANG_MISSING_DATA ||
                    result == TextToSpeech.LANG_NOT_SUPPORTED)
                Log.e("Error","Language not supported")
        }
        else
        {
            Log.e("Error","Text to speech not found")
        }
    }

    private fun saytheword(text:String)
    {
        tts!!.speak(text,TextToSpeech.QUEUE_FLUSH,null,"")
    }

    private fun setupRecyclerView()
    {
        appRecyclerView.layoutManager = LinearLayoutManager(this,
        LinearLayoutManager.HORIZONTAL,false)
        adapterObj = RecyclerViewAdapter(exerciseList!!,this)
        appRecyclerView.adapter = adapterObj
    }

}