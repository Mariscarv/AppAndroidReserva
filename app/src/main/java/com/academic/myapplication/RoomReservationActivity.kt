package com.academic.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.TimePicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class RoomReservationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_room_reservation)

        val roomNameTextView: TextView = findViewById(R.id.roomNameTextView)
        val startTimePicker: TimePicker = findViewById(R.id.startTimePicker)
        val durationRadioGroup: RadioGroup = findViewById(R.id.durationRadioGroup)
        val reserveButton: Button = findViewById(R.id.reserveButton)

        val roomId = intent.getIntExtra("roomId", -1)
        val roomName = intent.getStringExtra("roomName")
        roomNameTextView.text = "Reservar ${roomName ?: "Sala"}"

        reserveButton.setOnClickListener {
            val hour = startTimePicker.hour
            val minute = startTimePicker.minute
            val durationId = durationRadioGroup.checkedRadioButtonId
            var durationHours = 0

            when (durationId) {
                R.id.oneHourRadioButton -> durationHours = 1
                R.id.twoHoursRadioButton -> durationHours = 2
            }

            if (hour < 8 || hour >= 18) {
                Toast.makeText(this, "As reservas podem ser feitas apenas entre 8:00 e 18:00.", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            if (durationHours == 0) {
                Toast.makeText(this, "Por favor, selecione a duração da reserva.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val newReservationStartTimeHour = hour
            val newReservationEndTimeHour = hour + durationHours

            val existingReservations = MeetingRoomActivity.roomReservations[roomId] ?: emptyList()

            for (existingReservation in existingReservations) {
                val existingStartTimeHour = existingReservation.startTimeHour
                val existingEndTimeHour = existingReservation.getEndTimeHour()


                val newStartMinutes = newReservationStartTimeHour * 60 + minute
                val newEndMinutes = newReservationEndTimeHour * 60 + minute
                val existingStartMinutes = existingStartTimeHour * 60 + existingReservation.startTimeMinute
                val existingEndMinutes = existingEndTimeHour * 60 + existingReservation.startTimeMinute

                if (newStartMinutes < existingEndMinutes && newEndMinutes > existingStartMinutes) {
                    Toast.makeText(this, "Este horário já está reservado.", Toast.LENGTH_LONG).show()
                    return@setOnClickListener
                }
            }

            val reservation = RoomReservation(roomId, hour, minute, durationHours)
            val message = "Você reservou ${roomName ?: "esta sala"} das ${String.format("%02d:%02d", hour, minute)} por $durationHours hora(s)."
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

            MeetingRoomActivity.roomReservations[roomId]?.add(reservation)
            println("Reserva feita: $reservation")


            val intent = Intent(this, RoomDetailsActivity::class.java)
            intent.putExtra("roomId", roomId)
            intent.putExtra("roomName", roomName)
            startActivity(intent)
        }
    }
}