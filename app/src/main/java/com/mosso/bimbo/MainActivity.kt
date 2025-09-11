package com.mosso.bimbo

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.navOptions
import com.mosso.core.presentation.Navigator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), Navigator {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
        navController = navHostFragment.navController

    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    override fun navigateToPokemonList() {
        navController.navigate(
            R.id.action_loginFragment_to_pokemonListFragment4, null,
            navOptions {
                popUpTo(R.id.loginFragment) { inclusive = true }
                launchSingleTop = true
            })
    }

    override fun navigateToPokemonDetail(pokemonName: String) {
        navController.navigate(R.id.action_pokemonListFragment_to_pokemonDetailFragment,
            bundleOf("pokemon_name" to pokemonName)
        )
    }
}