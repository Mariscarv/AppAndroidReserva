package com.academic.myapplication

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.content.Intent

class MainActivity : AppCompatActivity() {

    private lateinit var tablesRecyclerView: RecyclerView
    private lateinit var tableAdapter: TableAdapter
    private val originalTablesWithSeatsMap = mutableMapOf<Int, List<Seat>>()
    private var currentTablesWithSeatsMap = mutableMapOf<Int, List<Seat>>()
    private val userReservations = mutableListOf<String>()
    private lateinit var viewReservationsButton: Button
    private var isFilterActive = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tablesRecyclerView = findViewById(R.id.seatsRecyclerView)
        tablesRecyclerView.layoutManager = LinearLayoutManager(this)

        val viewReservationsButton: Button = findViewById(R.id.viewReservationsButton)
        viewReservationsButton.setOnClickListener {
            val message = if (userReservations.isEmpty()) {
                "Você não possui nenhuma reserva."
            } else {
                val formattedReservations = userReservations.joinToString(", ") { reservationId ->
                    val parts = reservationId.split("-")
                    if (parts.size == 2) {
                        "mesa ${parts[0].replace("Mesa", "")}, cadeira ${parts[1]}"
                    } else {
                        reservationId
                    }
                }
                "Minhas reservas: $formattedReservations"
            }
            Toast.makeText(this, message, Toast.LENGTH_LONG).show()
        }

        val meetingRoomsButton: Button = findViewById(R.id.meetingRoomsButton)
        meetingRoomsButton.setOnClickListener {
            val intent = Intent(this, MeetingRoomActivity::class.java)
            startActivity(intent)
        }

        val filterAvailableButton: Button = findViewById(R.id.filterAvailableButton)
        filterAvailableButton.setOnClickListener {
            isFilterActive = !isFilterActive
            if (isFilterActive) {
                filterAvailableSeats()
                filterAvailableButton.text = "Mostrar Todos"
            } else {
                currentTablesWithSeatsMap.clear()
                currentTablesWithSeatsMap.putAll(originalTablesWithSeatsMap)
                tableAdapter.notifyDataSetChanged()
                filterAvailableButton.text = "Filtrar Disponíveis"
            }
        }

        for (table in 1..8) {
            val seatsForTable = mutableListOf<Seat>()
            for (seatInTable in 1..10) {
                val seatId = "Mesa${table}-${seatInTable}"
                val seat = Seat(seatId, tableNumber = table)
                seatsForTable.add(seat)
            }
            originalTablesWithSeatsMap[table] = seatsForTable.toList()
        }
        currentTablesWithSeatsMap.putAll(originalTablesWithSeatsMap)

        tableAdapter = TableAdapter(currentTablesWithSeatsMap, userReservations)
        tablesRecyclerView.adapter = tableAdapter
    }

    private fun filterAvailableSeats() {
        val filteredMap = mutableMapOf<Int, List<Seat>>()
        originalTablesWithSeatsMap.forEach { (tableNumber, seats) ->
            val availableSeats = seats.filter { !it.isReserved }
            if (availableSeats.isNotEmpty()) {
                filteredMap[tableNumber] = availableSeats
            }
        }
        currentTablesWithSeatsMap.clear()
        currentTablesWithSeatsMap.putAll(filteredMap)
        tableAdapter.notifyDataSetChanged()
    }
}