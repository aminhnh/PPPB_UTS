package com.example.pppb_uts

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.DatePicker
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.pppb_uts.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Locale

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private val calendar = Calendar.getInstance()
    private var formattedDate : String = ""
    private var selectedDate = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding){


            inputDob.setOnClickListener {
                showDatePicker()
            }

            btnRegister.setOnClickListener{
                print(formattedDate)

                val data = Bundle()
                data.putString("username", inputUsername.text.toString())
                data.putString("email", inputEmail.text.toString())
                data.putString("password", inputPassword.text.toString())
                data.putString("dob", inputDob.text.toString())

                if ( validate(data) ){
                    val intentToLogin = Intent(this@MainActivity, Login::class.java)
                    intentToLogin.apply { putExtras(data) }
                    startActivity(intentToLogin)
                }
            }
        }
    }
    private fun showDatePicker(){
        with(binding){
            val datePickerDialog = DatePickerDialog(this@MainActivity, {DatePicker, year : Int, monthOfYear : Int, dayOfMonth : Int ->
                selectedDate.set(year, monthOfYear, dayOfMonth)
                val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                formattedDate = dateFormat.format(selectedDate.time)
                inputDob.setText(formattedDate)
            },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )
            datePickerDialog.show()
        }
    }
    private fun validate(data : Bundle) : Boolean {
        with(binding){
            var result: Boolean = true

            if ( data.getString("username")!!.isEmpty() ){
                editTextUsername.error = "Username is required!"
                result = false
            }
            if ( data.getString("email") == "" ){
                inputEmail.error = "Email is required!"
                result = false
            }
            if ( data.getString("password")!!.isEmpty() ) {
                inputPassword.error = "Password is required!"
                result = false
            }
            if ( data.getString("password")!!.length < 7 ) {
                inputPassword.error = "Password must be at least 7 characters long"
                result = false
            }
            if ( data.getString("dob")!!.isEmpty() ){
                inputPassword.error = "Password is required!"
                result = false
            } else if (!isOldEnough(data.getString("dob")!!)) {
                Toast.makeText(this@MainActivity, "You are not above the age of 15", Toast.LENGTH_SHORT).show()
                result = false
            }
            if (result && !checkbox.isChecked) {
                Toast.makeText(this@MainActivity, "Please agree to the Terms and Conditions", Toast.LENGTH_SHORT).show()
                result = false
            }

            return result
        }
    }

    fun isOldEnough(inputDate: String) : Boolean {
        val currentYear = Calendar.getInstance().get(Calendar.YEAR)
        val currentMonth = Calendar.getInstance().get(Calendar.MONTH)
        val currentDate = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)

        val dayDob = inputDate.split("/")[0].toInt()
        val monthDob = inputDate.split("/")[1].toInt()
        val yearDob = inputDate.split("/")[2].toInt()

        return if ((currentYear - yearDob) > 15 ) {
            true
        } else if ((currentYear - yearDob) < 15 ){
            false
        } else { // Year is equal
            if (currentMonth > monthDob){
                false
            } else if ( currentMonth < monthDob ){
                true
            } else { // Month is equal
                dayDob >= currentDate
            }
        }
    }

}