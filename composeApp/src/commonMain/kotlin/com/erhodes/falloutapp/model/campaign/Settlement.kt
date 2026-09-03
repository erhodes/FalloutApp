package com.erhodes.falloutapp.model.campaign

class Settlement(id: Int, name: String): Location(id, name) {
    var food: Int = 1
    var population: Int = 1
}