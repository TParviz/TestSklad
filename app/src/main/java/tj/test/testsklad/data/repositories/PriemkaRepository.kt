package tj.test.testsklad.data.repositories

import android.app.Application
import tj.test.testsklad.data.models.PriemkaDataResponse
import tj.test.testsklad.data.models.PriemkaDetailsResponse
import tj.test.testsklad.data.models.SendCodesRequest
import tj.test.testsklad.data.repositories.api.CommonConnection
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PriemkaRepository @Inject constructor(val application: Application) {

    suspend fun getPriemka(): List<PriemkaDataResponse> {
        return CommonConnection(application).priemkaApi.getPriemkaList()
    }

    suspend fun getPriemkaDetails(code: String) : List<PriemkaDetailsResponse> {
        return CommonConnection(application).priemkaApi.getPriemkaDetails(code)
    }

    suspend fun sendBarcodes(details: SendCodesRequest) {
        return CommonConnection(application).priemkaApi.sendCode(details)
    }

}