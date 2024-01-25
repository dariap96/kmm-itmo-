package com.myapplication.model

enum class ERequestStatus(val id: Int) {
    PENDING(1), IN_PROGRESS(2), DONE(3);

    companion object {
        private val map = ERequestStatus.entries
        fun fromInt(type: Int) = map[type]
    }
}