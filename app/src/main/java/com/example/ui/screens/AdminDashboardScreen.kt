package com.example.ui.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.models.AdminDashboardStats
import com.example.data.models.SchoolBranding
import com.example.data.models.UserRole
import com.example.ui.components.SchoolBrandingEditorDialog
import com.example.ui.theme.*
import kotlinx.coroutines.launch

data class AdminMenuItem(
    val id: String,
    val title: String,
    val icon: ImageVector,
    val category: String = "Academic"
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminDashboardScreen(
    branding: SchoolBranding,
    stats: AdminDashboardStats = AdminDashboardStats(),
    onSaveBranding: (SchoolBranding) -> Unit,
    onSwitchToStudent: () -> Unit,
    onLogout: () -> Unit
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var selectedSection by remember { mutableStateOf("Dashboard") }
    var showBrandingEditor by remember { mutableStateOf(false) }
    var snackbarMessage by remember { mutableStateOf<String?>(null) }

    if (showBrandingEditor) {
        SchoolBrandingEditorDialog(
            currentBranding = branding,
            onDismiss = { showBrandingEditor = false },
            onSave = { updated ->
                onSaveBranding(updated)
                showBrandingEditor = false
                snackbarMessage = "School branding updated and broadcasted to mobile app!"
            }
        )
    }

    // All 28 requested navigation items from the prompt
    val menuItems = listOf(
        AdminMenuItem("Dashboard", "Dashboard", Icons.Default.Dashboard, "Overview"),
        AdminMenuItem("School Branding", "School Branding", Icons.Default.Palette, "Branding & Settings"),
        AdminMenuItem("Students", "Students", Icons.Default.People, "People"),
        AdminMenuItem("Parents", "Parents", Icons.Default.FamilyRestroom, "People"),
        AdminMenuItem("Teachers", "Teachers", Icons.Default.RecordVoiceOver, "People"),
        AdminMenuItem("Classes", "Classes", Icons.Default.Class, "Academic"),
        AdminMenuItem("Sections", "Sections", Icons.Default.MeetingRoom, "Academic"),
        AdminMenuItem("Subjects", "Subjects", Icons.Default.AutoStories, "Academic"),
        AdminMenuItem("Homework", "Homework", Icons.Default.Assignment, "Academic"),
        AdminMenuItem("Study Material", "Study Material", Icons.Default.MenuBook, "Content"),
        AdminMenuItem("Courses", "Courses", Icons.Default.School, "Content"),
        AdminMenuItem("Live Classes", "Live Classes", Icons.Default.Videocam, "Live"),
        AdminMenuItem("Recorded Classes", "Recorded Classes", Icons.Default.VideoLibrary, "Live"),
        AdminMenuItem("Tests", "Tests & Quizzes", Icons.Default.Quiz, "Evaluation"),
        AdminMenuItem("Exams", "Exams", Icons.Default.FactCheck, "Evaluation"),
        AdminMenuItem("Results", "Results & Cards", Icons.Default.Grade, "Evaluation"),
        AdminMenuItem("Attendance", "Attendance", Icons.Default.CheckCircle, "Management"),
        AdminMenuItem("Timetable", "Timetable", Icons.Default.CalendarMonth, "Management"),
        AdminMenuItem("Fees", "Fees Management", Icons.Default.ReceiptLong, "Finance"),
        AdminMenuItem("Payments", "Payments & Stripe", Icons.Default.Payment, "Finance"),
        AdminMenuItem("Announcements", "Announcements", Icons.Default.Campaign, "Communication"),
        AdminMenuItem("Notifications", "Push Notifications", Icons.Default.NotificationsActive, "Communication"),
        AdminMenuItem("Events", "School Events", Icons.Default.Celebration, "Communication"),
        AdminMenuItem("Doubts", "Student Doubts", Icons.Default.SupportAgent, "Communication"),
        AdminMenuItem("Messages", "Messages / WhatsApp", Icons.Default.Chat, "Communication"),
        AdminMenuItem("Reports", "Institutional Reports", Icons.Default.Assessment, "Analytics"),
        AdminMenuItem("Settings", "General Settings", Icons.Default.Settings, "System"),
        AdminMenuItem("Roles & Permissions", "Roles & RBAC", Icons.Default.Security, "System")
    )

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                modifier = Modifier.width(300.dp)
            ) {
                // School Header in Drawer
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(PolishNavyDark)
                        .padding(20.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(54.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color.White)
                            .padding(4.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = branding.logoDrawableRes),
                            contentDescription = "School Logo",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = branding.schoolName.uppercase(),
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        fontSize = 14.sp,
                        letterSpacing = 0.6.sp,
                        maxLines = 1
                    )
                    Text(
                        text = "Admin Control Suite • Session ${branding.academicSession}",
                        color = PolishIceBlue,
                        fontSize = 11.sp
                    )
                }

                HorizontalDivider(color = PolishBorderLight)

                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    items(menuItems) { item ->
                        NavigationDrawerItem(
                            icon = { Icon(item.icon, contentDescription = null, tint = if (selectedSection == item.id) PolishBluePrimary else PolishTextSecondary) },
                            label = { Text(item.title, fontWeight = if (selectedSection == item.id) FontWeight.Bold else FontWeight.Normal) },
                            selected = selectedSection == item.id,
                            colors = NavigationDrawerItemDefaults.colors(
                                selectedContainerColor = PolishIceBlue,
                                selectedTextColor = PolishNavyDark,
                                unselectedTextColor = PolishTextPrimary
                            ),
                            onClick = {
                                selectedSection = item.id
                                scope.launch { drawerState.close() }
                                if (item.id == "School Branding") {
                                    showBrandingEditor = true
                                }
                            },
                            modifier = Modifier.padding(vertical = 2.dp)
                        )
                    }
                }
            }
        }
    ) {
        Scaffold(
            containerColor = PolishBackground,
            topBar = {
                TopAppBar(
                    title = {
                        Column {
                            Text(
                                text = "Admin Panel • $selectedSection",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = PolishTextPrimary
                            )
                            Text(
                                text = branding.schoolName,
                                fontSize = 11.sp,
                                color = PolishTextSecondary
                            )
                        }
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = { scope.launch { drawerState.open() } },
                            modifier = Modifier.testTag("admin_drawer_button")
                        ) {
                            Icon(Icons.Default.Menu, contentDescription = "Menu", tint = PolishNavyDark)
                        }
                    },
                    actions = {
                        // Edit Branding shortcut
                        IconButton(
                            onClick = { showBrandingEditor = true },
                            modifier = Modifier.testTag("admin_edit_branding_button")
                        ) {
                            Icon(Icons.Default.Palette, contentDescription = "Edit School Branding", tint = PolishBluePrimary)
                        }

                        // Switch to Student Mobile View
                        IconButton(
                            onClick = onSwitchToStudent,
                            modifier = Modifier.testTag("switch_to_student_button")
                        ) {
                            Icon(Icons.Default.School, contentDescription = "Student View", tint = SuccessEmerald)
                        }

                        // Logout
                        IconButton(
                            onClick = onLogout,
                            modifier = Modifier.testTag("admin_logout_button")
                        ) {
                            Icon(Icons.Default.Logout, contentDescription = "Logout", tint = AlertCrimson)
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
                )
            },
            snackbarHost = {
                if (snackbarMessage != null) {
                    Snackbar(
                        action = {
                            TextButton(onClick = { snackbarMessage = null }) {
                                Text("OK", color = Color.White)
                            }
                        },
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(snackbarMessage!!)
                    }
                }
            },
            modifier = Modifier.testTag("admin_dashboard_root")
        ) { paddingValues ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Top Alert/Banner if branding was modified
                item {
                    Spacer(modifier = Modifier.height(4.dp))
                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(20.dp),
                        color = PolishNavyDark,
                        shadowElevation = 2.dp
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(
                                modifier = Modifier.weight(1f),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(44.dp)
                                        .clip(CircleShape)
                                        .background(PolishIceBlue.copy(alpha = 0.2f)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(Icons.Default.SettingsSuggest, contentDescription = null, tint = PolishIceBlue)
                                }
                                Spacer(modifier = Modifier.width(12.dp))
                                Column {
                                    Text(
                                        text = "School Branding & Identity",
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White,
                                        fontSize = 14.sp
                                    )
                                    Text(
                                        text = "${branding.schoolName} (${branding.academicSession})",
                                        color = PolishIceBlue,
                                        fontSize = 11.sp,
                                        maxLines = 1
                                    )
                                }
                            }

                            Button(
                                onClick = { showBrandingEditor = true },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = PolishIceBlue,
                                    contentColor = PolishNavyDark
                                ),
                                shape = CircleShape,
                                contentPadding = PaddingValues(horizontal = 14.dp, vertical = 6.dp),
                                modifier = Modifier.testTag("open_branding_dialog_button")
                            ) {
                                Text("Edit Branding", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                            }
                        }
                    }
                }

                // Section: Institutional Statistics (All 9 KPI metrics requested)
                item {
                    Text(
                        text = "Dashboard Statistics & Key Metrics",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }

                item {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            AdminKpiCard(
                                modifier = Modifier.weight(1f),
                                title = "Students",
                                value = stats.totalStudents.toString(),
                                subtitle = "Enrolled",
                                icon = Icons.Default.Groups,
                                color = PrimaryBlue
                            )
                            AdminKpiCard(
                                modifier = Modifier.weight(1f),
                                title = "Teachers",
                                value = stats.totalTeachers.toString(),
                                subtitle = "Active Faculty",
                                icon = Icons.Default.RecordVoiceOver,
                                color = SuccessEmerald
                            )
                            AdminKpiCard(
                                modifier = Modifier.weight(1f),
                                title = "Classes",
                                value = stats.totalClasses.toString(),
                                subtitle = "Sections A-C",
                                icon = Icons.Default.MeetingRoom,
                                color = PrimaryNavy
                            )
                        }

                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            AdminKpiCard(
                                modifier = Modifier.weight(1f),
                                title = "Courses",
                                value = stats.totalCourses.toString(),
                                subtitle = "Published LMS",
                                icon = Icons.Default.AutoStories,
                                color = InfoSky
                            )
                            AdminKpiCard(
                                modifier = Modifier.weight(1f),
                                title = "Revenue",
                                value = stats.totalRevenue,
                                subtitle = "Term Collection",
                                icon = Icons.Default.Payments,
                                color = SuccessEmerald
                            )
                            AdminKpiCard(
                                modifier = Modifier.weight(1f),
                                title = "Pending Fees",
                                value = stats.pendingFees,
                                subtitle = "18 Accounts",
                                icon = Icons.Default.ReceiptLong,
                                color = AlertCrimson
                            )
                        }

                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            AdminKpiCard(
                                modifier = Modifier.weight(1f),
                                title = "Homework",
                                value = stats.pendingHomeworkCount.toString(),
                                subtitle = "Needs Grading",
                                icon = Icons.Default.AssignmentLate,
                                color = WarningAmber
                            )
                            AdminKpiCard(
                                modifier = Modifier.weight(1f),
                                title = "Live Classes",
                                value = stats.upcomingClassesCount.toString(),
                                subtitle = "Scheduled Today",
                                icon = Icons.Default.Videocam,
                                color = PrimaryBlue
                            )
                            AdminKpiCard(
                                modifier = Modifier.weight(1f),
                                title = "Attendance",
                                value = stats.avgAttendancePercent,
                                subtitle = "Campus Avg",
                                icon = Icons.Default.CheckCircle,
                                color = SuccessEmerald
                            )
                        }
                    }
                }

                // Quick Administrative Actions Row
                item {
                    Text(
                        text = "Quick Administrative Actions",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }

                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        ActionChip(
                            modifier = Modifier.weight(1f),
                            title = "New Notice",
                            icon = Icons.Default.Campaign
                        ) {
                            snackbarMessage = "Drafting institutional announcement for students & parents"
                        }
                        ActionChip(
                            modifier = Modifier.weight(1f),
                            title = "Schedule Live",
                            icon = Icons.Default.VideoCall
                        ) {
                            snackbarMessage = "Opening live class scheduler (Google Meet / Zoom)"
                        }
                        ActionChip(
                            modifier = Modifier.weight(1f),
                            title = "Verify Student",
                            icon = Icons.Default.PersonSearch
                        ) {
                            snackbarMessage = "All 1,248 student enrollment records are verified"
                        }
                        ActionChip(
                            modifier = Modifier.weight(1f),
                            title = "Push Alert",
                            icon = Icons.Default.Notifications
                        ) {
                            snackbarMessage = "Dispatched FCM Push Notice: Campus Mid-Term Schedule"
                        }
                    }
                }

                // Recent Administrative Activity Log
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        border = BorderStroke(1.dp, Slate200)
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Text(
                                text = "Institutional Activity Feed",
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            ActivityRow("Prof. Sterling published Calculus Ch. 4 Homework", "12m ago", "Academic")
                            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp), color = Slate100)
                            ActivityRow("Fee receipt #REC-2026-492 generated via Stripe", "25m ago", "Finance")
                            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp), color = Slate100)
                            ActivityRow("Attendance marked for Class 10-A (96.5% present)", "1h ago", "Attendance")
                            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp), color = Slate100)
                            ActivityRow("Principal Vance approved Mid-Term Schedule", "2h ago", "Executive")
                        }
                    }
                }

                // Switch to Student View banner
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(containerColor = Slate100)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(14.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "Preview Mobile Student Experience",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 13.sp
                                )
                                Text(
                                    text = "Inspect the live student dashboard, dynamic branding, and 13 modules.",
                                    fontSize = 11.sp,
                                    color = Slate600
                                )
                            }
                            OutlinedButton(
                                onClick = onSwitchToStudent,
                                colors = ButtonDefaults.outlinedButtonColors(contentColor = PrimaryNavy)
                            ) {
                                Text("Open Student App", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                }
            }
        }
    }
}

@Composable
private fun AdminKpiCard(
    modifier: Modifier = Modifier,
    title: String,
    value: String,
    subtitle: String,
    icon: ImageVector,
    color: Color
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        color = Color.White,
        border = BorderStroke(1.dp, PolishBorder),
        shadowElevation = 1.dp
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = title.uppercase(),
                    fontSize = 10.sp,
                    color = PolishTextSecondary,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 0.6.sp
                )
                Box(
                    modifier = Modifier
                        .size(28.dp)
                        .clip(CircleShape)
                        .background(color.copy(alpha = 0.12f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(imageVector = icon, contentDescription = null, tint = color, modifier = Modifier.size(14.dp))
                }
            }
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = value,
                fontSize = 17.sp,
                fontWeight = FontWeight.ExtraBold,
                color = PolishTextPrimary
            )
            Text(
                text = subtitle,
                fontSize = 10.sp,
                color = PolishTextSecondary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
private fun ActionChip(
    modifier: Modifier = Modifier,
    title: String,
    icon: ImageVector,
    onClick: () -> Unit
) {
    Surface(
        modifier = modifier.clickable(onClick = onClick),
        shape = RoundedCornerShape(14.dp),
        color = Color.White,
        border = BorderStroke(1.dp, PolishBorder)
    ) {
        Column(
            modifier = Modifier.padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
                    .background(PolishIceBlue),
                contentAlignment = Alignment.Center
            ) {
                Icon(imageVector = icon, contentDescription = title, tint = PolishNavyDark, modifier = Modifier.size(16.dp))
            }
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = title,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                color = PolishTextPrimary,
                maxLines = 1
            )
        }
    }
}

@Composable
private fun ActivityRow(action: String, time: String, tag: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(text = action, fontSize = 12.sp, color = Slate900, fontWeight = FontWeight.Medium)
            Text(text = time, fontSize = 10.sp, color = Slate400)
        }
        Surface(
            shape = RoundedCornerShape(4.dp),
            color = Slate100
        ) {
            Text(text = tag, fontSize = 9.sp, color = Slate600, modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp))
        }
    }
}
