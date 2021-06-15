package com.example.healthandfitness

object ExerciseController
{
    fun exerciseFunction():ArrayList<ExerciseModel>
    {
        val exerciseList = ArrayList<ExerciseModel>()

        val ex1 = ExerciseModel(1,"Push Up",
        R.drawable.pushup,false,false)

        exerciseList.add(ex1)

        val ex2 = ExerciseModel(2,"Squats",
        R.drawable.squats,false,false)

        exerciseList.add(ex2)

        val ex3 = ExerciseModel(3,"Ab Crunches",
        R.drawable.abs,false,false)

        exerciseList.add(ex3)

        return exerciseList

    }
}