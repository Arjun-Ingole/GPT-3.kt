package io.arjuningole.gpt

sealed class Screen(val route: String){
    object Home : Screen(route = "homescreen")
    object Output : Screen(route = "outputscreen")
}
