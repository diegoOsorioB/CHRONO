package com.example.chrono


import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.chrononote.BusquedaActivity
import com.example.chrononote.MenuRegistroActivity
import com.example.chrononote.SegundaRevActivity

class MenuPrincipalSuperActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.menu_supervisor)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.menuprincipalS)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Obtener referencia a los botones
        val primeraRevisionButton: Button = findViewById(R.id.usuarios)
        val consultaButton: Button = findViewById(R.id.trasnportista)
        val modificarButton: Button = findViewById(R.id.modificaRe)
        val id = intent.getStringExtra("id")

        // Evento click para Primera Revisión
        primeraRevisionButton.setOnClickListener {
            val intent = Intent(this, FormActivity::class.java)
            startActivity(intent)
        }


        // Evento click para Consulta
        consultaButton.setOnClickListener {
            val intent = Intent(this, BusquedaActivity::class.java)
            startActivity(intent)
        }


        modificarButton.setOnClickListener {
            val intent = Intent(this, ModificarRevActivity::class.java)
            startActivity(intent)
        }
    }
}