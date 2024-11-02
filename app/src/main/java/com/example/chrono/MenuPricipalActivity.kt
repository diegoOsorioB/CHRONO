package com.example.chrononote


import android.os.Bundle
import android.content.Intent
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.chrono.FormActivity
import com.example.chrono.ModificarRevActivity
import com.example.chrono.R

class MenuPricipalActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.menu_principal)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.menuprincipal)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Obtener referencia a los botones
        val primeraRevisionButton: Button = findViewById(R.id.usuarios)
        val segundaRevisionButton: Button = findViewById(R.id.unidades)
        val consultaButton: Button = findViewById(R.id.trasnportista)
        val registroButton: Button = findViewById(R.id.registro)
        val modificarButton: Button = findViewById(R.id.modificaRe)
        val id = intent.getStringExtra("id")

        // Evento click para Primera Revisión
        primeraRevisionButton.setOnClickListener {
            val intent = Intent(this, FormActivity::class.java)
            intent.putExtra("id", id)
            startActivity(intent)
            Toast.makeText(this, "$id", Toast.LENGTH_SHORT).show()
        }

        // Evento click para Segunda Revisión
        segundaRevisionButton.setOnClickListener {
           val intent = Intent(this, SegundaRevActivity::class.java)
            intent.putExtra("id", id)
            startActivity(intent)
        }

        // Evento click para Consulta
        consultaButton.setOnClickListener {
            val intent = Intent(this, BusquedaActivity::class.java)
            startActivity(intent)
        }

        // Evento click para Registro
        registroButton.setOnClickListener {
           val intent = Intent(this, MenuRegistroActivity::class.java)
            startActivity(intent)
        }

        modificarButton.setOnClickListener {
            val intent = Intent(this, ModificarRevActivity::class.java)
            startActivity(intent)
        }
    }
}
