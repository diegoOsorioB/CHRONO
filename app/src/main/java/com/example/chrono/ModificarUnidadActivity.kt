package com.example.chrono

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

class ModificarUnidadActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.modificar_unidad)

        val editEconomico = findViewById<EditText>(R.id.economicoEditText1)
        val editEconomicoSec = findViewById<EditText>(R.id.economicoEditText2)
        val editPlacas = findViewById<EditText>(R.id.placasEditText)
        val spinnerEstatus = findViewById<Spinner>(R.id.estatusSpinner)
        val btnBuscar = findViewById<Button>(R.id.buscarButton)
        val btnEliminar = findViewById<Button>(R.id.eliminarButton)
        val btnGuardar = findViewById<Button>(R.id.guardarButton)
        val btnCancelar = findViewById<Button>(R.id.cancelarButton)

        // Set Spinner options
        val estatusAdapter = ArrayAdapter.createFromResource(
            this,
            R.array.estatus_options,
            android.R.layout.simple_spinner_item
        )
        estatusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerEstatus.adapter = estatusAdapter

        // Set button functionality
        btnGuardar.setOnClickListener {
            val economico = editEconomico.text.toString()
            val placas = editEconomico.text.toString()
            var estatus = spinnerEstatus.selectedItem.toString()

            if (estatus == "Activo") {
                estatus = "A"
            } else {
                estatus = "I"
            }
            if (economico.isEmpty() || placas.isEmpty()) {
                Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            val queue = Volley.newRequestQueue(this)
            val url = "http://${conexion.ip}/basechrono/consultaUnidad.php"
            var postRequest = object : StringRequest(
                Method.POST, url,
                Response.Listener<String> { response ->
                    Toast.makeText(this, "$response", Toast.LENGTH_LONG).show()
                }, Response.ErrorListener
                { error ->
                    Toast.makeText(this, "Ocurrio un error inesperado", Toast.LENGTH_LONG).show()
                }){
                override fun getParams(): MutableMap<String, String> {
                    val params = HashMap<String, String>()
                    params.put("economico", economico)
                    params.put("placas", placas)
                    params.put("estatus", estatus)
                    return params
                }
            }
            queue.add(postRequest)
        }

        btnCancelar.setOnClickListener {
            editEconomico.text.clear()
            editEconomicoSec.text.clear()
            editPlacas.text.clear()
            spinnerEstatus.setSelection(0)
        }

        btnBuscar.setOnClickListener {
            val economico = editEconomico.text.toString()
            val queue = Volley.newRequestQueue(this)
            val id = intent.getStringExtra("id")
            val url = "http://${conexion.ip}/basechrono/consultaUnidad.php?economico=$economico"

            val JsonObjectRequest = JsonObjectRequest(
                Request.Method.GET, url,null,
                { response ->
                    if (response.has("mensaje")) {
                        // Mostrar el mensaje de error en un Toast
                        val mensaje = response.getString("mensaje")
                        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()

                    } else {
                        editEconomicoSec.setText(response.getString("economico"))
                        editEconomico.isEnabled.not()
                        editPlacas.setText(response.getString("placas"))
                        val estatus = response.getString("estatus")
                        if (estatus == "A") {
                            val posicion = estatusAdapter.getPosition("Activo")
                            spinnerEstatus.setSelection(posicion)
                        } else {
                            val posicion = estatusAdapter.getPosition("Inactivo")
                            spinnerEstatus.setSelection(posicion)
                        }

                    }
                    Toast.makeText(this, "$response", Toast.LENGTH_SHORT).show()
                },
                { error ->

                    Toast.makeText(this, "$error", Toast.LENGTH_SHORT).show()
                    editEconomico.setText(error.toString())
                    editEconomicoSec.text.clear()
                    editPlacas.text.clear()
                    spinnerEstatus.setSelection(0)
                })

            queue.add(JsonObjectRequest)
        }

        btnEliminar.setOnClickListener {
            // Logic for deleting the vehicle
            Toast.makeText(this, "Eliminar clicked", Toast.LENGTH_SHORT).show()
        }
    }
}