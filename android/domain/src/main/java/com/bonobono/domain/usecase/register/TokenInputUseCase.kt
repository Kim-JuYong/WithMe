package com.bonobono.domain.usecase.register

import com.bonobono.domain.model.registration.Member
import com.bonobono.domain.model.registration.Token
import com.bonobono.domain.repository.registration.RegisterRepository
import javax.inject.Inject

class TokenInputUseCase @Inject constructor(
    private val registerRepository : RegisterRepository
) {
    suspend operator fun invoke(token : Token) {
        registerRepository.putToken(token)
    }
}