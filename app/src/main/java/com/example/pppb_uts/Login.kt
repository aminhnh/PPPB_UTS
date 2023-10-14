package com.example.pppb_uts

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.example.pppb_uts.databinding.ActivityLoginBinding

class Login : AppCompatActivity() {
    private lateinit var binding : ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val data = intent.extras
        val username = data?.getString("username")
        val password = data?.getString("password")

        with(binding){
            btnLogin.setOnClickListener{
                if ( inputUsername.text.toString() == "" ){
                    inputUsername.error = "Username is required!"
                    return@setOnClickListener
                }
                if ( inputPassword.text.toString() == "" ){
                    inputPassword.error = "Password is required!"
                    return@setOnClickListener
                }
                if (inputUsername.text.toString() != username){
                    inputUsername.error = "Username does not exist"
                    return@setOnClickListener
                } else {
                    if (inputPassword.text.toString() != password){
                        inputPassword.error = "Password is incorrect"
                        return@setOnClickListener
                    } else {
                        // Password dan username benar

                        val dataToSend = Bundle()
                        dataToSend.putString("username", inputUsername.text.toString())

                        val intentToDashboard = Intent(this@Login, DashboardActivity::class.java)
                        intentToDashboard.apply { putExtras(dataToSend) }
                        startActivity(intentToDashboard)
                    }
                }
            }
        }
    }
}