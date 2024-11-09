package com.example.chrono

import android.os.Bundle
import android.content.Intent
import android.view.View
import android.widget.Toast
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.android.volley.Request
import com.example.chrono.conexion
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest

import com.android.volley.toolbox.Volley
import com.example.chrononote.MenuPricipalActivity
import org.json.JSONObject


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val ip=conexion.ip

        val usernameEditText = findViewById<EditText>(R.id.usernameEditText)
        val passwordEditText = findViewById<EditText>(R.id.passwordEditText)
        val loginButton = findViewById<Button>(R.id.loginButton)


        loginButton.setOnClickListener {
            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()

            // Lógica para iniciar sesión
            if (username.isNotEmpty() && password.isNotEmpty()) {
                //loginToDatabase(username, password)

                val queue = Volley.newRequestQueue(this)
                val url = "http://${conexion.ip}/basechrono/consultaUsuario.php?usuario=$username"

                val JsonObjectRequest = JsonObjectRequest(Request.Method.GET, url,null,
                    { response ->
                        if (response.has("mensaje")) {
                            // Mostrar el mensaje de error en un Toast
                            val mensaje = response.getString("mensaje")
                            Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()

                        } else {
                            val contra = response.getString("contra")
                            val user = response.getString("usuario").trim()
                            val id = response.getString("id_usuario")
                            val rol = response.getString("rol")

                            if (contra == password && user == username.trim()) {
                                if (rol == "G") {
                                    val intent =
                                        Intent(this, MenuPrincipalGuardiaActivity::class.java)
                                    intent.putExtra("id", id)
                                    startActivity(intent)
                                } else if (rol == "S") {
                                    val intent =
                                        Intent(this, MenuPrincipalSuperActivity::class.java)
                                    intent.putExtra("id", id)
                                    startActivity(intent)
                                } else if (rol == "A") {
                                    val intent = Intent(this, MenuPricipalActivity::class.java)
                                    intent.putExtra("id", id)
                                    startActivity(intent)
                                }
                                Toast.makeText(this, "$id Bienvenido $username", Toast.LENGTH_SHORT).show()
                                usernameEditText.text.clear()
                                passwordEditText.text.clear()
                            } else {
                                Toast.makeText(
                                    this,
                                    "Usuario o contraseña incorrectos",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }

                    },
                    { error ->
                        println("Error: $error")
                        Toast.makeText(this, "Ocurrio un error inesperado", Toast.LENGTH_SHORT).show()
                    })

                queue.add(JsonObjectRequest)



            } else {
                Toast.makeText(this, "Por favor ingresa los datos.", Toast.LENGTH_SHORT).show()
            }
        }


    }

    fun loginToDatabase(username: String, password: String) {

    }
    fun clickLogin(view: View) {
        var txtID: EditText = findViewById(R.id.passwordEditText)

    }
}
