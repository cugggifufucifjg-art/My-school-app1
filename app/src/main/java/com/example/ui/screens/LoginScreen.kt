package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.models.SchoolBranding
import com.example.data.models.UserRole
import com.example.ui.components.StudentRegistrationDialog
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    branding: SchoolBranding,
    onGoogleSignIn: () -> Unit,
    onEmailSignIn: (email: String, role: UserRole) -> Unit,
    onRegisterStudent: (
        name: String,
        gmail: String,
        mobile: String,
        whatsapp: String,
        dob: String,
        gender: String,
        studentClass: String,
        section: String,
        rollNo: String,
        parentName: String,
        parentMobile: String,
        address: String
    ) -> Unit
) {
    var email by remember { mutableStateOf("alex.hayes@gmail.com") }
    var password by remember { mutableStateOf("••••••••") }
    var selectedRole by remember { mutableStateOf(UserRole.STUDENT) }
    var showRegistrationDialog by remember { mutableStateOf(false) }
    var showForgotPasswordDialog by remember { mutableStateOf(false) }
    var forgotEmail by remember { mutableStateOf("") }
    var forgotStatusMessage by remember { mutableStateOf<String?>(null) }

    if (showRegistrationDialog) {
        StudentRegistrationDialog(
            onDismiss = { showRegistrationDialog = false },
            onRegister = { name, gmail, mobile, whatsapp, dob, gender, sClass, sec, roll, pName, pMobile, addr ->
                showRegistrationDialog = false
                onRegisterStudent(name, gmail, mobile, whatsapp, dob, gender, sClass, sec, roll, pName, pMobile, addr)
            }
        )
    }

    if (showForgotPasswordDialog) {
        AlertDialog(
            onDismissRequest = { showForgotPasswordDialog = false },
            title = { Text("Reset Password") },
            text = {
                Column {
                    Text("Enter your registered school email address to receive password recovery instructions.")
                    Spacer(modifier = Modifier.height(12.dp))
                    OutlinedTextField(
                        value = forgotEmail,
                        onValueChange = { forgotEmail = it },
                        label = { Text("Email address") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )
                    if (forgotStatusMessage != null) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(forgotStatusMessage!!, color = SuccessEmerald, fontSize = 12.sp)
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        forgotStatusMessage = "Password reset instructions sent to $forgotEmail"
                    }
                ) {
                    Text("Send Recovery Link")
                }
            },
            dismissButton = {
                TextButton(onClick = { showForgotPasswordDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }

    Scaffold(
        containerColor = PolishBackground,
        modifier = Modifier
            .fillMaxSize()
            .testTag("login_screen_root")
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp, vertical = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            // School Logo and Dynamic Branding header
            Box(
                modifier = Modifier
                    .size(68.dp)
                    .clip(RoundedCornerShape(18.dp))
                    .background(Color.White)
                    .border(1.5.dp, PolishBorderLight, RoundedCornerShape(18.dp))
                    .padding(6.dp),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = branding.logoDrawableRes),
                    contentDescription = "School Logo",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = branding.schoolName.uppercase(),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = PolishBluePrimary,
                letterSpacing = 0.8.sp
            )

            Text(
                text = branding.tagline,
                style = MaterialTheme.typography.bodySmall,
                color = PolishTextSecondary,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(28.dp))

            // Role Selector Chips
            Text(
                text = "SELECT PORTAL ROLE",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 0.8.sp,
                color = PolishTextTertiary,
                modifier = Modifier.align(Alignment.Start)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                listOf(UserRole.STUDENT, UserRole.SCHOOL_ADMIN, UserRole.TEACHER, UserRole.PARENT).forEach { role ->
                    FilterChip(
                        selected = selectedRole == role,
                        onClick = { selectedRole = role },
                        label = { Text(role.displayName, fontSize = 12.sp) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = PolishIceBlue,
                            selectedLabelColor = PolishNavyDark
                        ),
                        border = FilterChipDefaults.filterChipBorder(
                            enabled = true,
                            selected = selectedRole == role,
                            borderColor = if (selectedRole == role) PolishBluePrimary else PolishBorder
                        ),
                        modifier = Modifier.testTag("role_chip_${role.name.lowercase()}")
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Google Sign-In Primary Action
            OutlinedButton(
                onClick = onGoogleSignIn,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .testTag("google_signin_button"),
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(1.dp, PolishBorder),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = PolishTextPrimary)
            ) {
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = "Google Sign In",
                    tint = PolishBluePrimary,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = "Sign in with Google / Gmail",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                HorizontalDivider(modifier = Modifier.weight(1f), color = PolishBorderLight)
                Text(
                    text = "  OR EMAIL LOGIN  ",
                    fontSize = 11.sp,
                    color = PolishTextSecondary,
                    fontWeight = FontWeight.Bold
                )
                HorizontalDivider(modifier = Modifier.weight(1f), color = PolishBorderLight)
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Email input
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email Address") },
                leadingIcon = { Icon(Icons.Default.Email, contentDescription = null, tint = PolishBluePrimary) },
                singleLine = true,
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("login_email_input")
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Password input
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null, tint = PolishBluePrimary) },
                visualTransformation = PasswordVisualTransformation(),
                singleLine = true,
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("login_password_input")
            )

            // Forgot Password button
            Box(modifier = Modifier.fillMaxWidth()) {
                TextButton(
                    onClick = {
                        forgotEmail = email
                        showForgotPasswordDialog = true
                    },
                    modifier = Modifier.align(Alignment.CenterEnd)
                ) {
                    Text(
                        text = "Forgot password?",
                        fontSize = 12.sp,
                        color = PolishBluePrimary,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Sign In Action
            Button(
                onClick = { onEmailSignIn(email, selectedRole) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .testTag("login_submit_button"),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = PolishNavyDark)
            ) {
                Text(
                    text = "Login as ${selectedRole.displayName}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // First-time Student Registration Prompt
            Surface(
                shape = RoundedCornerShape(20.dp),
                color = CategoryHomeworkBg,
                border = BorderStroke(1.dp, CategoryHomeworkText.copy(alpha = 0.2f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "New Student Enrollment?",
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp,
                        color = CategoryHomeworkText
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Complete your 13-field student academic registration profile to obtain your digital ID.",
                        fontSize = 11.sp,
                        color = PolishTextSecondary,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    OutlinedButton(
                        onClick = { showRegistrationDialog = true },
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = CategoryHomeworkText),
                        shape = CircleShape,
                        border = BorderStroke(1.dp, CategoryHomeworkText),
                        modifier = Modifier.testTag("register_student_prompt_button")
                    ) {
                        Icon(Icons.Default.PersonAdd, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Register Student Account", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Switch to Admin Portal directly
            TextButton(
                onClick = {
                    onEmailSignIn("admin@greenwoodacademy.edu", UserRole.SCHOOL_ADMIN)
                },
                modifier = Modifier.testTag("direct_admin_portal_button")
            ) {
                Icon(Icons.Default.AdminPanelSettings, contentDescription = null, tint = PolishBluePrimary, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text("Direct Admin & Management Portal", fontWeight = FontWeight.SemiBold, fontSize = 13.sp, color = PolishBluePrimary)
            }
        }
    }
}
