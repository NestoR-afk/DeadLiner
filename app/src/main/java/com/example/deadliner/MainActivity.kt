package com.example.deadliner

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.graphics.drawable.Animatable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver
import androidx.appcompat.app.AppCompatDelegate

import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.deadliner.bottomSheets.DeadlineBottomSheet
import com.example.deadliner.bottomSheets.SubjectBottomSheet
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.fab.*

/**
 * Main activity
 *
 * @constructor Create empty Main activity
 */
class MainActivity : AppCompatActivity() {

    var offset1: Float = 0f
    var offset2: Float = 0f
    private var expanded = false

    /**
     * On create
     *
     * @param savedInstanceState
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(findViewById(R.id.my_toolbar))
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        /**
         * Deadline bottom sheet
         */
        val deadlineBottomSheet = DeadlineBottomSheet()

        /**
         * Subject bottom sheet
         */
        val subjectBottomSheet = SubjectBottomSheet()

        fab.setOnClickListener {
            expanded = !expanded
            if (expanded) {
                expandFab()
            } else {
                collapseFab()
            }
        }

        add_deadline.setOnClickListener {
            if (!deadlineBottomSheet.isAdded) {
                deadlineBottomSheet.show(supportFragmentManager, "add_deadline_action_fragment")
            }
        }

        add_schedule.setOnClickListener {
            if (!subjectBottomSheet.isAdded) {
                subjectBottomSheet.show(supportFragmentManager, "add_subject_action_fragment")
            }
        }


        //ANIMATION STUFF
        fab_container.viewTreeObserver.addOnPreDrawListener(object : ViewTreeObserver.OnPreDrawListener {
            /**
             * On pre draw
             *
             * @return
             */
            override fun onPreDraw(): Boolean {
                fab_container.viewTreeObserver.removeOnPreDrawListener(this)
                offset1 = fab.y - add_deadline.y
                add_deadline.translationY = offset1
                offset2 = fab.y - add_schedule.y
                add_schedule.translationY = offset2
                return true
            }
        })
        /**
         * Nav view
         */
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        /**
         * Nav controller
         */
        val navController = findNavController(R.id.nav_host_fragment)

        /**
         * App bar configuration
         */
        val appBarConfiguration = AppBarConfiguration(setOf(
                R.id.navigation_deadlines, R.id.navigation_today, R.id.navigation_schedule))
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    /**
     * Create collapse animator
     *
     * @param view
     * @param offset
     * @return
     */
    private fun createCollapseAnimator(view: View, offset: Float): ObjectAnimator {
        return ObjectAnimator.ofFloat(view, "translationY", 0f, offset)
                .setDuration(resources.getInteger(android.R.integer.config_mediumAnimTime).toLong())
    }

    /**
     * Create expand animator
     *
     * @param view
     * @param offset
     * @return
     */
    fun createExpandAnimator(view: View, offset: Float): ObjectAnimator {
        return ObjectAnimator.ofFloat(view, "translationY", offset, 0f)
                .setDuration(resources.getInteger(android.R.integer.config_mediumAnimTime).toLong())
    }

    /**
     * Animate fab
     *
     */
    private fun animateFab() {
        val drawable = fab.drawable
        if (drawable is Animatable) {
            drawable.start()
        }
    }

    /**
     * Collapse fab
     *
     */
    private fun collapseFab() {
        fab.setImageResource(R.drawable.animated_minus)
        val animatorSet = AnimatorSet()
        animatorSet.playTogether(createCollapseAnimator(add_deadline, offset1),
                createCollapseAnimator(add_schedule, offset2))

        animatorSet.start()
        animateFab()
    }

    /**
     * Expand fab
     *
     */
    private fun expandFab() {
        fab.setImageResource(R.drawable.animated_plus)
        val animatorSet = AnimatorSet()
        animatorSet.playTogether(createExpandAnimator(add_deadline, offset1),
                createExpandAnimator(add_schedule, offset2))
        animatorSet.start()
        animateFab()
    }

}