package com.moondroid.pharmacyproject01.data.model

import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml

@Xml(name = "response")
data class DetailResponse(
    @Element(name = "header")
    val header: DetailHeader,
    @Element(name = "body")
    val body: DetailBody,
)

@Xml(name = "header")
data class DetailHeader(
    @PropertyElement(name = "resultCode")
    val resultCode: String,
    @PropertyElement(name = "resultMsg")
    val resultMsg: String,
)

@Xml(name = "body")
data class DetailBody(
    @Element(name = "items")
    val items: DetailItems,
    @PropertyElement(name = "numOfRows")
    val numOfRows: Int,
    @PropertyElement(name = "pageNo")
    val pageNo: Int,
    @PropertyElement(name = "totalCount")
    val totalCount: Int,
)

@Xml(name = "items")
data class DetailItems(
    @Element(name = "item")
    val item: DetailItem,
)

@Xml
data class DetailItem(
    @PropertyElement(name = "dutyName")
    val dutyName: String,
    @PropertyElement(name = "dutyAddr")
    val dutyAddr: String,
    @PropertyElement(name = "dutyTel1")
    val dutyTel: String,
    @PropertyElement(name = "dutyInf")
    val dutyInf: String? = "",
    @PropertyElement(name = "dutyEtc")
    val dutyEtc: String? = "",
    @PropertyElement(name = "dutyTime1c")
    val monClose: String? = "",
    @PropertyElement(name = "dutyTime1s")
    val monOpen: String? = "",
    @PropertyElement(name = "dutyTime2c")
    val tueClose: String? = "",
    @PropertyElement(name = "dutyTime2s")
    val tueOpen: String? = "",
    @PropertyElement(name = "dutyTime3c")
    val wedClose: String? = "",
    @PropertyElement(name = "dutyTime3s")
    val wedOpen: String? = "",
    @PropertyElement(name = "dutyTime4c")
    val thuClose: String? = "",
    @PropertyElement(name = "dutyTime4s")
    val thuOpen: String? = "",
    @PropertyElement(name = "dutyTime5c")
    val friClose: String? = "",
    @PropertyElement(name = "dutyTime5s")
    val friOpen: String? = "",
    @PropertyElement(name = "dutyTime6c")
    val satClose: String? = "",
    @PropertyElement(name = "dutyTime6s")
    val satOpen: String? = "",
    @PropertyElement(name = "dutyTime7c")
    val sunClose: String? = "",
    @PropertyElement(name = "dutyTime7s")
    val sunOpen: String? = "",
    @PropertyElement(name = "dutyTime8c")
    val holClose: String? = "",
    @PropertyElement(name = "dutyTime8s")
    val holOpen: String? = "",
    @PropertyElement(name = "hpid")
    val hpid: String,
    @PropertyElement(name = "wgs84Lat")
    val latitude: Double,
    @PropertyElement(name = "wgs84Lon")
    val longitude: Double,
) {
    fun getDescription(): String {
        val builder = StringBuilder()
        builder.append("$dutyAddr\n\n")
        builder.append("Tel. $dutyTel\n\n")
        if (!dutyInf.isNullOrEmpty()) builder.append("$dutyInf\n\n")
        if (!dutyEtc.isNullOrEmpty()) builder.append("$dutyEtc\n\n")
        builder.append("영업시간\n")
        if (!monClose.isNullOrEmpty() && !monOpen.isNullOrEmpty()) {
            builder.append("월 ${setTimeFormat(monOpen)} - ${setTimeFormat(monClose)}\n")
        } else {
            builder.append("월요일 휴무\n")
        }
        if (!tueClose.isNullOrEmpty() && !tueOpen.isNullOrEmpty()) {
            builder.append("화 ${setTimeFormat(tueOpen)} - ${setTimeFormat(tueClose)}\n")
        } else {
            builder.append("화요일 휴무\n")
        }
        if (!wedClose.isNullOrEmpty() && !wedOpen.isNullOrEmpty()) {
            builder.append("수 ${setTimeFormat(wedOpen)} - ${setTimeFormat(wedClose)}\n")
        } else {
            builder.append("수요일 휴무\n")
        }
        if (!thuClose.isNullOrEmpty() && !thuOpen.isNullOrEmpty()) {
            builder.append("목 ${setTimeFormat(thuOpen)} - ${setTimeFormat(thuClose)}\n")
        } else {
            builder.append("목요일 휴무\n")
        }
        if (!friClose.isNullOrEmpty() && !friOpen.isNullOrEmpty()) {
            builder.append("금 ${setTimeFormat(friOpen)} - ${setTimeFormat(friClose)}\n")
        } else {
            builder.append("금요일 휴무\n")
        }
        if (!satClose.isNullOrEmpty() && !satOpen.isNullOrEmpty()) {
            builder.append("토 ${setTimeFormat(satOpen)} - ${setTimeFormat(satClose)}\n")
        } else {
            builder.append("토요일 휴무\n")
        }
        if (!sunClose.isNullOrEmpty() && !sunOpen.isNullOrEmpty()) {
            builder.append("일 ${setTimeFormat(sunOpen)} - ${setTimeFormat(sunClose)}\n")
        } else {
            builder.append("일요일 휴무\n")
        }
        if (!holClose.isNullOrEmpty() && !holOpen.isNullOrEmpty()) {
            builder.append("공휴일 ${setTimeFormat(holOpen)} - ${setTimeFormat(holClose)}\n")
        } else {
            builder.append("공휴일 휴무\n")
        }

        return builder.toString()
    }

    fun setTimeFormat(time: String): String {
        val builder = StringBuilder(time)
        builder.insert(2, " : ")
        return builder.toString()
    }
}

