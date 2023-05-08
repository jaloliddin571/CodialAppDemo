package com.example.codialappdemo.Utils

import com.example.codialappdemo.model.MyCourse
import com.example.codialappdemo.model.MyGroup
import com.example.codialappdemo.model.MyMentor
import com.example.codialappdemo.model.MyStudent

interface MyDbHelperInterface {
    fun addCourse(course: MyCourse)
    fun getAllCourse(): ArrayList<MyCourse>
    fun deleteCourse(course: MyCourse)

    fun addMentor(mentor: MyMentor)
    fun getAllMentor(): ArrayList<MyMentor>
    fun editMentor(mentor: MyMentor)
    fun deleteMentor(mentor: MyMentor)

    fun addGroup(group: MyGroup)
    fun getAllGroup(): ArrayList<MyGroup>
    fun editGroup(group: MyGroup)
    fun deleteGroup(group: MyGroup)

    fun addStudent(student: MyStudent)
    fun getAllStudent(): ArrayList<MyStudent>
    fun editStudent(student: MyStudent)
    fun deleteStudent(student: MyStudent)

    fun getCourseById(id: Int): MyCourse
    fun getMentorById(id: Int): MyMentor
    fun getGroupById(id: Int): MyGroup

}