package com.example

import com.example.data.models.SchoolBranding
import com.example.data.models.UserRole
import com.example.data.repository.AuthRepository
import com.example.data.repository.SchoolRepository
import com.example.ui.AppScreen
import com.example.ui.MainViewModel
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class SchoolPlatformPhase1Test {

    private lateinit var schoolRepo: SchoolRepository
    private lateinit var authRepo: AuthRepository
    private lateinit var viewModel: MainViewModel

    @Before
    fun setup() {
        schoolRepo = SchoolRepository()
        authRepo = AuthRepository()
        viewModel = MainViewModel(schoolRepo, authRepo)
    }

    @Test
    fun testInitialBrandingIsLoaded() {
        val branding = viewModel.branding.value
        assertEquals("Greenwood International Academy", branding.schoolName)
        assertEquals("2026-2027", branding.academicSession)
        assertTrue(branding.tagline.isNotBlank())
    }

    @Test
    fun testDynamicSchoolBrandingUpdate() {
        val newBranding = SchoolBranding(
            schoolName = "Metropolis Collegiate Institute",
            tagline = "Empowering Tomorrow's Thinkers",
            principalName = "Dr. Marcus Sterling",
            academicSession = "2027-2028"
        )
        viewModel.updateSchoolBranding(newBranding)

        val updated = viewModel.branding.value
        assertEquals("Metropolis Collegiate Institute", updated.schoolName)
        assertEquals("Empowering Tomorrow's Thinkers", updated.tagline)
        assertEquals("Dr. Marcus Sterling", updated.principalName)
        assertEquals("2027-2028", updated.academicSession)
    }

    @Test
    fun testStudentRegistrationCapturesAllFields() {
        viewModel.registerStudent(
            studentName = "Elena Rostova",
            gmail = "elena.rostova@gmail.com",
            mobileNumber = "+1 (555) 777-8899",
            whatsappNumber = "+1 (555) 777-8899",
            dateOfBirth = "2011-03-15",
            gender = "Female",
            studentClass = "Class 11",
            section = "C",
            rollNumber = "07",
            parentGuardianName = "Viktor Rostova",
            parentMobile = "+1 (555) 333-2211",
            address = "904 Skyline Tower, Metropolis"
        )

        val profile = viewModel.studentProfile.value
        assertEquals("Elena Rostova", profile.studentName)
        assertEquals("elena.rostova@gmail.com", profile.gmail)
        assertEquals("Class 11", profile.studentClass)
        assertEquals("C", profile.section)
        assertEquals("07", profile.rollNumber)
        assertEquals("Viktor Rostova", profile.parentGuardianName)
        assertEquals("+1 (555) 333-2211", profile.parentMobile)
        assertEquals("904 Skyline Tower, Metropolis", profile.address)
        assertTrue(profile.digitalIdCode.contains("Class11C-07"))
        assertEquals(AppScreen.STUDENT_HOME, viewModel.currentScreen.value)
    }

    @Test
    fun testRoleSwitchingToAdminAndStudent() {
        viewModel.switchToAdmin()
        assertEquals(AppScreen.ADMIN_DASHBOARD, viewModel.currentScreen.value)
        assertEquals(UserRole.SCHOOL_ADMIN, viewModel.currentUser.value?.role)

        viewModel.switchToStudent()
        assertEquals(AppScreen.STUDENT_HOME, viewModel.currentScreen.value)
        assertEquals(UserRole.STUDENT, viewModel.currentUser.value?.role)
    }
}
