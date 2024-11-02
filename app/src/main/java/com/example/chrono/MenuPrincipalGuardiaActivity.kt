package com.example.chrono

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
//import com.example.chrononote.BusquedaActivity
import com.example.chrononote.SegundaRevActivity

class MenuPrincipalGuardiaActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.menu_guardia)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.menuprincipalG)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Obtener referencia a los botones
        val segundaRevisionButton: Button = findViewById(R.id.unidades)
        val consultaButton: Button = findViewById(R.id.trasnportista)
        val modificarButton: Button = findViewById(R.id.modificaRe)
        val id = intent.getStringExtra("id")


        // Evento click para Segunda Revisión
        segundaRevisionButton.setOnClickListener {
            val intent = Intent(this, SegundaRevActivity::class.java)
            intent.putExtra("id", id)
            startActivity(intent)
        }

        // Evento click para Consulta
        consultaButton.setOnClickListener {
         //   val intent = Intent(this, BusquedaActivity::class.java)
            startActivity(intent)
        }


        modificarButton.setOnClickListener {
            val intent = Intent(this, ModificarRevActivity::class.java)
            startActivity(intent)
        }
    }
}