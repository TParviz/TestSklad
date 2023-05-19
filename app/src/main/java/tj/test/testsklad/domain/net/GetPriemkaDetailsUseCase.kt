package tj.test.testsklad.domain.net

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import tj.test.testsklad.data.models.PriemkaDetailsResponse
import tj.test.testsklad.data.repositories.PriemkaRepository
import javax.inject.Inject

interface GetPriemkaDetailsUseCase : FlowUseCase<String, List<PriemkaDetailsResponse>>

class GetPriemkaDetailsUseCaseImpl @Inject constructor(
    private val priemkaRepository: PriemkaRepository
) : GetPriemkaDetailsUseCase {
    override fun execute(param: String): Flow<Result<List<PriemkaDetailsResponse>>> = flow {
        val result = priemkaRepository.getPriemkaDetails(param)
        emit(Result.success(result))
    }
}