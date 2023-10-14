package com.example.pppb_uts

import android.content.Intent
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.pppb_uts.databinding.ActivityDashboardBinding
import com.example.pppb_uts.databinding.ActivityLoginBinding
import com.example.pppb_uts.databinding.ActivityMainBinding

class DashboardActivity : AppCompatActivity() {
    private lateinit var binding : ActivityDashboardBinding

    private val TICKET_DATE = 0
    private val TICKET_FROM = 1
    private val TICKET_TO = 2
    private val TICKET_CLASS = 3
    private val TICKET_PACKETS = 4

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val receivedData = intent?.extras
        val username = receivedData?.getString("username") ?: ""
        val existingTicketData = receivedData?.getString("ticketData") ?: ""

        with(binding){


            txtUsername.text = "Welcome, $username"

            Log.d("PAAAA", existingTicketData)

            if (existingTicketData != ""){
                showLatestTicket(existingTicketData)
                cardRecent.visibility = View.VISIBLE
            } else {
                cardRecent.visibility = View.INVISIBLE
            }

            btnReserve.setOnClickListener{

                val dataToSend = Bundle()
                dataToSend.putString("username", username)
                dataToSend.putString("ticketData", existingTicketData)

                val intentToDashboard = Intent(this@DashboardActivity, BookingActivity::class.java)
                intentToDashboard.apply { putExtras(dataToSend) }
                startActivity(intentToDashboard)
            }

            calendarView.setOnDateChangeListener { calendarView, selectedYear, selectedMonth, dayOfMonth ->
                showAvailability(existingTicketData, dayOfMonth, selectedMonth, selectedYear)
            }

            btnLogout.setOnClickListener{
                val intentToLogin = Intent(this@DashboardActivity, Login::class.java)
                startActivity(intentToLogin)
            }
        }
    }
    private fun showAvailability(existingTicketData : String, date : Int, year : Int, month : Int){
        var city : String = ""

        if (!existingTicketData.isNullOrBlank()) {
            val ticketDataList = existingTicketData.split(";")
            if (ticketDataList.isNotEmpty()) {
                val lastTicketData = ticketDataList.last()
                city = getCity(lastTicketData)
            } else {
                city = ""
            }
        }
        val text : String
        if ( city == "" ){
            text = "No tickets booked on this date"
        } else {
            text = "Ticket to $city is booked on this date"
        }

        Toast.makeText(this@DashboardActivity, text, Toast.LENGTH_SHORT).show()
    }
    private fun getCity(data : String) : String {
        val dataList = data.split("-")
        return dataList[TICKET_TO]
    }
    private fun showLatestTicket(existingTicketData : String){
        with(binding){
            if (!existingTicketData.isNullOrBlank()) {
                Log.d("awal", "masuk if1"+existingTicketData)
                val ticketDataList = existingTicketData
//                Log.d("awal last", ticketDataList.last())
                if (ticketDataList.isNotEmpty()) {
                    Log.d("awal", "masuk if2")
                    val lastTicketData = ticketDataList.last()
//                    Log.d("awal", lastTicketData)

                    val data = existingTicketData.split("-")
//                    Log.d("awal", lastTicketData)
                    txtTo.text = "To " + data[TICKET_TO].toString()
                    txtFrom.text = "From " + data[TICKET_FROM].toString()
                    txtDate.text = data[TICKET_DATE].toString()
                    txtClass.text = data[TICKET_CLASS].toString() + " Class"
                    txtPackets.text = "Aditional ammenities: \n" + data[TICKET_PACKETS].toString()
                }
            }

        }
    }
}