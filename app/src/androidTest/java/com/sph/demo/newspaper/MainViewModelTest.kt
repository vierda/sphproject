package com.sph.demo.newspaper

import android.arch.lifecycle.Observer
import android.content.Context
import android.media.Image
import android.support.test.InstrumentationRegistry
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.sph.demo.newspaper.main.data.entity.RecordEntity
import com.sph.demo.newspaper.main.presentation.view.MainActivity
import com.sph.demo.newspaper.main.presentation.viewmodel.MainViewModel
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.ArrayList


@RunWith(AndroidJUnit4::class)
class MainViewModelTest {

    lateinit var appContext: Context

    @get:Rule
    var mainActivityRule: ActivityTestRule<MainActivity> = ActivityTestRule(MainActivity::class.java)

    @Before
    fun setup() {
        appContext = InstrumentationRegistry.getTargetContext()

    }

    @Test
    fun getPackageNameTest() {
        assertEquals("com.sph.demo.newspaper", appContext.packageName)
        assertNotEquals("org.sph.demo.newspaper", appContext.packageName)
    }

    @Test
    fun getRecordCountTest() {
        val viewModel = MainViewModel(mainActivityRule.getActivity().getApplication())

        assertNotNull(viewModel)

        val liveData = viewModel.getCountLiveData()

        assertNotNull(liveData)

        viewModel.getResourceCount()

        liveData.observe(mainActivityRule.getActivity(), object : Observer<Int> {
            override fun onChanged(total: Int?) {
                assertNotNull(total)
            }
        })
    }

    @Test
    fun getRecordListTest() {
        val viewModel = MainViewModel(mainActivityRule.getActivity().getApplication())

        assertNotNull(viewModel)

        val liveData = viewModel.getLoadRecordsLiveData()

        assertNotNull(liveData)

        viewModel.getAllRecords(10)

        liveData.observe(mainActivityRule.getActivity(), object : Observer<List<RecordEntity>> {
            override fun onChanged(records: List<RecordEntity>?) {

                assertNotNull(records)
                assertNotEquals(ArrayList<Image>(), records)

                val recordEntity = records!![0]
                assertNotNull(recordEntity)
                assertNotNull(recordEntity.year)
                assertNotNull(recordEntity.quarterRecords)
                assertTrue(recordEntity.total>0)
                assertNotNull(recordEntity.isAlertShow)

            }
        })
    }

}