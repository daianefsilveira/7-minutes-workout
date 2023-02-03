package br.com.a7minutesworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Toast
import br.com.a7minutesworkout.databinding.ActivityExerciseBinding

class ExerciseActivity : AppCompatActivity() {

    private var binding: ActivityExerciseBinding? = null

    private var restTimer: CountDownTimer? = null
    private var restProgress = 0

    private var exerciseTimer: CountDownTimer? = null
    private var exerciseProgress = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExerciseBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.toolbarExercise)

        if (supportActionBar != null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        binding?.toolbarExercise?.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        setupRestView()
    }

    /**
     * Function used to set the timer for REST.
     */
    private fun setupRestView() {
        /**
         * Here firstly we will check if the timer is running the and it is not null then cancel the running timer and start the new one.
         * And set the progress to initial which is 0.
         */
        if (restTimer != null) {
            restTimer!!.cancel()
            restProgress = 0
        }
        // Function used to set the progress details.
        setRestProgressBar()
    }

    /**
     * Function used to set the progress of the timer using the progress for Exercise View.
     */
    private fun setupExerciseView() {
        // Here according to the view make it visible as this is Exercise View so exercise view is visible and rest view is not.
        binding?.flProgressBar?.visibility = View.INVISIBLE
        binding?.flExerciseView?.visibility = View.VISIBLE
        /**
         * Here firstly we will check if the timer is running and it is not null then cancel the running timer and start the new one.
         * And set the progress to the initial value which is 0.
         */
        if (exerciseTimer != null) {
            exerciseTimer?.cancel()
            exerciseProgress = 0
        }
        setExerciseProgressBar()
    }

    /**
     * Function used to set the progress of timer using the progress
     */
    private fun setRestProgressBar() {
        binding?.progressBar?.progress = restProgress // Sets the current progress to the specified value.

        /**
         * @param millisInFuture The number of millis in the future from the call
         *   to {#start()} until the countdown is done and {#onFinish()}
         *   is called.
         * @param countDownInterval The interval along the way to receive
         *   {#onTick(long)} callbacks.
         */
        // Here we have started a timer of 10 seconds so the 10000 is milliseconds is 10 seconds and the countdown interval is 1 second so it 1000.
        restTimer = object : CountDownTimer(10000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                restProgress++
                binding?.progressBar?.progress = 10 - restProgress // Indicates progress bar progress
                binding?.tvTimer?.text = (10 - restProgress).toString() // Current progress is set to text view in terms of seconds.
            }
            override fun onFinish() {
                // When the 10 seconds will complete this will be executed.
                setupExerciseView()
            }
        }.start()
    }

    /**
     * Function used to set the progress of the timer using the progress for Exercise View for 30 Seconds
     */
    private fun setExerciseProgressBar() {
        binding?.progressBarExercise?.progress = exerciseProgress // Sets the current progress to the specified value.

        /**
         * @param millisInFuture The number of millis in the future from the call
         *   to {#start()} until the countdown is done and {#onFinish()}
         *   is called.
         * @param countDownInterval The interval along the way to receive
         *   {#onTick(long)} callbacks.
         */
        // Here we have started a timer of 10 seconds so the 10000 is milliseconds is 10 seconds and the countdown interval is 1 second so it 1000.
        exerciseTimer = object : CountDownTimer(30000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                exerciseProgress++
                binding?.progressBarExercise?.progress = 30 - exerciseProgress // Indicates progress bar progress
                binding?.tvTimerExercise?.text = (30 - exerciseProgress).toString() // Current progress is set to text view in terms of seconds.
            }
            override fun onFinish() {
                // When the 10 seconds will complete this will be executed.
                Toast.makeText(
                    this@ExerciseActivity,
                    "30 Seconds are over, let's go to the rest view",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }.start()
    }

    // Destroying the timer when closing the activity or app.
    /**
     * Here in the Destroy function we will reset the rest timer if it is running.
     */
    override fun onDestroy() {
        super.onDestroy()

        if (restTimer != null) {
            restTimer?.cancel()
            restProgress = 0
        }

        if (exerciseTimer != null) {
            exerciseTimer?.cancel()
            exerciseProgress = 0
        }

        binding = null
    }
}