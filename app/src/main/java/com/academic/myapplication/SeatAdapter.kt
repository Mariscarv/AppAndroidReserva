package com.academic.myapplication

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

data class Seat(val id: String, val tableNumber: Int, var isReserved: Boolean = false)
class SeatAdapter(private val seats: List<Seat>, private val userReservations: MutableList<String>) : RecyclerView.Adapter<SeatAdapter.SeatViewHolder>() {

    inner class SeatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val seatTextView: TextView = itemView.findViewById(R.id.seatTextView)
        val seatCardView: CardView = itemView.findViewById(R.id.cardView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeatViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_seat, parent, false)
        return SeatViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: SeatViewHolder, position: Int) {
        val currentSeat = seats[position]
        holder.seatTextView.text = currentSeat.id
        updateSeatUI(holder, currentSeat)

        holder.itemView.setOnClickListener {
            currentSeat.isReserved = !currentSeat.isReserved
            updateSeatUI(holder, currentSeat)

            if (currentSeat.isReserved) {
                if (!userReservations.contains(currentSeat.id)) {
                    userReservations.add(currentSeat.id)
                }
            } else {
                userReservations.remove(currentSeat.id)
            }

            val status = if (currentSeat.isReserved) "reservado" else "disponível"
            println("Assento ${currentSeat.id} agora está $status")
            println("Reservas do usuário: ${userReservations}")
        }
    }

    private fun updateSeatUI(holder: SeatViewHolder, seat: Seat) {
        if (seat.isReserved) {
            holder.seatTextView.setBackgroundColor(Color.RED)
        } else {
            holder.seatTextView.setBackgroundResource(R.color.seat_available)
        }
    }

    override fun getItemCount() = seats.size
}