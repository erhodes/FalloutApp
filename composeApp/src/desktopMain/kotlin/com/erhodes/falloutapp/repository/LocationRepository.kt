package com.erhodes.falloutapp.repository

import com.erhodes.falloutapp.data.LocationDataSource
import com.erhodes.falloutapp.model.campaign.BasicLocation
import com.erhodes.falloutapp.model.campaign.Location

class LocationRepository(
    private val dataSource: LocationDataSource
) {

    // Declared as Location, not BasicLocation: Server.kt responds with this property and Ktor
    // serializes the reified static type, so a narrower type would drop the "type" discriminator.
    var activeLocation: Location = BasicLocation("Test Location")
        private set

    suspend fun saveLocation(location: Location) = dataSource.save(location)

    suspend fun loadLocation(id: String): Location? = dataSource.load(id)

    suspend fun loadAllLocations(): List<Location> = dataSource.loadAll()

    suspend fun deleteLocation(id: String) = dataSource.delete(id)

    /** Swap in the given location as the active one. */
    fun setActiveLocation(location: Location) {
        activeLocation = location
    }
}
