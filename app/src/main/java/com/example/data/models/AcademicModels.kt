package com.example.data.models

data class LiveClassItem(
    val id: String,
    val title: String,
    val subjectName: String,
    val teacherName: String,
    val scheduleTime: String,
    val platform: String = "Google Meet",
    val meetingUrl: String = "https://meet.google.com/abc-defg-hij",
    val isLiveNow: Boolean = false,
    val description: String = ""
)

data class HomeworkPreviewItem(
    val id: String,
    val title: String,
    val subjectName: String,
    val chapter: String,
    val dueDate: String,
    val maxMarks: Int,
    val status: String, // "Pending", "Submitted", "Checked", "Late"
    val teacherRemarks: String = ""
)

data class AnnouncementItem(
    val id: String,
    val title: String,
    val category: String,
    val snippet: String,
    val date: String,
    val isImportant: Boolean = false
)

data class CoursePreviewItem(
    val id: String,
    val title: String,
    val teacherName: String,
    val subjectName: String,
    val price: String,
    val progressPercent: Int,
    val totalLessons: Int,
    val completedLessons: Int
)

data class DashboardCardMeta(
    val title: String,
    val subtitle: String,
    val iconName: String,
    val badge: String? = null,
    val accentColorHex: Long = 0xFF2563EB
)
