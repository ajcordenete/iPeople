package com.aljon.ipeople.features.person.detail

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import com.aljon.ipeople.R
import com.aljon.ipeople.base.BaseFragment
import com.aljon.ipeople.databinding.FragmentPersonDetailBinding
import com.aljon.ipeople.utils.Constants
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class PersonDetailFragment : BaseFragment<FragmentPersonDetailBinding>(), OnMapReadyCallback {

    private val args by navArgs<PersonDetailFragmentArgs>()

    override fun getLayoutId(): Int = R.layout.fragment_person_detail

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        setUpData()
        setUpViews()
    }

    private fun setupToolbar() {
        setToolbarNoTitle()
    }

    private fun setUpData() {
        val person = args.person

        binding.item = person
    }

    private fun setUpViews() {
        val mapFragment = childFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(map: GoogleMap) {
        val personLocation = LatLng(
            args.person.latitude,
            args.person.longitude
        )
        val markerTitle = String
            .format(
                Constants.LOCATION_FORMAT,
                args.person.latitude,
                args.person.longitude
            )

        map.addMarker(
            MarkerOptions()
                .position(personLocation)
                .title(markerTitle)
        ).showInfoWindow()

        map.moveCamera(
            CameraUpdateFactory
                .newLatLng(personLocation)
        )

        map.uiSettings.isScrollGesturesEnabled = false
    }

    override fun canBack(): Boolean = true
}
