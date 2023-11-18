package com.ogzkesk.filmapp.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.lifecycleScope
import com.ogzkesk.filmapp.R
import com.ogzkesk.filmapp.util.NetworkObserver
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(getColor(R.color.dark)),
            navigationBarStyle = SystemBarStyle.dark(getColor(R.color.dark))
        )

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        observeNetwork()
    }

    private fun observeNetwork() {
        lifecycleScope.launch(Dispatchers.IO) {
            NetworkObserver(this@MainActivity).observe().collect {
                if (it.not()) {
                    withContext(Dispatchers.Main) {
                        // internet yok show dialog
                    }
                }
            }
        }
    }
}