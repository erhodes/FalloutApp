package com.erhodes.falloutapp.repository

import com.erhodes.falloutapp.model.Encounter
import com.erhodes.falloutapp.model.campaign.Location
import com.erhodes.falloutapp.model.campaign.Settlement

class LocationRepository {

    var activeLocation = Location("Test Location")
        private set

    init {
        // todo temporary data set

    }

    fun loadAllLocations(): List<Location> {
        val result = ArrayList<Location>()

        result.add(Location("The Wastes"))
        result.add(Settlement("Honeywell"))
        return result
    }
}