package com.moondroid.pharmacyproject01.data.model

import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml

@Xml(name = "response")
data class LocationResponse(
    @Element(name = "header")
    val header: LocationHeader,
    @Element(name = "body")
    val body: LocationBody,
)

@Xml(name = "header")
data class LocationHeader(
    @PropertyElement(name = "resultCode")
    val resultCode: String,
    @PropertyElement(name = "resultMsg")
    val resultMsg: String,
)

@Xml(name = "body")
data class LocationBody(
    @Element(name = "items")
    val items: Items,
    @PropertyElement(name = "numOfRows")
    val numOfRows: Int,
    @PropertyElement(name = "pageNo")
    val pageNo: Int,
    @PropertyElement(name = "totalCount")
    val totalCount: Int,
)

@Xml(name= "items")
data class Items(
    @Element(name="item")
    val item: List<LocationItem>
)

@Xml
data class LocationItem(
    @PropertyElement(name = "dutyAddr")
    val dutyAddr: String,
    @PropertyElement(name = "dutyName")
    val dutyName: String,
    @PropertyElement(name = "dutyTel1")
    val dutyTel1: String,
    @PropertyElement(name = "hpid")
    val hpid: String,
    @PropertyElement(name = "latitude")
    val latitude: Double,
    @PropertyElement(name = "longitude")
    val longitude: Double,
)