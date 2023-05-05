package com.example.quick_cheque.presentation.screen.main_pages_fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.quick_cheque.data.local.entity.RoomEntity
import com.example.quick_cheque.data.repository.RoomRepositoryImpl
import com.example.quick_cheque.databinding.FragmentCreateRoomBinding
import com.example.quick_cheque.presentation.screen.BaseFragment
import javax.inject.Inject

class CreateScreenFragment : BaseFragment() {
    private var _binding: FragmentCreateRoomBinding? = null
    private val binding: FragmentCreateRoomBinding
        get() = _binding!!

    @Inject
    lateinit var roomRepositoryImpl: RoomRepositoryImpl

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateRoomBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setVisibleHomeButton(false)
        setVisibleToolBar()

        binding.buttonCreateRoom.setOnClickListener {
            roomRepositoryImpl.insertRoom(
                RoomEntity(
                    titleRoom = binding.editTextRoomName.text.toString(),
                    ownerId = 101
                )
            )
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}