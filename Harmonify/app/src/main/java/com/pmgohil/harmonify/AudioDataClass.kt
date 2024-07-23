package com.pmgohil.harmonify

data class AudioDataClass(val id: Long, val title: String, val artist: String, val path: String)

// Gaana Data Design
data class GaanaResponse(
    val tracks: List<SongTrack>
)

data class SongTrack(
    val trackTitle: String,
    val trackArtist: String,
    val trackUrl: String,
    val trackImage: String
)

// Spotify Data Design
data class SearchResponse(
    val tracks: PagingObject<Track>
)
data class PagingObject<T>(
    val href: String,
    val items: List<T>,
    val limit: Int,
    val next: String?,
    val offset: Int,
    val previous: String?,
    val total: Int
)

data class SavedTrack(
    val added_at: String, val track: Track
)

data class Track(
    val album: Album,
    val artists: List<Artist>,
    val available_markets: List<String>,
    val disc_number: Int,
    val duration_ms: Int,
    val explicit: Boolean,
    val external_ids: ExternalIds,
    val external_urls: ExternalUrls,
    val href: String,
    val id: String,
    val is_local: Boolean,
    val name: String,
    val popularity: Int,
    val preview_url: String?,
    val track_number: Int,
    val type: String,
    val uri: String
)

data class Album(
    val album_type: String,
    val artists: List<Artist>,
    val available_markets: List<String>,
    val external_urls: ExternalUrls,
    val href: String,
    val id: String,
    val images: List<Image>,
    val name: String,
    val release_date: String,
    val release_date_precision: String,
    val total_tracks: Int,
    val type: String,
    val uri: String
)

data class Artist(
    val external_urls: ExternalUrls,
    val href: String,
    val id: String,
    val name: String,
    val type: String,
    val uri: String
)

data class ExternalIds(
    val isrc: String
)

data class ExternalUrls(
    val spotify: String
)

data class Image(
    val height: Int, val url: String, val width: Int
)

data class UserProfile(
    val id: String,
    val display_name: String?,
    val email: String?,
    val external_urls: ExternalUrls,
    val followers: Followers,
    val href: String,
    val images: List<Image>,
    val type: String,
    val uri: String
)

data class Followers(
    val href: String?, val total: Int
)
