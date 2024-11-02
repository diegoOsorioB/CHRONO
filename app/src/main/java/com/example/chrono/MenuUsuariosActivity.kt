package com.example.chrononote

import android.os.Bundle
import android.content.Intent
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.chrono.FormActivity
import com.example.chrono.ModificaUsuariosActivity
import com.example.chrono.R
import com.example.chrono.RegistroTransActivity
import com.example.chrono.RegistroUnidadesActivity
import com.example.chrono.RegistroUsuariosActivity

class MenuUsuariosActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.menu_usuarios)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.menu_usuarios)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Obtener referencia a los botones
        val insertaButton: Button = findViewById(R.id.inserta)
        val consultaButton: Button = findViewById(R.id.consulta)

        // Evento click para registro de usuarios
        insertaButton.setOnClickListener {
            val intent = Intent(this, RegistroUsuariosActivity::class.java)
            startActivity(intent)
        }

        // Evento click para Segunda Revisión
        consultaButton.setOnClickListener {
            val intent = Intent(this, ModificaUsuariosActivity::class.java)
            startActivity(intent)
        }
    }
}
