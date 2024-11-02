package com.example.chrononote

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.chrono.R
import com.example.chrono.conexion

class SegundaRevActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.segunda_revision)

        val economicoEditText = findViewById<EditText>(R.id.economicoEditText)
        val buscarButton = findViewById<Button>(R.id.buscarButton)
        val tarimasEditText = findViewById<EditText>(R.id.tarimasEditText)
        val patinEditText = findViewById<EditText>(R.id.patinEditText)
        val operadorEditText = findViewById<EditText>(R.id.operadorEditText)
        val kilometrajeEditText = findViewById<EditText>(R.id.kilometrajeEditText)
        val cinchoEditText = findViewById<EditText>(R.id.cinchoEditText)

        val guardarButton = findViewById<Button>(R.id.guardarButton)
        val cancelarButton = findViewById<Button>(R.id.cancelarButton)
        var id_revisiaion= ""

        buscarButton.setOnClickListener {
            // Lógica para buscar información basada en el campo "Economico"
            val economico = economicoEditText.text.toString()
            val queue = Volley.newRequestQueue(this)
            val id = intent.getStringExtra("id")
            val url = "http://${conexion.ip}/basechrono/consultaPrimera.php?economico=$economico"

            val JsonObjectRequest = JsonObjectRequest(
                Request.Method.GET, url,null,
                { response ->
                    economicoEditText.setText(response.getString("economico"))
                   // placasEditText.setText(response.getString("placas"))
                    tarimasEditText.setText(response.getString("tarimas"))
                    patinEditText.setText(response.getString("patin"))
                    operadorEditText.setText(response.getString("no_operador"))
                    id_revisiaion=response.getString("id_revision")
                    Toast.makeText(this, "$response", Toast.LENGTH_SHORT).show()
                },
                { error ->

                    Toast.makeText(this, "$error", Toast.LENGTH_SHORT).show()
                })

            queue.add(JsonObjectRequest)

        }

        guardarButton.setOnClickListener {


            val kilometraje = kilometrajeEditText.text.toString()
            val cincho = cinchoEditText.text.toString()
            val id_usuario = intent.getStringExtra("id")

            if (kilometraje.isEmpty() || cincho.isEmpty()) {
                Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val queue = Volley.newRequestQueue(this)
            val url = "http://${conexion.ip}/basechrono/insertaSegunda.php"
            var postRequest = object : StringRequest(
                Method.POST, url,
                Response.Listener<String> { response ->
                    Toast.makeText(this, "id $response", Toast.LENGTH_LONG).show()
                }, Response.ErrorListener
                { error ->
                    Toast.makeText(this, "Ocurrio un error inesperado", Toast.LENGTH_SHORT).show()
                }){
                override fun getParams(): MutableMap<String, String> {
                    val params = HashMap<String, String>()
                    params.put("id_revision", id_revisiaion)
                    params.put("id_usuario", id_usuario.toString())
                    params.put("kilometrajer", kilometraje)
                    params.put("no_cincho", cincho)
                    return params
                }
            }
            queue.add(postRequest)
        }

        cancelarButton.setOnClickListener {
            // Lógica para cancelar o limpiar los campos
            economicoEditText.text.clear()
            tarimasEditText.text.clear()
            patinEditText.text.clear()
            operadorEditText.text.clear()
            kilometrajeEditText.text.clear()
            cinchoEditText.text.clear()

            Toast.makeText(this, "Acción cancelada", Toast.LENGTH_SHORT).show()
        }
    }
}
