package com.example.chrono

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

class RegistroUnidadesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.registro_unidades)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.registroUni)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val etEconomico = findViewById<EditText>(R.id.etEconomico)
        val etPlacas = findViewById<EditText>(R.id.etPlacas)
        val spEstatus = findViewById<Spinner>(R.id.spEstatus)
        val btnGuardar = findViewById<Button>(R.id.btnGuardar)
        val btnCancelar = findViewById<Button>(R.id.btnCancelar)

        btnGuardar.setOnClickListener {
            // Obtener los datos de los campos
            val economico = etEconomico.text.toString()
            val placas = etPlacas.text.toString()
            val estatus = spEstatus.selectedItem.toString()

            if (economico.isEmpty() || placas.isEmpty() || estatus.isEmpty()) {
                Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            val queue = Volley.newRequestQueue(this)
            val url = "http://${conexion.ip}/basechrono/insertaUnidad.php"
            var postRequest = object : StringRequest(Method.POST, url,
                Response.Listener<String> { response ->
                    Toast.makeText(this, "$response", Toast.LENGTH_LONG).show()
                },Response.ErrorListener
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
            // Limpiar o cancelar
            etEconomico.text.clear()
            etPlacas.text.clear()
            spEstatus.setSelection(0)
        }
    }
}