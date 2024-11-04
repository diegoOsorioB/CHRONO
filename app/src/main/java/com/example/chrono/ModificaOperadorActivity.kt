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
import org.json.JSONException
import org.json.JSONObject

class ModificaOperadorActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.modificar_operador)

        val editNoOperador = findViewById<EditText>(R.id.etNumeroOperador)
        val editNombreSec = findViewById<EditText>(R.id.etNombre)
        val editApellidoP = findViewById<EditText>(R.id.etApellidoP)
        val editApellidoM = findViewById<EditText>(R.id.etApellidoM)
        val spinnerEstatus = findViewById<Spinner>(R.id.spEstatus)
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

        // Set button functionality
        btnGuardar.setOnClickListener {
            val noOperador = editNoOperador.text.toString()
            val nombre = editNombreSec.text.toString()
            val apellidoP = editApellidoP.text.toString()
            val apellidoM =editApellidoM.text.toString()
            var estatus = spinnerEstatus.selectedItem.toString()

            if (estatus == "Activo") {
                estatus = "A"
            } else {
                estatus = "I"
            }

            if (noOperador.isEmpty() || nombre.isEmpty() || apellidoP.isEmpty() || apellidoM.isEmpty()) {
                Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            val queue = Volley.newRequestQueue(this)
            val url = "http://${conexion.ip}/basechrono/consultaOperador.php"
            var postRequest = object : StringRequest(
                Method.POST, url,
                Response.Listener<String> { response ->
                    try {
                        val jsonResponse = JSONObject(response)

                        if (jsonResponse.has("mensaje")) {
                            val mensaje = jsonResponse.getString("mensaje")

                            Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show()
                        }

                    } catch (e: JSONException) {
                        e.printStackTrace()
                        Toast.makeText(this, "Error al recibir la respuesta", Toast.LENGTH_LONG).show()
                    }
                }, Response.ErrorListener
                { error ->
                    Toast.makeText(this, "Ocurrio un error inesperado", Toast.LENGTH_LONG).show()
                }){
                override fun getParams(): MutableMap<String, String> {
                    val params = HashMap<String, String>()
                    params.put("no_operador", noOperador)
                    params.put("nombre",nombre)
                    params.put("apellido_paterno",apellidoP)
                    params.put("apellido_materno",apellidoM)
                    params.put("estatus", estatus)
                    return params
                }
            }
            queue.add(postRequest)
        }

        btnCancelar.setOnClickListener {
            editNoOperador.text.clear()
            editNombreSec.text.clear()
            editApellidoP.text.clear()
            editApellidoM.text.clear()
            spinnerEstatus.setSelection(0)
        }

        btnBuscar.setOnClickListener {
            // Lógica para buscar al usuario
            val no_operador = editNoOperador.text.toString()
            val queue = Volley.newRequestQueue(this)
            val url = "http://${conexion.ip}/basechrono/consultaOperador.php?no_operador=$no_operador"

            val jsonObjectRequest = JsonObjectRequest(
                Request.Method.GET, url, null,
                { response ->
                    // Verificar si el JSON contiene la clave "mensaje"
                    if (response.has("mensaje")) {
                        // Mostrar el mensaje de error en un Toast
                        val mensaje = response.getString("mensaje")
                        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()

                        // Limpiar los campos ya que no se encontró el operador
                        editNombreSec.setText("")
                        editApellidoP.setText("")
                        editApellidoM.setText("")
                        spinnerEstatus.setSelection(0)
                    } else {
                        // Llenar los campos con los valores de la respuesta
                        editNombreSec.setText(response.getString("nombre"))
                        editApellidoP.setText(response.getString("apellido_paterno"))
                        editApellidoM.setText(response.getString("apellido_materno"))

                        val estatus = response.getString("estatus")
                        if (estatus == "A") {
                            val posicion = estatusAdapter.getPosition("Activo")
                            spinnerEstatus.setSelection(posicion)
                        } else {
                            val posicion = estatusAdapter.getPosition("Inactivo")
                            spinnerEstatus.setSelection(posicion)
                        }
                    }
                },
                { error ->
                    // Manejo de errores de red o de la solicitud
                    Toast.makeText(this, "Ocurrió un error inesperado", Toast.LENGTH_SHORT).show()
                    editNoOperador.setText(error.toString())
                    editNombreSec.setText("")
                    editApellidoP.setText("")
                    editApellidoM.setText("")
                    spinnerEstatus.setSelection(0)
                }
            )

            // Agregar la solicitud a la cola
            queue.add(jsonObjectRequest)
        }



        btnEliminar.setOnClickListener {
            val noOperador = editNoOperador.text.toString()


            if (noOperador.isEmpty() ) {
                Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            val queue = Volley.newRequestQueue(this)
            val url = "http://${conexion.ip}/basechrono/consultaOperador.php"
            var postRequest = object : StringRequest(
                Method.POST, url,
                Response.Listener<String> { response ->
                    try {
                        val jsonResponse = JSONObject(response)

                        if (jsonResponse.has("mensaje")) {
                            val mensaje = jsonResponse.getString("mensaje")

                            Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show()
                        }

                    } catch (e: JSONException) {
                        e.printStackTrace()
                        Toast.makeText(this, "Error al recibir la respuesta", Toast.LENGTH_LONG).show()
                    }
                }, Response.ErrorListener
                { error ->
                    Toast.makeText(this, "Ocurrio un error inesperado", Toast.LENGTH_LONG).show()
                }){
                override fun getParams(): MutableMap<String, String> {
                    val params = HashMap<String, String>()
                    params.put("no_operador", noOperador)
                    return params
                }
            }
            queue.add(postRequest)
        }
    }
}