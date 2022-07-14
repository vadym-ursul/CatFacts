package com.dorozhan.catfacts.domain.model

import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.toLowerCase
import com.dorozhan.catfacts.data.remote.IMAGES_URL

data class Breed(
    val title: String
) {
    val imageUrl = "$IMAGES_URL${
        title
            .toLowerCase(Locale.current)
            .replace(" ", "_")
    }.jpg"
}