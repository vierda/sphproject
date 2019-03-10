package com.sph.demo.newspaper

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.v7.widget.RecyclerView
import android.widget.ProgressBar
import com.sph.demo.newspaper.main.presentation.view.MainActivity
import com.sph.demo.newspaper.main.presentation.view.adapter.MainAdapter
import junit.framework.Assert.assertNotNull
import junit.framework.Assert.assertTrue
import org.junit.Rule
import org.junit.Test


class MainActivityTest {


    @get: Rule
    open var activityRule = ActivityTestRule<MainActivity>(MainActivity::class.java)


    @Test
    @Throws(Exception::class)
    fun recyclerViewTest() {

        val recyclerView = activityRule.activity.findViewById(R.id.main_recycler_view) as RecyclerView
        val mainAdapter = recyclerView.adapter as MainAdapter

        assertNotNull(recyclerView)
        assertNotNull(mainAdapter)
        assertTrue(recyclerView.adapter?.itemCount!! > 0)
        assertNotNull(recyclerView.findViewHolderForAdapterPosition(0))

        onView(withId(R.id.load_progress)).check(matches(withEffectiveVisibility(Visibility.GONE)))

        onView(withId(R.id.main_recycler_view)).check(matches(isDisplayed()))
        onView(withId(R.id.main_recycler_view)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))

    }

    @Test
    @Throws(Exception::class)
    fun progressLoadingTest () {

        val progressBar = activityRule.activity.findViewById(R.id.load_progress) as ProgressBar
        assertNotNull(progressBar)
    }

}