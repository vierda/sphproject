package com.sph.demo.newspaper

import android.content.Intent
import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.rule.ActivityTestRule
import android.widget.Button
import com.github.mikephil.charting.charts.LineChart
import com.sph.demo.newspaper.chart.presentation.view.ChartActivity
import junit.framework.Assert.assertNotNull
import org.junit.Rule
import org.junit.Test


class ChartActivityTest {

    @get: Rule
    open var mActivityRule: ActivityTestRule<ChartActivity> = object : ActivityTestRule<ChartActivity>(ChartActivity::class.java) {
        override fun getActivityIntent(): Intent {
            val targetContext = InstrumentationRegistry.getInstrumentation().targetContext
            return Intent(targetContext, ChartActivity::class.java)
        }
    }


    @Test
    @Throws(Exception::class)
    fun buttonTest() {

        val button = mActivityRule.activity.findViewById(R.id.close_button) as Button
        assertNotNull(button)

        onView(ViewMatchers.withId(R.id.close_button)).check(ViewAssertions.matches(ViewMatchers.isDisplayed())).perform(ViewActions.click())
        onView(ViewMatchers.withId(R.id.close_button)).check(ViewAssertions.matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))
    }

    @Test
    @Throws(Exception::class)
    fun lineChartTest() {

        val lineChart = mActivityRule.activity.findViewById(R.id.line_chart) as LineChart
        assertNotNull(lineChart)

        onView(ViewMatchers.withId(R.id.line_chart)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        onView(ViewMatchers.withId(R.id.line_chart)).check(ViewAssertions.matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))
    }
}