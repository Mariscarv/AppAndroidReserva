package com.academic.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import android.content.Intent

data class MeetingRoom(val id: Int, val name: String)

class MeetingRoomAdapter(private val meetingRooms: List<MeetingRoom>, private val onItemClick: (MeetingRoom) -> Unit) : RecyclerView.Adapter<MeetingRoomAdapter.MeetingRoomViewHolder>() {

    inner class MeetingRoomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.roomNameTextView)

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val room = meetingRooms[position]
                    val intent = Intent(itemView.context, RoomReservationActivity::class.java)
                    intent.putExtra("roomId", room.id)
                    intent.putExtra("roomName", room.name)
                    itemView.context.startActivity(intent)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MeetingRoomViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_meeting_room, parent, false)
        return MeetingRoomViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MeetingRoomViewHolder, position: Int) {
        val currentItem = meetingRooms[position]
        holder.nameTextView.text = currentItem.name
    }

    override fun getItemCount() = meetingRooms.size
}