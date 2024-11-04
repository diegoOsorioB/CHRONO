package com.example.chrononote

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.pm.PackageManager
import android.icu.util.Calendar
import android.os.AsyncTask
import android.os.Bundle
import android.os.Environment
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.chrono.R
import com.example.chrono.conexion
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.json.JSONArray
import org.json.JSONObject
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL
import java.util.jar.Manifest

class BusquedaActivity : AppCompatActivity() {

    private lateinit var economicoEditText: EditText
    private lateinit var buscarButton: Button
    private lateinit var resultTableLayout: TableLayout
    private lateinit var generarExcelButton: Button
    private lateinit var volverButton: Button
    private lateinit var dataList: ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.busqueda)
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE), 1)
        }

        economicoEditText = findViewById(R.id.economicoEditText)
        buscarButton = findViewById(R.id.buscarButton)
        resultTableLayout = findViewById(R.id.resultTableLayout)
        generarExcelButton = findViewById(R.id.generarExcelButton)
        volverButton = findViewById(R.id.volverButton)
        val operadorEditText = findViewById<EditText>(R.id.operadorEditText)

        val dateEditText = findViewById<EditText>(R.id.economicoDateEditText)
        val dateEditText2 = findViewById<EditText>(R.id.economicoDateEditText2)
        dateEditText.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                this,
                { _, yearSelected, monthSelected, dayOfMonth ->
                    val selectedDate = "$yearSelected-${monthSelected + 1}-$dayOfMonth"
                    dateEditText.setText(selectedDate)
                },
                year, month, day
            )
            datePickerDialog.show()
        }
        dateEditText2.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                this,
                { _, yearSelected, monthSelected, dayOfMonth ->
                    val selectedDate = "$yearSelected-${monthSelected + 1}-$dayOfMonth"
                    dateEditText2.setText(selectedDate)
                },
                year, month, day
            )
            datePickerDialog.show()
        }
        dataList = ArrayList()
        //val selectedDate = dateEditText.text.toString() // Obtiene la fecha seleccionada del EditText

        buscarButton.setOnClickListener {
            val selectedDate = findViewById<EditText>(R.id.economicoDateEditText).text.toString()
            val economico = economicoEditText.text.toString()
             val operadorEditText = findViewById<EditText>(R.id.operadorEditText)
            val selectedDate2 = findViewById<EditText>(R.id.economicoDateEditText2).text.toString()

                val url = "http://${conexion.ip}/basechrono/consultaSegunda.php?economico=$economico&fecha_revision=$selectedDate&operador=$operadorEditText&fecha_revision2=$selectedDate2"
               // val queue = Volley.newRequestQueue(this)

                val jsonArrayRequest = JsonArrayRequest(
                    Request.Method.GET, url, null,
                    { response ->
                        try {
                            if (response is JSONArray) {
                                // Limpia la tabla excepto el encabezado
                                resultTableLayout.removeViews(1, resultTableLayout.childCount - 1)

                                // Limitar la cantidad de registros mostrados a 4
                                val totalRecordsToShow = minOf(response.length(), 4)

                                // Recorrer el JSONArray y agregar cada fila a la tabla
                                for (i in 0 until totalRecordsToShow) {
                                    val item = response.getJSONObject(i)
                                    val revisionID = item.getString("id_revision")
                                    val economico = item.getString("Economico_Unidad")
                                    val placas = item.getString("Placas_Unidad")
                                    val tarimas = item.getString("Tarimas")
                                    val patin = item.getString("Patin")
                                    val kilometraje = item.getString("Kilometraje")
                                    val nocIncho = item.getString("No_Cincho")
                                    val operador = item.getString("Nombre_Operador")
                                    val usuario = item.getString("Nombre_Usuario")
                                    val fechaPrimeraRevision =
                                        item.getString("Fecha_Primera_Revision")
                                    val fechaSegundaRevision =
                                        item.getString("Fecha_Segunda_Revision")
                                    val apellidoPaternoUsuario = item.getString("Apellido_Usuario")
                                    val apellidoOperador =
                                        item.getString("Apellido_Paterno_Operador")
                                    val apellidoMaternoOperador =
                                        item.getString("Apellido_Materno_Operador")

                                    // Crear una nueva fila para la tabla
                                    val tableRow = TableRow(this)

                                    // Mostrar solo 4 datos principales
                                    val economicoCell = TextView(this).apply {
                                        text = revisionID
                                        setPadding(8, 8, 8, 8)
                                    }

                                    val placasCell = TextView(this).apply {
                                        text = economico
                                        setPadding(8, 8, 8, 8)
                                    }

                                    val kilometrajeCell = TextView(this).apply {
                                        text = operador
                                        setPadding(8, 8, 8, 8)
                                    }



                                    // Crear botón de detalles
                                    val detailsButton = Button(this).apply {
                                        text = "Detalles"
                                        setPadding(8, 8, 8, 8)
                                        setOnClickListener {
                                            // Mostrar información adicional en un diálogo
                                            AlertDialog.Builder(this@BusquedaActivity)
                                                .setTitle("Detalles de la revisión")
                                                .setMessage(
                                                            "ID de Revisión: $revisionID\n" +
                                                            "Economico: $economico\n" +
                                                            "Placas: $placas\n" +
                                                            "Tarimas: $tarimas\n" +
                                                            "Patin: $patin\n" +
                                                            "Kilometraje: $kilometraje\n" +
                                                            "Cincho: $nocIncho\n" +
                                                            "Operador: $operador $apellidoOperador $apellidoMaternoOperador\n" +
                                                            "Usuario: $usuario $apellidoPaternoUsuario\n" +
                                                            "Fecha 1ª Revisión: $fechaPrimeraRevision\n" +
                                                            "Fecha 2ª Revisión: $fechaSegundaRevision"
                                                )
                                                .setPositiveButton("Cerrar") { dialog, _ ->
                                                    dialog.dismiss()
                                                }
                                                .show()
                                        }
                                    }

                                    // Agregar las celdas a la fila
                                    tableRow.addView(economicoCell)
                                    tableRow.addView(placasCell)
                                    tableRow.addView(kilometrajeCell)
                                    tableRow.addView(detailsButton)

                                    // Agregar la fila a la tabla
                                    resultTableLayout.addView(tableRow)
                                }
                            }else {
                                // Si la respuesta no es un JSONArray, manejarlo como un JSONObject
                                val jsonObject = response as JSONObject
                                if (jsonObject.has("error")) {
                                    // Mostrar mensaje de error si existe
                                    val errorMessage = jsonObject.getString("error")
                                    Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()
                                } else {
                                    Toast.makeText(this, "Respuesta inesperada del servidor.", Toast.LENGTH_LONG).show()
                                }
                            }
                        } catch (e: Exception) {
                            e.printStackTrace()
                            Toast.makeText(this, "Error al procesar los datos", Toast.LENGTH_LONG).show()
                            economicoEditText.setText("$e")
                        }
                    },
                    { error ->
                        Toast.makeText(this, "Error: ${error.message}", Toast.LENGTH_LONG).show()
                        economicoEditText.setText("$error")
                    }
                )


                Volley.newRequestQueue(this).add(jsonArrayRequest)

        }

        // Botón para generar el archivo Excel
        generarExcelButton.setOnClickListener {
            if (isExternalStorageWritable()) {
                try {
                    // Crear un archivo Excel
                    val workbook: Workbook = XSSFWorkbook()
                    val sheet: Sheet = workbook.createSheet("Revisiones")

                    // Crear encabezado de la tabla
                    val headerRow: Row = sheet.createRow(0)
                    headerRow.createCell(0).setCellValue("ID Revisión")
                    headerRow.createCell(1).setCellValue("Economico")
                    headerRow.createCell(2).setCellValue("Placas")
                    headerRow.createCell(3).setCellValue("Tarimas")
                    headerRow.createCell(4).setCellValue("Patin")
                    headerRow.createCell(5).setCellValue("Kilometraje")
                    headerRow.createCell(6).setCellValue("No_cincho")
                    headerRow.createCell(7).setCellValue("Operador")
                    headerRow.createCell(8).setCellValue("Usuario")
                    headerRow.createCell(9).setCellValue("Fecha Primera Revision")
                    headerRow.createCell(10).setCellValue("Fecha Segunda Revision")


                    // Agregar datos de la tabla resultTableLayout
                    for (i in 1 until resultTableLayout.childCount) {
                        val tableRow = resultTableLayout.getChildAt(i) as TableRow
                        val row: Row = sheet.createRow(i)

                        for (j in 0 until tableRow.childCount) {
                            val cell = tableRow.getChildAt(j) as TextView
                            row.createCell(j).setCellValue(cell.text.toString())
                        }
                    }

                    // Guardar el archivo Excel en la carpeta de Descargas
                    val fileName = "Revisiones_${System.currentTimeMillis()}.xlsx"
                    val downloadsDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                    val file = File(downloadsDirectory, fileName)
                    val fileOutputStream = FileOutputStream(file)

                    workbook.write(fileOutputStream)
                    fileOutputStream.close()
                    workbook.close()

                    Toast.makeText(this, "Archivo Excel guardado en Descargas: $fileName", Toast.LENGTH_LONG).show()
                } catch (e: IOException) {
                    e.printStackTrace()
                    Toast.makeText(this, "Error al generar el archivo Excel", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "No se puede escribir en el almacenamiento externo", Toast.LENGTH_SHORT).show()
            }
        }


        volverButton.setOnClickListener {
            finish()
        }

    }
    fun isExternalStorageWritable(): Boolean {
        return Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED
    }


}