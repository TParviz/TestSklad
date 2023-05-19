package tj.test.testsklad.ui

import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.internal.Contexts.getApplication
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import tj.test.testsklad.data.models.PriemkaDataResponse
import tj.test.testsklad.data.models.PriemkaDetailsResponse
import tj.test.testsklad.data.models.SendCodesRequest
import tj.test.testsklad.domain.db.AppDatabase
import tj.test.testsklad.domain.db.info.PriemkaInfoEntity
import tj.test.testsklad.domain.net.GetPriemkaDetailsUseCaseImpl
import tj.test.testsklad.domain.net.GetPriemkaUseCaseImpl
import tj.test.testsklad.domain.net.SendBarcodesUseCaseImpl
import tj.test.testsklad.ui.models.PriemkaDetailsUi
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getPriemkaUseCaseImpl: GetPriemkaUseCaseImpl,
    private val getPriemkaDetailsUseCaseImpl: GetPriemkaDetailsUseCaseImpl,
    private val sendBarcodesUseCaseImpl: SendBarcodesUseCaseImpl,
    private val application: Application
) : ViewModel() {
    
    private val _priemkaList = MutableSharedFlow<List<PriemkaDataResponse>>()
    val priemkaList = _priemkaList.asSharedFlow()

    private val _priemkaDetailsList = MutableSharedFlow<List<PriemkaDetailsResponse>>()
    val priemkaDetailsList = _priemkaDetailsList.asSharedFlow()

    private val _scannedBarcode = MutableLiveData<SendCodesRequest>()
    val scannedBarcode: LiveData<SendCodesRequest> = _scannedBarcode

    private val _barcodesList = MutableLiveData<List<String>>()
    val barcodesList: LiveData<List<String>> = _barcodesList

    private val _isResponseOk = MutableLiveData(false)
    val isResponseOk: LiveData<Boolean> = _isResponseOk

    private val context = getApplication(application).applicationContext

    private var currentPriemkaDetails: PriemkaDetailsUi? = null

    private val sharedPreferences = context.getSharedPreferences("userinfo", Context.MODE_PRIVATE)

    private val priemkaCode = sharedPreferences?.getString("priemka", "")

    private val db = AppDatabase(context)

    fun sendBarcodes() {
        viewModelScope.launch {
            _scannedBarcode.value?.let {
                sendBarcodesUseCaseImpl(it).collect { result ->
                    result.onFailure {
                        _isResponseOk.value = false
                    }.onSuccess {
                        deleteFromDB()
                        _isResponseOk.value = true
                    }
                }
            }
        }
    }

    fun deleteFromDB() {
        GlobalScope.launch {
            db.priemkaInfoDao().deleteByPriemka(seria = currentPriemkaDetails?.series.toString())
        }
    }

    fun setCurrentPriemkaDetails(details: PriemkaDetailsUi) {
        currentPriemkaDetails = details
    }

    fun addBarcodeToList(barcode: String) {
        // Получаем текущее значение списка
        val currentList = _barcodesList.value ?: emptyList()

        if (!currentList.contains(barcode)) {
            // Создаем новый список, добавляя новый элемент
            val newList = currentList.toMutableList().apply { add(barcode) }

            // Устанавливаем обновленный список в MutableLiveData
            _barcodesList.value = newList

            addBarcode()
        }
    }

    private fun addBarcode() {
        _scannedBarcode.value = SendCodesRequest(
            priemka = priemkaCode.toString(),
            code = currentPriemkaDetails?.code.toString(),
            seria = currentPriemkaDetails?.series.toString(),
            barCodes = _barcodesList.value ?: emptyList()
        )
        val list = _scannedBarcode.value
        list?.let {
            updateDatabase(it)
        }
    }

    private fun updateDatabase(sendCodeInfo: SendCodesRequest) {
        GlobalScope.launch {
            db.priemkaInfoDao().insertAll(
                PriemkaInfoEntity(
                    priemka = sendCodeInfo.priemka,
                    code = sendCodeInfo.code,
                    seria = sendCodeInfo.seria,
                    barCodes = sendCodeInfo.barCodes
                )
            )
        }
    }

    fun getAllDb() {
        viewModelScope.launch {
            val list =
                db.priemkaInfoDao().findByTitle(currentPriemkaDetails?.series.toString()) ?: null
            if (list?.barCodes?.isNotEmpty() == true) {
                _barcodesList.value = list.barCodes
                _scannedBarcode.value = SendCodesRequest(
                    priemkaCode.toString(),
                    currentPriemkaDetails?.code ?: "",
                    currentPriemkaDetails?.series ?: "",
                    list.barCodes
                )
            }
        }
    }

    fun getPriemkaList() {
        viewModelScope.launch {
            getPriemkaUseCaseImpl(Unit).collect { result ->
                result.onFailure {
                }.onSuccess { response ->
                    _priemkaList.emit(response)
                }

            }
        }
    }

    fun getPriemkaDetailsList(code: String) {
        viewModelScope.launch {
            getPriemkaDetailsUseCaseImpl(code).collect { result ->
                result.onFailure {
                }.onSuccess { response ->
                    _priemkaDetailsList.emit(response)
                }
            }
        }
    }
}