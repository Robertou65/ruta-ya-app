package com.rutaya.app.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp

@Composable
fun RutaYaLogo(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier.size(150.dp)) {
        val green = Color(0xFF176B61)
        val orange = Color(0xFFE99B33)
        drawCircle(Color(0xFFE0F4EE), radius = size.minDimension / 2)
        val box = Path().apply {
            moveTo(size.width * .27f, size.height * .39f)
            lineTo(size.width * .5f, size.height * .27f)
            lineTo(size.width * .73f, size.height * .39f)
            lineTo(size.width * .5f, size.height * .52f)
            close()
        }
        drawPath(box, orange)
        drawRect(orange, Offset(size.width * .28f, size.height * .4f), Size(size.width * .44f, size.height * .3f))
        drawLine(green, Offset(size.width * .36f, size.height * .78f), Offset(size.width * .63f, size.height * .78f), 8.dp.toPx(), StrokeCap.Round)
        drawArc(green, 180f, 180f, false, Offset(size.width * .31f, size.height * .57f), Size(size.width * .38f, size.height * .3f), style = Stroke(5.dp.toPx(), cap = StrokeCap.Round))
        drawCircle(green, 6.dp.toPx(), Offset(size.width * .38f, size.height * .78f))
        drawCircle(green, 6.dp.toPx(), Offset(size.width * .62f, size.height * .78f))
    }
}
