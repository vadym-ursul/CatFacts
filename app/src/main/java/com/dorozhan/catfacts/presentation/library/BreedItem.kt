package com.dorozhan.catfacts.presentation.library

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
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

@Composable
fun BreedListItem(
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

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BreedCardItem(
    modifier: Modifier = Modifier,
    breed: Breed,
    isFavoriteVisible: Boolean = true,
    onItemClick: (Breed) -> Unit = {},
    onFavoriteClick: (Breed, Boolean) -> Unit = { _, _ -> },
) {
    val shape = RoundedCornerShape(16.dp) // todo add to theme
    Card(
        modifier = modifier,
        onClick = { onItemClick(breed) },
        elevation = 4.dp,
        shape = shape
    ) {
        Box(modifier = modifier) {
            AsyncImage(
                model = breed.imageUrl,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize(),
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
                    elevation = 4.dp
                ) {
                    FavoriteButton(
                        modifier = Modifier.padding(8.dp),
                        checked = breed.favorite,
                        onCheckedChange = { onFavoriteClick(breed, it) })
                }
            }

            Surface(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(16.dp),
                shape = shape,
                elevation = 4.dp
            ) {
                Text(
                    modifier = Modifier.padding(10.dp),
                    text = breed.title,
                    maxLines = 2,
                    style = MaterialTheme.typography.subtitle2,
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