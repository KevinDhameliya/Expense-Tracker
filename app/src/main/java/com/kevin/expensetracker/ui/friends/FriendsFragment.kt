package com.kevin.expensetracker.ui.friends

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.kevin.expensetracker.adapter.FriendAdapter
import com.kevin.expensetracker.databinding.FragmentFriendsBinding
import com.kevin.expensetracker.model.Friend

class FriendsFragment : Fragment() {

    private var _binding: FragmentFriendsBinding? = null
    private val binding get() = _binding!!

    private lateinit var friendAdapter: FriendAdapter

    private val friends = mutableListOf(
        Friend(
            id = 1,
            name = "Swati",
            email = "swati@email.com"
        ),
        Friend(
            id = 2,
            name = "Kevin",
            email = "kevin@email.com"
        )
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFriendsBinding.inflate(
            inflater,
            container,
            false
        )

        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupButtons()
        loadFriends()
    }

    private fun setupRecyclerView() {

        friendAdapter = FriendAdapter(

            onFriendClick = { friend ->
                openFriendDetails(friend)
            },

            onMoreClick = { friend ->
                showFriendOptions(friend)
            }
        )

        binding.rvFriends.apply {

            layoutManager =
                LinearLayoutManager(requireContext())

            adapter = friendAdapter

            setHasFixedSize(true)
        }
    }

    private fun loadFriends() {

        if (friends.isEmpty()) {

            binding.rvFriends.visibility =
                View.GONE

            binding.emptyState.visibility =
                View.VISIBLE

        } else {

            binding.rvFriends.visibility =
                View.VISIBLE

            binding.emptyState.visibility =
                View.GONE

            friendAdapter.submitList(friends)
        }
    }

    private fun setupButtons() {

        binding.btnAddFriend.setOnClickListener {
            openAddFriend()
        }

        binding.btnAddFriendBottom.setOnClickListener {
            openAddFriend()
        }

        binding.etSearchFriend.setOnEditorActionListener { _, _, _ ->

            filterFriends(
                binding.etSearchFriend.text
                    ?.toString()
                    ?.trim()
                    .orEmpty()
            )

            false
        }
    }

    private fun filterFriends(query: String) {

        if (query.isEmpty()) {

            friendAdapter.submitList(friends)

            return
        }

        val filteredFriends =
            friends.filter { friend ->

                friend.name.contains(
                    query,
                    ignoreCase = true
                ) ||
                        friend.email.contains(
                            query,
                            ignoreCase = true
                        )
            }

        friendAdapter.submitList(filteredFriends)

        if (filteredFriends.isEmpty()) {

            binding.emptyState.visibility =
                View.VISIBLE

            binding.rvFriends.visibility =
                View.GONE

        } else {

            binding.emptyState.visibility =
                View.GONE

            binding.rvFriends.visibility =
                View.VISIBLE
        }
    }

    private fun openAddFriend() {

        // We will create AddFriendFragment next.
        // Example:
        //
        // parentFragmentManager
        //     .beginTransaction()
        //     .replace(
        //         R.id.fragmentContainer,
        //         AddFriendFragment()
        //     )
        //     .addToBackStack(null)
        //     .commit()
    }

    private fun openFriendDetails(friend: Friend) {

        // Open FriendDetailsFragment here.
    }

    private fun showFriendOptions(friend: Friend) {

        // Edit / Delete options can be added here.
    }

    override fun onDestroyView() {

        super.onDestroyView()

        _binding = null
    }
}