package com.example.recviewlesson

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.quick_cheque.R
import com.example.quick_cheque.model.Room

class ListRoomAdapter(
    private val rooms: ArrayList<Room>
) : RecyclerView.Adapter<ListRoomAdapter.ListRooViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListRooViewHolder {
        return ListRooViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.list_room_item, parent, false)
        )
    }

    fun addRoom(room: Room){
        rooms.add(room)
        notifyItemInserted(rooms.size - 1)
    }

    override fun getItemCount(): Int {
        return rooms.size
    }

    override fun onBindViewHolder(holder: ListRooViewHolder, position: Int) {
        holder.bind(rooms[position])
    }

    class ListRooViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private val title: TextView = itemView.findViewById(R.id.room_title)
        private val host: TextView = itemView.findViewById(R.id.room_host_name)
        private val users: TextView = itemView.findViewById(R.id.room_users_count)
        private val cheques: TextView = itemView.findViewById(R.id.room_cheques_count)
        fun bind(room: Room){
            title.text = room.title
            host.text = room.host
            users.text = room.users.toString()
            cheques.text = room.cheques.toString()
        }
    }
}