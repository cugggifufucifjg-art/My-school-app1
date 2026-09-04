# Cloud Firestore Complete Schema Specification

## Root Collections Overview

Multi-tenant root structure:
- `/schools/{schoolId}` (School root document & operational subcollections)
- `/users/{userId}` (Global auth directory & cross-tenant mappings)
- `/platformSettings` (Super admin global parameters)

---

## 1. `schools/{schoolId}`
```json
{
  "id": "sch_greenwood_2026",
  "name": "Greenwood International Academy",
  "tagline": "Inspiring Excellence, Fostering Innovation",
  "logoUrl": "https://storage.googleapis.com/.../logo.png",
  "bannerUrl": "https://storage.googleapis.com/.../banner.jpg",
  "address": "450 Academic Boulevard, Suite 100, Metropolis",
  "phone": "+1 (555) 234-5678",
  "email": "info@greenwoodacademy.edu",
  "website": "https://www.greenwoodacademy.edu",
  "whatsappNumber": "+15552345678",
  "principalName": "Dr. Eleanor Vance, Ph.D.",
  "academicSession": "2026-2027",
  "welcomeMessage": "Welcome to Greenwood International Academy. Where future leaders are nurtured.",
  "aboutSchool": "Founded in 1998, Greenwood offers cutting-edge STEM and humanities curricula with state-of-the-art facilities.",
  "socialLinks": {
    "facebook": "https://facebook.com/greenwood",
    "instagram": "https://instagram.com/greenwood",
    "youtube": "https://youtube.com/@greenwood",
    "twitter": "https://x.com/greenwood",
    "linkedin": "https://linkedin.com/school/greenwood"
  },
  "isActive": true,
  "createdAt": "2026-01-01T00:00:00Z",
  "updatedAt": "2026-09-04T06:00:00Z"
}
```

---

## 2. `schools/{schoolId}/academicSessions/{sessionId}`
- Fields: `id`, `name` ("2026-2027"), `startDate`, `endDate`, `isCurrent` (boolean).

## 3. `schools/{schoolId}/classes/{classId}`
- Fields: `id`, `name` ("Class 10"), `numericOrder` (10), `sectionIds` (["sec_A", "sec_B"]), `createdAt`.

## 4. `schools/{schoolId}/sections/{sectionId}`
- Fields: `id`, `classId`, `name` ("Section A"), `classTeacherId`, `maxCapacity` (40), `roomNumber` ("Room 302").

## 5. `schools/{schoolId}/subjects/{subjectId}`
- Fields: `id`, `name` ("Advanced Mathematics"), `code` ("MATH-10"), `iconName` ("calculate"), `colorHex` ("#3B82F6"), `teacherIds` (["tch_01", "tch_04"]).

## 6. `schools/{schoolId}/teachers/{teacherId}`
- Fields: `id`, `userId`, `name`, `email`, `phone`, `qualification`, `designation`, `assignedClasses`: `[ { "classId": "cls_10", "sectionId": "sec_A", "subjectId": "sub_math" } ]`, `photoUrl`.

## 7. `schools/{schoolId}/students/{studentId}`
- Fields: `id`, `userId`, `name`, `email`, `photoUrl`, `phone`, `whatsappNumber`, `dateOfBirth`, `gender`, `classId`, `className`, `sectionId`, `sectionName`, `rollNumber`, `parentName`, `parentMobile`, `parentEmail`, `address`, `isVerified` (true/false), `digitalIdCode`, `enrollmentDate`, `stats`: `{ "attendancePercent": 94.5, "completedCourses": 3, "points": 1450 }`.

## 8. `schools/{schoolId}/parents/{parentId}`
- Fields: `id`, `userId`, `name`, `email`, `phone`, `linkedStudentIds` (["stu_101", "stu_102"]).

## 9. `schools/{schoolId}/homework/{homeworkId}`
- Fields: `id`, `classId`, `sectionId`, `subjectId`, `subjectName`, `teacherId`, `teacherName`, `chapter`, `title`, `description`, `attachments`: `[ { "name": "ch3_problems.pdf", "url": "...", "type": "pdf", "sizeBytes": 2048576 } ]`, `videoUrl`, `dueDate`, `maxMarks`, `status` ("ACTIVE" / "ARCHIVED"), `whatsappReminderSent`: true, `createdAt`.

## 10. `schools/{schoolId}/homeworkSubmissions/{submissionId}`
- Composite ID: `{homeworkId}_{studentId}`
- Fields: `id`, `homeworkId`, `studentId`, `studentName`, `rollNumber`, `classId`, `sectionId`, `submittedAt`, `files`: `[ { "name": "solution.pdf", "url": "..." } ]`, `studentComment`, `status` ("PENDING" / "SUBMITTED" / "CHECKED" / "LATE"), `marksAwarded`, `teacherFeedback`, `checkedAt`, `checkedByTeacherId`.

## 11. `schools/{schoolId}/courses/{courseId}` (Paid & Free LMS Courses)
- Fields: `id`, `name`, `thumbnailUrl`, `description`, `teacherId`, `teacherName`, `classId`, `subjectId`, `price`, `discountPercent`, `durationHours`, `demoVideoUrl`, `isPublished`, `enrolledStudentCount`, `rating`.

## 12. `schools/{schoolId}/courses/{courseId}/lessons/{lessonId}`
- Fields: `id`, `title`, `chapterNumber`, `lessonOrder`, `videoType` ("DIRECT_URL" / "YOUTUBE"), `videoUrl`, `pdfNotesUrl`, `durationMinutes`, `isFreePreview`.

## 13. `schools/{schoolId}/liveClasses/{liveClassId}`
- Fields: `id`, `title`, `classId`, `sectionId`, `subjectId`, `subjectName`, `teacherId`, `teacherName`, `scheduledDate`, `startTime`, `endTime`, `platform` ("GOOGLE_MEET" / "ZOOM" / "YOUTUBE_LIVE"), `meetingUrl`, `description`, `status` ("UPCOMING" / "LIVE_NOW" / "COMPLETED").

## 14. `schools/{schoolId}/studyMaterials/{materialId}`
- Fields: `id`, `title`, `type` ("NOTES" / "PDF" / "WORKSHEET" / "QUESTION_PAPER" / "SAMPLE_PAPER" / "EBOOK" / "VIDEO"), `classId`, `subjectId`, `chapter`, `fileUrl`, `fileSize`, `uploadedByTeacherId`, `downloadsCount`.

## 15. `schools/{schoolId}/tests/{testId}`
- Fields: `id`, `title`, `classId`, `sectionId`, `subjectId`, `totalDurationMinutes`, `totalMarks`, `passingMarks`, `negativeMarking`, `shuffleQuestions`, `isPublished`, `startTime`, `endTime`.

## 16. `schools/{schoolId}/tests/{testId}/questions/{questionId}`
- Fields: `id`, `questionOrder`, `questionType` ("MCQ" / "TRUE_FALSE" / "FILL_BLANK" / "SHORT_ANSWER"), `questionText`, `options`: `[ { "id": "A", "text": "..." }, ... ]`, `correctAnswerId`, `explanation`, `marks`.

## 17. `schools/{schoolId}/testAttempts/{attemptId}`
- Fields: `id`, `testId`, `studentId`, `startTime`, `submittedTime`, `answers`: `[ { "questionId": "q1", "selectedAnswer": "B" } ]`, `score`, `isPassed`, `feedback`.

## 18. `schools/{schoolId}/exams/{examId}`
- Fields: `id`, `name` ("Mid-Term Examination 2026"), `classId`, `startDate`, `endDate`, `status` ("DRAFT" / "SCHEDULED" / "COMPLETED").

## 19. `schools/{schoolId}/results/{resultId}`
- Fields: `id`, `examId`, `examName`, `studentId`, `studentName`, `rollNumber`, `classId`, `sectionId`, `subjectGrades`: `[ { "subjectId": "sub_math", "subjectName": "Mathematics", "maxMarks": 100, "obtainedMarks": 94, "grade": "A+", "remarks": "Outstanding" } ]`, `totalMarks`, `obtainedMarks`, `percentage`, `overallGrade`, `classRank`, `principalRemarks`, `publishedDate`.

## 20. `schools/{schoolId}/attendance/{recordId}`
- Composite ID: `{classId}_{sectionId}_{date}`
- Fields: `id`, `classId`, `sectionId`, `date` ("2026-09-04"), `markedByTeacherId`, `records`: `[ { "studentId": "stu_101", "status": "PRESENT" | "ABSENT" | "LATE" | "LEAVE", "remarks": "" } ]`.

## 21. `schools/{schoolId}/timetable/{timetableId}`
- Fields: `id`, `classId`, `sectionId`, `dayOfWeek` ("MONDAY" .. "SATURDAY"), `periods`: `[ { "periodNumber": 1, "subjectId": "sub_math", "subjectName": "Mathematics", "teacherId": "tch_01", "teacherName": "Mr. Sharma", "startTime": "08:30", "endTime": "09:15", "room": "302" } ]`.

## 22. `schools/{schoolId}/fees/{feeId}`
- Fields: `id`, `studentId`, `studentName`, `classId`, `sectionId`, `feeType` ("TUITION" / "COURSE" / "EXAM" / "TRANSPORT" / "OTHER"), `title`, `amount`, `paidAmount`, `pendingAmount`, `dueDate`, `status` ("PAID" / "PARTIAL" / "PENDING" / "OVERDUE").

## 23. `schools/{schoolId}/payments/{paymentId}`
- Fields: `id`, `feeId`, `studentId`, `amount`, `paymentMethod` ("CARD" / "UPI" / "NETBANKING" / "CASH"), `gateway` ("RAZORPAY" / "STRIPE"), `transactionReference`, `verifiedByBackend`: true, `receiptNumber` ("REC-2026-00492"), `receiptUrl`, `paidAt`.

## 24. `schools/{schoolId}/announcements/{announcementId}`
- Fields: `id`, `title`, `category` ("NOTICE" / "HOLIDAY" / "EXAM_NOTICE" / "EVENT" / "WORKSHOP" / "SPORTS_DAY" / "ANNUAL_FUNCTION"), `content`, `targetAudience` ("ALL" / "STUDENTS" / "PARENTS" / "TEACHERS"), `targetClassId`, `attachmentUrl`, `imageUrl`, `priority` ("NORMAL" / "URGENT"), `publishedAt`, `publishedByAdminId`.

## 25. `schools/{schoolId}/doubts/{doubtId}`
- Fields: `id`, `studentId`, `studentName`, `teacherId`, `teacherName`, `classId`, `subjectId`, `subjectName`, `questionText`, `attachmentUrl`, `status` ("OPEN" / "ANSWERED" / "RESOLVED"), `messages`: `[ { "senderId": "...", "senderRole": "...", "text": "...", "timestamp": "..." } ]`, `createdAt`.

## 26. `schools/{schoolId}/achievements/{achievementId}`
- Fields: `id`, `studentId`, `badgeName` ("Homework Hero" / "Quiz Master" / "7 Day Study Streak" / "Perfect Attendance"), `badgeIcon`, `description`, `earnedAt`, `points`.
