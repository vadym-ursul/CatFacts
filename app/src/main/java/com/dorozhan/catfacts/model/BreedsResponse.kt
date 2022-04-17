package com.dorozhan.catfacts.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BreedsResponse(
    @SerialName("current_page")
    val currentPage: Int,
    val data: List<Datum>,
    @SerialName("first_page_url")
    val firstPageURL: String,
    val from: Long,
    @SerialName("last_page")
    val lastPage: Long,
    @SerialName("last_page_url")
    val lastPageURL: String,
    val links: List<Link>,
    @SerialName("next_page_url")
    val nextPageURL: String? = null,
    val path: String,
    @SerialName("per_page")
    val perPage: Long,
    @SerialName("prev_page_url")
    val prevPageURL: String? = null,
    val to: Long,
    val total: Long
)

@Serializable
data class Datum(
    val breed: String,
    val country: String,
    val origin: String,
    val coat: String,
    val pattern: String
) {
    fun toBreed(): Breed {
        return Breed(title = breed)
    }
}

@Serializable
data class Link(
    val url: String? = null,
    val label: String,
    val active: Boolean
)
