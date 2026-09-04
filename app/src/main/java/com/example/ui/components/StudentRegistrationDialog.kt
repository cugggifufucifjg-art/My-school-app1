package com.example.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.ui.theme.PrimaryNavy

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudentRegistrationDialog(
    onDismiss: () -> Unit,
    onRegister: (
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
    var studentName by remember { mutableStateOf("Sophia Reynolds") }
    var gmail by remember { mutableStateOf("sophia.reynolds@gmail.com") }
    var mobile by remember { mutableStateOf("+1 (555) 782-9012") }
    var whatsapp by remember { mutableStateOf("+1 (555) 782-9012") }
    var dob by remember { mutableStateOf("2011-08-22") }
    var gender by remember { mutableStateOf("Female") }
    var studentClass by remember { mutableStateOf("Class 10") }
    var section by remember { mutableStateOf("B") }
    var rollNumber by remember { mutableStateOf("24") }
    var parentName by remember { mutableStateOf("David Reynolds") }
    var parentMobile by remember { mutableStateOf("+1 (555) 431-8890") }
    var address by remember { mutableStateOf("128 Oakridge Heights, Metropolis") }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            topBar = {
                TopAppBar(
                    title = {
                        Text("First-Time Student Registration", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    },
                    navigationIcon = {
                        IconButton(onClick = onDismiss) {
                            Icon(Icons.Default.Close, contentDescription = "Close")
                        }
                    },
                    actions = {
                        Button(
                            onClick = {
                                if (studentName.isNotBlank() && gmail.isNotBlank()) {
                                    onRegister(
                                        studentName,
                                        gmail,
                                        mobile,
                                        whatsapp,
                                        dob,
                                        gender,
                                        studentClass,
                                        section,
                                        rollNumber,
                                        parentName,
                                        parentMobile,
                                        address
                                    )
                                }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = PrimaryNavy),
                            modifier = Modifier.testTag("submit_registration_button")
                        ) {
                            Icon(Icons.Default.PersonAdd, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Complete Profile")
                        }
                    }
                )
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                Card(
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.AccountCircle, contentDescription = null, tint = PrimaryNavy)
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "Please verify your personal and academic details. This data is linked to your digital ID card, grades, and parent notifications.",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                }

                Text("Student Personal Information", fontWeight = FontWeight.Bold)

                OutlinedTextField(
                    value = studentName,
                    onValueChange = { studentName = it },
                    label = { Text("Full Student Name *") },
                    modifier = Modifier.fillMaxWidth().testTag("reg_student_name_input"),
                    singleLine = true
                )

                OutlinedTextField(
                    value = gmail,
                    onValueChange = { gmail = it },
                    label = { Text("Gmail / Primary Email *") },
                    modifier = Modifier.fillMaxWidth().testTag("reg_student_gmail_input"),
                    singleLine = true
                )

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    OutlinedTextField(
                        value = mobile,
                        onValueChange = { mobile = it },
                        label = { Text("Mobile Number *") },
                        modifier = Modifier.weight(1f),
                        singleLine = true
                    )
                    OutlinedTextField(
                        value = whatsapp,
                        onValueChange = { whatsapp = it },
                        label = { Text("WhatsApp Number") },
                        modifier = Modifier.weight(1f),
                        singleLine = true
                    )
                }

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    OutlinedTextField(
                        value = dob,
                        onValueChange = { dob = it },
                        label = { Text("Date of Birth (YYYY-MM-DD)") },
                        modifier = Modifier.weight(1f),
                        singleLine = true
                    )
                    OutlinedTextField(
                        value = gender,
                        onValueChange = { gender = it },
                        label = { Text("Gender") },
                        modifier = Modifier.weight(1f),
                        singleLine = true
                    )
                }

                Text("Academic Enrollment", fontWeight = FontWeight.Bold)

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    OutlinedTextField(
                        value = studentClass,
                        onValueChange = { studentClass = it },
                        label = { Text("Class (e.g. Class 10)") },
                        modifier = Modifier.weight(1.2f),
                        singleLine = true
                    )
                    OutlinedTextField(
                        value = section,
                        onValueChange = { section = it },
                        label = { Text("Section (e.g. A)") },
                        modifier = Modifier.weight(0.8f),
                        singleLine = true
                    )
                    OutlinedTextField(
                        value = rollNumber,
                        onValueChange = { rollNumber = it },
                        label = { Text("Roll No") },
                        modifier = Modifier.weight(0.8f),
                        singleLine = true
                    )
                }

                Text("Parent & Guardian Contact", fontWeight = FontWeight.Bold)

                OutlinedTextField(
                    value = parentName,
                    onValueChange = { parentName = it },
                    label = { Text("Parent / Guardian Name *") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                OutlinedTextField(
                    value = parentMobile,
                    onValueChange = { parentMobile = it },
                    label = { Text("Parent Mobile Number *") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                OutlinedTextField(
                    value = address,
                    onValueChange = { address = it },
                    label = { Text("Residential Address") },
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 2
                )

                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}
