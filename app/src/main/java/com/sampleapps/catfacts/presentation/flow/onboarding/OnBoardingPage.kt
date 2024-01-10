package com.sampleapps.catfacts.presentation.flow.onboarding

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.sampleapps.catfacts.R

sealed class OnBoardingPage(
    @DrawableRes
    val image: Int,
    @StringRes
    val title: Int,
    @StringRes
    val description: Int,
) {
    object First : OnBoardingPage(
        image = R.drawable.ic_cat1,
        title = R.string.onboarding_first_page_title,
        description = R.string.onboarding_first_page_description
    )

    object Second : OnBoardingPage(
        image = R.drawable.ic_cat2,
        title = R.string.onboarding_second_page_title,
        description = R.string.onboarding_second_page_description
    )

    object Third : OnBoardingPage(
        image = R.drawable.ic_cat3,
        title = R.string.onboarding_third_page_title,
        description = R.string.onboarding_third_page_description
    )
}