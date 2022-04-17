package com.dorozhan.catfacts.model

import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.toLowerCase
import com.dorozhan.catfacts.data.network.IMAGES_URL

data class Breed(
    val title: String
) {
    val imageUrl = "$IMAGES_URL${
        title
            .toLowerCase(Locale.current)
            .replace(" ", "_")
    }.jpg"
}