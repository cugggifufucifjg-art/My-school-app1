package com.example.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.models.SchoolBranding
import com.example.data.models.StudentProfile
import com.example.ui.components.DigitalIdCardDialog
import com.example.ui.theme.*

data class EducationModuleCard(
    val id: String,
    val title: String,
    val subtitle: String,
    val icon: ImageVector,
    val iconBgColor: Color,
    val iconTintColor: Color,
    val badge: String? = null
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudentHomeScreen(
    branding: SchoolBranding,
    student: StudentProfile,
    onLogout: () -> Unit,
    onSwitchToAdmin: () -> Unit
) {
    val context = LocalContext.current
    var showIdCard by remember { mutableStateOf(false) }
    var selectedModule by remember { mutableStateOf<EducationModuleCard?>(null) }
    var activeActionNotice by remember { mutableStateOf<String?>(null) }
    var selectedNavIndex by remember { mutableIntStateOf(0) }

    if (showIdCard) {
        DigitalIdCardDialog(
            student = student,
            branding = branding,
            onDismiss = { showIdCard = false }
        )
    }

    // Modal Sheet when a card is clicked
    if (selectedModule != null) {
        AlertDialog(
            onDismissRequest = { selectedModule = null },
            icon = {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(selectedModule!!.iconBgColor),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = selectedModule!!.icon,
                        contentDescription = null,
                        tint = selectedModule!!.iconTintColor,
                        modifier = Modifier.size(24.dp)
                    )
                }
            },
            title = {
                Text(
                    text = selectedModule!!.title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = PolishTextPrimary
                )
            },
            text = {
                Column {
                    Text(
                        text = "Module Records & Overview",
                        fontWeight = FontWeight.SemiBold,
                        color = PolishTextSecondary,
                        fontSize = 12.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = when (selectedModule!!.id) {
                            "courses" -> "You are enrolled in 4 academic courses. Current progress: ${student.courseProgressPercent.toInt()}% across STEM and Humanities modules."
                            "live_classes" -> "Live Stream: Advanced Physics Mechanics with Dr. H. Watson is starting at 10:30 AM via Google Meet."
                            "homework" -> "You have ${student.pendingHomeworkCount} pending assignments due this week for Mathematics and Chemistry."
                            "study_material" -> "14 downloadable PDF chapter notes, sample question banks, and video lectures available for Class 10-A."
                            "tests" -> "Upcoming Mock Term Examination starting in 3 days. Instant grading and solutions enabled."
                            "results" -> "Latest Mid-Term Grade: ${student.recentGrade} • Class Rank #3 • 98th Percentile."
                            "attendance" -> "Current Academic Attendance: ${student.attendancePercent}% (162 of 171 days present). Status: Excellent."
                            "timetable" -> "Today's Schedule: Mathematics (08:30) • Physics (09:30) • English (11:00) • Chemistry Lab (13:00)."
                            "fees" -> "Tuition Fee for Academic Term ${branding.academicSession}: Paid in full. Next term due in 45 days."
                            "announcements" -> "Annual STEM Science Expo registration is now open. Principal Dr. ${branding.principalName} invites student project proposals."
                            "events" -> "Sports Day 2026 scheduled for next month. Track and field trials start this Friday."
                            "ask_teacher" -> "Direct 1-on-1 doubt clearing desk open with Prof. Sterling and Mrs. Sharma."
                            "achievements" -> "Earned Badges: Homework Hero 🌟 • Quiz Master 🏆 • 12 Day Study Streak 🔥 • Total Points: ${student.achievementPoints}."
                            else -> "Educational records synchronized with school server."
                        },
                        fontSize = 14.sp,
                        color = PolishTextPrimary,
                        lineHeight = 20.sp
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        activeActionNotice = "Opened ${selectedModule!!.title} workspace"
                        selectedModule = null
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = PolishNavyDark),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("View Workspace", color = Color.White, fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { selectedModule = null }) {
                    Text("Close", color = PolishTextSecondary)
                }
            }
        )
    }

    // All 13 modules styled according to the Professional Polish design system
    val moduleCards = listOf(
        EducationModuleCard("courses", "Courses", "4 Active", Icons.Default.AutoStories, CategoryCourseBg, CategoryCourseText, "${student.courseProgressPercent.toInt()}%"),
        EducationModuleCard("homework", "Homework", "${student.pendingHomeworkCount} Due", Icons.Default.Assignment, CategoryHomeworkBg, CategoryHomeworkText, "DUE"),
        EducationModuleCard("live_classes", "Live Class", "1 Soon", Icons.Default.Videocam, CategoryLiveBg, CategoryLiveText, "LIVE"),
        EducationModuleCard("results", "Results", "Mid-Term", Icons.Default.Grade, CategoryResultsBg, CategoryResultsText, "A+"),
        EducationModuleCard("timetable", "Timetable", "Class 10-A", Icons.Default.CalendarMonth, CategoryTimetableBg, CategoryTimetableText),
        EducationModuleCard("fees", "Fees", "Paid", Icons.Default.ReceiptLong, CategoryFeesBg, CategoryFeesText, "CLEAR"),
        EducationModuleCard("study_material", "Study Material", "Notes & PDFs", Icons.Default.MenuBook, CategoryLiveBg, CategoryLiveText, "NEW"),
        EducationModuleCard("tests", "Tests & Quizzes", "Mock Exam", Icons.Default.Quiz, CategoryCourseBg, CategoryCourseText),
        EducationModuleCard("attendance", "Attendance", "${student.attendancePercent}%", Icons.Default.CheckCircle, CategoryLiveBg, CategoryLiveText, "95%"),
        EducationModuleCard("announcements", "Notices", "School Updates", Icons.Default.Campaign, CategoryHomeworkBg, CategoryHomeworkText, "3"),
        EducationModuleCard("events", "Events", "Annual Sports", Icons.Default.Celebration, CategoryTimetableBg, CategoryTimetableText),
        EducationModuleCard("ask_teacher", "Ask Teacher", "Doubts Desk", Icons.Default.SupportAgent, CategoryFeesBg, CategoryFeesText, "OPEN"),
        EducationModuleCard("achievements", "Achievements", "${student.achievementPoints} pts", Icons.Default.EmojiEvents, CategoryHomeworkBg, CategoryHomeworkText, "BADGES")
    )

    // Student initials for avatar badge
    val studentInitials = student.studentName.split(" ").mapNotNull { it.firstOrNull()?.toString() }.take(2).joinToString("").ifEmpty { "ST" }
    val schoolInitials = branding.schoolName.split(" ").mapNotNull { it.firstOrNull()?.toString() }.take(2).joinToString("").ifEmpty { "SC" }

    Scaffold(
        containerColor = PolishBackground,
        bottomBar = {
            // Professional Polish Bottom Navigation Bar
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(76.dp),
                color = PolishNavBg,
                border = BorderStroke(1.dp, PolishBorderLight)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 8.dp),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    PolishNavItem(
                        icon = Icons.Default.Home,
                        label = "Home",
                        selected = selectedNavIndex == 0,
                        onClick = { selectedNavIndex = 0 }
                    )
                    PolishNavItem(
                        icon = Icons.Default.MenuBook,
                        label = "Learning",
                        selected = selectedNavIndex == 1,
                        onClick = {
                            selectedNavIndex = 1
                            selectedModule = moduleCards.first { it.id == "courses" }
                        }
                    )
                    PolishNavItem(
                        icon = Icons.Default.Notifications,
                        label = "Notices",
                        selected = selectedNavIndex == 2,
                        onClick = {
                            selectedNavIndex = 2
                            selectedModule = moduleCards.first { it.id == "announcements" }
                        }
                    )
                    PolishNavItem(
                        icon = Icons.Default.Person,
                        label = "Profile",
                        selected = selectedNavIndex == 3,
                        onClick = {
                            selectedNavIndex = 3
                            showIdCard = true
                        }
                    )
                }
            }
        },
        modifier = Modifier.testTag("student_home_screen_root")
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Professional Polish Top Header with rounded-b-[32px] and shadow
            item {
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp),
                    color = Color.White,
                    shadowElevation = 4.dp
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp, vertical = 18.dp)
                    ) {
                        // School Header Bar
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.weight(1f)
                            ) {
                                // School Emblem Box
                                Box(
                                    modifier = Modifier
                                        .size(42.dp)
                                        .clip(RoundedCornerShape(12.dp))
                                        .background(PolishBluePrimary),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = schoolInitials,
                                        color = Color.White,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 16.sp
                                    )
                                }
                                Spacer(modifier = Modifier.width(12.dp))
                                Column {
                                    Text(
                                        text = branding.schoolName.uppercase(),
                                        color = PolishBluePrimary,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 12.sp,
                                        letterSpacing = 0.6.sp,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                    Text(
                                        text = branding.tagline,
                                        color = PolishTextSecondary,
                                        fontSize = 10.sp,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                }
                            }

                            // Quick Action Icons and Avatar Pill
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                IconButton(
                                    onClick = onSwitchToAdmin,
                                    modifier = Modifier.size(34.dp).testTag("switch_to_admin_button")
                                ) {
                                    Icon(
                                        Icons.Default.AdminPanelSettings,
                                        contentDescription = "Admin Portal",
                                        tint = PolishTextSecondary,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }

                                IconButton(
                                    onClick = onLogout,
                                    modifier = Modifier.size(34.dp).testTag("logout_button")
                                ) {
                                    Icon(
                                        Icons.Default.Logout,
                                        contentDescription = "Logout",
                                        tint = AlertCrimson,
                                        modifier = Modifier.size(18.dp)
                                    )
                                }

                                // Avatar circle with border #E1E2E5 and inner #DDE2F9
                                Box(
                                    modifier = Modifier
                                        .size(40.dp)
                                        .clip(CircleShape)
                                        .border(2.dp, PolishBorderLight, CircleShape)
                                        .background(PolishIceBlue)
                                        .clickable { showIdCard = true }
                                        .testTag("open_digital_id_button"),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = studentInitials,
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        color = PolishNavyDark
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        // Greeting typography: "Good Morning," + "[Name] 👋"
                        Text(
                            text = "Good Morning,",
                            color = PolishTextSecondary,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium
                        )
                        Text(
                            text = "${student.studentName} 👋",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = PolishTextPrimary,
                            modifier = Modifier.testTag("student_greeting_text")
                        )
                    }
                }
            }

            // Two Prominent Stat Cards from the Design (Attendance 94% + Course Progress 78%)
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Attendance Card: bg-[#DDE2F9] rounded-3xl p-4
                    Surface(
                        modifier = Modifier
                            .weight(1f)
                            .height(118.dp)
                            .clickable {
                                selectedModule = moduleCards.first { it.id == "attendance" }
                            },
                        shape = RoundedCornerShape(24.dp),
                        color = PolishIceBlue
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp),
                            verticalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "ATTENDANCE",
                                color = PolishNavyDark.copy(alpha = 0.7f),
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 0.8.sp
                            )
                            Column {
                                Text(
                                    text = "${student.attendancePercent.toInt()}%",
                                    fontSize = 28.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = PolishNavyDark
                                )
                                Text(
                                    text = "+2% from last month",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = PolishNavyDark
                                )
                            }
                        }
                    }

                    // Course Progress Card: bg-[#F1E0FF] rounded-3xl p-4
                    Surface(
                        modifier = Modifier
                            .weight(1f)
                            .height(118.dp)
                            .clickable {
                                selectedModule = moduleCards.first { it.id == "courses" }
                            },
                        shape = RoundedCornerShape(24.dp),
                        color = PolishPurpleLight
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp),
                            verticalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "COURSE PROGRESS",
                                color = PolishPurpleDark.copy(alpha = 0.7f),
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 0.8.sp
                            )
                            Column {
                                Text(
                                    text = "${student.courseProgressPercent.toInt()}%",
                                    fontSize = 28.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = PolishPurpleDark
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                // Progress track: bg-[#E1D4EC] with bar bg-[#6750A4]
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(6.dp)
                                        .clip(RoundedCornerShape(3.dp))
                                        .background(PolishPurpleTrack)
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth(student.courseProgressPercent / 100f)
                                            .fillMaxHeight()
                                            .clip(RoundedCornerShape(3.dp))
                                            .background(PolishPurpleBar)
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // Quick Access Section Heading
            item {
                Text(
                    text = "QUICK ACCESS",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = PolishTextTertiary,
                    letterSpacing = 1.sp,
                    modifier = Modifier.padding(horizontal = 20.dp)
                )
            }

            // 3-Column Quick Access Grid (aspect-square bg-white border border-[#C4C7CF] rounded-2xl)
            item {
                Column(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    val chunked = moduleCards.chunked(3)
                    for (row in chunked) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            for (card in row) {
                                Surface(
                                    modifier = Modifier
                                        .weight(1f)
                                        .aspectRatio(1f)
                                        .clickable { selectedModule = card }
                                        .testTag("module_card_${card.id}"),
                                    shape = RoundedCornerShape(16.dp),
                                    color = Color.White,
                                    border = BorderStroke(1.dp, PolishBorder)
                                ) {
                                    Column(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .padding(6.dp),
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        verticalArrangement = Arrangement.Center
                                    ) {
                                        // Pastel circular icon container
                                        Box(
                                            modifier = Modifier
                                                .size(36.dp)
                                                .clip(CircleShape)
                                                .background(card.iconBgColor),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Icon(
                                                imageVector = card.icon,
                                                contentDescription = card.title,
                                                tint = card.iconTintColor,
                                                modifier = Modifier.size(18.dp)
                                            )
                                        }

                                        Spacer(modifier = Modifier.height(6.dp))

                                        Text(
                                            text = card.title,
                                            fontSize = 10.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = PolishTextPrimary,
                                            maxLines = 1,
                                            textAlign = TextAlign.Center
                                        )

                                        if (card.badge != null) {
                                            Text(
                                                text = card.badge,
                                                fontSize = 9.sp,
                                                fontWeight = FontWeight.SemiBold,
                                                color = card.iconTintColor
                                            )
                                        }
                                    }
                                }
                            }
                            // Fill empty slots in the row
                            val remaining = 3 - row.size
                            repeat(remaining) {
                                Spacer(modifier = Modifier.weight(1f))
                            }
                        }
                    }
                }
            }

            // Up Next / Live Class Banner (bg-[#001B3D] text-white rounded-[24px] p-4)
            item {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    shape = RoundedCornerShape(24.dp),
                    color = PolishNavyDark
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "UP NEXT",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 1.sp,
                                color = PolishIceBlue.copy(alpha = 0.7f)
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = "Advanced Physics",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color.White
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = "Room 402 • 10:30 AM",
                                fontSize = 10.sp,
                                color = PolishIceBlue.copy(alpha = 0.8f)
                            )
                        }

                        Button(
                            onClick = {
                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://meet.google.com"))
                                try {
                                    context.startActivity(intent)
                                } catch (e: Exception) {
                                    activeActionNotice = "Launching live lecture..."
                                }
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = PolishIceBlue,
                                contentColor = PolishNavyDark
                            ),
                            shape = CircleShape,
                            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                            modifier = Modifier.testTag("join_class_button")
                        ) {
                            Text("Join Now", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }

            // Pending Homework Banner
            item {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    shape = RoundedCornerShape(16.dp),
                    color = CategoryHomeworkBg,
                    border = BorderStroke(1.dp, CategoryHomeworkText.copy(alpha = 0.3f))
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                            Box(
                                modifier = Modifier
                                    .size(34.dp)
                                    .clip(CircleShape)
                                    .background(Color.White),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(Icons.Default.AssignmentLate, contentDescription = null, tint = CategoryHomeworkText, modifier = Modifier.size(18.dp))
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text(
                                    text = "Pending: Calculus Integrals Ch. 4",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 12.sp,
                                    color = CategoryHomeworkText
                                )
                                Text(
                                    text = "Due Tomorrow 11:59 PM • 25 Marks",
                                    fontSize = 10.sp,
                                    color = PolishTextSecondary
                                )
                            }
                        }

                        Button(
                            onClick = {
                                selectedModule = moduleCards.first { it.id == "homework" }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = CategoryHomeworkText),
                            shape = RoundedCornerShape(10.dp),
                            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
                        ) {
                            Text("Submit", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color.White)
                        }
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
private fun PolishNavItem(
    icon: ImageVector,
    label: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .clickable(onClick = onClick)
            .padding(horizontal = 12.dp, vertical = 4.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (selected) {
            Box(
                modifier = Modifier
                    .width(48.dp)
                    .height(32.dp)
                    .clip(CircleShape)
                    .background(PolishIceBlue),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = label,
                    tint = PolishNavyDark,
                    modifier = Modifier.size(18.dp)
                )
            }
        } else {
            Box(
                modifier = Modifier
                    .width(48.dp)
                    .height(32.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = label,
                    tint = PolishTextSecondary.copy(alpha = 0.7f),
                    modifier = Modifier.size(18.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(3.dp))

        Text(
            text = label,
            fontSize = 10.sp,
            fontWeight = if (selected) FontWeight.Bold else FontWeight.Medium,
            color = if (selected) PolishNavyDark else PolishTextSecondary.copy(alpha = 0.8f)
        )
    }
}
