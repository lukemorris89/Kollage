package dev.rarebit.kollage

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Surface
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import dev.rarebit.design.theme.KollageTheme
import dev.rarebit.kollage.navigation.AppNavHost

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        installSplashScreen()

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            KollageTheme {
                Surface {
                    AppNavHost()
                }
            }
        }
    }
}
