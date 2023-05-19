package tj.test.testsklad

import android.app.Application
import android.content.Context
import dagger.hilt.android.internal.Contexts
import okhttp3.Credentials

fun collectUrl(application: Application): String {
    val context = Contexts.getApplication(application).applicationContext
    val sharedPreferences = context.getSharedPreferences("userinfo", Context.MODE_PRIVATE)
    val login = sharedPreferences?.getString("login", "")
    val password = sharedPreferences?.getString("password", "")
    val link = sharedPreferences?.getString("link", "")
    return "https://$login:$password@$link/"
}

fun getCredentials(application: Application): String {
    val context = Contexts.getApplication(application).applicationContext
    val sharedPreferences = context.getSharedPreferences("userinfo", Context.MODE_PRIVATE)
    val defaultLogin = context.getString(R.string.default_user_login)
    val defaultPassword = context.getString(R.string.default_user_password)
    var login = sharedPreferences?.getString("login", "") ?: defaultLogin
    var password = sharedPreferences?.getString("password", "") ?: defaultPassword
    if (login.isEmpty() && password.isEmpty()) {
        login = defaultLogin
        password = defaultPassword
    }
    return Credentials.basic(login, password)
}