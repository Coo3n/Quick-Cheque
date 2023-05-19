package com.example.quick_cheque.presentation.screen.main_pages_fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.quick_cheque.MyApp
import com.example.quick_cheque.R
import com.example.quick_cheque.data.repository.RoomRepositoryImpl
import com.example.quick_cheque.databinding.FragmentCreateRoomBinding
import com.example.quick_cheque.presentation.events.CreateRoomEvent
import com.example.quick_cheque.presentation.events.ValidationEvent
import com.example.quick_cheque.presentation.screen.BaseFragment
import com.example.quick_cheque.presentation.screen.viewmodel.CreateRoomViewModel
import com.google.android.material.textfield.TextInputEditText
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class CreateScreenFragment : BaseFragment() {
    private var _binding: FragmentCreateRoomBinding? = null
    private val binding: FragmentCreateRoomBinding
        get() = _binding!!

    @Inject
    lateinit var roomRepositoryImpl: RoomRepositoryImpl
    private val disposeBag = CompositeDisposable()

    private lateinit var createRoomViewModel: CreateRoomViewModel
    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as MyApp).appComponent.injectCreateScreenFragment(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factory = CreateRoomViewModel.CreateRoomViewModelFactory(roomRepositoryImpl)
        createRoomViewModel = ViewModelProvider(
            this,
            factory
        )[CreateRoomViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateRoomBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.editTextRoomName.textChanges().addTo(disposeBag)

        binding.buttonCreateRoom.setOnClickListener {
            createRoomViewModel.onEvent(CreateRoomEvent.CreateRoomSubmit)
            lifecycleScope.launchWhenStarted {
                createRoomViewModel.validationEventChannel.collect { event ->
                    when (event) {
                        is ValidationEvent.Success -> {
                            Toast.makeText(
                                requireContext(),
                                "Комната создана!",
                                Toast.LENGTH_SHORT
                            ).show()
                            findNavController().navigate(R.id.action_createScreenFragment_to_choiceChequeFragment)
                        }
                        is ValidationEvent.Failure -> {
                            Toast.makeText(
                                requireContext(),
                                "Произошла ошибка",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        }
    }

    private fun TextInputEditText.textChanges(): Disposable {
        return RxTextView.textChanges(this)
            .debounce(300, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ newTitle ->
                Log.i("TextInputEditText", newTitle.toString())
                createRoomViewModel.onEvent(CreateRoomEvent.TitleRoomChanged(newTitle.toString()))
            }, { error ->
                Log.e("MyTag", "Error: $error")
            })
    }

    private fun Disposable.addTo(disposeBag: CompositeDisposable): Disposable {
        disposeBag.add(this)
        return this
    }


    override fun onDestroy() {
        super.onDestroy()
        disposeBag.clear()
        _binding = null
    }
}