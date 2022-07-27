package com.dorozhan.catfacts.presentation.library

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun FavoriteButton(
    modifier: Modifier = Modifier,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    color: Color = MaterialTheme.colorScheme.primary
) {
    IconToggleButton(
        checked = checked,
        onCheckedChange = {
            onCheckedChange.invoke(it)
        },
        modifier = modifier
    ) {
        Icon(
            tint = color,
            imageVector = if (checked) {
                Icons.Filled.Favorite
            } else {
                Icons.Default.FavoriteBorder
            },
            contentDescription = null
        )
    }

}

@Composable
@Preview
private fun FavoriteButtonPreview() {
    FavoriteButton(
        checked = true,
        onCheckedChange = {}
    )
}