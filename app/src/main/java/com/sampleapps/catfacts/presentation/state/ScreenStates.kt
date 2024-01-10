package com.sampleapps.catfacts.presentation.state

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sampleapps.catfacts.R
import com.sampleapps.catfacts.presentation.theme.PADDING_16

@Composable
fun LoadingView(
    modifier: Modifier = Modifier,
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
fun ErrorView(
    modifier: Modifier = Modifier,
    onClickRetry: () -> Unit,
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
fun LoadingItem() {
    CircularProgressIndicator(
        modifier = Modifier
            .fillMaxWidth()
            .padding(PADDING_16)
            .wrapContentWidth(Alignment.CenterHorizontally)
            .size(24.dp),
        strokeWidth = 2.dp
    )
}

@Composable
fun EmptyItemView(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.empty_list),
            style = MaterialTheme.typography.body2)
    }
}

@Composable
fun ErrorItem(
    onClickRetry: () -> Unit,
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
    onClick: () -> Unit,
) {
    IconButton(onClick = onClick) {
        Icon(
            painter = painterResource(id = R.drawable.ic_baseline_refresh_24),
            contentDescription = stringResource(id = R.string.reload)
        )
    }
}

@Composable
@Preview(showBackground = true)
fun LoadingViewPreview() {
    LoadingView()
}

@Composable
@Preview(showBackground = true)
fun ErrorViewPreview() {
    ErrorView {}
}

@Composable
@Preview(showBackground = true)
fun LoadingItemPreview() {
    LoadingItem()
}

@Composable
@Preview(showBackground = true)
fun ErrorItemPreview() {
    ErrorItem {}
}

@Composable
@Preview(showBackground = true)
fun EmptyItemViewPreview() {
    EmptyItemView()
}