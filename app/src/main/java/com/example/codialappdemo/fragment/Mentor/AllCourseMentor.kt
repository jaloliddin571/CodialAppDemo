package com.example.codialappdemo.fragment.Mentor

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.example.codialappdemo.Adapter.RvAdapterCourses
import com.example.codialappdemo.Adapter.RvItemClick
import com.example.codialappdemo.R
import com.example.codialappdemo.databinding.FragmentAllCoursesBinding
import com.example.codialappdemo.db.MyDbHelper
import com.example.codialappdemo.model.MyCourse


class AllCourseMentor : Fragment(), RvItemClick {
    private lateinit var binding: FragmentAllCoursesBinding
    private lateinit var rvAdapterCourses: RvAdapterCourses


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAllCoursesBinding.inflate(layoutInflater, container, false)
        val myDbHelper = MyDbHelper(requireContext())
        rvAdapterCourses = RvAdapterCourses(requireContext(), myDbHelper.getAllCourse(), this)
        binding.apply {
            myRv.adapter = rvAdapterCourses

            btnBack.setOnClickListener {
                findNavController().popBackStack()
            }

        }


        return binding.root
    }

    override fun itemClicked(courseData: MyCourse, position: Int) {
        findNavController().navigate(R.id.allMentor_zzz, bundleOf("key" to courseData))
    }

}
