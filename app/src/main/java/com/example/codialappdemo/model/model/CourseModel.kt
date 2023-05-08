package com.example.codialappdemo.model.model

import com.example.codialappdemo.model.model.MentorModel
import com.example.codialappdemo.model.model.StudentModel

data class CourseModel(
    var id: Int,
    val name: String,
    val about: String,
    var listStudent: ArrayList<StudentModel>,
    var listMentor:ArrayList<MentorModel>
):java.io.Serializable