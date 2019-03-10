package com.sph.demo.newspaper.main.presentation.view.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.sph.demo.newspaper.R
import com.sph.demo.newspaper.main.data.entity.RecordEntity
import com.sph.demo.newspaper.main.presentation.view.MainDataView
import com.sph.demo.newspaper.main.presentation.view.viewholder.MainViewHolder

class MainAdapter(val context: Context, val dataView: MainDataView) : RecyclerView.Adapter<MainViewHolder>() {

    private var records = ArrayList<RecordEntity>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder(context,LayoutInflater.from(parent.context).inflate(R.layout.item_report_detail, null), dataView)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        if (records.isNotEmpty()) {
            holder.bindData(records[position])
        }

    }

    override fun getItemCount(): Int {
        if (records.isNotEmpty())
            return records.size
        else
            return 0
    }

    fun setRecords(list: ArrayList<RecordEntity>) {
        if (records.isNotEmpty()) records.clear()
        records.addAll(list)
    }
}