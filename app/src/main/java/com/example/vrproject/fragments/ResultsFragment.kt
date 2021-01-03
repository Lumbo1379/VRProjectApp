package com.example.vrproject.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.vrproject.R
import com.example.vrproject.helpers.UserHelper
import com.example.vrproject.models.User
import com.google.firebase.auth.FirebaseAuth
import com.jjoe64.graphview.LegendRenderer
import com.jjoe64.graphview.series.BarGraphSeries
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries
import kotlinx.android.synthetic.main.fragment_results.*
import kotlin.math.abs


class ResultsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_results, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configureView()
    }

    private fun configureView() {
        val usersQuery = UserHelper.getUsers()
        usersQuery.get().addOnSuccessListener { querySnapshot ->
            val users = querySnapshot.toObjects(User::class.java)

            val dataPoints = mutableListOf<DataPoint>()
            var currentUserPosition = 0

            for (user in users.indices) {
                dataPoints.add(DataPoint(user.toDouble(), users[user].score.toDouble()))

                if (users[user].uid == FirebaseAuth.getInstance().currentUser?.uid) {
                    currentUserPosition = user
                }
            }

            val series = BarGraphSeries<DataPoint>(dataPoints.toTypedArray())
            series.spacing = 50
            series.isAnimated = true

            val seriesBlank = LineGraphSeries<DataPoint>()

            fragment_results_graph.addSeries(series)
            fragment_results_graph.addSeries(seriesBlank)

            series.setValueDependentColor { data ->
                if (data.x.toInt() == currentUserPosition) {
                    Color.rgb(73, 190, 182)
                } else {
                    Color.rgb(250, 207, 90)
                }
            }

            fragment_results_graph.viewport.setMinX(0.0)
            fragment_results_graph.viewport.setMaxX((users.size - 1).toDouble())
            fragment_results_graph.viewport.isXAxisBoundsManual = true
            fragment_results_graph.gridLabelRenderer.verticalAxisTitle = "Score"
            fragment_results_graph.gridLabelRenderer.isHorizontalLabelsVisible = false

            // --Legend--
            series.title = "Your result"
            series.color = Color.rgb(73, 190, 182)
            seriesBlank.title = "Other results"
            seriesBlank.color = Color.rgb(250, 207, 90)
            fragment_results_graph.legendRenderer.isVisible = true
            fragment_results_graph.legendRenderer.align = LegendRenderer.LegendAlign.TOP;
        }
    }
}