package com.example.deadliner.UI.deadlines

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
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
import kotlinx.android.synthetic.main.fragment_deadlines.*
import kotlinx.coroutines.launch
import java.lang.Exception
import java.net.InetAddress

/**
 * Deadlines fragment
 *
 * @constructor Create empty Deadlines fragment
 */
class DeadlinesFragment : Fragment() {
    /**
     * Deadline view model
     */
    private lateinit var deadlineViewModel: DeadlineViewModel

    /**
     * Adapter
     */
    private lateinit var adapter: DeadlineAdapter

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
        val root = inflater.inflate(R.layout.fragment_deadlines, container, false)

        deadlineViewModel = ViewModelProvider(this).get(DeadlineViewModel::class.java)
        adapter = DeadlineAdapter(inflater, deadlineViewModel)
        /**
         * List of deadlines
         */
        var list = root.findViewById<RecyclerView>(R.id.deadlinesList)
        list.adapter = adapter

        deadlineViewModel.allDeadlines.observe(viewLifecycleOwner) {
            adapter.setData(it)
        }

        root.findViewById<SwipeRefreshLayout>(R.id.deadlines_swipe_refresh).setOnRefreshListener {
            if (context?.let { NetworkHelper.checkConnection(it) }!!) {
                lifecycleScope.launch {
                    val response = MyApp.INSTANCE.api.getDeadlines()
                    if (response.isSuccessful) {
                        deadlineViewModel.deleteServerDeadlines()
                        deadlines_swipe_refresh.isRefreshing = false
                        if (!response.body().isNullOrEmpty()) {
                            for (deadline in response.body()!!) {
                                deadlineViewModel.addDeadline(
                                        Deadline(0, deadline.subject, deadline.description,
                                                deadline.date * 1000, true))
                            }
                        }
                    }
                }
            }
            else {
                deadlines_swipe_refresh.isRefreshing = false
                Toast.makeText(context, "Отсутствует интернет-соединение",Toast.LENGTH_SHORT).show()
            }
        }
        return root
    }
}