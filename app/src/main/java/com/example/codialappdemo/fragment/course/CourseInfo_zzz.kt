package com.example.codialappdemo.fragment.course

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import com.example.codialappdemo.R
import com.example.codialappdemo.databinding.FragmentCourseInfoZzzBinding
import com.example.codialappdemo.db.MyDbHelper
import com.example.codialappdemo.model.MyCourse

class CourseInfo_zzz : Fragment() {
    private lateinit var binding:FragmentCourseInfoZzzBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentCourseInfoZzzBinding.inflate(layoutInflater)
        val course = arguments?.getSerializable("course") as MyCourse
        val myDbHelper = MyDbHelper(requireContext())
        binding.apply {
            tvNameCourse.text = course.name
            tvAboutCourse.text = course.about

            btnBack.setOnClickListener {
                findNavController().popBackStack()
            }

            btnDelete.setOnClickListener {
                val dialog: AlertDialog.Builder =
                    AlertDialog.Builder(requireContext(), R.style.MyMenuDialogTheme)
                        .setMessage("Ushbu kursni o'chirmoqchimisiz?")
                        .setNegativeButton("Yo'q", null)
                        .setPositiveButton(
                            "Xa"
                        ) { _, _ ->
                            val courseList = myDbHelper.getAllCourse()
                            val mentorList = myDbHelper.getAllMentor()
                            val groupList = myDbHelper.getAllGroup()
                            val studentList = myDbHelper.getAllStudent()
                            courseList.forEach { courseIt ->

                                if (courseIt.id == course.id) {
                                    mentorList.forEach { mentorIt ->
                                        if (course.id == mentorIt.courseId?.id) {
                                            groupList.forEach { groupIt ->
                                                if (groupIt.mentorId?.id == mentorIt.id) {
                                                    studentList.forEach { studentIT ->
                                                        if (studentIT.groupId?.id == groupIt.id) {
                                                            myDbHelper.deleteStudent(studentIT)
                                                        }
                                                    }
                                                    myDbHelper.deleteGroup(groupIt)
                                                }

                                            }
                                            myDbHelper.deleteMentor(mentorIt)
                                        }
                                    }
                                    myDbHelper.deleteCourse(course)
                                }
                            }
                            Toast.makeText(context, "O'chirildi", Toast.LENGTH_SHORT).show()
                            findNavController().popBackStack()
                        }
                dialog.show()


            }


        }

        return binding.root
    }

}