package com.example.zapateria_unica.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.zapateria_unica.R
import com.example.zapateria_unica.databinding.FragmentFirstBinding
import com.example.zapateria_unica.viewmodel.ZapateriaViewModel

class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private val viewModel: ZapateriaViewModel by activityViewModels()
    private lateinit var adapter: FirstAdapter

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = FirstAdapter(emptyList())
        setupRecyclerView()

        //por si se quiere borrar los datos guardados
        //viewModel.deleteAllZapatoDetalles()
    }

    private fun setupRecyclerView() {
        binding.recyclerViewFirst.adapter = adapter
        binding.recyclerViewFirst.layoutManager = GridLayoutManager(context, 2)
        binding.recyclerViewFirst.setHasFixedSize(false)

        observeZapatos()
        observeSelectedtemId()
    }

    private fun observeZapatos() {
        //viewModel.zapatos.observe(viewLifecycleOwner, Observer {
        viewModel.getZapatos().observe(viewLifecycleOwner, Observer {
            adapter.setData(it)
        })
    }

    private fun observeSelectedtemId() {
        adapter.selectedItemId.observe(viewLifecycleOwner, Observer {
            it?.let {
                changeFragment(it)
            }
        })
    }

    private fun changeFragment(id: Int) {
        val args = Bundle()
        args.putInt("id", id)
        findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment, args)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}