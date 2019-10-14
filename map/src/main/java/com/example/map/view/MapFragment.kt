package com.example.map.view


import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.map.MapFragmentPresenter
import com.example.map.R
import com.google.android.gms.common.GooglePlayServicesNotAvailableException
import com.google.android.gms.maps.MapsInitializer
import com.google.android.gms.maps.SupportMapFragment
import dagger.android.support.DaggerFragment
import kekmech.ru.coreui.Resources
import kotlinx.android.synthetic.main.fragment_map.*
import javax.inject.Inject


class MapFragment : DaggerFragment(), MapFragmentView {

    @Inject
    lateinit var presenter: MapFragmentPresenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(presenter)
        placeContentUnderStatusBar()
    }

    private fun placeContentUnderStatusBar() {
        val rectangle = Rect(0, 0, 0, 0)
        val window = activity?.window
        window?.decorView?.getWindowVisibleDisplayFrame(rectangle)
        val statusBarHeight = rectangle.top
        val p = mapView?.layoutParams as ViewGroup.MarginLayoutParams?
        p?.topMargin = -statusBarHeight
        mapView?.layoutParams = p
    }

    override fun onResume() {
        mapView?.onResume()
        super.onResume()
        presenter.onResume(this)
        placeContentUnderStatusBar()
        activity?.window?.statusBarColor = Resources.getColor(context, android.R.color.transparent)
    }

    override fun onPause() {
        mapView?.onPause()
        presenter.onPause(this)
        super.onPause()
    }

    override fun onLowMemory() {
        mapView?.onLowMemory()
        super.onLowMemory()
    }

    override fun onDestroy() {
        mapView?.onDestroy()
        super.onDestroy()
    }
}
