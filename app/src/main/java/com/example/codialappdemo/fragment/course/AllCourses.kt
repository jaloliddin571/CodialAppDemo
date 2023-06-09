package com.example.codialappdemo.fragment.course

import android.content.DialogInterface.BUTTON_POSITIVE
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.codialappdemo.Adapter.RvAdapterCourses
import com.example.codialappdemo.Adapter.RvItemClick
import com.example.codialappdemo.R
import com.example.codialappdemo.databinding.AlertDialogCoursesAddLayoutBinding
import com.example.codialappdemo.databinding.FragmentAllCoursesBinding
import com.example.codialappdemo.db.MyDbHelper
import com.example.codialappdemo.model.MyCourse


open class AllCourses : Fragment(), RvItemClick {
    private lateinit var binding: FragmentAllCoursesBinding
    private lateinit var rvAdapterCourses: RvAdapterCourses
    private lateinit var myDbHelper: MyDbHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAllCoursesBinding.inflate(layoutInflater, container, false)
        myDbHelper = MyDbHelper(requireContext())

        val list = myDbHelper.getAllCourse()

        rvAdapterCourses = RvAdapterCourses(requireContext(), list, this)
        binding.apply {
            binding.myRv.adapter = rvAdapterCourses

            btnBack.setOnClickListener {
                findNavController().popBackStack()
            }

            btnAdd.setOnClickListener {
                val alertDialogLayoutBinding =
                    AlertDialogCoursesAddLayoutBinding.inflate(layoutInflater)
                val dialog: AlertDialog =
                    AlertDialog.Builder(requireContext(), R.style.MyMenuDialogTheme)
                        .setView(alertDialogLayoutBinding.root)
                        .setPositiveButton(
                            "Qo'shish"
                        ) { _, _ ->
                            val nameCourse = alertDialogLayoutBinding.edtName.text.toString().trim()
                            val aboutCourse =
                                alertDialogLayoutBinding.edtAbout.text.toString().trim()
                            myDbHelper.addCourse(MyCourse(nameCourse, aboutCourse))
                            rvAdapterCourses.list = myDbHelper.getAllCourse()
                            rvAdapterCourses.notifyItemInserted(rvAdapterCourses.itemCount - 1)
                        }
                        .setNegativeButton("Yopish", null)
                        .create()
                dialog.show()
                dialog.getButton(BUTTON_POSITIVE).setTextColor(Color.parseColor("#FFB800"))
            }

        }




        return binding.root
    }

    override fun itemClicked(courseData: MyCourse, position: Int) {
        findNavController().navigate(
            R.id.courseInfo_zzz,
            bundleOf("course" to courseData, "position" to position)
        )
    }

}