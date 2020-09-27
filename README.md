<p align="center">
  <img src="https://www.corda.net/wp-content/uploads/2016/11/fg005_corda_b.png" alt="Corda" width="500">
</p>

# Simple CorDapp Example

This CorDapp serves as a demo for a simple use case of a vehicle trading application. The hypothetical scenario I used is AM General selling their newly manufactured Humvee NXT 360 to US military. 

Although the project contains a Client folder, for the sake of simplicity I decided not to build the frontend.

The CorDapp templates for both Kotlin and Java can be found here:
- [Kotlin](https://github.com/corda/cordapp-template-kotlin)
- [Java](https://github.com/corda/cordapp-template-java)


# Pre-Requisites

See https://docs.corda.net/getting-set-up.html.

## Running the nodes

### On Windows
Open a terminal and go to the project root directory and type: (to deploy the nodes using bootstrapper)
- `gradlew clean deployNodes`

Then type: (to run the nodes)
- `build/nodes/runnodes.bat`

### On Mac
- `./gradlew clean deployNodes`
- `./build/nodes/runnodes`

See example here: https://docs.corda.net/tutorial-cordapp.html#running-the-example-cordapp.

## Interacting with the nodes

### Shell

When started via the command line, each node will display an interactive shell:

    Welcome to the Corda interactive shell.
    Useful commands include 'help' to see what is available, and 'bye' to shut down the node.
    
    Tue Nov 06 11:58:13 GMT 2018>>>

You can use this shell to interact with your node. For example, enter `run networkMapSnapshot` to see a list of 
the other nodes on the network:

    Tue Nov 06 11:58:13 GMT 2018>>> run networkMapSnapshot
    [
      {
      "addresses" : [ "localhost:10002" ],
      "legalIdentitiesAndCerts" : [ "O=Notary, L=London, C=GB" ],
      "platformVersion" : 3,
      "serial" : 1541505484825
    },
      {
      "addresses" : [ "localhost:10005" ],
      "legalIdentitiesAndCerts" : [ "O=PartyA, L=London, C=GB" ],
      "platformVersion" : 3,
      "serial" : 1541505382560
    },
      {
      "addresses" : [ "localhost:10008" ],
      "legalIdentitiesAndCerts" : [ "O=PartyB, L=New York, C=US" ],
      "platformVersion" : 3,
      "serial" : 1541505384742
    }
    ]
    
    Tue Nov 06 12:30:11 GMT 2018>>> 

You can find out more about the node shell [here](https://docs.corda.net/shell.html).

## Running flows in this Cordapp
This cordapp contains only one flow class called ShipmentFlow. To run the flow execute the following command from the AM General's interactive shell:
- `start com.template.flows.ShipmentFlowInitiator model: NXT 360, owner: US Military`

Once the flow is completed, we can query the US military's vault using the following command (execute this from US military's window):
- `run vaultQuery contractStateType: com.template.states.CarState`
