package com.template.flows

import co.paralleluniverse.fibers.Suspendable
import com.template.contracts.CarContract
import com.template.contracts.CarContract.Companion.CAR_CONTRACT_ID
import com.template.states.CarState
import net.corda.core.flows.*
import net.corda.core.identity.Party
import net.corda.core.transactions.TransactionBuilder
import net.corda.core.utilities.ProgressTracker
import net.corda.core.contracts.Command

// *********
// * Flows *
// *********
@InitiatingFlow
@StartableByRPC
class ShipmentFlowInitiator(val model: String, val owner: Party) : FlowLogic<Unit>() {
    override val progressTracker = ProgressTracker()

    @Suspendable
    override fun call() {
        // Initiator flow logic goes here.

        //Checking if node is AM General
        check(ourIdentity.name.organisation == "AM General") {"Only the manufacturer can issue shipment"}

        //Retrieve the notary identity from the network
        val notary = serviceHub.networkMapCache.notaryIdentities.first()

        //Create the transaction components (inputs/outputs)
        val outputState = CarState(model, owner, ourIdentity)
        val command = CarContract.Commands.Shipment()

        //Create the transaction builder and add components
        val txBuilder = TransactionBuilder(notary = notary)
                .addOutputState(outputState, CAR_CONTRACT_ID)
                .addCommand(command, ourIdentity.owningKey)

        //signing the transaction
        val signedTransaction = serviceHub.signInitialTransaction(txBuilder)

        //Create session with counterparty
        val otherPartySession = initiateFlow(owner)

        //Finalise the transaction (using subflow)
        subFlow(FinalityFlow(signedTransaction, otherPartySession))
    }
}

@InitiatedBy(ShipmentFlowInitiator::class)
class ShipmentFlowResponder(val otherPartySession: FlowSession) : FlowLogic<Unit>() {
    @Suspendable
    override fun call() {
        // Responder flow logic goes here.
        subFlow(ReceiveFinalityFlow(otherPartySession))
    }
}
