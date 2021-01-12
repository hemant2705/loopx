package com.edgetechs.salonx.common.loginactivity.user

class userDatahelper {
    lateinit var fullname :String
    lateinit var mobile:String
    lateinit var password:String
    constructor(){}
    constructor(
        fullname: String,
        MobileNo: String,
        Password: String
    ) {
        this.fullname = fullname
        this.mobile= MobileNo
        this.password = Password}
}