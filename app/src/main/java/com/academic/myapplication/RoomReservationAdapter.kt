package com.academic.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RoomReservationAdapter(private val reservations: List<RoomReservation>) : RecyclerView.Adapter<RoomReservationAdapter.ReservationViewHolder>() {

    inner class ReservationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val timeTextView: TextView = itemView.findViewById(R.id.reservationTimeTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReservationViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_room_reservation, parent, false)
        return ReservationViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ReservationViewHolder, position: Int) {
        val currentItem = reservations[position]
        val time = "${currentItem.getStartTimeFormatted()} - ${currentItem.getEndTimeFormatted()}"
        holder.timeTextView.text = time
    }

    override fun getItemCount() = reservations.size
}