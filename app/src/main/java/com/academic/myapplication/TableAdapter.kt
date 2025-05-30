package com.academic.myapplication

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TableAdapter(private val tablesWithSeats: Map<Int, List<Seat>>, private val userReservations: MutableList<String>) : RecyclerView.Adapter<TableAdapter.TableViewHolder>() {

    inner class TableViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tableNumberTextView: TextView = itemView.findViewById(R.id.tableNumberTextView)
        val seatsContainer: LinearLayout = itemView.findViewById(R.id.seatsContainer)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TableViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_table, parent, false)
        return TableViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TableViewHolder, position: Int) {
        val tableNumber = tablesWithSeats.keys.elementAt(position)
        val seatsInTable = tablesWithSeats[tableNumber] ?: emptyList()

        holder.tableNumberTextView.text = "Mesa $tableNumber"
        holder.seatsContainer.removeAllViews()

        seatsInTable.forEach { seat ->
            val seatTextView = TextView(holder.itemView.context).apply {
                text = seat.id.split("-").last()
                width = 60
                height = 60
                gravity = android.view.Gravity.CENTER
                textSize = 12f
                typeface = android.graphics.Typeface.DEFAULT_BOLD

                setBackgroundResource(if (seat.isReserved) android.R.color.holo_red_light else R.color.seat_available)

                setOnClickListener {
                    seat.isReserved = !seat.isReserved
                    setBackgroundResource(if (seat.isReserved) android.R.color.holo_red_light else R.color.seat_available)
                    if (seat.isReserved) {
                        if (!userReservations.contains(seat.id)) {
                            userReservations.add(seat.id)
                        }
                    } else {
                        userReservations.remove(seat.id)
                    }
                    println("Assento ${seat.id} reservado: ${seat.isReserved}")
                    println("Reservas do usuário: ${userReservations}")
                }
                val params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                params.setMargins(8, 0, 8, 0)
                layoutParams = params
            }
            holder.seatsContainer.addView(seatTextView)
        }
    }

    override fun getItemCount() = tablesWithSeats.size
}