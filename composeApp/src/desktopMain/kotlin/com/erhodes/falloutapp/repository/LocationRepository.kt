package com.erhodes.falloutapp.repository

import com.erhodes.falloutapp.model.Encounter
import com.erhodes.falloutapp.model.campaign.Location
import com.erhodes.falloutapp.model.campaign.Settlement

class LocationRepository {

    var activeLocation = Location(0, "Test Location")
        private set

    // todo temporary, until locations are persisted and ids come from the data source
    private var nextId = 2

    init {
        // todo temporary data set

    }

    /** Build a blank location with a fresh id. It is not saved until the caller saves it. */
    fun createLocation(): Location = Location(nextId++, "New Location")

    /** Swap in the given location as the active one. */
    fun setActiveLocation(location: Location) {
        activeLocation = location
    }

    fun loadAllLocations(): List<Location> {
        val result = ArrayList<Location>()

        result.add(Location(0, "The Wastes", "Irradiated scrubland stretching to the horizon."))
        result.add(Settlement(1, "Honeywell", "A walled farming town built around an old water plant."))
        return result
    }
}