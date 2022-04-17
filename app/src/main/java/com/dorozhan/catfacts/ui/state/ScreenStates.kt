package com.dorozhan.catfacts.ui.state

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dorozhan.catfacts.R
import com.dorozhan.catfacts.ui.theme.blackColor

@Composable
fun LoadingView(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator()
    }
}

@Composable
@Preview
fun LoadingViewPreview() {
    LoadingView()
}

@Composable
fun ErrorView(
    modifier: Modifier = Modifier,
    onClickRetry: () -> Unit
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        RetryImage(
            modifier = Modifier.size(40.dp),
            onClick = onClickRetry
        )
    }
}

@Composable
@Preview
fun ErrorViewPreview() {
    ErrorView {}
}

@Composable
fun LoadingItem() {
    CircularProgressIndicator(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .wrapContentWidth(Alignment.CenterHorizontally)
            .size(24.dp),
        strokeWidth = 2.dp
    )
}


@Composable
@Preview
fun LoadingItemPreview() {
    LoadingItem()
}

@Composable
fun ErrorItem(
    onClickRetry: () -> Unit
) {
    RetryImage(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .wrapContentWidth(Alignment.CenterHorizontally),
        onClick = onClickRetry
    )
}

@Composable
fun RetryImage(
    modifier: Modifier,
    onClick: () -> Unit
) {
    Image(
        painter = painterResource(id = R.drawable.ic_baseline_refresh_24),
        contentDescription = stringResource(id = R.string.reload),
        colorFilter = ColorFilter.tint(blackColor),
        modifier = modifier.clickable(
            onClick = onClick
        ),
    )
}

@Composable
@Preview
fun ErrorItemPreview() {
    ErrorItem {}
}