package com.example.pppb_uts

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.pppb_uts.databinding.ActivityBookingBinding
import com.example.pppb_uts.databinding.ActivityDashboardBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class BookingActivity : AppCompatActivity() {
    private lateinit var binding : ActivityBookingBinding
    var price : Int = 700000
    val packets = mutableListOf<String>()
    var selectedClass : String = ""


    private val calendar = Calendar.getInstance()
    private var formattedDate : String = ""
    private var selectedDate = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val classTypes = arrayOf("Economy", "Business", "Executive", "VIP")

        val receivedData = intent?.extras
        val username = receivedData?.getString("username") ?: ""
        var existingTicketData = receivedData?.getString("ticketData") ?: ""


        with(binding){

            val adapter = ArrayAdapter(this@BookingActivity, android.R.layout.simple_spinner_item, classTypes)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerClass.adapter = adapter
            updatePrice()



            spinnerClass.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(p0: AdapterView<*>?, view: View?, position: Int, id: Long) {
                        selectedClass = classTypes[position]
                    }
                    override fun onNothingSelected(parent: AdapterView<*>?) {
                        //
                    }
                }


            btnBook.setOnClickListener{

                if ( validate() ){

                    val date = inputDate.text.toString()
                    val city1 = inputCity1.text.toString()
                    val city2 = inputCity2.text.toString()
                    val station1 = inputStation1.text.toString()
                    val station2 = inputStation2.text.toString()

                    val listPackets : String = if (packets.isNotEmpty()){
                        packets.joinToString(", ")
                    } else {
                        "none"
                    }

                    val newTicket = "$date-$station1, $city1-$station2, $city2-$selectedClass-$listPackets-$price;"

                    existingTicketData += newTicket
                    Log.d("OMG", existingTicketData + "================================================")

                    val dataToSend = Bundle()
                    dataToSend.putString("username", username)
                    dataToSend.putString("ticketData", existingTicketData)

                    val intentToDashboard = Intent(this@BookingActivity, DashboardActivity::class.java)
                    intentToDashboard.apply { putExtras(dataToSend) }
                    startActivity(intentToDashboard)

                } else {
                    Toast.makeText(this@BookingActivity, "Make sure to fill every form", Toast.LENGTH_SHORT).show()
                }

            }

            inputDate.setOnClickListener {
                showDatePicker()
            }


            toggleButton1.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) {
                    price += 20000
                    packets.add(toggleButton1.text.toString())
                } else {
                    price -= 20000
                    packets.remove(toggleButton1.text.toString())
                }
                updatePrice()
            }

            toggleButton2.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) {
                    price += 20000
                    packets.add(toggleButton2.text.toString())
                } else {
                    price -= 20000
                    packets.remove(toggleButton2.text.toString())
                }
                updatePrice()
            }

            toggleButton3.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) {
                    price += 20000
                    packets.add(toggleButton3.text.toString())
                } else {
                    price -= 20000
                    packets.remove(toggleButton3.text.toString())
                }
                updatePrice()
            }

            toggleButton4.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) {
                    price += 20000
                    packets.add(toggleButton4.text.toString())
                } else {
                    price -= 20000
                    packets.remove(toggleButton4.text.toString())
                }
                updatePrice()
            }

            toggleButton5.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) {
                    price += 20000
                    packets.add(toggleButton5.text.toString())
                } else {
                    price -= 20000
                    packets.remove(toggleButton5.text.toString())
                }
                updatePrice()
            }

            toggleButton6.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) {
                    price += 20000
                    packets.add(toggleButton6.text.toString())
                } else {
                    price -= 20000
                    packets.remove(toggleButton6.text.toString())
                }
                updatePrice()
            }

            toggleButton7.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) {
                    price += 20000
                    packets.add(toggleButton7.text.toString())
                } else {
                    price -= 20000
                    packets.remove(toggleButton7.text.toString())
                }
                updatePrice()
            }


        }
    }
    private fun validate(): Boolean {
        with(binding){
            return (inputDate.text.toString().isNotEmpty()
                    && inputCity1.text.toString().isNotEmpty()
                    && inputCity2.text.toString().isNotEmpty()
                    && inputStation1.text.toString().isNotEmpty()
                    && inputStation2.text.toString().isNotEmpty()
                    && selectedClass.isNotEmpty())
        }
    }
    private fun updatePrice(){
        with(binding){
            txtPrice.text = "Total Price: Rp.$price"
        }
    }

    private fun showDatePicker(){
        with(binding){
            val datePickerDialog = DatePickerDialog(this@BookingActivity, {DatePicker, year : Int, monthOfYear : Int, dayOfMonth : Int ->
                selectedDate.set(year, monthOfYear, dayOfMonth)
                val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                formattedDate = dateFormat.format(selectedDate.time)
                inputDate.setText(formattedDate)
            },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )
            datePickerDialog.show()
        }
    }
}