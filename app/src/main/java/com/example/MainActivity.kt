package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.AppScreen
import com.example.ui.MainViewModel
import com.example.ui.screens.AdminDashboardScreen
import com.example.ui.screens.LoginScreen
import com.example.ui.screens.SplashScreen
import com.example.ui.screens.StudentHomeScreen
import com.example.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .testTag("app_root_surface")
                ) {
                    EduManageApp()
                }
            }
        }
    }
}

@Composable
fun EduManageApp(viewModel: MainViewModel = viewModel()) {
    val currentScreen by viewModel.currentScreen.collectAsState()
    val branding by viewModel.branding.collectAsState()
    val studentProfile by viewModel.studentProfile.collectAsState()
    val adminStats by viewModel.adminStats.collectAsState()

    Crossfade(targetState = currentScreen, label = "screen_crossfade") { screen ->
        when (screen) {
            AppScreen.SPLASH -> {
                SplashScreen(
                    branding = branding,
                    onContinue = { viewModel.onSplashFinished() }
                )
            }
            AppScreen.LOGIN -> {
                LoginScreen(
                    branding = branding,
                    onGoogleSignIn = { viewModel.loginWithGoogle() },
                    onEmailSignIn = { email, role -> viewModel.loginWithEmail(email, role) },
                    onRegisterStudent = { name, gmail, mob, wa, dob, gender, sClass, sec, roll, pName, pMob, addr ->
                        viewModel.registerStudent(name, gmail, mob, wa, dob, gender, sClass, sec, roll, pName, pMob, addr)
                    }
                )
            }
            AppScreen.STUDENT_HOME -> {
                StudentHomeScreen(
                    branding = branding,
                    student = studentProfile,
                    onLogout = { viewModel.logout() },
                    onSwitchToAdmin = { viewModel.switchToAdmin() }
                )
            }
            AppScreen.ADMIN_DASHBOARD -> {
                AdminDashboardScreen(
                    branding = branding,
                    stats = adminStats,
                    onSaveBranding = { updated -> viewModel.updateSchoolBranding(updated) },
                    onSwitchToStudent = { viewModel.switchToStudent() },
                    onLogout = { viewModel.logout() }
                )
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(text = "Hello $name!", modifier = modifier)
}
