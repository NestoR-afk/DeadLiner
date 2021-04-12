package com.example.deadliner

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.lifecycle.ViewModelProvider
import com.example.deadliner.model.Deadline
import com.example.deadliner.viewmodel.DeadlineViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.datepicker.MaterialDatePicker
import kotlinx.android.synthetic.main.bottom_sheet.*
import java.util.*

class BottomSheet: BottomSheetDialogFragment() {

    private lateinit var deadlineViewModel: DeadlineViewModel
    private var datePicker: MaterialDatePicker<Long>  =
            MaterialDatePicker.Builder.datePicker()
            .setTitleText("Select date")
            .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
            .build()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.bottom_sheet,container,false)
        deadlineViewModel = ViewModelProvider(this).get(DeadlineViewModel::class.java)

        root.findViewById<AppCompatButton>(R.id.appCompatButton).setOnClickListener {

                val subject = spinner.selectedItem.toString()
                val description = edit_description.text.toString()
                val date = getDateFromDatePicker(datePicker)

                val deadline = Deadline(0, subject, description, date)
                deadlineViewModel.addDeadline(deadline)
                Toast.makeText(context, "Дедлайн успешно добавлен", Toast.LENGTH_SHORT).show()
                dismiss()
                spinner.id = 0
                edit_description.setText("")
        }

        root.findViewById<AppCompatButton>(R.id.set_date_button).setOnClickListener {
                datePicker.show(parentFragmentManager ,"date_picker")
        }
        return root
    }

    private fun getDateFromDatePicker(datePicker: MaterialDatePicker<Long>): Date {
           return try {Date(datePicker.selection!!)}
        catch (e:NullPointerException) {Date(MaterialDatePicker.todayInUtcMilliseconds())}
    }
}