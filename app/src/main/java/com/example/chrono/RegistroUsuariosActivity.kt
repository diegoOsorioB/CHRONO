package com.example.chrono

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
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

class RegistroUsuariosActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.registro_usuarios)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

    val etNombre = findViewById<EditText>(R.id.etNombre)
    val etApellidoP = findViewById<EditText>(R.id.etApellidoP)
    val etUsuario = findViewById<EditText>(R.id.etUsuario)
    val etContrasena = findViewById<EditText>(R.id.etContrasena)
    val spEstatus = findViewById<Spinner>(R.id.spEstatus)
    val spRol = findViewById<Spinner>(R.id.spRol)
    val btnGuardar = findViewById<Button>(R.id.btnGuardar)
    val btnCancelar = findViewById<Button>(R.id.btnCancelar)

    // Listeners para el Spinner de Estatus
    spEstatus.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            val estatusSeleccionado = parent?.getItemAtPosition(position).toString()
            // Puedes manejar la selección de estatus aquí
        }

        override fun onNothingSelected(parent: AdapterView<*>?) {
            // Opcional: manejar el caso donde no se selecciona nada
        }
    }

    // Listeners para el Spinner de Rol
    spRol.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            val rolSeleccionado = parent?.getItemAtPosition(position).toString()
            // Puedes manejar la selección de rol aquí
        }

        override fun onNothingSelected(parent: AdapterView<*>?) {
            // Opcional: manejar el caso donde no se selecciona nada
        }
    }

    btnGuardar.setOnClickListener {
        // Obtener los datos de los campos
        val nombre = etNombre.text.toString()
        val apellidoP = etApellidoP.text.toString()
        val usuario = etUsuario.text.toString()
        val contrasena = etContrasena.text.toString()
        var estatus = spEstatus.selectedItem.toString()
        var rol = spRol.selectedItem.toString()
        if (rol == "Guardia") {
            rol = "G"
        }else{
            rol = "S"
        }

        if(estatus=="Activo"){
            estatus="A"
        }else{
            estatus="I"
        }
        if (nombre.isEmpty() || apellidoP.isEmpty() || usuario.isEmpty() || contrasena.isEmpty()) {
            Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_LONG).show()
            return@setOnClickListener
        }

        val queue = Volley.newRequestQueue(this)
        val url = "http://${conexion.ip}/basechrono/inserta.php"
        var postRequest = object : StringRequest(Method.POST, url,
            Response.Listener<String> { response ->
                Toast.makeText(this, "$response", Toast.LENGTH_LONG).show()
            },Response.ErrorListener
            { error ->
                Toast.makeText(this, "Ocurrio un error inesperado", Toast.LENGTH_LONG).show()

    }){
            override fun getParams(): MutableMap<String, String> {
                val params = HashMap<String, String>()
                params.put("nombre", nombre)
                params.put("apellido_paterno", apellidoP)
                params.put("usuario", usuario)
                params.put("contra", contrasena)
                params.put("estatus", estatus)
                params.put("rol", rol)
                return params
            }
        }
        queue.add(postRequest)
    }

    btnCancelar.setOnClickListener {
        // Limpiar o cancelar
        etNombre.text.clear()
        etApellidoP.text.clear()
        etUsuario.text.clear()
        etContrasena.text.clear()
        spEstatus.setSelection(0)
        spRol.setSelection(0)
    }
}

}