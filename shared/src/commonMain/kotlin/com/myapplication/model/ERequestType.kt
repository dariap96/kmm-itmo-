package com.myapplication.model

enum class ERequestType(val id: Int) {
    REFILL(1), REPAIR(2), REPLACEMENT(3);

    companion object {
        private val map = ERequestType.entries
        fun fromInt(type: Int) = map[type]
    }
}