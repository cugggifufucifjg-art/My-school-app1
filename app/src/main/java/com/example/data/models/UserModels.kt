package com.example.data.models

enum class UserRole(val displayName: String, val badgeColorHex: Long) {
    SUPER_ADMIN("Super Admin", 0xFF6366F1),
    SCHOOL_ADMIN("School Admin", 0xFF2563EB),
    PRINCIPAL("Principal", 0xFF0D9488),
    TEACHER("Teacher", 0xFF059669),
    ACCOUNTANT("Accountant", 0xFFEA580C),
    CONTENT_MANAGER("Content Manager", 0xFF8B5CF6),
    STUDENT("Student", 0xFF1E3A8A),
    PARENT("Parent", 0xFFD97706)
}

data class AuthUser(
    val uid: String,
    val name: String,
    val email: String,
    val role: UserRole,
    val schoolId: String,
    val photoUrl: String = "",
    val isVerified: Boolean = true
)

data class StudentProfile(
    val id: String = "STU-2026-0842",
    val studentName: String = "Alexander Hayes",
    val gmail: String = "alex.hayes@gmail.com",
    val profilePhotoUrl: String = "",
    val mobileNumber: String = "+1 (555) 349-1123",
    val whatsappNumber: String = "+1 (555) 349-1123",
    val dateOfBirth: String = "2010-05-14",
    val gender: String = "Male",
    val studentClass: String = "Class 10",
    val section: String = "A",
    val rollNumber: String = "18",
    val parentGuardianName: String = "Marcus & Evelyn Hayes",
    val parentMobile: String = "+1 (555) 890-4412",
    val address: String = "742 Evergreen Terrace, Metropolis",
    val isVerified: Boolean = true,
    val digitalIdCode: String = "GIA-2026-CL10A-018",
    val attendancePercent: Float = 94.8f,
    val courseProgressPercent: Float = 68.0f,
    val pendingHomeworkCount: Int = 2,
    val recentGrade: String = "A+ (94%)",
    val studyStreakDays: Int = 12,
    val achievementPoints: Int = 1450
)
