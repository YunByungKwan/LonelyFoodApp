package org.ybk.fooddiaryapp.data.model.place

import org.ybk.fooddiaryapp.data.model.place.Place

/**
 * @property lastBuildDate - 검색 결과를 생성한 시간이다.
 * @property total - 검색 결과 문서의 총 개수를 의미한다.
 * @property start - 검색 결과 문서 중, 문서의 시작점을 의미한다.
 * @property display - 검색된 검색 결과의 개수이다.
 * @property category - 검색 결과 업체, 기관의 분류 정보를 제공한다.
 * @property items - XML 포멧에서는 item 태그로, JSON 포멧에서는 items 속성으로 표현된다.
 *                   개별 검색 결과이며 title, link, description, address, mapx, mapy를 포함한다.
 */
data class PlaceResponse(
    var lastBuildDate: String,
    var total: Int,
    var start: Int,
    var display: Int,
    var category: String,
    var items: List<Place>
) {
    override fun toString(): String {
        return "SearchPlaceResult(" +
                "lastBuildDate='$lastBuildDate', " +
                "total=$total, " +
                "start=$start, " +
                "display=$display, " +
                "category='$category', " +
                "items=$items)"
    }
}