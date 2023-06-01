package com.greenranger.seoulforveggi.data.model.response

data class MapResponse(
    val markerInfoList: List<MarkerInfo>
) {
    data class MarkerInfo(
        val id: Int,
        val latitude: Double,
        val longitude: Double
    )
}