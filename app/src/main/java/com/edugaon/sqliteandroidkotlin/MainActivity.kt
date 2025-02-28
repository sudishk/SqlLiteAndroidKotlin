package com.edugaon.sqliteandroidkotlin

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.ArrayList

class MainActivity : AppCompatActivity(), StudentItemClickListener {

    var students: ArrayList<StudentModel> = ArrayList()
    lateinit var studentAdapter: StudentAdapter
    lateinit var dbHelper: DbHelper
    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val  addStudentButton = findViewById<Button>(R.id.addStudent)
        val  nameEditText = findViewById<EditText>(R.id.nameEditText)
        val  emailEditText = findViewById<EditText>(R.id.emailEditText)
        val  userDetailText = findViewById<TextView>(R.id.userDetails)
        val  studentListView = findViewById<ListView>(R.id.studentListView)

         dbHelper = DbHelper(this)

        addStudentButton.setOnClickListener {
           val studentId = dbHelper.insertStudent(nameEditText.text.toString(),emailEditText.text.toString())

            if (studentId.toInt() != -1){
                getData()
                Toast.makeText(this, "Student Added Successfully", Toast.LENGTH_SHORT).show()
            }else {
                Toast.makeText(this, "Student Adding Failed", Toast.LENGTH_SHORT).show()

            }
        }


//        Toast.makeText(this, "${allStudents[1].name}", Toast.LENGTH_SHORT).show()
//        userDetailText.text ="${allStudents[0].name}"

        studentAdapter = StudentAdapter(students, this, this)
        studentListView.adapter = studentAdapter

        getData()
    }

    override fun onUpdateClick(student: StudentModel) {
        val intent = Intent(this, UpdateStudentActivity::class.java)
        intent.putExtra("name_key", student.name)
        intent.putExtra("id_key", student.id)
        intent.putExtra("email_key", student.email)
        startActivity(intent)
        finish()
    }
    fun getData(){
        val allStudents = dbHelper.getAllStudents()
        students.clear()
        students.addAll(allStudents)
        studentAdapter.notifyDataSetChanged()
//        Toast.makeText(this, "${allStudents[1].name}", Toast.LENGTH_SHORT).show()

    }
    override fun onDeleteClick(student: StudentModel) {
      val result = student.id?.let { dbHelper.deleteStudent(it) }
        if (result != -1){
           getData()
            Toast.makeText(this, "Student deleted Successfully", Toast.LENGTH_SHORT).show()
        }else {
            Toast.makeText(this, "Student deletion Failed", Toast.LENGTH_SHORT).show()

        }
    }


}