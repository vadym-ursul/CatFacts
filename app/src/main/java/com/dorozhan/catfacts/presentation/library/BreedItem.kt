package com.dorozhan.catfacts.presentation.library

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.dorozhan.catfacts.R
import com.dorozhan.catfacts.domain.model.Breed
import com.dorozhan.catfacts.presentation.theme.PADDING_10
import com.dorozhan.catfacts.presentation.theme.PADDING_16
import com.dorozhan.catfacts.presentation.theme.PADDING_8

@Composable
fun BreedListItem(
    breed: Breed,
    isFavoriteVisible: Boolean = true,
    onItemClick: (Breed) -> Unit = {},
    onFavoriteClick: (Breed, Boolean) -> Unit = { _, _ -> },
) {
    Row(
        modifier = Modifier
            .padding(horizontal = PADDING_16, vertical = PADDING_8)
            .fillMaxWidth()
            .clickable { onItemClick(breed) },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.weight(1f),
            text = breed.title,
            maxLines = 1,
            style = MaterialTheme.typography.bodyLarge,
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
                    color = MaterialTheme.colorScheme.primary,
                    checked = breed.favorite,
                    onCheckedChange = { onFavoriteClick(breed, it) })
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BreedCardItem(
    modifier: Modifier = Modifier,
    breed: Breed,
    isFavoriteVisible: Boolean = true,
    onItemClick: (Breed) -> Unit = {},
    onFavoriteClick: (Breed, Boolean) -> Unit = { _, _ -> },
) {
    val shape = MaterialTheme.shapes.large
    Card(
        modifier = modifier,
        onClick = { onItemClick(breed) },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = shape
    ) {
        Box(modifier = modifier) {
            AsyncImage(
                model = breed.imageUrl,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f),
                contentScale = ContentScale.Crop,
                placeholder = painterResource(id = R.drawable.ic_cat_placeholder_512),
                error = painterResource(id = R.drawable.ic_cat_placeholder_512),
            )

            if (isFavoriteVisible) {
                Surface(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(16.dp)
                        .size(34.dp),
                    shape = CircleShape,
                    shadowElevation = 4.dp
                ) {
                    FavoriteButton(
                        modifier = Modifier.padding(PADDING_8),
                        color = MaterialTheme.colorScheme.primary,
                        checked = breed.favorite,
                        onCheckedChange = { onFavoriteClick(breed, it) })
                }
            }

            Surface(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(16.dp),
                shape = shape,
                shadowElevation = 4.dp
            ) {
                Text(
                    modifier = Modifier.padding(PADDING_10),
                    text = breed.title,
                    maxLines = 2,
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.labelMedium,
                    overflow = TextOverflow.Ellipsis)
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun BreedListItemPreview() {
    BreedListItem(breed = Breed("Persian", imageUrl = ""))
}

@Composable
@Preview(showBackground = true)
private fun BreedGridItemPreview() {
    BreedCardItem(breed = Breed("Persian", imageUrl = ""))
}