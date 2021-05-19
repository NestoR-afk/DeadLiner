package com.example.deadliner.bottomSheets

import android.app.TimePickerDialog
import android.app.TimePickerDialog.OnTimeSetListener
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatRadioButton
import androidx.lifecycle.ViewModelProvider
import com.example.deadliner.R
import com.example.deadliner.model.Subject
import com.example.deadliner.viewmodel.SubjectViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.datepicker.MaterialDatePicker
import kotlinx.android.synthetic.main.subject_bottom_sheet.*
import java.util.*


class SubjectBottomSheet : BottomSheetDialogFragment() {

    lateinit var subjectViewModel: SubjectViewModel
    private var calendar = Calendar.getInstance()

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

        root.findViewById<AppCompatButton>(R.id.save_subject_button).setOnClickListener {
            val subjectName = subjects_input.text.toString()
            val type = root.findViewById<AppCompatRadioButton>(types_rg.checkedRadioButtonId).text.toString()
            val place = place_input.text.toString()
            val teacher = teacher_input.text.toString()
            val howOften =
                    when (regularity_rg.checkedRadioButtonId) {
                        R.id.regularity_once_a_week -> 7
                        R.id.regularity_once_a_two_week -> 14
                        else -> 7
                    }

            val date = getDateFromDatePicker(datePicker)
            calendar[Calendar.DATE] = date.date
            calendar[Calendar.MONTH] = date.month
            calendar[Calendar.YEAR] = date.year

            val subject = Subject(0, subjectName, type, place, teacher, calendar.time, howOften)
            subjectViewModel.addSubject(subject)
            Toast.makeText(context, "Предмет успешно добавлен", Toast.LENGTH_SHORT).show()
            dismiss()
        }
        root.findViewById<AppCompatButton>(R.id.set_date_subject).setOnClickListener {
            datePicker.show(parentFragmentManager, "date_picker")
        }
        root.findViewById<AppCompatButton>(R.id.set_time_subject).setOnClickListener {
            showHourPicker()
        }
        return root
    }

    private fun showHourPicker() {
        val hour = calendar[Calendar.HOUR_OF_DAY]
        val minute = calendar[Calendar.MINUTE]
        val myTimeListener = OnTimeSetListener { view, hourOfDay, minute ->
            if (isAdded) {
                calendar[Calendar.HOUR_OF_DAY] = hourOfDay
                calendar[Calendar.MINUTE] = minute
            }
        }

        val timePickerDialog = TimePickerDialog(activity, android.R.style.Theme_Holo_Light_Dialog_NoActionBar, myTimeListener, hour, minute, true)
        timePickerDialog.setTitle("Choose hour:")
        timePickerDialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)

        timePickerDialog.show()
    }

    private fun getDateFromDatePicker(datePicker: MaterialDatePicker<Long>): Date {
        return try {
            Date(datePicker.selection!!)
        } catch (e: NullPointerException) {
            Date(MaterialDatePicker.todayInUtcMilliseconds())
        }
    }
}

