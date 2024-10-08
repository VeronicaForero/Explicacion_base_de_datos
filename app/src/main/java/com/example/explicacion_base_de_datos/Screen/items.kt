package com.example.explicacion_base_de_datos.Screen

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.explicacion_base_de_datos.Dao.UserDao
import com.example.explicacion_base_de_datos.Model.User
import com.example.explicacion_base_de_datos.Repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@Composable

fun  UserApp(userRepository: UserRepository) {

    var nombre by remember { mutableStateOf("") }
    var apellido by remember { mutableStateOf("") }
    var edad by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    var Borrar by remember { mutableStateOf("") }
    var Buscar by remember { mutableStateOf("") }


    val context = LocalContext.current

    LazyColumn(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        item {
            TextField(
                value = nombre,
                onValueChange = { nombre = it },
                label = { Text("Nombre") }
            )
            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = apellido,
                onValueChange = { apellido = it },
                label = { Text("Apellido") }
            )
            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = edad,
                onValueChange = { edad = it },
                label = { Text("Edad") }
            )
            Spacer(modifier = Modifier.height(8.dp))



            Button(
                onClick = {
                    val user = User(
                        nombre = nombre,
                        apellido = apellido,
                        edad = edad.toIntOrNull() ?: 0
                    )
                    scope.launch {
                        withContext(Dispatchers.IO) {
                            userRepository.insert(user)
                        }
                        Toast.makeText(context, "Usuario Registrado", Toast.LENGTH_SHORT).show()
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Gray,
                    contentColor = Color.Black


                ),
            ) {
                Text("Registrar")
            }
            Spacer(modifier = Modifier.height(16.dp))

            var users by remember { mutableStateOf(listOf<User>()) }

            Button(
                onClick = {
                    scope.launch {
                        users = withContext(Dispatchers.IO) {
                            userRepository.getAllUser()
                        }
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    contentColor = Color.Black,
                    containerColor = Color.Gray
                    ),
            ) {
                Text(text = "Listar")
            }

            Spacer(modifier = Modifier.height(8.dp))

            Column {
                users.forEach { user ->
                    Text("ID:${user.id} Nombre: ${user.nombre} Apellido: ${user.apellido} Edad: ${user.edad}")

                }
            }

            TextField(
                value = Borrar,
                onValueChange = { Borrar = it },
                label = { Text("Borrar") }
            )
            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    scope.launch {
                        withContext(Dispatchers.IO) {
                            userRepository.deleteById(Borrar.toInt())
                        }
                        Toast.makeText(context, "Usuario Borrado", Toast.LENGTH_SHORT).show()
                    }
                },

                colors = ButtonDefaults.buttonColors(
                    contentColor = Color.Black,
                    containerColor = Color.Gray

                    ),
            ) {
                Text(text = "Borrar")
            }
            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = Buscar,
                onValueChange = { Buscar = it },
                label = { Text("ID BUSCAR") }
            )
            Spacer(modifier = Modifier.height(8.dp))

            var Listar by remember { mutableStateOf(listOf<User>()) }
            var SegundoNombre by remember { mutableStateOf("") }
            var SegundoApellido by remember { mutableStateOf("") }
            var CambioEdad by remember { mutableStateOf("") }


            Button(
                onClick = {
                    scope.launch {
                        val Listar = withContext(Dispatchers.IO) {
                            userRepository.buscarId(Buscar.toInt())
                       }
                        if (Listar !=null){
                            SegundoNombre= Listar.nombre
                            SegundoApellido =Listar.apellido
                            CambioEdad =Listar.edad.toString()

                        }

                    }
                },

                colors = ButtonDefaults.buttonColors(
                    contentColor = Color.Black,
                    containerColor = Color.Gray


                ),


            ) {
                Text(text = "Buscar")

            }

            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = SegundoNombre,
                onValueChange = { SegundoNombre = it },
                label = { Text("Nombre") }
            )
            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = SegundoApellido,
                onValueChange = { SegundoApellido = it },
                label = { Text("Apellido") }
            )
            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = CambioEdad,
                onValueChange = { CambioEdad= it },
                label = { Text("Edad") }
            )
            Spacer(modifier = Modifier.height(8.dp))


            Button(
                    onClick = {
                        scope.launch {
                             withContext(Dispatchers.IO) {
                                userRepository.updateUser(Buscar.toInt(), SegundoNombre,SegundoApellido, CambioEdad.toInt())
                            }

                        }
                    },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Gray,
                    contentColor = Color.Black,


                    ),
                ) {
                    Text(text = "Actualizar")

                }






        }
    }
}