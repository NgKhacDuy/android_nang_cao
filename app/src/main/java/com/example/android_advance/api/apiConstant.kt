package com.example.android_advance.api

class apiConstant {
    companion object {
        //const val baseApi = "https://android-nang-cao-backend.onrender.com/api/v1/"
            const val baseApi="http://192.168.183.214:3000/api/v1/"
        //        const val baseApi = "http:192.168.100.2:3000/api/v1/"
        const val userSignUpApi = "user/signup"
        const val userSignInApi = "user/signin"
        const val userRefresh = "user/refresh"
        const val userInfo = "user/profile"
        const val userSearch = "user/search"
        const val userFriend = "friend"
        const val userSignOut = "user/logout"
        const val agora = "agora"
        const val userPatchPassword = "user/password"
        const val userPatchInfo = "user"
        const val friendRequest = "user/friendrequest"
        const val userImg = "user/image"
        const val isPhoneNumberExist = "user/isPhoneNumberExist"
        const val resetPassword="user/resetPassword"
        const val generateOtp="user/generateotp"
    }

}