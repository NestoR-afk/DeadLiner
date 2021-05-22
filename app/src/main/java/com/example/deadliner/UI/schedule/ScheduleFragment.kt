package com.example.deadliner.UI.schedule

import android.content.Context
import android.net.ConnectivityManager
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
import com.example.deadliner.model.Subject
import com.example.deadliner.network.NetworkHelper
import com.example.deadliner.viewmodel.SubjectViewModel
import kotlinx.android.synthetic.main.fragment_deadlines.*
import kotlinx.android.synthetic.main.fragment_schedule.*
import kotlinx.coroutines.launch
import java.lang.Exception
import java.net.InetAddress

/**
 * Schedule fragment
 *
 * @constructor Create empty Schedule fragment
 */
class ScheduleFragment : Fragment() {
    /**
     * Subject view model
     */
    private lateinit var subjectViewModel: SubjectViewModel

    /**
     * Adapter
     */
    private lateinit var adapter: ScheduleAdapter

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
        /**
         * Root
         */
        var root = inflater.inflate(R.layout.fragment_schedule, container, false)

        subjectViewModel = ViewModelProvider(this).get(SubjectViewModel::class.java)
        adapter = ScheduleAdapter(inflater, subjectViewModel, context)

        /**
         * List
         */
        var list = root.findViewById<RecyclerView>(R.id.subjectsList)
        list.adapter = adapter
        registerForContextMenu(list)

        subjectViewModel.allSubjects.observe(viewLifecycleOwner) {
            adapter.setData()
        }

        root.findViewById<SwipeRefreshLayout>(R.id.schedule_swipe_refresh).setOnRefreshListener {
            if (context?.let { NetworkHelper.checkConnection(it) }!!) {
                lifecycleScope.launch {
                    val response = MyApp.INSTANCE.api.getSubjects()
                    if (response.isSuccessful) {
                        subjectViewModel.deleteServerDeadlines()
                        schedule_swipe_refresh.isRefreshing = false
                        if (!response.body().isNullOrEmpty()) {
                            for (subject in response.body()!!) {
                                subjectViewModel.addSubject(
                                        Subject(0, subject.name,
                                                subject.type, subject.place,
                                                subject.teacher, subject.date * 1000,
                                                subject.how_often, true)
                                )
                            }
                        }
                    }
                }
            }
            else {
                schedule_swipe_refresh.isRefreshing = false
                Toast.makeText(context, "Отсутствует интернет-соединение", Toast.LENGTH_SHORT).show()
            }
        }
        return root
    }

}