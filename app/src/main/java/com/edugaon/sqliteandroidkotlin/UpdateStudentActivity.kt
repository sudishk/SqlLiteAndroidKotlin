package com.edugaon.sqliteandroidkotlin

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class UpdateStudentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_update_student)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val id = intent.extras?.getInt("id_key")
        val name = intent.extras?.getString("name_key")
        val email = intent.extras?.getString("email_key")

        val  updateButton = findViewById<Button>(R.id.updateButton)
        val  nameEditText = findViewById<EditText>(R.id.nameEditText)
        val  emailEditText = findViewById<EditText>(R.id.emailEditText)

        nameEditText.setText(name.toString())
        emailEditText.setText(email.toString())
        updateButton.setOnClickListener {
            val dbHelper = DbHelper(this)
            val result = id?.let { it1 -> dbHelper.updateStudent(it1,nameEditText.text.toString(),emailEditText.text.toString() ) }

            if (result != -1){
                Toast.makeText(this, "Student updated Successfully", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }else {
                Toast.makeText(this, "Student updating Failed", Toast.LENGTH_SHORT).show()

            }
        }
    }
}