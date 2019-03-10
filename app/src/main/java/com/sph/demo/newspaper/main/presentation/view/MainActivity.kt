package com.sph.demo.newspaper.main.presentation.view

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.sph.demo.newspaper.R
import com.sph.demo.newspaper.chart.presentation.view.ChartActivity
import com.sph.demo.newspaper.common.data.model.Record
import com.sph.demo.newspaper.common.util.Util
import com.sph.demo.newspaper.main.data.entity.RecordEntity
import com.sph.demo.newspaper.main.presentation.view.adapter.MainAdapter
import com.sph.demo.newspaper.main.presentation.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*

open class MainActivity : AppCompatActivity(), MainDataView {

    private var records = ArrayList<RecordEntity>()
    private lateinit var adapter: MainAdapter

    private lateinit var mainViewModel: MainViewModel
    private lateinit var loadRecordsLiveData: LiveData<List<RecordEntity>>
    private lateinit var  loadCountLiveData: LiveData<Int>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        adapter = MainAdapter(this, this)
        main_recycler_view.adapter = adapter
        main_recycler_view.layoutManager = LinearLayoutManager(this)
        main_recycler_view.addItemDecoration(DividerItemDecoration(this, 0))

        mainViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        loadRecordsLiveData = mainViewModel.getLoadRecordsLiveData()
        loadCountLiveData = mainViewModel.getCountLiveData()

        mainViewModel.setDataView(this)

        mainViewModel.getResourceCount()
        load_progress.visibility = View.VISIBLE
        ObserveCount()

    }

    private fun ObserveCount() {

        loadCountLiveData.observe(this, Observer<Int> { t ->
            mainViewModel.getAllRecords(t!!)
            observe()
        })

    }

    private fun observe() {
        loadRecordsLiveData.observe(this, Observer<List<RecordEntity>>{t ->

            if (t!!.isNotEmpty()){
                if (records.isNotEmpty()) records.clear()
                records.addAll(t)
                adapter.setRecords(records)
                adapter.notifyDataSetChanged()
            }

            hideProgressLoading()
        })
    }

    override fun hideProgressLoading() {
        if (load_progress.visibility == View.VISIBLE)
            load_progress.visibility = View.GONE
    }

    override fun showRecordChart(year:String?,quarterRecords: Array<Record>?) {
        val gson = Util.initGson()
        val intent = Intent(this,ChartActivity::class.java)
        intent.putExtra(ChartActivity.RECORD_DATA, gson.toJson(quarterRecords))
        intent.putExtra(ChartActivity.YEAR,year)
        startActivity(intent)
    }
}
