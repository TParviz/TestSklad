package tj.test.testsklad.domain.net

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import tj.test.testsklad.data.models.PriemkaDataResponse
import tj.test.testsklad.data.repositories.PriemkaRepository
import javax.inject.Inject

interface GetPriemkaUseCase : FlowUseCase<Unit, List<PriemkaDataResponse>>

class GetPriemkaUseCaseImpl @Inject constructor(
    private val priemkaRepository: PriemkaRepository
) : GetPriemkaUseCase {
    override fun execute(param: Unit): Flow<Result<List<PriemkaDataResponse>>> = flow {
        val result = priemkaRepository.getPriemka()
        emit(Result.success(result))
    }

}