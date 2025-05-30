package com.academic.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MeetingRoomActivity : AppCompatActivity() {

    companion object {
        val roomReservations = mutableMapOf<Int, MutableList<RoomReservation>>()

        init {
            roomReservations[1] = mutableListOf()
            roomReservations[2] = mutableListOf()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meeting_room)

        val meetingRoomsRecyclerView: RecyclerView = findViewById(R.id.meetingRoomsRecyclerView)
        meetingRoomsRecyclerView.layoutManager = LinearLayoutManager(this)

        val meetingRoomsList = listOf(
            MeetingRoom(1, "Sala 1"),
            MeetingRoom(2, "Sala 2")
        )

        val adapter = MeetingRoomAdapter(meetingRoomsList) { room ->
            val intent = Intent(this, RoomReservationActivity::class.java)
            intent.putExtra("roomId", room.id)
            intent.putExtra("roomName", room.name)
            startActivity(intent)
        }
        meetingRoomsRecyclerView.adapter = adapter
    }

    private val roomReservations = mutableMapOf<Int, MutableList<RoomReservation>>()

    init {
        roomReservations[1] = mutableListOf()
        roomReservations[2] = mutableListOf()
    }
}