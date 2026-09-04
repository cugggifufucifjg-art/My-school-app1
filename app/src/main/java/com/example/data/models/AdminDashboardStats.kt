package com.example.data.models

data class AdminDashboardStats(
    val totalStudents: Int = 1248,
    val totalTeachers: Int = 64,
    val totalClasses: Int = 36,
    val totalCourses: Int = 18,
    val totalRevenue: String = "$48,250",
    val pendingFees: String = "$6,420",
    val pendingHomeworkCount: Int = 142,
    val upcomingClassesCount: Int = 6,
    val avgAttendancePercent: String = "94.8%"
)

data class ActivityLog(
    val id: String,
    val action: String,
    val actor: String,
    val timestamp: String,
    val tag: String
)
