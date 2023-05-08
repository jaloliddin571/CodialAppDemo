package com.example.codialappdemo.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.codialappdemo.R
import com.example.codialappdemo.databinding.FragmentHomeBinding

class HomeFragment : Fragment(), View.OnClickListener {


    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)

        binding.apply {
            btnCourse.setOnClickListener(this@HomeFragment)
            btnGroup.setOnClickListener(this@HomeFragment)
            btnMentor.setOnClickListener(this@HomeFragment)
        }

        return binding.root
    }

    override fun onClick(p0: View?) {
        binding.apply {
            when (p0?.id) {
                btnCourse.id -> {

                    findNavController().navigate(R.id.allCourses)
                }
                btnGroup.id -> {
                    findNavController().navigate(R.id.allCourseGroup)
                }
                btnMentor.id -> {
                    findNavController().navigate(R.id.addCourseMentor)
                }


            }
        }

    }

}