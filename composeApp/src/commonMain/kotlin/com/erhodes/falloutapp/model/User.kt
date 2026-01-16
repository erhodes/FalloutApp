package com.erhodes.falloutapp.model

import kotlinx.serialization.Serializable

@Serializable
data class User(val uuid: String, val name: String)