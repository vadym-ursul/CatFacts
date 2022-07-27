package com.dorozhan.catfacts.presentation.library

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ContentAlpha
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import com.dorozhan.catfacts.R

@Composable
private fun BaseTitleContentProvider(
    content: @Composable () -> Unit,
) = ProvideTextStyle(value = MaterialTheme.typography.titleLarge) {
    CompositionLocalProvider(
        LocalContentAlpha provides ContentAlpha.high,
        content = content
    )
}

@Composable
private fun BaseTitleText(
    text: String,
) = BaseTitleContentProvider {
    Text(text = text,
        color = MaterialTheme.colorScheme.onSurfaceVariant)
}

@Composable
fun BaseBackIcon(
    onBackClick: () -> Unit,
) = IconButton(onClick = { onBackClick() }) {
    Icon(
        imageVector = Icons.Rounded.ArrowBack,
        contentDescription = "Back",
        tint = MaterialTheme.colorScheme.onSurfaceVariant
    )
}

@Composable
fun BackAppBar(
    text: String = "",
    title: @Composable () -> Unit = { BaseTitleText(text = text) },
    onBackClick: () -> Unit,
    actions: @Composable RowScope.() -> Unit = {},
) {
    SmallTopAppBar(
        navigationIcon = { BaseBackIcon(onBackClick = onBackClick) },
        title = title,
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
            titleContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
            actionIconContentColor = MaterialTheme.colorScheme.onSurfaceVariant
        ),
        actions = actions
    )
}

@Composable
fun CatalogAppBar(
    title: String,
    onSearchClick: () -> Unit = {},
    onFavoritesClick: () -> Unit = {},
) {
    SmallTopAppBar(
        title = { BaseTitleText(text = title) },
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
            titleContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
            actionIconContentColor = MaterialTheme.colorScheme.onSurfaceVariant
        ),
        actions = {
            IconButton(
                onClick = onSearchClick
            ) {
                Icon(imageVector = Icons.Rounded.Search,
                    contentDescription = "Search")
            }
            IconButton(
                onClick = onFavoritesClick
            ) {
                Icon(imageVector = Icons.Default.FavoriteBorder,
                    contentDescription = "Favorites")
            }
        }
    )
}

@Composable
fun SearchAppBar(
    text: String,
    textPlaceholder: String = stringResource(id = R.string.search),
    onBackClick: () -> Unit = {},
    onTextChange: (String) -> Unit = {},
    onSearchClick: (String) -> Unit = {},
) {
    val onBackClickListener: () -> Unit = {
        onTextChange("")
        onBackClick()
    }

    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    BackHandler(onBack = onBackClickListener)

    BackAppBar(
        title = {
            NoPaddingTextField(
                value = text,
                onValueChange = onTextChange,
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(focusRequester),
                placeholder = {
                    Text(
                        text = textPlaceholder,
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = ContentAlpha.medium),
                        style = MaterialTheme.typography.titleMedium
                    )
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Search
                ),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        onSearchClick(text)
                    }
                ),
                textStyle = MaterialTheme.typography.titleMedium,
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.Transparent,
                    textColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    cursorColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = ContentAlpha.medium),
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent)
            )
        },
        onBackClick = onBackClickListener,
        actions = {
            if (text.isNotEmpty()) {
                IconButton(
                    onClick = { onTextChange("") }
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close")
                }
            }
        }
    )
}

@Composable
@Preview
fun BackAppBarPreview() {
    BackAppBar(
        text = "Top Bar title",
        onBackClick = {}
    )
}

@Composable
@Preview
fun SearchAppBarPreview() {
    SearchAppBar(
        text = "Top Bar title",
        onBackClick = {},
        onTextChange = {},
        onSearchClick = {}
    )
}