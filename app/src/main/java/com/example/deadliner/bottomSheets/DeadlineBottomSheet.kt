package com.example.deadliner.bottomSheets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.lifecycle.ViewModelProvider
import com.example.deadliner.R
import com.example.deadliner.model.Deadline
import com.example.deadliner.viewmodel.DeadlineViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.datepicker.MaterialDatePicker
import kotlinx.android.synthetic.main.deadline_bottom_sheet.*

import java.util.*

/**
 * Deadline bottom sheet
 *
 * @constructor Create Deadline bottom sheet for adding deeadlines
 */
class DeadlineBottomSheet : BottomSheetDialogFragment() {
    /**
     * Deadline view model
     */
    private lateinit var deadlineViewModel: DeadlineViewModel

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
        val root = inflater.inflate(R.layout.deadline_bottom_sheet, container, false)
        deadlineViewModel = ViewModelProvider(this).get(DeadlineViewModel::class.java)

        root.findViewById<AppCompatButton>(R.id.saveDeadlineButton).setOnClickListener {
            /**
             * Subject
             */
            val subject = spinner.selectedItem.toString()

            /**
             * Description
             */
            val description = edit_description.text.toString()

            /**
             * Date
             */
            val date = getDateFromDatePicker(datePicker)

            /**
             * Deadline
             */
            val deadline = Deadline(0, subject, description, date.time,false)
            deadlineViewModel.addDeadline(deadline)
            Toast.makeText(context, "Дедлайн успешно добавлен", Toast.LENGTH_SHORT).show()
            dismiss()
            spinner.id = 0
            edit_description.setText("")
        }

        root.findViewById<AppCompatButton>(R.id.set_date_button).setOnClickListener {
            datePicker.show(parentFragmentManager, "date_picker")
        }
        return root
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