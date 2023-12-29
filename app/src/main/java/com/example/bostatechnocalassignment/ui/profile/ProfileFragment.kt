package com.example.bostatechnocalassignment.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bostatechnocalassignment.databinding.FragmentProfileBinding
import com.example.bostatechnocalassignment.model.users.UserItem
import com.example.bostatechnocalassignment.network.httpStatus.ApiStatus
import com.example.bostatechnocalassignment.utils.hasInternetConnection
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment(), OnAlbumsClickListener {

    private lateinit var binding: FragmentProfileBinding
    private val profileViewModel: ProfileViewModel by viewModels()
    private val albumsAdapter by lazy { AlbumsAdapter(this) }
    private var usersList = emptyList<UserItem>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(layoutInflater, container, false)
        setupRecyclerView()
        isConnected()
        return binding.root
    }

    private fun setupRecyclerView() {
        with(binding.userAlbumsRecyclerView) {
            adapter = albumsAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun isConnected() {
        if (hasInternetConnection(requireActivity().application)) {
            showConnectedViews()
            getRandomUser()
        } else {
            showDisconnectedViews()
            Toast.makeText(requireContext(), "No Internet Connection!!!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showConnectedViews() {
        with(binding) {
            userAlbumsRecyclerView.visibility = View.VISIBLE
            noConnectionMsgTxt.visibility = View.GONE
            userNameTxt.visibility = View.VISIBLE
            addressTxt.visibility = View.VISIBLE
        }
    }

    private fun showDisconnectedViews() {
        with(binding) {
            userAlbumsRecyclerView.visibility = View.GONE
            noConnectionMsgTxt.visibility = View.VISIBLE
            userNameTxt.visibility = View.GONE
            addressTxt.visibility = View.GONE
        }
    }

    private fun getRandomUser() {
        profileViewModel.getAllUsers().observe(viewLifecycleOwner) { result ->
            when (result.status) {
                ApiStatus.SUCCESS -> {
                    result.data?.let { users ->
                        usersList = users
                        getUserAlbum()
                    }
                }

                ApiStatus.ERROR -> showErrorToast(result.message.toString())
                ApiStatus.LOADING -> {}
            }
        }
    }

    private fun getUserAlbum() {
        val user = usersList.random()
        with(binding) {
            userNameTxt.text = user.name
            val address = "${user.address?.city} ${user.address?.street} ${user.address?.zipcode} "
            addressTxt.text = address
        }

        user.id?.let { userId ->
            profileViewModel.getUserAlbums(userId).observe(viewLifecycleOwner) { result ->
                when (result.status) {
                    ApiStatus.SUCCESS -> result.data?.let { albumsAdapter.updateAlbum(it) }
                    ApiStatus.ERROR -> showErrorToast(result.message.toString())
                    ApiStatus.LOADING -> {}
                }
            }
        }
    }

    private fun showErrorToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onAlbumItemClick(albumId: Int) {
        findNavController().navigate(ProfileFragmentDirections.actionProfileFragmentToDetailsFragment(albumId))
    }
}
