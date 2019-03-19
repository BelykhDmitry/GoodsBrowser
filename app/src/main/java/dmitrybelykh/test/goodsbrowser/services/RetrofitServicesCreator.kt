package dmitrybelykh.test.goodsbrowser.services

interface RetrofitServicesCreator {
    fun createRetrofitServices(
        login: String,
        password: String
    ): RetrofitServices?
}