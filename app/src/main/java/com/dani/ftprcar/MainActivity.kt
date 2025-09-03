package com.dani.ftprcar

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dani.ftprcar.databinding.ActivityMainBinding
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import com.dani.ftprcar.auth.AuthViewModel
import com.dani.ftprcar.data.Car
import com.dani.ftprcar.ui.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        requestLocationPermission()
        setupView()

        // 1- Criar tela de Login com algum provedor do Firebase (Telefone, Google)
        //      Cadastrar o Seguinte celular para login de test: +5511912345678
        //      Código de verificação: 101010

        // 2- Criar Opção de Logout no aplicativo

        // 3- Integrar API REST /car no aplicativo
        //      API será disponibilida no Github
        //      JSON Necessário para salvar e exibir no aplicativo
        //      O Image Url deve ser uma foto armazenada no Firebase Storage
        //      { "id": "001", "imageUrl":"https://image", "year":"2020/2020", "name":"Gaspar", "licence":"ABC-1234", "place": {"lat": 0, "long": 0} }

        // Opcionalmente trabalhar com o Google Maps ara enviar o place

        setContent {
            val authVm = remember { AuthViewModel() }
            val listVm = remember { CarListViewModel() }
            val formVm = remember { CarFormViewModel() }
            var screen by remember { mutableStateOf("list") }
            var mapCar by remember { mutableStateOf<Car?>(null) }

            val uid by authVm.uid.collectAsState()

            if (uid == null) {
                LoginScreen(authVm) { screen = "list"; listVm.load() }
            } else {
                when {
                    mapCar != null -> MapScreen(mapCar!!) { mapCar = null; screen = "list" }
                    screen == "form" -> CarFormScreen(formVm) {
                        screen = "list"; listVm.load()
                    }
                    else -> { listVm.load(); CarListScreen(listVm, authVm, onNew = { screen = "form" }, onMap = { mapCar = it }) }
                }
            }
        }
    }

}

    override fun onResume() {
        super.onResume()
        fetchItems()
    }

    private fun setupView() {
        // TODO
    }

    private fun requestLocationPermission() {
        // TODO
    }

    private fun fetchItems() {
        // TODO
    }
}
