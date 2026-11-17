package com.rutaya.app

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import com.rutaya.app.data.Driver
import com.rutaya.app.data.DriverDatabase
import com.rutaya.app.ui.RutaYaTheme
import com.rutaya.app.ui.screens.AuthScreen
import com.rutaya.app.ui.screens.ProfileScreen
import com.rutaya.app.ui.screens.WelcomeScreen

class MainActivity : ComponentActivity() {
    private lateinit var database: DriverDatabase

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        database = DriverDatabase(applicationContext)

        setContent {
            RutaYaTheme {
                var screen by rememberSaveable { mutableStateOf("welcome") }
                var activeDriver by remember { mutableStateOf<Driver?>(null) }

                when (screen) {
                    "welcome" -> WelcomeScreen(onContinue = { screen = "auth" })
                    "auth" -> AuthScreen(
                        onLogin = { document, password ->
                            database.login(document, password)?.also {
                                activeDriver = it
                                screen = "profile"
                            }
                        },
                        onRegister = { driver, password ->
                            database.register(driver, password).onSuccess {
                                activeDriver = it
                                screen = "profile"
                            }
                        }
                    )
                    "profile" -> activeDriver?.let { driver ->
                        ProfileScreen(
                            driver = driver,
                            onExit = { finishAndRemoveTask() }
                        )
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        database.close()
        super.onDestroy()
    }
}
