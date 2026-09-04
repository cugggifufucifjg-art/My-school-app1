package com.example.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.data.models.SchoolBranding
import com.example.ui.theme.PrimaryNavy

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SchoolBrandingEditorDialog(
    currentBranding: SchoolBranding,
    onDismiss: () -> Unit,
    onSave: (SchoolBranding) -> Unit
) {
    var schoolName by remember { mutableStateOf(currentBranding.schoolName) }
    var tagline by remember { mutableStateOf(currentBranding.tagline) }
    var principalName by remember { mutableStateOf(currentBranding.principalName) }
    var academicSession by remember { mutableStateOf(currentBranding.academicSession) }
    var phone by remember { mutableStateOf(currentBranding.phone) }
    var email by remember { mutableStateOf(currentBranding.email) }
    var website by remember { mutableStateOf(currentBranding.website) }
    var whatsappNumber by remember { mutableStateOf(currentBranding.whatsappNumber) }
    var schoolAddress by remember { mutableStateOf(currentBranding.schoolAddress) }
    var welcomeMessage by remember { mutableStateOf(currentBranding.welcomeMessage) }
    var aboutSchool by remember { mutableStateOf(currentBranding.aboutSchool) }

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
                        Text("Settings → School Branding", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    },
                    navigationIcon = {
                        IconButton(onClick = onDismiss) {
                            Icon(Icons.Default.Close, contentDescription = "Close")
                        }
                    },
                    actions = {
                        Button(
                            onClick = {
                                onSave(
                                    currentBranding.copy(
                                        schoolName = schoolName,
                                        tagline = tagline,
                                        principalName = principalName,
                                        academicSession = academicSession,
                                        phone = phone,
                                        email = email,
                                        website = website,
                                        whatsappNumber = whatsappNumber,
                                        schoolAddress = schoolAddress,
                                        welcomeMessage = welcomeMessage,
                                        aboutSchool = aboutSchool
                                    )
                                )
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = PrimaryNavy),
                            modifier = Modifier.testTag("save_branding_button")
                        ) {
                            Icon(Icons.Default.Save, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Save & Broadcast")
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.surface)
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
                        Icon(Icons.Default.School, contentDescription = null, tint = PrimaryNavy)
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "Modifications made here will dynamically update the mobile app header, splash screen, and digital student ID cards in real time.",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                }

                Text("Primary Identity", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)

                OutlinedTextField(
                    value = schoolName,
                    onValueChange = { schoolName = it },
                    label = { Text("School Name *") },
                    modifier = Modifier.fillMaxWidth().testTag("branding_name_input"),
                    singleLine = true
                )

                OutlinedTextField(
                    value = tagline,
                    onValueChange = { tagline = it },
                    label = { Text("Tagline / Motto *") },
                    modifier = Modifier.fillMaxWidth().testTag("branding_tagline_input"),
                    singleLine = true
                )

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    OutlinedTextField(
                        value = academicSession,
                        onValueChange = { academicSession = it },
                        label = { Text("Academic Session *") },
                        modifier = Modifier.weight(1f).testTag("branding_session_input"),
                        singleLine = true
                    )
                    OutlinedTextField(
                        value = principalName,
                        onValueChange = { principalName = it },
                        label = { Text("Principal Name") },
                        modifier = Modifier.weight(1f),
                        singleLine = true
                    )
                }

                Text("Contact & Communication", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    OutlinedTextField(
                        value = phone,
                        onValueChange = { phone = it },
                        label = { Text("Phone") },
                        modifier = Modifier.weight(1f),
                        singleLine = true
                    )
                    OutlinedTextField(
                        value = whatsappNumber,
                        onValueChange = { whatsappNumber = it },
                        label = { Text("WhatsApp Number") },
                        modifier = Modifier.weight(1f),
                        singleLine = true
                    )
                }

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text("Official Email") },
                        modifier = Modifier.weight(1f),
                        singleLine = true
                    )
                    OutlinedTextField(
                        value = website,
                        onValueChange = { website = it },
                        label = { Text("Website URL") },
                        modifier = Modifier.weight(1f),
                        singleLine = true
                    )
                }

                OutlinedTextField(
                    value = schoolAddress,
                    onValueChange = { schoolAddress = it },
                    label = { Text("Campus Address") },
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 2
                )

                Text("Institutional Description", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)

                OutlinedTextField(
                    value = welcomeMessage,
                    onValueChange = { welcomeMessage = it },
                    label = { Text("Welcome Message") },
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 3
                )

                OutlinedTextField(
                    value = aboutSchool,
                    onValueChange = { aboutSchool = it },
                    label = { Text("About School") },
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 4
                )

                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
}
