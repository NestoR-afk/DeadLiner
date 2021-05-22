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

/**
 * Subject bottom sheet
 *
 * @constructor Create empty Subject bottom sheet
 */
class SubjectBottomSheet : BottomSheetDialogFragment() {
    /**
     * Subject view model
     */
    lateinit var subjectViewModel: SubjectViewModel

    /**
     * Calendar
     */
    private var calendar = Calendar.getInstance()

    /**
     * Date picker
     */
    private var datePicker: MaterialDatePicker<Long> =
            MaterialDatePicker.Builder.datePicker()
                    .setTitleText("Select date")
                    .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                    .build()

    /**
     * On create view
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        /**
         * Root
         */
        val root = inflater.inflate(R.layout.subject_bottom_sheet, container, false)
        subjectViewModel = ViewModelProvider(this).get(SubjectViewModel::class.java)

        root.findViewById<AppCompatButton>(R.id.save_subject_button).setOnClickListener {
            /**
             * Subject name
             */
            val subjectName = subjects_input.text.toString()

            /**
             * Type
             */
            val type = root.findViewById<AppCompatRadioButton>(types_rg.checkedRadioButtonId).text.toString()

            /**
             * Place
             */
            val place = place_input.text.toString()

            /**
             * Teacher
             */
            val teacher = teacher_input.text.toString()

            /**
             * How often
             */
            val howOften =
                    when (regularity_rg.checkedRadioButtonId) {
                        R.id.regularity_once_a_week -> 7
                        R.id.regularity_once_a_two_week -> 14
                        else -> 7
                    }

            /**
             * Date
             */
            val date = getDateFromDatePicker(datePicker)
            calendar[Calendar.DATE] = date.date
            calendar[Calendar.MONTH] = date.month
            calendar[Calendar.YEAR] = date.year
            /**
             * Subject
             */
            val subject = Subject(0, subjectName, type, place, teacher, calendar.timeInMillis, howOften, false)
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

    /**
     * Show hour picker
     *
     */
    private fun showHourPicker() {
        /**
         * Hour
         */
        val hour = calendar[Calendar.HOUR_OF_DAY]

        /**
         * Minute
         */
        val minute = calendar[Calendar.MINUTE]

        /**
         * My time listener
         */
        val myTimeListener = OnTimeSetListener { view, hourOfDay, minute ->
            if (isAdded) {
                calendar[Calendar.HOUR_OF_DAY] = hourOfDay
                calendar[Calendar.MINUTE] = minute
            }
        }

        /**
         * Time picker dialog
         */
        val timePickerDialog = TimePickerDialog(activity, android.R.style.Theme_Holo_Light_Dialog_NoActionBar, myTimeListener, hour, minute, true)
        timePickerDialog.setTitle("Choose hour:")
        timePickerDialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)

        timePickerDialog.show()
    }

    /**
     * Get date from date picker
     *
     * @param datePicker
     * @return
     */
    private fun getDateFromDatePicker(datePicker: MaterialDatePicker<Long>): Date {
        return try {
            Date(datePicker.selection!!)
        } catch (e: NullPointerException) {
            Date(MaterialDatePicker.todayInUtcMilliseconds())
        }
    }
}

