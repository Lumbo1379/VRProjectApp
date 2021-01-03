package com.example.vrproject.fragments

import android.content.Intent
import android.graphics.Color
import android.net.Uri
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
import kotlinx.android.synthetic.main.fragment_contact.*
import kotlinx.android.synthetic.main.fragment_results.*

class ContactFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_contact, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configureView()
    }

    private fun configureView() {
        fragment_contact_button_contact.setOnClickListener {
            val intent = Intent(Intent.ACTION_SENDTO)

            intent.type = "message/rfc822"
            intent.data = Uri.parse("mailto:16647670@students.lincoln.ac.uk")

            context?.startActivity(Intent.createChooser(intent, "Contact me"))
        }
    }
}