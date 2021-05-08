package com.submission.githubusersearch.data.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.submission.githubusersearch.databinding.AdapterListUserBinding
import com.submission.githubusersearch.storage.persistence.ListUserEntity

class ResultAdapter(
    var users: ArrayList<ListUserEntity>,
    var listener: OnAdapterListener
) :
    RecyclerView.Adapter<ResultAdapter.ViewHolder>(), Filterable {

    private var usernameFilter = ArrayList<ListUserEntity>()

    init {
        usernameFilter = users
    }


    interface OnAdapterListener {
        fun onClick(result: ListUserEntity)
    }

    class ViewHolder(val binding: AdapterListUserBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        AdapterListUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = usernameFilter[position]
        Glide.with(holder.binding.profileImage.context)
            .load(user.profilePict)
            .centerCrop()
            .into(holder.binding.profileImage)

        holder.binding.tvUsername.text = user.username
        holder.binding.container.setOnClickListener {
            listener.onClick(user)
        }
    }

    override fun getItemCount() = usernameFilter.size

    fun setData(data: List<ListUserEntity>) {
        users.clear()
        users.addAll(data)
        notifyDataSetChanged()
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val char = constraint.toString()
                if (char.isEmpty()) {
                    usernameFilter = users
                } else {
                    val usernameFiltered = ArrayList<ListUserEntity>()
                    for (user in users) {
                        if (user.username.toLowerCase().contains(char.toLowerCase())) {
                            usernameFiltered.add(user)
                        }
                    }
                    usernameFilter = usernameFiltered
                }
                val usernameResult = FilterResults()
                usernameResult.values = usernameFilter
                return usernameResult
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                usernameFilter = results?.values as ArrayList<ListUserEntity>
                notifyDataSetChanged()
            }

        }
    }

}



