package com.example.explicacion_base_de_datos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.explicacion_base_de_datos.Dao.UserDao
import com.example.explicacion_base_de_datos.Database.UserDatabase
import com.example.explicacion_base_de_datos.Repository.UserRepository
import com.example.explicacion_base_de_datos.Screen.UserApp
import com.example.explicacion_base_de_datos.ui.theme.Explicacion_base_de_datosTheme

class MainActivity : ComponentActivity() {

    private lateinit var userDao: UserDao
    private lateinit var userRepository: UserRepository


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val db = UserDatabase.getDatabase(applicationContext)
        userDao = db.userDao()
        userRepository = UserRepository(userDao)

        enableEdgeToEdge()
        setContent {
           UserApp(userRepository)
        }
    }
}


