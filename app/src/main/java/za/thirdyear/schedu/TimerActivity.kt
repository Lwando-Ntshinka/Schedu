package za.thirdyear.schedu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.NumberPicker
import android.widget.ProgressBar

class TimerActivity : BaseActivity()
{
    private lateinit var btnTimerButton : Button
    lateinit var npHours : NumberPicker
    lateinit var npMinutes : NumberPicker
    lateinit var npSeconds : NumberPicker
    lateinit var pdTimerProgressBar : ProgressBar
    lateinit var timer : CountDownTimer
    var totalMillis: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timer)

        /******Hooks******/
        npHours = findViewById(R.id.npTimerHours)
        npMinutes = findViewById(R.id.npTimerMinutes)
        npSeconds = findViewById(R.id.npTimerSeconds)
        pdTimerProgressBar = findViewById(R.id.pbTimerProgress)
        btnTimerButton = findViewById(R.id.btnStopStartTimer)

        npSeconds.maxValue = 59
        npSeconds.minValue = 0
        npMinutes.maxValue = 59
        npMinutes.minValue = 0

        //Event Handler for Button
        btnTimerButton.setOnClickListener()
        {
            var totalmils : Long = ((npHours.value * 3600 + npMinutes.value * 60 + npSeconds.value) * 1000).toLong()
            //Count down Timer
            if (this.btnTimerButton.text == "Start timer") //If Timer is not Counting Down
            {
                if(npSeconds.value == 0 && npMinutes.value == 0 && npHours.value == 0)
                {
                    ShowAlert("Please enter a time")
                }

                else
                {
                    StartTimer()
                }

            }
            else if(btnTimerButton.text == "Stop timer") //If the timer is counting down
            {
                StopTimer()
            }

        }
    }

    override fun getLayoutResourceId(): Int {
        return R.layout.activity_base
    }



    public fun StartTimer()
    {
        totalMillis = ((npHours.value * 3600 + npMinutes.value * 60 + npSeconds.value) * 1000).toLong()
        if (totalMillis == 0L) {
            ShowToast("Please enter a time")
            return
        }

        timer = object : CountDownTimer(totalMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                totalMillis = millisUntilFinished
                val hours = (millisUntilFinished / 1000) / 3600
                val minutes = ((millisUntilFinished / 1000) % 3600) / 60
                val seconds = (millisUntilFinished / 1000) % 60

                npHours.value = hours.toInt()
                npMinutes.value = minutes.toInt()
                npSeconds.value = seconds.toInt()
            }

            override fun onFinish() {
                ShowToast("Timer finished!")
                StopTimer()
            }
        }.start()

        btnTimerButton.text = getString(R.string.stop_timer) //Show Stop Timer while counting down
    }

    public fun StopTimer()
    {
        timer.cancel()
        npHours.value = 0
        npMinutes.value = 0
        npSeconds.value = 0
        btnTimerButton.text = getString(R.string.start_timer)
    }

}