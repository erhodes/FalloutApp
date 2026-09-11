package com.erhodes.falloutapp.model.campaign

class Settlement(id: Int, name: String, description: String = ""): Location(id, name, description) {
    var food: Int = 1
    var population: Int = 1
}
