package com.edgetechs.salonx.common.loginactivity.merchant

import android.location.Address

class DataHelper {

    lateinit var fullname :String
    lateinit var username:String
    lateinit var emailAddress:String
    lateinit var mobile:String
    lateinit var password:String

    lateinit var salonName :String
    lateinit var salonAddress: String
    lateinit var salonWebsite:String
    lateinit var salonType:String
    lateinit var salonPhone :String
    lateinit var salonDescription:String
    lateinit var latitude:String
   lateinit var longitude:String
    constructor(){}

    constructor(
        fullname: String,
        username: String,
        emailAdress: String,
        mobile: String,
        password: String,
        salonName: String,
        salonAddress: String,
        salonWebsite: String,
        salonType: String,
        salonPhone: String,
        salonDescription: String,
        latitude:String,
       longitude:String
    ) {
        this.fullname = fullname
        this.username = username
        this.emailAddress = emailAdress
        this.mobile = mobile
        this.password = password
        this.salonName = salonName
        this.salonAddress = salonAddress
        this.salonWebsite = salonWebsite
        this.salonType = salonType
        this.salonPhone = salonPhone
        this.salonDescription = salonDescription
        this.latitude=latitude
        this.longitude=longitude
    }
}