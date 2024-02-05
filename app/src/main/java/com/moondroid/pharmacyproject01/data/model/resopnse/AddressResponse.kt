package com.moondroid.pharmacyproject01.data.model.resopnse

import androidx.annotation.Keep
import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml

@Keep
@Xml(name = "response")
data class AddressResponse(
    @Element(name = "header")
    val header: AddressHeader,
    @Element(name = "body")
    val body: AddressBody,
)

@Keep
@Xml(name = "header")
data class AddressHeader(
    @PropertyElement(name = "resultCode")
    val resultCode: String,
    @PropertyElement(name = "resultMsg")
    val resultMsg: String,
)

@Keep
@Xml(name = "body")
data class AddressBody(
    @Element(name = "items")
    val items: AddressItems,
    @PropertyElement(name = "numOfRows")
    val numOfRows: Int,
    @PropertyElement(name = "pageNo")
    val pageNo: Int,
    @PropertyElement(name = "totalCount")
    val totalCount: Int,
)

@Keep
@Xml(name = "items")
data class AddressItems(
    @Element(name = "item")
    val item: List<AddressItem>,
)

@Keep
@Xml
data class AddressItem(
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
)

