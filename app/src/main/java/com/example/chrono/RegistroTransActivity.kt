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

class RegistroTransActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.registro_transportistas)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.registroTransp)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    val etOperador=findViewById<EditText>(R.id.etNoOperador)
    val etNombre = findViewById<EditText>(R.id.etNombre)
    val etApellidoP = findViewById<EditText>(R.id.etApellidoP)
    val etApellidoM = findViewById<EditText>(R.id.etApellidoM)
    val spEstatus = findViewById<Spinner>(R.id.spEstatus)
    val btnGuardar = findViewById<Button>(R.id.btnGuardar)
    val btnCancelar = findViewById<Button>(R.id.btnCancelar)

    btnGuardar.setOnClickListener {
        // Obtener los datos de los campos
        val noOperador = etOperador.text.toString()
        val nombre = etNombre.text.toString()
        val apellidoP = etApellidoP.text.toString()
        val apellidoM = etApellidoM.text.toString()
        var estatus = spEstatus.selectedItem.toString()

        if(estatus=="Activo"){
            estatus="A"
        }else{
            estatus="I"
        }
        if (noOperador.isEmpty() || nombre.isEmpty() || apellidoP.isEmpty() || apellidoM.isEmpty()) {
            Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_LONG).show()
            return@setOnClickListener
        }

        val queue = Volley.newRequestQueue(this)
        val url = "http://${conexion.ip}/basechrono/insertaOperador.php"
        var postRequest = object : StringRequest(Method.POST, url,
            Response.Listener<String> { response ->
                Toast.makeText(this, "$response", Toast.LENGTH_LONG).show()
            },Response.ErrorListener
            { error ->
                Toast.makeText(this, "Ocurrio un error inesperado", Toast.LENGTH_LONG).show()
            }){
            override fun getParams(): MutableMap<String, String> {
                val params = HashMap<String, String>()
                params.put("no_operador",noOperador)
                params.put("nombre", nombre)
                params.put("apellido_paterno", apellidoP)
                params.put("apellido_materno", apellidoM)
                params.put("estatus", estatus)
                return params
            }
        }
        queue.add(postRequest)
    }

    btnCancelar.setOnClickListener {
        // Limpiar o cancelar
        etNombre.text.clear()
        etApellidoP.text.clear()
        etApellidoM.text.clear()
        spEstatus.setSelection(0)
    }
}
}
