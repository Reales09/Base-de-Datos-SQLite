package com.example.basesdedatosenandroid

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    var txtCodigo: EditText? = null
    var txtDescripcion: EditText? = null
    var txtPrecio: EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        txtCodigo = findViewById(R.id.txtCodigo)
        txtDescripcion=findViewById(R.id.txtDescripcion)
        txtPrecio=findViewById(R.id.txtPrecio)

    }

    fun insertar(view: View) {
        var con = SQLite(this, "tienda", null, 1)
        var baseDatos = con.writableDatabase
        var codigo = txtCodigo?.text.toString()
        var descripcion = txtDescripcion?.text.toString()
        var precio = txtPrecio?.text.toString()

        if (codigo.isEmpty() == false && descripcion.isEmpty() == false && precio.isEmpty() == false) {
            var registro = ContentValues()
            registro.put("codigo", codigo)
            registro.put("descripcion", descripcion)
            registro.put("precio", precio)

            baseDatos.insert("productos", null, registro)
            txtCodigo?.setText("")
            txtDescripcion?.setText("")
            txtPrecio?.setText("")

            Toast.makeText(this, "Se inserto correntamente", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(this, "Los campos deben tener texto", Toast.LENGTH_LONG).show()
        }
        baseDatos.close()
    }

        fun buscar(view: View){
            val con=SQLite(this,"tienda",null,1)
            val baseDatos=con.writableDatabase
            val codigo=txtCodigo?.text.toString()
            if (codigo.isEmpty()==false){
                val fila=baseDatos.rawQuery("select descripcion,precio from productos where codigo = '$codigo'",null)
                if (fila.moveToFirst()==true){
                    txtDescripcion?.setText(fila.getString(0))
                    txtPrecio?.setText(fila.getString(1))
                    baseDatos.close()
                }else{
                    txtDescripcion?.setText("")
                    txtPrecio?.setText("")
                    Toast.makeText(this, "No se encontraron registros", Toast.LENGTH_SHORT).show()
                }
            }

        }
    fun eliminar(view: View){
        val con = SQLite(this,"tienda",null,1)
        val baseDatos=con.writableDatabase
        val codigo=txtCodigo?.text.toString()
        if (codigo.isEmpty()==false){
            val cant=baseDatos.delete("productos","codigo='"+codigo+"'",null)
            if (cant>0){
                Toast.makeText(this, "El producto fue eliminado", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this, "El producto no se encontro", Toast.LENGTH_SHORT).show()
            }
            txtCodigo?.setText("")
            txtDescripcion?.setText("")
            txtPrecio?.setText("")
        }else{
            Toast.makeText(this, "El campo codigo debe tener texto", Toast.LENGTH_SHORT).show()
        }
    }
    fun editar(view: View){
        val con=SQLite(this,"tienda",null,1)
        val baseDatos=con.writableDatabase

        val codigo=txtCodigo?.text.toString()
        val descripcion=txtDescripcion?.text.toString()
        val precio=txtPrecio?.text.toString()

        if (!codigo.isEmpty() && !descripcion.isEmpty() && !precio.isEmpty() ){
            var registro=ContentValues()
            registro.put("codigo",codigo)
            registro.put("descripcion",descripcion)
            registro.put("precio",precio)
            
            val cant=baseDatos.update("productos",registro,"codigo='$codigo'",null)
            
            if (cant>0){
                Toast.makeText(this, "El registro se modifico exitosamente", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this, "El registro no fue encontrado", Toast.LENGTH_SHORT).show()
            }

        }else{
            Toast.makeText(this, "Los campos no deben estar vacios", Toast.LENGTH_SHORT).show()
        }
    }

    }
