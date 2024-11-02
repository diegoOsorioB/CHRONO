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

class ModificaUsuariosActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.modificar_usuario)

        val editUsuario = findViewById<EditText>(R.id.etUsuario)
        val editNombre = findViewById<EditText>(R.id.etNombre)
        val editApellidoP = findViewById<EditText>(R.id.etApellidoP)
        val editUsuarioSec = findViewById<EditText>(R.id.etUsuario2)
        val editContrasena = findViewById<EditText>(R.id.etContrasena)
        val spinnerEstatus = findViewById<Spinner>(R.id.spEstatus)
        val spinnerRol = findViewById<Spinner>(R.id.spRol)
        val btnBuscar = findViewById<Button>(R.id.btnBuscar)
        val btnEliminar = findViewById<Button>(R.id.btnEliminar)
        val btnGuardar = findViewById<Button>(R.id.btnGuardar)
        val btnCancelar = findViewById<Button>(R.id.btnCancelar)

        // Set Spinner options
        val estatusAdapter = ArrayAdapter.createFromResource(
            this,
            R.array.estatus_options,
            android.R.layout.simple_spinner_item
        )
        estatusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerEstatus.adapter = estatusAdapter

        val rolAdapter = ArrayAdapter.createFromResource(
            this,
            R.array.rol_options,
            android.R.layout.simple_spinner_item
        )
        rolAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerRol.adapter = rolAdapter

        // Set button functionality
        btnGuardar.setOnClickListener {
            val nombre = editNombre.text.toString()
            val apellidoP = editApellidoP.text.toString()
            val usuario = editUsuario.text.toString()
            val contrasena = editContrasena.text.toString()
            var estatus = spinnerEstatus.selectedItem.toString()
            var rol = spinnerRol.selectedItem.toString()
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
            val url = "http://${conexion.ip}/basechrono/consultaUsuario.php"
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
            editUsuario.text.clear()
            editNombre.text.clear()
            editApellidoP.text.clear()
            editUsuarioSec.text.clear()
            editContrasena.text.clear()
            spinnerEstatus.setSelection(0)
            spinnerRol.setSelection(0)
        }

        btnBuscar.setOnClickListener {
            val usuario = editUsuario.text.toString()
            val queue = Volley.newRequestQueue(this)
            val id = intent.getStringExtra("id")
            val url = "http://${conexion.ip}/basechrono/consultaUsuario.php?usuario=$usuario"

            val JsonObjectRequest = JsonObjectRequest(
                Request.Method.GET, url,null,
                { response ->
                    if (response.has("mensaje")) {
                        // Mostrar el mensaje de error en un Toast
                        val mensaje = response.getString("mensaje")
                        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()

                    } else {
                        editNombre.setText(response.getString("nombre"))
                        editApellidoP.setText(response.getString("apellido_paterno"))
                        editUsuarioSec.setText(response.getString("usuario"))
                        editContrasena.setText(response.getString("contra"))
                        val estatus = response.getString("estatus")
                        if (estatus == "A") {
                            val posicion = estatusAdapter.getPosition("Activo")
                            spinnerEstatus.setSelection(posicion)
                        } else {
                            val posicion = estatusAdapter.getPosition("Inactivo")
                            spinnerEstatus.setSelection(posicion)
                        }
                        val rol = response.getString("rol")
                        if (rol == "G") {
                            val posicion = rolAdapter.getPosition("Guardia")
                            spinnerRol.setSelection(posicion)
                        } else if (rol == "A") {
                            val posicion = rolAdapter.getPosition("Admin")
                            spinnerRol.setSelection(posicion)
                        } else {
                            val posicion = rolAdapter.getPosition("Supervisor")
                            spinnerRol.setSelection(posicion)
                        }
                    }
                },
                { error ->

                    Toast.makeText(this, "$error", Toast.LENGTH_SHORT).show()
                    editUsuario.setText(error.toString())
                    editContrasena.setText("")
                    editApellidoP.setText("")
                    editNombre.setText("")
                    spinnerEstatus.setSelection(0)
                })

            queue.add(JsonObjectRequest)
        }

        btnEliminar.setOnClickListener {
            // Logic for deleting the user
            Toast.makeText(this, "Eliminar clicked", Toast.LENGTH_SHORT).show()
        }
    }
}