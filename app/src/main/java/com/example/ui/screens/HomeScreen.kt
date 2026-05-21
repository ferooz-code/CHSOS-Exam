package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.*

@Composable
fun HomeScreen(
    onNavigateToSetup: () -> Unit,
    onNavigateToDomains: () -> Unit,
    onNavigateToStudyPlan: () -> Unit,
    onNavigateToAbout: () -> Unit,
    onNavigateToGlossary: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SleekBackground),
    ) {
        // Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(SleekSurface)
                .padding(horizontal = 24.dp, vertical = 24.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "CHSOS PREP PLATFORM",
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp,
                        color = SleekBlue700
                    )
                )
                Text(
                    text = "Good morning, Alex",
                    style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.SemiBold),
                    color = SleekTextPrimary
                )
            }
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(Color(0xFFDBEAFE), CircleShape)
                    .border(2.dp, Color.White, CircleShape)
                    .shadow(1.dp, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "AS",
                    color = SleekBlue700,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Main Card
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(32.dp))
                    .background(Brush.linearGradient(listOf(Color(0xFF2563EB), Color(0xFF1E40AF))))
                    .padding(24.dp)
            ) {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Exam Readiness Score",
                            color = Color(0xFFDBEAFE),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium
                        )
                        Box(
                            modifier = Modifier
                                .background(Color.White.copy(alpha = 0.2f), RoundedCornerShape(8.dp))
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = "78%",
                                color = Color.White,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(verticalAlignment = Alignment.Bottom) {
                        Text(
                            text = "782",
                            color = Color.White,
                            fontSize = 36.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "/ 1000",
                            color = Color.White.copy(alpha = 0.8f),
                            fontSize = 18.sp,
                            modifier = Modifier.padding(bottom = 6.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp)
                            .background(Color(0xFF1E3A8A).copy(alpha = 0.3f), CircleShape)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(0.78f)
                                .fillMaxHeight()
                                .background(Color.White, CircleShape)
                        )
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "Based on your last 3 practice attempts",
                        color = Color(0xFFDBEAFE),
                        fontSize = 12.sp
                    )
                }
            }

            // Grid Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                SleekCardButton(
                    modifier = Modifier.weight(1f),
                    title = "Full Test",
                    subtitle = "100 Questions",
                    icon = "⏱️",
                    iconBgColor = Color(0xFFFFEDD5),
                    onClick = onNavigateToSetup
                )
                SleekCardButton(
                    modifier = Modifier.weight(1f),
                    title = "Study Mode",
                    subtitle = "With Rationales",
                    icon = "📖",
                    iconBgColor = Color(0xFFDCFCE7),
                    onClick = onNavigateToStudyPlan
                )
            }
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                SleekCardButton(
                    modifier = Modifier.weight(1f),
                    title = "About Exam",
                    subtitle = "Exam Info",
                    icon = "ℹ️",
                    iconBgColor = Color(0xFFF3E8FF),
                    onClick = onNavigateToAbout
                )
                SleekCardButton(
                    modifier = Modifier.weight(1f),
                    title = "Glossary",
                    subtitle = "Terms & Definitions",
                    icon = "📚",
                    iconBgColor = Color(0xFFFEF3C7),
                    onClick = onNavigateToGlossary
                )
            }

            // Domain Performance
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(24.dp))
                    .border(1.dp, SleekBorder, RoundedCornerShape(24.dp))
                    .background(SleekSurface)
                    .padding(20.dp)
            ) {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Domain Performance",
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                            color = SleekTextPrimary
                        )
                        Text(
                            text = "View All",
                            color = SleekBlue600,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.clickable(onClick = onNavigateToDomains)
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    DomainProgressRow("D2: Simulation Tech Ops (35%)", "82%", 0.82f, SleekBlue600)
                    Spacer(modifier = Modifier.height(12.dp))
                    DomainProgressRow("D3: Healthcare Sim Practices (25%)", "64%", 0.64f, SleekBlue600)
                    Spacer(modifier = Modifier.height(12.dp))
                    DomainProgressRow("D5: Instructional Design (15%)", "48%", 0.48f, SleekOrange500)
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = "This website is an independent CHSOS preparation tool. It is not affiliated with, endorsed by, or provided by the Society for Simulation in Healthcare (SSH). Users should review official SSH materials.",
                style = MaterialTheme.typography.bodySmall.copy(fontSize = 10.sp),
                color = SleekTextSecondary,
                textAlign = TextAlign.Center,
                lineHeight = 14.sp
            )
        }
    }
}

@Composable
fun SleekCardButton(
    modifier: Modifier = Modifier,
    title: String,
    subtitle: String,
    icon: String,
    iconBgColor: Color,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(24.dp))
            .background(SleekSurface)
            .border(1.dp, SleekBorder, RoundedCornerShape(24.dp))
            .clickable(onClick = onClick)
            .padding(16.dp)
    ) {
        Column(horizontalAlignment = Alignment.Start) {
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .background(iconBgColor, RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text(text = icon, fontSize = 16.sp)
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                color = SleekTextPrimary
            )
            Text(
                text = subtitle,
                style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
                color = SleekTextSecondary
            )
        }
    }
}

@Composable
fun DomainProgressRow(title: String, percentageStr: String, progress: Float, color: Color) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = title, fontSize = 11.sp, fontWeight = FontWeight.Medium, color = SleekTextSecondary)
            Text(text = percentageStr, fontSize = 11.sp, fontWeight = FontWeight.Bold, color = color)
        }
        Spacer(modifier = Modifier.height(6.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(6.dp)
                .background(SleekBorder, CircleShape)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(progress)
                    .fillMaxHeight()
                    .background(color, CircleShape)
            )
        }
    }
}
