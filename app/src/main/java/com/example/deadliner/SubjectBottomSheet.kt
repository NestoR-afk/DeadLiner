package com.example.deadliner

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.appcompat.widget.AppCompatButton
import androidx.lifecycle.ViewModelProvider

import com.example.deadliner.viewmodel.SubjectViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.datepicker.MaterialDatePicker

import java.util.*

class SubjectBottomSheet : BottomSheetDialogFragment() {

     lateinit var subjectViewModel: SubjectViewModel

    private var datePicker: MaterialDatePicker<Long> =
            MaterialDatePicker.Builder.datePicker()
                    .setTitleText("Select date")
                    .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                    .build()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.subject_bottom_sheet, container, false)
        subjectViewModel = ViewModelProvider(this).get(SubjectViewModel::class.java)

        return root
    }

    private fun getDateFromDatePicker(datePicker: MaterialDatePicker<Long>): Date {
        return try {
            Date(datePicker.selection!!)
        } catch (e: NullPointerException) {
            Date(MaterialDatePicker.todayInUtcMilliseconds())
        }
    }
}