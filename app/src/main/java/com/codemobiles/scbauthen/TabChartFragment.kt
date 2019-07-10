package com.codemobiles.scbauthen


import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.github.mikephil.charting.utils.ViewPortHandler
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_tab_chart.view.*
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class TabChartFragment : Fragment() {
    private lateinit var mChart: LineChart

    private val mDataSets = ArrayList<ILineDataSet>()
    private var mPriceResult: ChartLineResult? = null
    private val yVals = ArrayList<Entry>()
    private val xVals = ArrayList<String>()

    private var mIndexSelect = 3
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var _view = inflater.inflate(R.layout.fragment_tab_chart,container,false)

        setupEventWidget(_view)
        mChart = _view.chart

        //dummy data
        loadData(2)
        return _view

    }

    private fun loadData(flag: Int) {
        //important ก่อนโหลดต้องเคลียก่อน
        mDataSets.clear()
        yVals.clear()
        xVals.clear()

        if (flag % 2 == 0) {
            mPriceResult =
                Gson().fromJson<ChartLineResult>(getString(R.string.dummy_data_chart_line_1), ChartLineResult::class.java)

        } else {
            mPriceResult =
                Gson().fromJson<ChartLineResult>(getString(R.string.dummy_data_chart_line_2), ChartLineResult::class.java)
        }

        drawChart()
    }

    fun drawChart() {

        // Main
        mChart.setBackgroundColor(Color.parseColor("#252934"))
        mChart.setDescription("SiamGold App.")
        mChart.setDescriptionColor(Color.parseColor("#FFFFFF"))
        mChart.setDescriptionTextSize(20f)
        mChart.setDescriptionPosition(350f, 50f)
        mChart.setExtraOffsets(5f, 30f, 5f, 5f)
        mChart.setOnChartValueSelectedListener(object : OnChartValueSelectedListener {
            override fun onNothingSelected() {
            }

            override fun onValueSelected(e: Entry?, dataSetIndex: Int, h: Highlight?) {
                mIndexSelect = e!!.xIndex
            }
        })


        // animation
        //mChart.animateXY(1000,1000, Easing.EasingOption.Linear, Easing.EasingOption.Linear);
        mChart.animateX(1500, Easing.EasingOption.EaseInCubic)


        // left
        mChart.axisLeft.setDrawLabels(false)
        mChart.axisLeft.setDrawAxisLine(false)
        mChart.axisLeft.gridColor = Color.parseColor("#8D8A9A")


        // right
        mChart.axisRight.setDrawLabels(false)
        mChart.axisRight.setDrawAxisLine(false)


        // bottom
        mChart.xAxis.setDrawLabels(true)
        mChart.xAxis.textColor = Color.parseColor("#FFFFFF")
        mChart.xAxis.position = XAxis.XAxisPosition.BOTTOM
        mChart.xAxis.gridColor = Color.parseColor("#9d9272")
        mChart.xAxis.setDrawGridLines(false)


        // set data
        val priceArray = mPriceResult!!.data

        for (i in 0 until priceArray!!.size - 2) {
            val price = priceArray.get(i).prices.toString() + ""
            val date = priceArray.get(i).date

            yVals.add(Entry(Integer.valueOf(price).toFloat(), i))
            xVals.add(date!!)
        }

        mChart.legend.isEnabled = true
        mChart.legend.textColor = Color.parseColor("#FFFFFF")
        mChart.legend.textSize = 15f

        val dataSet = LineDataSet(yVals, "Dummy Data")
        dataSet.color = Color.parseColor("#E91E63")
        dataSet.lineWidth = 5f
        dataSet.setDrawCubic(true)

        dataSet.setDrawFilled(true)
        dataSet.fillColor = Color.parseColor("#E91E63")
        dataSet.fillAlpha = 10 //0-100

        dataSet.setDrawCircleHole(true)
        dataSet.setCircleColor(Color.parseColor("#252934"))
        dataSet.circleRadius = 12f

        dataSet.valueTextSize = 15f
        dataSet.valueTypeface = Typeface.DEFAULT
        dataSet.valueTextColor = Color.parseColor("#FBFBFB")
        mDataSets.add(dataSet)

        val data = LineData(xVals, mDataSets)
        data.setValueFormatter(MyValueFormatter())
        mChart.data = data
    }

    inner class MyValueFormatter : ValueFormatter {
        override fun getFormattedValue(
            value: Float,
            entry: Entry,
            dataSetIndex: Int,
            viewPortHandler: ViewPortHandler
        ): String {
            val result = NumberFormat.getNumberInstance(Locale.US).format(value)

            if (entry.xIndex == mIndexSelect) {
                return result;
            }
            return ""
        }
    }

    private fun setupEventWidget(_view: View) {
        _view.mSliver.setOnClickListener {

        }

        _view.mGold.setOnClickListener {

        }

        _view.mPlatinum.setOnClickListener {

        }

    }
    //จิงๆclassต้องสร้างในbean
    data class ChartLineResult(
        val title: String? = null,
        val type: Double = 0.toDouble(),
        val data: List<DataBean>? = null
    )

    data class DataBean(val date: String? = null, val prices: Int = 0)


}
