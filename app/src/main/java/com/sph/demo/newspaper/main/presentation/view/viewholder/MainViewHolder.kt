package com.sph.demo.newspaper.main.presentation.view.viewholder

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import com.sph.demo.newspaper.R
import com.sph.demo.newspaper.main.data.entity.RecordEntity
import com.sph.demo.newspaper.main.presentation.view.MainDataView
import kotlinx.android.synthetic.main.item_report_detail.view.*

class MainViewHolder(val context: Context, val view: View, val dataView: MainDataView) : RecyclerView.ViewHolder(view) {

    fun bindData(entity: RecordEntity?) {
        view.year.text = "${context.getString(R.string.year)} ${entity?.year}"
        view.total.text = "${context.getString(R.string.total)} ${entity?.total}"

        view.alert_image.visibility = if (entity?.isAlertShow==true) View.VISIBLE else View.GONE

        view.alert_image.setOnClickListener { view ->
            dataView.showRecordChart("${entity?.year}",entity?.quarterRecords)
        }

    }
}