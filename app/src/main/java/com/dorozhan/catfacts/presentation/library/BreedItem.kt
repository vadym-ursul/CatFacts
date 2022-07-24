package com.dorozhan.catfacts.presentation.library

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconToggleButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.dorozhan.catfacts.R
import com.dorozhan.catfacts.domain.model.Breed

@Composable
fun BreedItem(
    breed: Breed,
    isFavoriteVisible: Boolean = true,
    onItemClick: (Breed) -> Unit = {},
    onFavoriteClick: (Breed, Boolean) -> Unit = { _, _ -> },
) {
    Row(
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 8.dp)
            .fillMaxWidth()
            .clickable { onItemClick(breed) },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.weight(1f),
            text = breed.title,
            maxLines = 1,
            style = MaterialTheme.typography.h6,
            overflow = TextOverflow.Ellipsis
        )
        Box(
            Modifier
                .padding(start = 16.dp)
                .size(100.dp)
        ) {
            AsyncImage(
                model = breed.imageUrl,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(MaterialTheme.shapes.small),
                contentScale = ContentScale.Crop,
                placeholder = painterResource(id = R.drawable.ic_cat_placeholder_512),
                error = painterResource(id = R.drawable.ic_cat_placeholder_512),
            )
            if (isFavoriteVisible) {
                FavoriteButton(
                    modifier = Modifier.align(Alignment.TopEnd),
                    checked = breed.favorite,
                    onCheckedChange = { onFavoriteClick(breed, it) })
            }
        }
    }
}

@Composable
private fun FavoriteButton(
    modifier: Modifier = Modifier,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    color: Color = MaterialTheme.colors.primary,
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
@Preview(showBackground = true)
private fun FavoriteButtonPreview() {
    FavoriteButton(
        checked = true,
        onCheckedChange = {}
    )
}

@Composable
@Preview(showBackground = true)
private fun BreedItemPreview() {
    BreedItem(breed = Breed("Persian", imageUrl = ""))
}