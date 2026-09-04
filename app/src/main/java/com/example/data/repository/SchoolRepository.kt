package com.example.data.repository

import com.example.data.models.SchoolBranding
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Repository managing dynamic School Branding and Institutional profile.
 * When the Admin updates branding parameters, all observers (Splash screen,
 * Student Dashboard, Digital ID Card, headers) reflect changes instantly.
 */
class SchoolRepository {
    private val _branding = MutableStateFlow(SchoolBranding())
    val branding: StateFlow<SchoolBranding> = _branding.asStateFlow()

    fun updateBranding(updated: SchoolBranding) {
        _branding.value = updated
    }

    fun updateBrandingFields(
        schoolName: String,
        tagline: String,
        principalName: String,
        academicSession: String,
        phone: String,
        email: String,
        website: String,
        whatsappNumber: String,
        address: String,
        welcomeMessage: String,
        aboutSchool: String
    ) {
        val current = _branding.value
        _branding.value = current.copy(
            schoolName = schoolName,
            tagline = tagline,
            principalName = principalName,
            academicSession = academicSession,
            phone = phone,
            email = email,
            website = website,
            whatsappNumber = whatsappNumber,
            schoolAddress = address,
            welcomeMessage = welcomeMessage,
            aboutSchool = aboutSchool
        )
    }

    fun resetToDefault() {
        _branding.value = SchoolBranding()
    }
}
