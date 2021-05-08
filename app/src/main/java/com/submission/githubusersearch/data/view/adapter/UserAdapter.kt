package com.submission.githubusersearch.data.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.githubapi.network.response.UserResponse
import com.submission.githubusersearch.databinding.AdapterListUserBinding

class UserAdapter(
    var users: ArrayList<UserResponse>,
    var listener: OnAdapterListener
) :
    RecyclerView.Adapter<UserAdapter.ViewHolder>(), Filterable {

    private var usernameFilter = ArrayList<UserResponse>()

    init {
        usernameFilter = users
    }


    interface OnAdapterListener {
        fun onClick(result: UserResponse)
    }

    class ViewHolder(val binding: AdapterListUserBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        AdapterListUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = usernameFilter[position]
        Glide.with(holder.binding.profileImage.context)
            .load(user.avatarUrl)
            .centerCrop()
            .into(holder.binding.profileImage)

        holder.binding.tvUsername.text = user.login
        holder.binding.container.setOnClickListener {
           listener.onClick(user)
        }
    }

    override fun getItemCount() = usernameFilter.size

    fun setData(data: List<UserResponse>) {
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
                    val usernameFiltered = ArrayList<UserResponse>()
                    for (user in users) {
                        if (user.login!!.toLowerCase().contains(char.toLowerCase())) {
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
                usernameFilter = results?.values as ArrayList<UserResponse>
                notifyDataSetChanged()
            }

        }
    }

}



