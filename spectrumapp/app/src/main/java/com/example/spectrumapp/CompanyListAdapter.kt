package com.example.spectrumapp

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.spectrumapp.db.entities.CompanyEntity


class CompanyListAdapter(
    val context: Context,
    private var companies: MutableList<CompanyEntity>,
 private val itemClickListener: ItemClickListener) : RecyclerView.Adapter<CompanyListAdapter.MyViewHolder>() {


    public fun setList(list: ArrayList<CompanyEntity>) {
        companies = list

        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CompanyListAdapter.MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.company_item, parent, false)
        return MyViewHolder(view)
    }


    override fun onBindViewHolder(holder: CompanyListAdapter.MyViewHolder, position: Int) {
        val company = companies[position]
        holder.tvName.setText(company.company)
        holder.tvWebsite.setText(company.website)
        holder.tvAbout.setText(company.about)

        Glide.with(context).load("http://placehold.it/100x100")
            .placeholder(context.resources.getDrawable(R.drawable.ic_flag)).into(holder.flag)

        if (company.favorite){
            holder.favorite.setImageDrawable(context.getDrawable(R.drawable.ic_favorite))
        }else{
            holder.favorite.setImageDrawable(context.getDrawable(R.drawable.ic_unfavorite))
        }

        if (company.follow){
            holder.folow.setImageDrawable(context.getDrawable(R.drawable.ic_following))
        }else{
            holder.folow.setImageDrawable(context.getDrawable(R.drawable.ic_follow))
        }

        holder.view.setOnClickListener {
            val intent = Intent(context, MemberActivity::class.java)
            intent.putExtra("companyId", company.id)
            context.startActivity(intent)
        }

        holder.favorite.setOnClickListener {
            if (company.favorite){
                //holder.favorite.setImageDrawable(context.getDrawable(R.drawable.ic_unfavorite))
                itemClickListener.onItemClicked(1, company.id, false)
            }else{
               // holder.favorite.setImageDrawable(context.getDrawable(R.drawable.ic_favorite))
                itemClickListener.onItemClicked(1, company.id, true)
            }
        }

        holder.folow.setOnClickListener {
            if (company.follow){
                itemClickListener.onItemClicked(2, company.id, false)
            }else{
                itemClickListener.onItemClicked(2, company.id, true)
            }
        }
    }

    override fun getItemCount(): Int {
        return companies.size
    }

    class MyViewHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {

        var flag: ImageView
        var favorite: ImageView
        var folow: ImageView
        var tvName: TextView
        var tvWebsite: TextView
        var tvAbout: TextView

        var view: CardView
        init {
            flag = itemView.findViewById(R.id.flag)
            favorite = itemView.findViewById(R.id.favorite)
            folow = itemView.findViewById(R.id.follow)
            tvName = itemView.findViewById(R.id.tvName)
            tvWebsite = itemView.findViewById(R.id.tvWebsite)
            tvAbout = itemView.findViewById(R.id.tvAbout)
            view = itemView.findViewById(R.id.view)
        }
    }

}
