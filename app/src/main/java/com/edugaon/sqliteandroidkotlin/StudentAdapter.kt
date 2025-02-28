package com.edugaon.sqliteandroidkotlin

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import java.util.ArrayList

class StudentAdapter(val students: ArrayList<StudentModel>, val context: Context, val studentItemClickListener: StudentItemClickListener):BaseAdapter() {
    override fun getCount(): Int {
        return students.size
    }

    override fun getItem(p0: Int): Any {
        return  students[p0]
    }

    override fun getItemId(p0: Int): Long {
       return  p0.toLong()
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val student = students[p0]
        val itemLayout = LayoutInflater.from(context).inflate(R.layout.student_list_item, p2, false)
        val nameTextView = itemLayout.findViewById<TextView>(R.id.nameTextView)
        val emailTextView = itemLayout.findViewById<TextView>(R.id.emailTextView)
        val deleteImageView = itemLayout.findViewById<ImageView>(R.id.deleteImage)
        val updateImageView = itemLayout.findViewById<ImageView>(R.id.updateImage)

        nameTextView.text = student.name
        emailTextView.text = student.email

        updateImageView.setOnClickListener {
            studentItemClickListener.onUpdateClick(student)
        }

        deleteImageView.setOnClickListener {
            studentItemClickListener.onDeleteClick(student)
        }

        return itemLayout
    }

}

interface StudentItemClickListener{
    fun onUpdateClick(student: StudentModel)
    fun onDeleteClick(student: StudentModel)
}