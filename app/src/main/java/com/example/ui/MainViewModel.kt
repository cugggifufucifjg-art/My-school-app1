package com.example.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.models.AdminDashboardStats
import com.example.data.models.AuthUser
import com.example.data.models.SchoolBranding
import com.example.data.models.StudentProfile
import com.example.data.models.UserRole
import com.example.data.repository.AuthRepository
import com.example.data.repository.SchoolRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

enum class AppScreen {
    SPLASH,
    LOGIN,
    STUDENT_HOME,
    ADMIN_DASHBOARD
}

class MainViewModel(
    private val schoolRepository: SchoolRepository = SchoolRepository(),
    private val authRepository: AuthRepository = AuthRepository()
) : ViewModel() {

    private val _currentScreen = MutableStateFlow(AppScreen.SPLASH)
    val currentScreen: StateFlow<AppScreen> = _currentScreen.asStateFlow()

    val branding: StateFlow<SchoolBranding> = schoolRepository.branding
    val currentUser: StateFlow<AuthUser?> = authRepository.currentUser
    val studentProfile: StateFlow<StudentProfile> = authRepository.studentProfile

    private val _adminStats = MutableStateFlow(AdminDashboardStats())
    val adminStats: StateFlow<AdminDashboardStats> = _adminStats.asStateFlow()

    fun navigateTo(screen: AppScreen) {
        _currentScreen.value = screen
    }

    fun onSplashFinished() {
        val user = currentUser.value
        if (user == null) {
            _currentScreen.value = AppScreen.LOGIN
        } else {
            when (user.role) {
                UserRole.SCHOOL_ADMIN, UserRole.SUPER_ADMIN, UserRole.PRINCIPAL -> {
                    _currentScreen.value = AppScreen.ADMIN_DASHBOARD
                }
                else -> {
                    _currentScreen.value = AppScreen.STUDENT_HOME
                }
            }
        }
    }

    fun loginWithGoogle() {
        authRepository.loginWithGoogle()
        _currentScreen.value = AppScreen.STUDENT_HOME
    }

    fun loginWithEmail(email: String, role: UserRole) {
        authRepository.loginWithEmail(email, role)
        if (role == UserRole.SCHOOL_ADMIN || role == UserRole.SUPER_ADMIN || role == UserRole.PRINCIPAL) {
            _currentScreen.value = AppScreen.ADMIN_DASHBOARD
        } else {
            _currentScreen.value = AppScreen.STUDENT_HOME
        }
    }

    fun registerStudent(
        studentName: String,
        gmail: String,
        mobileNumber: String,
        whatsappNumber: String,
        dateOfBirth: String,
        gender: String,
        studentClass: String,
        section: String,
        rollNumber: String,
        parentGuardianName: String,
        parentMobile: String,
        address: String
    ) {
        authRepository.registerStudent(
            studentName, gmail, mobileNumber, whatsappNumber,
            dateOfBirth, gender, studentClass, section, rollNumber,
            parentGuardianName, parentMobile, address
        )
        _currentScreen.value = AppScreen.STUDENT_HOME
    }

    fun updateSchoolBranding(updated: SchoolBranding) {
        schoolRepository.updateBranding(updated)
    }

    fun logout() {
        authRepository.logout()
        _currentScreen.value = AppScreen.LOGIN
    }

    fun switchToAdmin() {
        authRepository.switchRole(UserRole.SCHOOL_ADMIN)
        _currentScreen.value = AppScreen.ADMIN_DASHBOARD
    }

    fun switchToStudent() {
        authRepository.switchRole(UserRole.STUDENT)
        _currentScreen.value = AppScreen.STUDENT_HOME
    }
}
