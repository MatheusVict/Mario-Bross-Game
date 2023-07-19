package dev.matheusvict.mariogame

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.android.material.snackbar.Snackbar
import dev.matheusvict.mariogame.databinding.ActivityMainBinding
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var numberList: MutableList<Int> = mutableListOf()
    private val imgsList: MutableList<Int> = mutableListOf()
    private var progress = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN

        binding.surpriseNumber.setImageResource(R.drawable.bloco)

        binding.btnPlay.setOnClickListener { view ->
            val typedNumber = binding.replyInput.text.toString()
            if (typedNumber.isEmpty()) {
                message(view, "Coloque um número!", "#FF0000")
                return@setOnClickListener
            }
            generateRandomNumber(view, typedNumber.toInt())

        }

        binding.btnReset.setOnClickListener { resetAll() }
    }

    private fun generateRandomNumber(view: View, typedNumber: Int) {
        for (i in 0..11) {
            numberList.add(i)
        }

        val randomNumber = Random.nextInt(11)

        val imgNumber = getImageFromNumber(randomNumber)

        verifyCorrectNumber(view, typedNumber, randomNumber, imgNumber)

    }

    private fun getImageFromNumber(randomNumber: Int): Int {
        setImagesForList()
        return imgsList[randomNumber.coerceIn(0, imgsList.size - 1)]
    }

    private fun setImagesForList() {
        val imgs: MutableList<Int> = mutableListOf(
            R.drawable.n0,
            R.drawable.n1,
            R.drawable.n3,
            R.drawable.n4,
            R.drawable.n5,
            R.drawable.n6,
            R.drawable.n7,
            R.drawable.n8,
            R.drawable.n9,
            R.drawable.n10,
            R.drawable.bloco,
        )

        imgsList.addAll(imgs)
    }

    private fun verifyCorrectNumber(
        view: View,
        typedNumber: Int,
        randomNumber: Int,
        imgNumber: Int
    ) {
        if (typedNumber != randomNumber) {
            message(view, "Você errou! tente novamente!", "#FF0000")
            progress += 30
            binding.linearProgressIndicator.setProgress(progress, true)
        } else {
            message(view, "Parabéns você acertou!", "#2D9031")
            progress -= 120
            binding.surpriseNumber.setImageResource(imgNumber)
            binding.replyInput.setText("")
            binding.linearProgressIndicator.setProgress(progress, true)
            progress = 0
        }

        if (progress > 90) {
            val intent = Intent(this, GameOver::class.java)
            startActivity(intent)
        }

    }

    private fun resetAll() {
        binding.replyInput.setText("")
        progress = 0
        binding.linearProgressIndicator.setProgress(progress, true)
        binding.surpriseNumber.setImageResource(R.drawable.bloco)
    }

    private fun message(view: View, message: String, color: String) {
        val snackbar = Snackbar.make(view, message, Snackbar.LENGTH_SHORT)
        snackbar.setBackgroundTint(Color.parseColor(color))
        snackbar.setTextColor(Color.parseColor("#FFFFFF"))
        snackbar.show()
    }

}