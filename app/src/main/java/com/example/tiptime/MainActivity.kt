package com.example.tiptime

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.tiptime.databinding.ActivityMainBinding
import java.text.NumberFormat

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.calculateButton.setOnClickListener{calculateTip()}
    }

    @SuppressLint("StringFormatInvalid")
    fun calculateTip() {
        val stringInTextField = binding.costOfServiceEditText.text.toString()
        val selectedId = binding.tipOptions.checkedRadioButtonId

        if (stringInTextField.isBlank()) {
            binding.costOfService.error = "Please enter a cost"
            return
        }

        var cost = 0.0
        try {
            cost = stringInTextField.toDouble()
        } catch (e: NumberFormatException) {
            // EditText contains an invalid number, display an error message
            binding.costOfService.error = "Invalid number"
            return
        }

        val tipPercentage = when (selectedId) {
            R.id.option_twenty_percent -> 0.20
            R.id.option_eighteen_percent -> 0.18
            else -> 0.15
        }
        var tip = tipPercentage * cost

        val roundUp = binding.roundUpSwitch.isChecked
        if (roundUp) {
            tip = kotlin.math.ceil(tip)
        }

        NumberFormat.getCurrencyInstance()
        val formattedTip = NumberFormat.getCurrencyInstance().format(tip)
        binding.tipResult.text = getString(R.string.tip_amount, formattedTip)
    }
}