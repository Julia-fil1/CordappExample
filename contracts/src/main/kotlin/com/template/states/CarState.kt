package com.template.states

import com.template.contracts.CarContract
import net.corda.core.contracts.BelongsToContract
import net.corda.core.contracts.ContractState
import net.corda.core.identity.AbstractParty
import net.corda.core.identity.Party

// *********
// * State *
// *********
@BelongsToContract(CarContract::class)
data class CarState(val model: String,
                    val owner: Party,
                    val manufacturer: Party,
                    override val participants: List<AbstractParty> = listOf(owner, manufacturer)) : ContractState
