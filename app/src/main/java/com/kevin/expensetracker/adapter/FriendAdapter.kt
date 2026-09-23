package com.kevin.expensetracker.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kevin.expensetracker.databinding.ItemFriendBinding
import com.kevin.expensetracker.model.Friend

class FriendAdapter(
    private val onFriendClick: (Friend) -> Unit,
    private val onMoreClick: (Friend) -> Unit
) : RecyclerView.Adapter<FriendAdapter.FriendViewHolder>() {

    private val friends = mutableListOf<Friend>()

    fun submitList(newList: List<Friend>) {

        friends.clear()
        friends.addAll(newList)

        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FriendViewHolder {

        val binding = ItemFriendBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return FriendViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: FriendViewHolder,
        position: Int
    ) {
        holder.bind(friends[position])
    }

    override fun getItemCount(): Int {
        return friends.size
    }

    inner class FriendViewHolder(
        private val binding: ItemFriendBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(friend: Friend) {

            binding.tvFriendName.text =
                friend.name

            binding.tvFriendEmail.text =
                friend.email

            binding.tvFriendAvatar.text =
                friend.name
                    .trim()
                    .firstOrNull()
                    ?.uppercase()
                    ?: "?"

            binding.root.setOnClickListener {
                onFriendClick(friend)
            }

            binding.btnFriendMore.setOnClickListener {
                onMoreClick(friend)
            }
        }
    }
}