package com.example.spectrumapp

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.spectrumapp.db.entities.MemberEntity


class MemberListAdapter(
    val context: Context,
    private var members: MutableList<MemberEntity>,
 private val itemClickListener: ItemClickListener) : RecyclerView.Adapter<MemberListAdapter.MyViewHolder>() {


    public fun setList(list: ArrayList<MemberEntity>) {
        members = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemberListAdapter.MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.member_item, parent, false)
        return MyViewHolder(view)
    }


    override fun onBindViewHolder(holder: MemberListAdapter.MyViewHolder, position: Int) {
        val member = members[position]
        holder.tvName.setText(member.name)
        holder.tvAge.setText("${member.age} year old")
        holder.tvEmail.setText(member.email)
        holder.tvPhone.setText(member.phone)

        if (member.favorite){
            holder.favorite.setImageDrawable(context.getDrawable(R.drawable.ic_favorite))
        }else{
            holder.favorite.setImageDrawable(context.getDrawable(R.drawable.ic_unfavorite))
        }



        holder.favorite.setOnClickListener {
            if (member.favorite){
                itemClickListener.onItemClicked(1, member.id, false)
            }else{
                itemClickListener.onItemClicked(1, member.id, true)
            }
        }
    }

    override fun getItemCount(): Int {
        return members.size
    }

    class MyViewHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {

        var favorite: ImageView
        var tvName: TextView
        var tvAge: TextView
        var tvEmail: TextView
        var tvPhone: TextView

        var view: CardView
        init {
            favorite = itemView.findViewById(R.id.favorite)
            tvName = itemView.findViewById(R.id.tvName)
            tvAge = itemView.findViewById(R.id.tvAge)
            tvEmail = itemView.findViewById(R.id.tvEmail)
            tvPhone = itemView.findViewById(R.id.tvPhone)
            view = itemView.findViewById(R.id.view)
        }
    }

}
