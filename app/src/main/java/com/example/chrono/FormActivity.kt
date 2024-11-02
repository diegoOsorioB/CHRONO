package com.example.chrono

import android.os.Bundle
import android.content.Intent
import android.widget.Toast
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley


class FormActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.primera_revision)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.primera)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val economicoEditText = findViewById<EditText>(R.id.economicoEditText)
        val placasEditText = findViewById<EditText>(R.id.placasEditText)
        val tarimasEditText = findViewById<EditText>(R.id.tarimasEditText)
        val patinEditText = findViewById<EditText>(R.id.patinEditText)
        val operadorEditText = findViewById<EditText>(R.id.operadorEditText)


        val guardarButton = findViewById<Button>(R.id.guardarButton)
        val cancelarButton = findViewById<Button>(R.id.cancelarButton)

        guardarButton.setOnClickListener {
            // Capturar los datos de los campos
            val economico = economicoEditText.text.toString()
            val placas = placasEditText.text.toString()
            val tarimas = tarimasEditText.text.toString()
            val patin = patinEditText.text.toString()
            val operador = operadorEditText.text.toString()

            if (economico.isEmpty() || placas.isEmpty() || tarimas.isEmpty() || patin.isEmpty() || operador.isEmpty()) {
                Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val id_usuario = intent.getStringExtra("id")



            val queue = Volley.newRequestQueue(this)
            val url = "http://${conexion.ip}/basechrono/insertaPrimera.php"
            var postRequest = object : StringRequest(Method.POST, url,
                Response.Listener<String> { response ->
                    Toast.makeText(this, " $id_usuario $response", Toast.LENGTH_SHORT).show()
                }, Response.ErrorListener
                { error ->
                    Toast.makeText(this, "$error", Toast.LENGTH_SHORT).show()
                }){
                override fun getParams(): MutableMap<String, String> {
                    val params = HashMap<String, String>()
                    params.put("economico", economico)
                    params.put("id_usuario", id_usuario.toString())
                    params.put("tarimas", tarimas)
                    params.put("patin", patin)
                    params.put("no_operador", operador)
                    return params
                }
            }
            queue.add(postRequest)

        }

        cancelarButton.setOnClickListener {
            // Lógica de cancelar o limpiar campos
            economicoEditText.text.clear()
            placasEditText.text.clear()
            tarimasEditText.text.clear()
            patinEditText.text.clear()
            operadorEditText.text.clear()


            Toast.makeText(this, "Acción cancelada", Toast.LENGTH_SHORT).show()
        }
    }
}