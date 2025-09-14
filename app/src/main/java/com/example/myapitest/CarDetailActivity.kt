package com.example.myapitest

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapitest.databinding.ActivityCarDetailBinding
import com.example.myapitest.model.Car
import com.example.myapitest.service.RetrofitClient
import com.example.myapitest.service.safeApiCall
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.example.myapitest.service.Result
import com.example.myapitest.ui.loadUrl
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.storage.FirebaseStorage
import java.io.ByteArrayInputStream
import java.util.UUID

class CarDetailActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var binding: ActivityCarDetailBinding
    private lateinit var mMap: GoogleMap
    private lateinit var car: Car

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCarDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setNavigationOnClickListener { finish() }

        binding.destroyCar.setOnClickListener { deleteCar() }
        binding.editCTA.setOnClickListener { updateCar() }

        (supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment)
            .getMapAsync(this)

        loadCar()
    }

    private fun loadCar() {
        val id = intent.getStringExtra(ARG_ID) ?: return
        CoroutineScope(Dispatchers.IO).launch {
            val result = safeApiCall { RetrofitClient.apiService.getCar(id) }
            withContext(Dispatchers.Main) {
                when (result) {
                    is Result.Success -> { car = result.data; bindCar(); loadMapMarkerIfReady() }
                    is Result.Error -> Toast.makeText(this@CarDetailActivity, R.string.could_not_retrieve_vehicle_data, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun bindCar() {
        binding.model.setText(car.name)
        binding.year.setText(car.year)
        binding.license.setText(car.licence)
        binding.image.loadUrl(car.imageUrl)
    }


    private fun updateCar() {
        CoroutineScope(Dispatchers.IO).launch {
            val updated = car.copy(
                year = binding.year.text.toString(),
                licence = binding.license.text.toString()
            )
            val result = safeApiCall { RetrofitClient.apiService.updateCar(car.id, updated) }
            withContext(Dispatchers.Main) {
                when (result) {
                    is Result.Success -> { car = result.data; Toast.makeText(this@CarDetailActivity, R.string.success_update, Toast.LENGTH_SHORT).show(); bindCar() }
                    is Result.Error -> Toast.makeText(this@CarDetailActivity, R.string.unknown_error, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun deleteCar() {
        CoroutineScope(Dispatchers.IO).launch {
            val result = safeApiCall { RetrofitClient.apiService.deleteCar(car.id) }
            withContext(Dispatchers.Main) {
                when (result) {
                    is Result.Success -> { Toast.makeText(this@CarDetailActivity, R.string.succes_delete, Toast.LENGTH_SHORT).show(); finish() }
                    is Result.Error -> Toast.makeText(this@CarDetailActivity, R.string.unknown_error, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        loadMapMarkerIfReady()
    }

    private fun loadMapMarkerIfReady() {
        if (!::mMap.isInitialized || !::car.isInitialized) return
        car.place?.let {
            val pos = LatLng(it.lat, it.long)
            binding.googleMapContent.visibility = View.VISIBLE
            mMap.clear()
            mMap.addMarker(MarkerOptions().position(pos))
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(pos, 17f))
        }
    }

    companion object {
        private const val ARG_ID = "ARG_ID"
        fun newIntent(context: Context, carId: String) =
            Intent(context, CarDetailActivity::class.java).putExtra(ARG_ID, carId)
    }
}
