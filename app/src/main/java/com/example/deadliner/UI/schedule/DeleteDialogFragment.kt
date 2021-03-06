package com.example.deadliner.UI.schedule

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.deadliner.model.Subject
import com.example.deadliner.viewmodel.SubjectViewModel

/**
 * My dialog fragment
 *
 * @property subjectViewModel
 * @property subject
 * @constructor Create empty My dialog fragment
 */
class DeleteDialogFragment(val subjectViewModel: SubjectViewModel, val subject: Subject) : DialogFragment() {
    /**
     * On create dialog
     *
     * @param savedInstanceState
     * @return
     */
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setTitle("Удалить расписание?")
                    .setPositiveButton("Удалить") { dialog, _ ->
                        run {
                            subjectViewModel.deleteSubject(subject)
                            dialog.cancel()
                        }
                    }
                    .setNegativeButton("Отмена") { dialog, id ->
                        dialog.cancel()
                    }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}