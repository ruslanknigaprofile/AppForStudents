package com.example.appforstudents.Presentation.View

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.example.appforstudents.Domain.ViewModel.RegistrationViewModel
import com.example.appforstudents.Presentation.View.Student.MainActivityStudent
import com.example.appforstudents.Presentation.View.Teacher.MainActivityTeacher
import com.example.appforstudents.R

class RegistrationActivity : AppCompatActivity() {

    private lateinit var vm: RegistrationViewModel

    var registrationView: LinearLayout? = null
    var progressView: LinearLayout? = null
    var firstName: EditText? = null
    var secondName: EditText? = null
    var spiner: Spinner? = null
    var code: EditText? = null
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
        registrationView = findViewById(R.id.registationView)
        registrationView?.isVisible = false
        progressView = findViewById(R.id.progressBar)
        firstName = findViewById(R.id.first_name)
        secondName = findViewById(R.id.second_name)
        spiner = findViewById(R.id.spinner)
        code = findViewById(R.id.verificationCode)
        code?.isVisible = false

        vm.haveData.observe(this){
            if (!it){
                registrationView?.isVisible = true
                progressView?.isVisible = false
            }
        }

        spiner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                val position = resources.getStringArray(R.array.positionSpiner)
                if (position.get(p2) == "Студент"){
                    code?.isVisible = false
                }
                else if (position.get(p2) == "Учитель"){
                    code?.isVisible = true
                }
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }

        reg = findViewById(R.id.registration_btn)
        reg?.setOnClickListener {
            val insertText = firstName?.text.toString() + " " + secondName?.text.toString()
            if (spiner?.selectedItem == "Студент"){
                vm.createId(insertText, "Student")
                toMainActivity()
            }
            else if(spiner?.selectedItem == "Учитель"){
                if (code?.text.toString() == "teacher01"){
                    vm.createId(insertText, "Teacher")
                    toMainActivity()
                }
                else{
                    notification("Введён не верный код!")
                }
            }
        }
    }

    fun toMainActivity(){
        if (vm.position.value == "Student"){
            val intent = Intent(this, MainActivityStudent::class.java)
            startActivity(intent)
        } else if(vm.position.value == "Teacher"){
            val intent = Intent(this, MainActivityTeacher::class.java)
            startActivity(intent)
        }


    }

    fun notification(message: String){
        val toast = Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT)
        toast.show()
    }
}