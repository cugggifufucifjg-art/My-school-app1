package com.example.data.models

import com.example.R

/**
 * Model representing dynamic School Branding parameters.
 * These are configured by the School Admin and reactively broadcasted
 * to all mobile clients, splash screens, and headers.
 */
data class SchoolBranding(
    val schoolName: String = "Greenwood International Academy",
    val tagline: String = "Inspiring Excellence, Fostering Innovation",
    val logoDrawableRes: Int = R.drawable.img_app_icon,
    val bannerDrawableRes: Int = R.drawable.img_school_banner,
    val logoUrl: String = "",
    val bannerUrl: String = "",
    val schoolAddress: String = "450 Academic Boulevard, Metropolis, NY 10024",
    val phone: String = "+1 (555) 234-5678",
    val email: String = "admissions@greenwoodacademy.edu",
    val website: String = "https://www.greenwoodacademy.edu",
    val whatsappNumber: String = "+15552345678",
    val principalName: String = "Dr. Eleanor Vance, Ph.D.",
    val academicSession: String = "2026-2027",
    val welcomeMessage: String = "Welcome to Greenwood International Academy. Where future leaders are nurtured with world-class curriculum and character education.",
    val aboutSchool: String = "Greenwood International Academy was established to provide holistic education integrating modern STEM curricula, arts, and athletic excellence.",
    val socialFacebook: String = "https://facebook.com/greenwoodacademy",
    val socialInstagram: String = "https://instagram.com/greenwoodacademy",
    val socialYoutube: String = "https://youtube.com/@greenwoodacademy",
    val socialTwitter: String = "https://x.com/greenwoodacademy",
    val socialLinkedin: String = "https://linkedin.com/school/greenwoodacademy"
)
