package ar.com.instafood.models

import android.location.Location
import ar.com.instafood.activities.R

data class Restaurant(var title: String, var description: String, var distance: String, var longitud: Double, var latitud: Double, var resource: String)

fun setDistances(restaurants: List<Restaurant>?, currentLocation: Location?) {
    return restaurants!!.forEach { it -> calculateLoc(currentLocation, it) }
}

fun getSampleRestaurants(): List<Restaurant> {
    return listOf(
            Restaurant("La Birra Bar", "Carlos Calvo 4317", "1 Km.", -58.3740076  , -34.6044714, "http://instafood-server.herokuapp.com/instafood-api/restaurants/0/image"),
            Restaurant("Aloha", " Av. Chiclana 3299", "2 Km.", -58.3740077, -34.6044643, "http://instafood-server.herokuapp.com/instafood-api/restaurants/1/image"),
            Restaurant("Perez-H", "Defensa 435", "10 Km.", -58.3740098, -34.6044669, "http://instafood-server.herokuapp.com/instafood-api/restaurants/2/image"),
            Restaurant("Mc Donalds", "Av. Belgrano 985", "12 Km.", -58.3740079, -34.6044692, "http://instafood-server.herokuapp.com/instafood-api/restaurants/3/image"),
            Restaurant("Burger King", "Av. Corrientes 206", "13 Km.", -58.3740082, -34.6044711, "http://instafood-server.herokuapp.com/instafood-api/restaurants/4/image")
    )
}

fun calculateLoc(currentLocation: Location?, restaurant: Restaurant): Restaurant {
    var location2 = Location("")
    location2.latitude = restaurant.latitud
    location2.longitude = restaurant.longitud
    restaurant.distance = currentLocation?.distanceTo(location2)!!.div(1000).toString()
    return restaurant
}
