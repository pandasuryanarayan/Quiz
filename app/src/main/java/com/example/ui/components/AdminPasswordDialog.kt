package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AdminPanelSettings
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.ErrorOutline
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material.icons.rounded.Visibility
import androidx.compose.material.icons.rounded.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.ui.theme.ArcadeBorder
import com.example.ui.theme.ArcadeBorderBright
import com.example.ui.theme.ArcadeCard
import com.example.ui.theme.ArcadeCardElevated
import com.example.ui.theme.ArcadeCardSecondary
import com.example.ui.theme.ArcadeNeonGreen
import com.example.ui.theme.ArcadeText
import com.example.ui.theme.ArcadeTextDim
import com.example.ui.theme.ArcadeTextMuted
import com.example.util.AdminSecurity

@Composable
fun AdminPasswordDialog(
    onSuccess: () -> Unit,
    onDismiss: () -> Unit
) {
    var passwordInput by remember { mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) }
    var isError by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    var animateIn by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        animateIn = true
    }

    val scale by animateFloatAsState(
        targetValue = if (animateIn) 1f else 0.85f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "adminDialogScale"
    )

    fun attemptVerify() {
        if (passwordInput.trim().isEmpty()) {
            isError = true
            errorMessage = "Please enter the password"
            return
        }

        if (AdminSecurity.verifyPassword(passwordInput.trim())) {
            isError = false
            onSuccess()
        } else {
            isError = true
            errorMessage = "Invalid admin password"
        }
    }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = true)
    ) {
        Surface(
            shape = RoundedCornerShape(26.dp),
            color = ArcadeCard,
            border = BorderStroke(1.dp, ArcadeBorderBright),
            modifier = Modifier
                .fillMaxWidth()
                .scale(scale)
                .shadow(16.dp, RoundedCornerShape(26.dp))
                .padding(4.dp)
                .testTag("admin_password_dialog")
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Emblem
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = Color(0xFF0E2A1A),
                    border = BorderStroke(1.dp, ArcadeNeonGreen.copy(alpha = 0.5f)),
                    modifier = Modifier.size(56.dp)
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.AdminPanelSettings,
                            contentDescription = "Admin verification",
                            tint = ArcadeNeonGreen,
                            modifier = Modifier.size(30.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                Text(
                    text = "Admin Sandbox Login",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Black,
                    color = ArcadeText,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "Enter the security password to unlock all logos across every category for sandbox testing.",
                    fontSize = 12.sp,
                    color = ArcadeTextDim,
                    textAlign = TextAlign.Center,
                    lineHeight = 16.sp
                )

                Spacer(modifier = Modifier.height(18.dp))

                // Password TextField
                OutlinedTextField(
                    value = passwordInput,
                    onValueChange = {
                        passwordInput = it
                        if (isError) {
                            isError = false
                            errorMessage = ""
                        }
                    },
                    textStyle = TextStyle(
                        color = ArcadeText,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    label = { Text("Password", color = if (isError) Color(0xFFEF4444) else ArcadeTextDim) },
                    placeholder = { Text("Enter admin password", color = ArcadeTextMuted) },
                    singleLine = true,
                    isError = isError,
                    visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = { attemptVerify() }
                    ),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Rounded.Lock,
                            contentDescription = null,
                            tint = if (isError) Color(0xFFEF4444) else ArcadeNeonGreen,
                            modifier = Modifier.size(18.dp)
                        )
                    },
                    trailingIcon = {
                        IconButton(
                            onClick = { isPasswordVisible = !isPasswordVisible },
                            modifier = Modifier.testTag("admin_password_toggle_visibility")
                        ) {
                            Icon(
                                imageVector = if (isPasswordVisible) Icons.Rounded.VisibilityOff else Icons.Rounded.Visibility,
                                contentDescription = if (isPasswordVisible) "Hide password" else "Show password",
                                tint = ArcadeTextDim,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    },
                    shape = RoundedCornerShape(14.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = ArcadeText,
                        unfocusedTextColor = ArcadeText,
                        focusedContainerColor = ArcadeCardSecondary,
                        unfocusedContainerColor = ArcadeCardSecondary,
                        focusedBorderColor = ArcadeNeonGreen,
                        unfocusedBorderColor = ArcadeBorder,
                        focusedLabelColor = ArcadeNeonGreen,
                        unfocusedLabelColor = ArcadeTextDim,
                        cursorColor = ArcadeNeonGreen,
                        errorTextColor = ArcadeText,
                        errorBorderColor = Color(0xFFEF4444),
                        errorCursorColor = Color(0xFFEF4444)
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("admin_password_input")
                )

                AnimatedVisibility(
                    visible = isError,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 6.dp, start = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.ErrorOutline,
                            contentDescription = null,
                            tint = Color(0xFFEF4444),
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = errorMessage,
                            color = Color(0xFFEF4444),
                            fontSize = 11.5.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Spacer(modifier = Modifier.height(18.dp))

                // Actions: Verify & Cancel
                Button(
                    onClick = { attemptVerify() },
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = ArcadeNeonGreen),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .testTag("admin_password_submit_button")
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Check,
                        contentDescription = null,
                        tint = Color.Black,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Verify & Proceed",
                        fontSize = 13.5.sp,
                        fontWeight = FontWeight.Black,
                        color = Color.Black
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedButton(
                    onClick = onDismiss,
                    shape = RoundedCornerShape(14.dp),
                    border = BorderStroke(1.dp, ArcadeBorder),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(44.dp)
                        .testTag("admin_password_cancel_button")
                ) {
                    Text(
                        text = "Cancel",
                        color = ArcadeTextDim,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}
