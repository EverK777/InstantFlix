package com.challenge.instantflix.presentation.feature.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed class Routes(val route: String, val arguments: List<NamedNavArgument> = emptyList()) {

    object HomeRoute : Routes("home")
    object DetailRoute : Routes(
        "detail/{posterImagePath}/{type}/{name}/{overview}/{genres}/{vote}/{year}",
        arguments = listOf(
            navArgument("posterImagePath") { type = NavType.StringType },
            navArgument("type") { type = NavType.StringType },
            navArgument("name") { type = NavType.StringType },
            navArgument("overview") { type = NavType.StringType },
            navArgument("genres") { type = NavType.StringType },
            navArgument("vote") { type = NavType.StringType },
            navArgument("year") { type = NavType.StringType },
        ),
    )
}

val navigationItems = listOf(
    Routes.HomeRoute,
    Routes.DetailRoute,
)
