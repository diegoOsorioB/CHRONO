package com.example.chrono

import android.os.Bundle
import android.content.Intent
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley


class ModificarRevActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.modificar_revision)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.modificarRevision)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val etBuscarEconomico = findViewById<EditText>(R.id.etBuscarEconomico)
        val etTarimas = findViewById<EditText>(R.id.etTarimas)
        val etPatin = findViewById<EditText>(R.id.etPatin)
        val etOperador = findViewById<EditText>(R.id.etOperador)
        val etKilometraje = findViewById<EditText>(R.id.etKilometraje)
        val etNoCincho = findViewById<EditText>(R.id.etNoCincho)
        val btnBuscar = findViewById<Button>(R.id.btnBuscar)
        val btnModificar = findViewById<Button>(R.id.btnModificar)
        val btnCancelar = findViewById<Button>(R.id.btnCancelar)
        var idRevision=""

        // Lógica del botón Buscar
        btnBuscar.setOnClickListener {

            // Lógica para buscar información basada en el campo "Economico"
            val economico = etBuscarEconomico.text.toString()
            if (economico.isEmpty()) {
                Toast.makeText(this, "Por favor, ingrese un valor en el campo 'Economico'", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val queue = Volley.newRequestQueue(this)
            val id = intent.getStringExtra("id")
            val url = "http://${conexion.ip}/basechrono/consultaSR.php?economico=$economico"

            val JsonObjectRequest = JsonObjectRequest(
                Request.Method.GET, url,null,
                { response ->
                    if (response.has("mensaje")) {
                        // Mostrar el mensaje de error en un Toast
                        val mensaje = response.getString("mensaje")
                        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()

                    } else {
                        etTarimas.setText(response.getString("Tarimas"))
                        etPatin.setText(response.getString("Patin"))
                        etOperador.setText(response.getString("ID_Operador"))
                        etKilometraje.setText(response.getString("Kilometraje"))
                        etNoCincho.setText(response.getString("No_Cincho"))
                        idRevision = response.getString("id_revision")
                    }
                },
                { error ->
                    Toast.makeText(this, "$error", Toast.LENGTH_SHORT).show()
                })

            queue.add(JsonObjectRequest)
        }

        // Lógica del botón Modificar
        btnModificar.setOnClickListener {
            // Obtener los datos ingresados
            val tarimas = etTarimas.text.toString()
            val patin = etPatin.text.toString()
            val operador = etOperador.text.toString()
            val kilometraje = etKilometraje.text.toString()
            val noCincho = etNoCincho.text.toString()

            val id_usuario = intent.getStringExtra("id")

            if (tarimas.isEmpty() || patin.isEmpty() || operador.isEmpty() || kilometraje.isEmpty() || noCincho.isEmpty()) {
                Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            val queue = Volley.newRequestQueue(this)
            val url = "http://${conexion.ip}/basechrono/modificarRevision.php?"
            var postRequest = object : StringRequest(
                Method.POST, url,
                Response.Listener<String> { response ->


                    Toast.makeText(this, "$response", Toast.LENGTH_LONG).show()
                }, Response.ErrorListener
                { error ->
                    Toast.makeText(this, "Ocurrio un error inesperado", Toast.LENGTH_SHORT).show()
                }){
                override fun getParams(): MutableMap<String, String> {
                    val params = HashMap<String, String>()
                    params.put("id_revision", idRevision)
                    params.put("kilometrajer", kilometraje)
                    params.put("no_cincho", noCincho)
                    params.put("tarimas", tarimas)
                    params.put("patin", patin)
                    params.put("no_operador", operador)
                    return params
                }
            }
            queue.add(postRequest)
        }

        // Lógica del botón Cancelar
        btnCancelar.setOnClickListener {
            // Limpiar todos los campos
            etBuscarEconomico.text.clear()
            etTarimas.text.clear()
            etPatin.text.clear()
            etOperador.text.clear()
            etKilometraje.text.clear()
            etNoCincho.text.clear()
        }
    }
}