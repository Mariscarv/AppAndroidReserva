package com.academic.myapplication

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class RoomDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_room_details)

        val roomNameTextView: TextView = findViewById(R.id.roomNameTextView)
        val reservationsRecyclerView: RecyclerView = findViewById(R.id.reservationsRecyclerView)
        reservationsRecyclerView.layoutManager = LinearLayoutManager(this)

        val roomId = intent.getIntExtra("roomId", -1)
        val roomName = intent.getStringExtra("roomName")

        roomNameTextView.text = "Reservas de ${roomName ?: "Sala"}"

        val reservationsList = MeetingRoomActivity.roomReservations[roomId] ?: emptyList()

        val adapter = RoomReservationAdapter(reservationsList)
        reservationsRecyclerView.adapter = adapter
    }
}