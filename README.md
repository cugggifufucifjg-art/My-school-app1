# EduManage - School & Education Management Platform

A modern, production-ready School & Education Management Platform and Learning Management System (LMS) built with Kotlin and Jetpack Compose, architected for Firebase backend, multi-school isolation, dynamic branding, and granular Role-Based Access Control (RBAC).

---

## Key Features in Phase 1

1. **Dynamic School Branding**:
   - The school name, logo, banner, and details are **never hardcoded**.
   - Admin can update 13+ branding and institutional parameters from **Admin Panel -> Settings -> School Branding**.
   - Changes propagate dynamically in real time to the Splash Screen, Student Dashboard, ID Card, and headers.
2. **Splash Screen**:
   - Modern brand entrance displaying dynamic School Logo, Name, and Tagline.
3. **Authentication & Student Registration**:
   - Google Sign-In & Email/Password workflows.
   - 13-field complete student registration profile (Name, Gmail, Photo, Mobile, WhatsApp, DOB, Gender, Class, Section, Roll Number, Parent Name, Parent Mobile, Address).
   - Session persistence and role authorization.
4. **Student Home Dashboard**:
   - Personalized greeting ("Good Morning, [Student Name] 👋").
   - Live metrics bar: Attendance %, Course Progress %, Pending Homework, Recent Grade.
   - 13 functional modules (My Courses, Live Classes, Homework, Study Material, Tests & Quizzes, Results, Attendance, Timetable, Fees, Announcements, Events, Ask Teacher, Achievements).
   - Upcoming live class with 1-tap "JOIN CLASS" integration.
   - Pending homework task countdown.
   - Interactive Digital ID Card preview.
5. **Admin Panel & Dashboard**:
   - Institutional KPI counters (Total Students, Teachers, Classes, Courses, Revenue, Pending Fees, Homework to Grade, Upcoming Classes, Attendance Rate).
   - Dynamic School Branding Editor with instant broadcast to mobile clients.
   - Quick action shortcuts (Schedule Class, Add Student, Publish Notice, Push Notification).
   - Navigation drawer with 24+ administrative domains.
   - Multi-role switcher for rapid evaluation between Admin, Teacher, Student, and Parent experiences.

---

## Project Structure

```
app/src/main/java/com/example/
├── data/
│   ├── models/
│   │   ├── SchoolBranding.kt       # School identity, contact, social links
│   │   ├── UserModels.kt           # User, StudentProfile, Role, Permissions
│   │   ├── AcademicModels.kt       # Homework, Courses, LiveClasses, etc.
│   │   └── AdminDashboardStats.kt  # Institutional KPI metrics
│   └── repository/
│       ├── SchoolRepository.kt     # Reactive dynamic branding & persistence
│       └── AuthRepository.kt       # Authentication, session, and role state
├── ui/
│   ├── theme/
│   │   ├── Color.kt                # Premium academic palette (Navy, Indigo, Amber)
│   │   ├── Theme.kt                # Material Design 3 dynamic & custom theme
│   │   └── Type.kt                 # Accessible typography scale
│   ├── components/
│   │   ├── AppTopBar.kt            # Consistent header with school identity
│   │   ├── DigitalIdCardDialog.kt  # Digital student ID card with QR & branding
│   │   └── MetricCard.kt           # Modern scannable KPI cards
│   ├── screens/
│   │   ├── SplashScreen.kt         # Dynamic school logo, name, tagline
│   │   ├── LoginScreen.kt          # Google & Email auth + Student registration
│   │   ├── StudentHomeScreen.kt    # 13-card student learning dashboard
│   │   └── AdminDashboardScreen.kt # Admin management, KPIs, and Branding Editor
│   └── MainViewModel.kt            # Unified MVVM state coordinator
└── MainActivity.kt                 # Edge-to-edge entry point and navigation host
```

---

## Firebase & Backend Configuration

To connect this application to your live Firebase project:

1. **Firebase Console Setup**:
   - Create a project at [Firebase Console](https://console.firebase.google.com).
   - Enable **Authentication** (Email/Password & Google Sign-In providers).
   - Enable **Cloud Firestore** and deploy `firestore.rules` provided in this repository.
   - Enable **Firebase Storage** and deploy `storage.rules`.
2. **Download Credentials**:
   - Add an Android app with package name `com.aistudio.edumanage.sklmgt`.
   - Download `google-services.json` and place it in the `/app` folder.
3. **Environment Secrets**:
   - In Google AI Studio Secrets panel, configure:
     - `FIREBASE_PROJECT_ID`: Your Google Cloud project ID.
     - `WHATSAPP_API_TOKEN`: Meta WhatsApp Cloud API access token.
     - `RAZORPAY_KEY_ID`: Payment gateway key.

---

## Build & Run Instructions

### Android APK Build
```bash
# Debug build and test execution
gradle :app:assembleDebug
gradle :app:testDebugUnitTest

# Release APK build
gradle :app:assembleRelease
```

### iOS Setup (Flutter/React Native Multiplatform Reference)
The project architecture and database schema in `ARCHITECTURE.md` and `FIREBASE_SCHEMA.md` are 100% standard and ready for multiplatform targets (Flutter / iOS) sharing the identical Firestore subcollection hierarchy and security rules.
