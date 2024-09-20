package gr.unipi.thesis.dimstyl.ui.navigation

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource

class NavItem(
    private val icon: ImageVector? = null,
    @DrawableRes private val iconRes: Int? = null,
    val route: NavRoute,
    val navLabel: String = route.toString()
) {

    @Composable
    fun getIcon(): ImageVector = icon ?: ImageVector.vectorResource(iconRes!!)

}
