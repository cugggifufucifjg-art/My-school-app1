package com.example.data.repository

import com.example.data.models.AuthUser
import com.example.data.models.StudentProfile
import com.example.data.models.UserRole
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class AuthRepository {
    private val defaultUser = AuthUser(
        uid = "stu_01_alex",
        name = "Alexander Hayes",
        email = "alex.hayes@gmail.com",
        role = UserRole.STUDENT,
        schoolId = "sch_greenwood_2026",
        photoUrl = "",
        isVerified = true
    )

    private val _currentUser = MutableStateFlow<AuthUser?>(defaultUser)
    val currentUser: StateFlow<AuthUser?> = _currentUser.asStateFlow()

    private val _studentProfile = MutableStateFlow(StudentProfile())
    val studentProfile: StateFlow<StudentProfile> = _studentProfile.asStateFlow()

    fun loginWithGoogle(): Boolean {
        _currentUser.value = defaultUser.copy(
            role = UserRole.STUDENT,
            name = "Alexander Hayes",
            email = "alex.hayes@gmail.com"
        )
        return true
    }

    fun loginWithEmail(email: String, role: UserRole): Boolean {
        val name = when (role) {
            UserRole.SUPER_ADMIN -> "Platform Super Admin"
            UserRole.SCHOOL_ADMIN -> "Dean Arthur Pendelton"
            UserRole.PRINCIPAL -> "Dr. Eleanor Vance"
            UserRole.TEACHER -> "Prof. Robert Sterling"
            UserRole.ACCOUNTANT -> "Sarah Jenkins, CPA"
            UserRole.CONTENT_MANAGER -> "Mark Davies"
            UserRole.PARENT -> "Marcus Hayes (Parent)"
            UserRole.STUDENT -> "Alexander Hayes"
        }
        _currentUser.value = AuthUser(
            uid = "usr_${role.name.lowercase()}",
            name = name,
            email = email,
            role = role,
            schoolId = "sch_greenwood_2026",
            isVerified = true
        )
        return true
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
    ): Boolean {
        val updated = StudentProfile(
            id = "STU-2026-${(1000..9999).random()}",
            studentName = studentName,
            gmail = gmail,
            mobileNumber = mobileNumber,
            whatsappNumber = whatsappNumber,
            dateOfBirth = dateOfBirth,
            gender = gender,
            studentClass = studentClass,
            section = section,
            rollNumber = rollNumber,
            parentGuardianName = parentGuardianName,
            parentMobile = parentMobile,
            address = address,
            isVerified = true,
            digitalIdCode = "GIA-2026-${studentClass.replace(" ", "")}$section-$rollNumber"
        )
        _studentProfile.value = updated
        _currentUser.value = AuthUser(
            uid = updated.id,
            name = studentName,
            email = gmail,
            role = UserRole.STUDENT,
            schoolId = "sch_greenwood_2026",
            isVerified = true
        )
        return true
    }

    fun switchRole(newRole: UserRole) {
        val current = _currentUser.value ?: defaultUser
        val name = when (newRole) {
            UserRole.SUPER_ADMIN -> "Platform Super Admin"
            UserRole.SCHOOL_ADMIN -> "Dean Arthur Pendelton (Admin)"
            UserRole.PRINCIPAL -> "Dr. Eleanor Vance (Principal)"
            UserRole.TEACHER -> "Prof. Robert Sterling (Teacher)"
            UserRole.ACCOUNTANT -> "Sarah Jenkins (Finance)"
            UserRole.CONTENT_MANAGER -> "Mark Davies (LMS)"
            UserRole.PARENT -> "Marcus Hayes (Parent)"
            UserRole.STUDENT -> _studentProfile.value.studentName
        }
        _currentUser.value = current.copy(
            role = newRole,
            name = name
        )
    }

    fun updateStudentAllowedProfile(
        mobileNumber: String,
        whatsappNumber: String,
        address: String
    ) {
        val current = _studentProfile.value
        _studentProfile.value = current.copy(
            mobileNumber = mobileNumber,
            whatsappNumber = whatsappNumber,
            address = address
        )
    }

    fun logout() {
        _currentUser.value = null
    }
}
