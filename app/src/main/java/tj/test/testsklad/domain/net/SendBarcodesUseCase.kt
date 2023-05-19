package tj.test.testsklad.domain.net

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import tj.test.testsklad.data.models.SendCodesRequest
import tj.test.testsklad.data.repositories.PriemkaRepository
import javax.inject.Inject

interface SendBarcodesUseCase : FlowUseCase<SendCodesRequest, Unit>

class SendBarcodesUseCaseImpl @Inject constructor(
    private val priemkaRepository: PriemkaRepository
) : SendBarcodesUseCase {
    override fun execute(param: SendCodesRequest): Flow<Result<Unit>> = flow {
        val result = priemkaRepository.sendBarcodes(param)
        emit(Result.success(result))
    }
}
