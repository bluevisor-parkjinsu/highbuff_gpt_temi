package kr.bluevisor.robot.libs.presentation.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import kr.bluevisor.robot.libs.BuildConfig

val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
).let {
    if (!BuildConfig.FOR_TEMI) return@let it

    val fontSizeRatio = 2.4f
    fun zoomFontSize(textStyle: TextStyle) =
        textStyle.copy(
            fontSize = textStyle.fontSize * fontSizeRatio,
            lineHeight = textStyle.lineHeight * fontSizeRatio)

    Typography(
        displayLarge = zoomFontSize(it.displayLarge),
        displayMedium = zoomFontSize(it.displayMedium),
        headlineLarge = zoomFontSize(it.headlineLarge),
        headlineMedium = zoomFontSize(it.headlineMedium),
        headlineSmall = zoomFontSize(it.headlineSmall),
        titleLarge = zoomFontSize(it.titleLarge),
        titleMedium = zoomFontSize(it.titleMedium),
        titleSmall = zoomFontSize(it.titleSmall),
        bodyLarge = zoomFontSize(it.bodyLarge),
        bodyMedium = zoomFontSize(it.bodyMedium),
        bodySmall = zoomFontSize(it.bodySmall),
        labelLarge = zoomFontSize(it.labelLarge),
        labelMedium = zoomFontSize(it.labelMedium),
        labelSmall = zoomFontSize(it.labelSmall),
    )
}