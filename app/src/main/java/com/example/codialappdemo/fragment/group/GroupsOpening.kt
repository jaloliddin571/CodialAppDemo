package com.example.codialappdemo.fragment.group

import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.codialappdemo.Adapter.RvAdapterGroup
import com.example.codialappdemo.Adapter.RvGroupClick
import com.example.codialappdemo.R
import com.example.codialappdemo.Utils.Constants
import com.example.codialappdemo.Utils.Constants.CURRENT_COURSE
import com.example.codialappdemo.databinding.AlertDialogGroupEditBinding
import com.example.codialappdemo.databinding.FragmentGroupsOpeningBinding
import com.example.codialappdemo.db.MyDbHelper
import com.example.codialappdemo.model.MyGroup
import com.example.codialappdemo.model.MyMentor
import com.example.codialappdemo.model.MyStudent


class GroupsOpening : Fragment(), RvGroupClick {

    private lateinit var binding: FragmentGroupsOpeningBinding
    private lateinit var rvAdapter: RvAdapterGroup
    private lateinit var myDbHelper: MyDbHelper
    private lateinit var dbStudentList: ArrayList<MyStudent>
    private val mentorList = ArrayList<MyMentor>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGroupsOpeningBinding.inflate(layoutInflater, container, false)
        myDbHelper = MyDbHelper(requireContext())
        dbStudentList = myDbHelper.getAllStudent()
        val allGroupList = myDbHelper.getAllGroup()
        val openingList = ArrayList<MyGroup>()
        allGroupList.forEach {
            if (it.isOpen == 1 && it.courseId?.id == CURRENT_COURSE.id) {
                openingList.add(it)
            }
        }



        rvAdapter = RvAdapterGroup(openingList,myDbHelper.getAllStudent(), requireContext(), this)

        binding.myRv.adapter = rvAdapter


        return binding.root

    }

    override fun btnEditClick(group: MyGroup, position: Int) {
        group.dayIndex?.let {
            Toast.makeText(requireContext(), it.toString(), Toast.LENGTH_SHORT).show()
        }

        val name = group.name
        val alertDialogLayoutBinding =
            AlertDialogGroupEditBinding.inflate(layoutInflater)
        alertDialogLayoutBinding.apply {
            edtName.setText(name)
            val mentorNamesList = ArrayList<String>()
            val dbMentorList = myDbHelper.getAllMentor()
            mentorList.clear()
            dbMentorList.forEach {
                if (it.courseId?.id == CURRENT_COURSE.id) {
                    mentorList.add(it)
                    mentorNamesList.add("${it.name} ${it.surname}")

                }
            }
            val adapter = ArrayAdapter<String>(
                requireContext(),
                android.R.layout.simple_spinner_dropdown_item,
                mentorNamesList
            )

            spinnerMentor.adapter = adapter
            Toast.makeText(
                context,
                dbMentorList.indexOf(group.mentorId).toString(),
                Toast.LENGTH_SHORT
            ).show()
            spinnerMentor.setSelection(dbMentorList.indexOf(group.mentorId))

            spinnerTime.adapter = ArrayAdapter<String>(
                requireContext(),
                android.R.layout.simple_list_item_1,
                Constants.ARRAY_TIME
            )

            spinnerDays.adapter = ArrayAdapter<String>(
                requireContext(),
                android.R.layout.simple_list_item_1,
                Constants.ARRAY_DAYS
            )


        }
        val dialog: AlertDialog =
            AlertDialog.Builder(requireContext(), R.style.MyMenuDialogTheme)
                .setView(alertDialogLayoutBinding.root)
                .setPositiveButton(
                    "O'zgartirish"
                ) { _, _ ->
                    alertDialogLayoutBinding.apply {
                        val editedGroup = MyGroup(
                            edtName.text.toString().trim(),
                            group.courseId,
                            mentorList[spinnerMentor.selectedItemPosition],
                            spinnerTime.selectedItemPosition,
                            spinnerDays.selectedItemPosition,
                            1
                        )

                        myDbHelper.editGroup(editedGroup)
                        rvAdapter.groupList[position] = editedGroup
                        rvAdapter.notifyItemChanged(position)
                    }

                }
                .setNegativeButton("Yopish", null)
                .create()
        dialog.show()
        dialog.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(Color.parseColor("#FFB800"))

    }

    override fun btnViewClick(group: MyGroup, position: Int) {
        findNavController().navigate(R.id.viewGroup, bundleOf("key" to group))
    }

    override fun btnDeleteClick(group: MyGroup, position: Int) {
        val dialog: AlertDialog.Builder =
            AlertDialog.Builder(requireContext(), R.style.MyMenuDialogTheme)
                .setMessage("${group.name}ni o'chirmoqchimisiz?")
                .setNegativeButton("Yo'q", null)
                .setPositiveButton(
                    "Xa"
                ) { _, _ ->
                    binding.apply {
                        rvAdapter.groupList.removeAt(position)
                        myRv.adapter?.notifyItemRemoved(position)
                        myRv.adapter?.notifyItemRangeChanged(0, rvAdapter.groupList.size)
                    }
                    myDbHelper.deleteGroup(group)

                    dbStudentList.forEach {
                        if (it.groupId?.id == group.id) {
                            myDbHelper.deleteStudent(it)
                        }
                    }
                }
        dialog.show()

    }

}