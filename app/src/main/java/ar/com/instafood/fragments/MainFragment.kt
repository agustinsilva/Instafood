package ar.com.instafood.fragments


import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Button
import ar.com.instafood.activities.R
import ar.com.instafood.activities.ScanActivity
import ar.com.instafood.activities.SearchRestaurantsActivity
import ar.com.instafood.adapters.MainRestaurantAdapter
import ar.com.instafood.models.Restaurant
import ar.com.instafood.models.getSampleRestaurants
import ar.com.instafood.models.setDistances
import kotlinx.android.synthetic.main.fragment_main.*

/**
 * A simple [Fragment] subclass.
 *
 */
class MainFragment : Fragment() {

    private var locationManager : LocationManager? = null
    private var currentLocation : Location? = null
    private var restaurants : List<Restaurant>? = null
    private var noDataText : View? = null

    @SuppressLint("MissingPermission")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.fragment_main, container, false)
        restaurants = getSampleRestaurants()
        locationManager = activity!!.getSystemService(Context.LOCATION_SERVICE) as LocationManager?
        locationManager?.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0L, 0f, locationListener)

        return view
    }

    //define the listener
    private val locationListener: LocationListener = object : LocationListener {
        override fun onLocationChanged(location: Location) {
            currentLocation = location
            if(restaurants !== null) {
                restaurants = getSampleRestaurants()
                setDistances(restaurants, currentLocation)
                restaurants = restaurants!!.filter{it.distance.toDouble() <= 3}
                if(restaurants!!.isEmpty()){
                    closeMeNoData?.visibility = VISIBLE
                }else{
                    closeMeNoData?.visibility = INVISIBLE
                }
                val recyclerViewMainRestaurant = getView()?.findViewById<RecyclerView>(R.id.recyclerViewMainRestaurant)
                recyclerViewMainRestaurant?.adapter = MainRestaurantAdapter(restaurants)

            }
        }

        override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
        override fun onProviderEnabled(provider: String) {}
        override fun onProviderDisabled(provider: String) {}
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerViewMainRestaurant = getView()?.findViewById<RecyclerView>(R.id.recyclerViewMainRestaurant)
        switchSearchRestaurants()
        setQRscan()
        recyclerViewMainRestaurant?.setHasFixedSize(true)
        recyclerViewMainRestaurant?.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recyclerViewMainRestaurant?.adapter = MainRestaurantAdapter(restaurants)
    }

    private fun setQRscan(){
        val btn_QRScan = getView()?.findViewById<Button>(R.id.QRScan)
        btn_QRScan?.setOnClickListener { _ ->
            val intent = Intent(activity, ScanActivity::class.java)
            activity?.startActivityForResult(intent,1)
        }
    }
    private fun switchSearchRestaurants() {
        val restSearch = view?.findViewById<Button>(R.id.restaurantSearch)
        restSearch?.setOnClickListener { _ ->
            activity?.startActivityForResult(Intent(activity, SearchRestaurantsActivity::class.java),1)
        }
    }
}
