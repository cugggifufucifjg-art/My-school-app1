package com.example.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.models.SchoolBranding
import com.example.ui.theme.*

@Composable
fun SplashScreen(
    branding: SchoolBranding,
    onContinue: () -> Unit
) {
    val infiniteTransition = rememberInfiniteTransition(label = "splash_anim")
    val scale by infiniteTransition.animateFloat(
        initialValue = 0.96f,
        targetValue = 1.04f,
        animationSpec = infiniteRepeatable(
            animation = tween(1800, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "logo_scale"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(PolishNavyDark, Color(0xFF0A2647), PolishBluePrimary)
                )
            )
            .statusBarsPadding()
            .navigationBarsPadding()
            .padding(24.dp)
            .testTag("splash_screen_root"),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            // School Logo Container (Dynamic from Branding)
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .scale(scale)
                    .clip(RoundedCornerShape(32.dp))
                    .background(Color.White)
                    .border(3.dp, PolishIceBlue, RoundedCornerShape(32.dp))
                    .padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = branding.logoDrawableRes),
                    contentDescription = "School Logo",
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(24.dp)),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.height(28.dp))

            // Dynamic School Name
            Text(
                text = branding.schoolName.uppercase(),
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.ExtraBold,
                color = Color.White,
                textAlign = TextAlign.Center,
                letterSpacing = 1.sp,
                lineHeight = 28.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("splash_school_name")
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Dynamic Tagline
            Text(
                text = branding.tagline,
                style = MaterialTheme.typography.bodyMedium,
                color = PolishIceBlue,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .testTag("splash_school_tagline")
            )

            Spacer(modifier = Modifier.height(18.dp))

            // Academic Session Pill
            Surface(
                shape = CircleShape,
                color = Color.White.copy(alpha = 0.15f),
                border = BorderStroke(1.dp, PolishIceBlue.copy(alpha = 0.4f))
            ) {
                Text(
                    text = "ACADEMIC SESSION ${branding.academicSession}",
                    color = Color.White,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 0.6.sp,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)
                )
            }

            Spacer(modifier = Modifier.height(44.dp))

            // Continue Button
            Button(
                onClick = onContinue,
                modifier = Modifier
                    .fillMaxWidth(0.85f)
                    .height(54.dp)
                    .testTag("splash_continue_button"),
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(
                    containerColor = PolishIceBlue,
                    contentColor = PolishNavyDark
                )
            ) {
                Text(
                    text = "Enter Campus Portal",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    imageVector = Icons.Default.ArrowForward,
                    contentDescription = null,
                    tint = PolishNavyDark
                )
            }
        }

        // Footer Principal info
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Principal: ${branding.principalName}",
                color = PolishIceBlue.copy(alpha = 0.8f),
                fontSize = 11.sp
            )
            Text(
                text = "EduManage Platform • Professional Polish Edition",
                color = PolishIceBlue.copy(alpha = 0.5f),
                fontSize = 10.sp
            )
        }
    }
}
