package com.template.contracts

import com.template.states.CarState
import net.corda.core.contracts.CommandData
import net.corda.core.contracts.Contract
import net.corda.core.contracts.requireThat
import net.corda.core.transactions.LedgerTransaction
import java.lang.IllegalArgumentException

// ************
// * Contract *
// ************
class CarContract : Contract {
    companion object {
        // Used to identify our contract when building a transaction.
        const val CAR_CONTRACT_ID = "com.template.contracts.CarContract"
    }

    // A transaction is valid if the verify() function of the contract of all the transaction's input and output states
    // does not throw an exception.
    override fun verify(tx: LedgerTransaction) {
        // Verification logic goes here.
        val output = tx.outputsOfType<CarState>().single()
        val command = tx.getCommand<Commands>(0)
        when (command.value){
            //shipment rules
            is Commands.Shipment -> {
                requireThat {
                    "manufacturer is a required signer" using (command.signers.contains(output.manufacturer.owningKey))
                    "the model is a " using (output.model == "NXT 360")
                }
            }
        }
    }

    // Used to indicate the transaction's intent.
    interface Commands : CommandData {
        class Shipment : Commands
    }
}