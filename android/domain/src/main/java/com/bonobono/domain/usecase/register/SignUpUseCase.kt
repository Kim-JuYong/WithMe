package com.bonobono.domain.usecase.register

import com.bonobono.domain.model.NetworkResult
import com.bonobono.domain.model.registration.Member
import com.bonobono.domain.repository.registration.RegisterRepository
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
    private val registerRepository : RegisterRepository
) {
     suspend operator fun invoke(member : Member) : NetworkResult<Member> {
         return registerRepository.signUp(member)
     }
}