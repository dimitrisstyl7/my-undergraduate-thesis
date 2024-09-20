package gr.unipi.thesis.dimstyl.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Home

val HomeNavItem = NavItem(icon = Icons.Rounded.Home, route = NavRoute.HOME)

val bottomNavBarItems = listOf(HomeNavItem, DietPlansNavItem, ProfileNavItem)