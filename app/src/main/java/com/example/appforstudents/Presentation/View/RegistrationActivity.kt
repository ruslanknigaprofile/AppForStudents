package com.example.appforstudents.Presentation.View

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.example.appforstudents.Domain.ViewModel.RegistrationViewModel
import com.example.appforstudents.R
import com.google.android.material.appbar.MaterialToolbar

class RegistrationActivity : AppCompatActivity() {

    private lateinit var vm: RegistrationViewModel

    var firstName: EditText? = null
    var secondName: EditText? = null
    var reg: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
        vm = ViewModelProvider(this).get(RegistrationViewModel::class.java)

        vm.loadId {
            toMainActivity()
        }

        init()
    }

    private fun init(){
        firstName = findViewById(R.id.first_name)
        secondName = findViewById(R.id.second_name)

        reg = findViewById(R.id.registration_btn)
        reg?.setOnClickListener {
            val insertText = firstName?.text.toString() + " " + secondName?.text.toString()
            vm.createId(insertText)

            toMainActivity()
        }
    }

    fun toMainActivity(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}