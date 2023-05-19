package tj.test.testsklad.data.repositories.api

import android.app.Application
import tj.test.testsklad.data.ConnectionFactory

class CommonConnection(application: Application): ConnectionFactory() {
    val priemkaApi: PriemkaApi = getRetrofit(application).create(PriemkaApi::class.java)
}