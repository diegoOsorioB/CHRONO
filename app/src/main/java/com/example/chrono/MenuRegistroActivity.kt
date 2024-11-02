package com.example.chrononote

import android.os.Bundle
import android.content.Intent
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.chrono.FormActivity
import com.example.chrono.R
import com.example.chrono.RegistroTransActivity
import com.example.chrono.RegistroUnidadesActivity
import com.example.chrono.RegistroUsuariosActivity

class MenuRegistroActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.menu_registro)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.menuregistro)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Obtener referencia a los botones
        val primeraRevisionButton: Button = findViewById(R.id.usuarios)
        val segundaRevisionButton: Button = findViewById(R.id.unidades)
        val consultaButton: Button = findViewById(R.id.trasnportista)

        // Evento click para registro de usuarios
        primeraRevisionButton.setOnClickListener {
            val intent = Intent(this, MenuUsuariosActivity::class.java)
            startActivity(intent)
        }

        // Evento click para Segunda Revisión
        segundaRevisionButton.setOnClickListener {
            val intent = Intent(this, MenuUnidadesActivity::class.java)
            startActivity(intent)
        }

        // Evento click para Consulta
        consultaButton.setOnClickListener {
            val intent = Intent(this, MenuOperadoresActivity::class.java)
            startActivity(intent)
        }
    }
}
