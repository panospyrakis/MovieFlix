package com.spyrakis.movieflix.core.navcontroller

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.spyrakis.movieflix.R
import com.spyrakis.movieflix.features.moviedetails.MOVIE_ID
import timber.log.Timber
import java.lang.ref.WeakReference
import javax.inject.Inject

class AppNavigator @Inject constructor() : Navigator {

    private var navController: WeakReference<NavController>? = null

    override fun <T : Destination> navigate(destination: T) {
        Timber.d("navigate() called with: destination = $destination")
        navController?.get()?.let {
            when (destination) {
                is Destination.Details -> it.navigate(
                    R.id.movieDetailsFragment, destination.getArgs()
                )
            }
        }
    }

    override fun bind(fragment: Fragment) {
        fragment.findNavController().run {
            navController = WeakReference(this)
        }
    }

    override fun unbind() {
        navController = null
    }
}

interface Navigator {
    fun <T : Destination> navigate(destination: T)
    fun bind(fragment: Fragment)
    fun unbind()
}

sealed interface Destination {
    data class Details(val id: Int) : Destination {
        fun getArgs(): Bundle {
            val bundle = Bundle()
            bundle.putInt(MOVIE_ID, id)
            return bundle
        }
    }
}