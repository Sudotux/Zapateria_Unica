package com.example.zapateria_unica.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.zapateria_unica.R
import com.example.zapateria_unica.databinding.FragmentSecondBinding
import com.example.zapateria_unica.viewmodel.ZapateriaViewModel

class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null
    private val viewModel: ZapateriaViewModel by activityViewModels()
    private val delayMillis = 0L //tpo. retardo

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        resetScreen()

        val id: Int? = arguments?.getInt("id", 0)

        if (id != null) {
            viewModel.fetchZapatoDetalle(id)
            observeZapatoDetalles(id)
        }
    }

    private fun observeZapatoDetalles(id: Int) {
        viewModel.getZapatoDetalles().observe(viewLifecycleOwner, Observer {
            if (it != null) {
                viewModel.getZapatoDetalleById(id)
                observeZapatoDetalle()
            }
        })
    }

    private fun observeZapatoDetalle() {
        viewModel.getZapatoDetalle()?.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                binding.tvName.text = it.nombre
                Glide.with(requireContext()).load(it.imagenLink).into(binding.ivImage)

                binding.tvOrigen.text =
                    getString(R.string.label_origen, it.origen)
                binding.tvMarca.text =
                    getString(R.string.label_marca, it.marca)
                binding.tvNumero.text =
                    getString(R.string.label_numero, it.numero.toString())
                binding.tvPrecio.text =
                    getString(R.string.label_precio, it.precio.toString())

                if (it.entrega)
                    binding.tvEntrega.text = getString(R.string.label_entrega_yes)
                else
                    binding.tvEntrega.text = getString(R.string.label_entrega_no)

                //envia correo al presionar boton
                val id: Int = it.id
                val nombre: String = it.nombre
                binding.btnEmail.setOnClickListener { enviarEmail(id, nombre) }

                //actualiza pantalla
                updateScreen()
            } else {
                showNoData()
            }
        })
    }

    private fun resetScreen() {
        binding.nestedScrollView.visibility = View.GONE
        binding.tvNoData.visibility = View.GONE
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun showNoData() {
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            binding.nestedScrollView.visibility = View.GONE
            binding.progressBar.visibility = View.GONE
            binding.tvNoData.visibility = View.VISIBLE
        }, delayMillis)
    }

    /* Se incluye opcion de un pequeño retardo al actualizar la pantalla, en caso de que
     * demore un poco en actualizar los datos.
     */
    private fun updateScreen() {
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            binding.progressBar.visibility = View.GONE
            binding.tvNoData.visibility = View.GONE
            binding.nestedScrollView.visibility = View.VISIBLE
        }, delayMillis)
    }

    private fun enviarEmail(id: Int, nombre: String) {
        val destinatarios: Array<String> = arrayOf(getString(R.string.email_destinatarios))
        val asunto: String = getString(R.string.email_asunto, nombre, id.toString())
        val mensaje: String = getString(R.string.email_mensaje, nombre, id.toString())

        val intent = Intent(Intent.ACTION_SEND)
        intent.data = Uri.parse("mailto")
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_EMAIL, destinatarios)
        intent.putExtra(Intent.EXTRA_SUBJECT, asunto)
        intent.putExtra(Intent.EXTRA_TEXT, mensaje)

        try {
            startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}