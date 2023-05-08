package com.example.codialappdemo.model.model

import com.example.codialappdemo.model.MyGroup

data class StudentModel(
    val surname: String,
    val name: String,
    val fatherName: String,
    val date: String,
    val groupId: MyGroup,
)