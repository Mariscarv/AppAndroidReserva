package com.academic.myapplication

data class RoomReservation(
    val roomId: Int,
    val startTimeHour: Int,
    val startTimeMinute: Int,
    val durationHours: Int
) {

    fun getStartTimeFormatted(): String {
        return String.format("%02d:%02d", startTimeHour, startTimeMinute)
    }


    fun getEndTimeHour(): Int {
        return startTimeHour + durationHours
    }


    fun getEndTimeFormatted(): String {
        return String.format("%02d:%02d", getEndTimeHour(), startTimeMinute)
    }
}