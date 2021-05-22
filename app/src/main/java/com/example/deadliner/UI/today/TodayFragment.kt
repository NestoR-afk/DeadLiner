package com.example.deadliner.UI.today

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.observe
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.deadliner.MyApp
import com.example.deadliner.R
import com.example.deadliner.model.Deadline
import com.example.deadliner.network.NetworkHelper

import com.example.deadliner.viewmodel.DeadlineViewModel
import com.example.deadliner.viewmodel.SubjectViewModel
import kotlinx.android.synthetic.main.fragment_deadlines.*
import kotlinx.android.synthetic.main.fragment_today.*
import kotlinx.coroutines.launch


/**
 * Today fragment
 *
 * @constructor Create empty Today fragment
 */
class TodayFragment : Fragment() {

    private lateinit var subjectViewModel: SubjectViewModel
    private lateinit var scheduleAdapter: TodayScheduleAdapter

    private lateinit var deadlineViewModel: DeadlineViewModel
    private lateinit var deadlineAdapter: TodayDeadlineAdapter


    /**
     * On create view
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_today, container, false)

        subjectViewModel = ViewModelProvider(this).get(SubjectViewModel::class.java)
        scheduleAdapter = TodayScheduleAdapter(inflater, subjectViewModel, context)

        var subjectsList = root.findViewById<RecyclerView>(R.id.todaySubjects)
        subjectsList.adapter = scheduleAdapter

        subjectViewModel.allSubjects.observe(viewLifecycleOwner) {
            scheduleAdapter.setData()
        }

        deadlineViewModel = ViewModelProvider(this).get(DeadlineViewModel::class.java)
        deadlineAdapter = TodayDeadlineAdapter(inflater, deadlineViewModel)

        var deadlinesList = root.findViewById<RecyclerView>(R.id.todayDeadlines)
        deadlinesList.adapter = deadlineAdapter

        deadlineViewModel.allDeadlines.observe(viewLifecycleOwner) {
            deadlineAdapter.setData(it)
        }

        root.findViewById<SwipeRefreshLayout>(R.id.today_swipe_refresh).setOnRefreshListener {
            if (context?.let { NetworkHelper.checkConnection(it) }!!) {
                lifecycleScope.launch {
                    val response = MyApp.INSTANCE.api.getDeadlines()
                    if (response.isSuccessful) {
                        deadlineViewModel.deleteServerDeadlines()
                        today_swipe_refresh.isRefreshing = false
                        val deadlines = response.body()
                        if (!deadlines.isNullOrEmpty()) {
                            for (deadline in deadlines) {
                                deadlineViewModel.addDeadline(
                                        Deadline(0, deadline.subject, deadline.description,
                                                deadline.date * 1000, true))
                            }
                        }
                    }
                }
            }
            else {
                today_swipe_refresh.isRefreshing = false
                Toast.makeText(context, "Отсутствует интернет-соединение", Toast.LENGTH_SHORT).show()
            }
        }
        return root
    }
}