# School & Education Management Platform - System Architecture

## 1. High-Level Architecture Overview

The platform is designed as an enterprise-grade, multi-tenant Education ERP and Learning Management System (LMS). It serves multiple user personas across mobile and web interfaces, backed by Google Cloud and Firebase infrastructure.

```
+-----------------------------------------------------------------------------------+
|                                 CLIENT CLIENTS                                    |
|                                                                                   |
|  +-------------------------------+   +-----------------------------------------+  |
|  |   Student / Parent App        |   |   Teacher / Admin Responsive Web Panel  |  |
|  |   (Android / iOS / Tablets)   |   |   (Desktop, Tablet, Mobile Web)         |  |
|  +-------------------------------+   +-----------------------------------------+  |
+------------------------------------------+----------------------------------------+
                                           | HTTPS / WSS / gRPC
                                           v
+-----------------------------------------------------------------------------------+
|                           AUTHENTICATION & API GATEWAY                            |
|                                                                                   |
|  * Firebase Authentication (Email/Password, Google OAuth, Phone OTP)              |
|  * Custom JWT Claims (tenantId/schoolId, role, permissions, assignedClasses)       |
|  * App Check (SafetyNet / Play Integrity / reCAPTCHA Enterprise)                   |
+------------------------------------------+----------------------------------------+
                                           |
                                           v
+-----------------------------------------------------------------------------------+
|                        CLOUD FUNCTIONS & BUSINESS SERVICES                        |
|                                                                                   |
|  +-------------------------+  +--------------------------+  +------------------+  |
|  | Payment Gateway Service |  | WhatsApp Cloud API Svc   |  | FCM Notification |  |
|  | (Stripe / Razorpay)     |  | (Webhook & Reminders)    |  | Dispatcher       |  |
|  +-------------------------+  +--------------------------+  +------------------+  |
|  +-------------------------+  +--------------------------+  +------------------+  |
|  | Academic Analytics      |  | Homework & Grading Svc   |  | Video & Content  |  |
|  | & Report Card Generator |  | (Late check, audit log)  |  | Streaming (CDN)  |  |
|  +-------------------------+  +--------------------------+  +------------------+  |
+------------------------------------------+----------------------------------------+
                                           |
                                           v
+-----------------------------------------------------------------------------------+
|                           DATABASE & STORAGE LAYER                                |
|                                                                                   |
|  +---------------------------------------+  +----------------------------------+  |
|  | Cloud Firestore (Multi-Tenant Schemas)|  | Firebase Cloud Storage           |  |
|  | - Isolated by schoolId                |  | - Homework files, PDFs, E-books  |  |
|  | - Granular RBAC Security Rules        |  | - School Logos, Banners, Photos  |  |
|  +---------------------------------------+  +----------------------------------+  |
+-----------------------------------------------------------------------------------+
```

---

## 2. Multi-Tenant Architecture (Multi-School Isolation)

The database schema supports multi-school multi-tenancy. Every tenant data element belongs to a distinct `schoolId`.

### Isolation Strategies:
1. **Root-Level Tenancy with Subcollections**:
   - `schools/{schoolId}` contains metadata, branding, academic settings, and configurations.
   - Core operational data is nested under `schools/{schoolId}/...` (classes, homework, tests, fees, etc.) to guarantee zero-leakage security boundaries via Firestore rules.
2. **User Directory**:
   - Global `users/{userId}` documents store identity, tenant associations (`schools: [{ schoolId, role, status }]`), and profile baselines.
   - School-specific profiles exist in `schools/{schoolId}/students/{studentId}` and `schools/{schoolId}/teachers/{teacherId}`.
3. **Custom Claims Token Encasement**:
   ```json
   {
     "uid": "usr_948271049",
     "schoolId": "sch_greenwood_high",
     "role": "STUDENT",
     "classId": "cls_10",
     "sectionId": "sec_A",
     "permissions": ["view_courses", "submit_homework", "view_results"]
   }
   ```

---

## 3. User Roles & Permission Matrix (RBAC)

| Role | Scope | Key Permissions | Restrictions |
| :--- | :--- | :--- | :--- |
| **Super Admin** | Global Platform | Create/deactivate schools, platform subscriptions, global audits | No direct alteration of classroom academic records without audit |
| **School Admin** | Single School | Full access to school branding, classes, teachers, students, fees, settings | Restricted to own `schoolId` |
| **Principal** | Single School | Academic oversight, teacher evaluation, school analytics, approvals | Cannot alter financial ledger without accounting clearance |
| **Teacher** | Assigned Classes | Create homework, grade submissions, mark attendance, launch live class, post study material | Restricted strictly to assigned Class + Section + Subject |
| **Accountant** | Single School | Fee structures, invoice generation, payment reconciliations, receipts | Read-only for academic grades |
| **Content Mgr** | Single School | Upload e-books, course videos, study materials | Cannot access student personal records |
| **Student** | Own Profile & Class | View homework, submit solutions, join live classes, take quizzes, view fees/results | Isolated to own class/section; read-only for other students |
| **Parent** | Linked Children | View attendance, progress, report cards, fee dues, notices for linked students | Cannot modify submissions; zero access to other families |

---

## 4. WhatsApp Business Cloud API Architecture

To facilitate automated homework reminders, attendance notices, and fee receipts without violating WhatsApp policies:
1. **Opt-in / Consent Registry**: Stored in `students/{studentId}/whatsappConsent: boolean`.
2. **Approved Meta Template Messages**:
   - `homework_published_v1`: Parameters: `{{1}}` School Name, `{{2}}` Student Name, `{{3}}` Class, `{{4}}` Subject, `{{5}}` Due Date, `{{6}}` Deep Link.
   - `fee_reminder_v1`: Parameters: School Name, Parent Name, Due Amount, Due Date, Payment Link.
3. **Trigger Pipeline**:
   - Teacher clicks "Publish Homework" -> Firestore Document written -> Cloud Function triggers -> Dispatches HTTPS POST to Meta Graph API `https://graph.facebook.com/v19.0/{phone-number-id}/messages`.
   - Webhook listener updates message delivery status (`SENT`, `DELIVERED`, `READ`).

---

## 5. Payment Gateway Architecture (Razorpay & Stripe)

1. **Order Creation**: Client requests fee payment -> Cloud Function creates order on Payment Gateway with `orderId` and signature token.
2. **Client Checkout**: Mobile app displays native SDK sheet.
3. **Backend Verification (Never trust client)**:
   - Client sends `paymentId`, `orderId`, and `signature`.
   - Cloud Function cryptographically verifies HMAC-SHA256 signature using webhook secret.
   - Cloud Function atomically updates Firestore transaction: sets fee status to `PAID`, generates formal sequential receipt number, and emits receipt PDF to Storage.

---

## 6. Real-Time School Dynamic Branding

1. The Admin edits branding in **Admin Panel -> Settings -> School Branding**.
2. Updates write directly to `schools/{schoolId}/settings/branding`.
3. The Mobile App listens via Firestore Real-Time Snapshot Listener / Kotlin StateFlow.
4. Changes to logo, banner, school title, tagline, or contact info reflect immediately on the student dashboard, splash screen, and digital ID card without requiring an app update or restart.
