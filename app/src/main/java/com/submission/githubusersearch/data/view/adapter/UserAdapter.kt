package com.submission.githubusersearch.data.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.githubapi.network.response.UserResponse
import com.submission.githubusersearch.databinding.AdapterListUserBinding

class UserAdapter(
    var users: ArrayList<UserResponse>,
    var listener: OnAdapterListener
) :
    RecyclerView.Adapter<UserAdapter.ViewHolder>() {


    interface OnAdapterListener {
        fun onClick(result: UserResponse)
    }

    class ViewHolder(val binding: AdapterListUserBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        AdapterListUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = users[position]
        Glide.with(holder.binding.profileImage.context)
            .load(user.avatarUrl)
            .centerCrop()
            .into(holder.binding.profileImage)

        holder.binding.tvUsername.text = user.login
        holder.binding.container.setOnClickListener {
//            listener.onClick()
        }
    }

    override fun getItemCount() = users.size

    fun setData(data: List<UserResponse>) {
        users.clear()
        users.addAll(data)
        notifyDataSetChanged()
    }

}



