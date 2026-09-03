package com.erhodes.falloutapp.repository

import com.erhodes.falloutapp.model.Encounter
import com.erhodes.falloutapp.model.campaign.Location
import com.erhodes.falloutapp.model.campaign.Settlement

class LocationRepository {

    var activeLocation = Location(0, "Test Location")
        private set

    init {
        // todo temporary data set

    }

    /** Swap in the given location as the active one. */
    fun setActiveLocation(location: Location) {
        activeLocation = location
    }

    fun loadAllLocations(): List<Location> {
        val result = ArrayList<Location>()

        result.add(Location(0, "The Wastes"))
        result.add(Settlement(1, "Honeywell"))
        return result
    }
}