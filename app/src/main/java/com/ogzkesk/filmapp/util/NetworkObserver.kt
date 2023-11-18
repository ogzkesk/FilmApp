package com.ogzkesk.filmapp.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import androidx.core.content.getSystemService
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch

class NetworkObserver(context: Context) {

    private val cm = context.getSystemService<ConnectivityManager>()
    val hasActiveNetwork = cm?.activeNetwork

    fun observe(): Flow<Boolean> {
        return callbackFlow {
            val callBack = object : ConnectivityManager.NetworkCallback(){
                override fun onLost(network: Network) {
                    super.onLost(network)
                    launch { send(false) }
                }

                override fun onUnavailable() {
                    super.onUnavailable()
                    launch { send(false) }
                }

                override fun onLosing(network: Network, maxMsToLive: Int) {
                    super.onLosing(network, maxMsToLive)
                    launch { send(false) }
                }

                override fun onAvailable(network: Network) {
                    super.onAvailable(network)
                    launch { send(true) }
                }
            }

            cm?.registerDefaultNetworkCallback(callBack)

            awaitClose{
                cm?.unregisterNetworkCallback(callBack)
            }
        }
    }
}