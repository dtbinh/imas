/**
 *  IMAS base code for the practical work.
 *  Copyright (C) 2014 DEIM - URV
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package cat.urv.imas.agent;

import static cat.urv.imas.agent.ImasAgent.OWNER;
import jade.core.*;
import jade.domain.*;
import jade.domain.FIPAAgentManagement.*;

public class Scout extends ImasAgent {

    /**
     * Game settings in use.
     */
//    private GameSettings game;
    /**
     * System agent id.
     */
    private AID scout;

    /**
     * Builds the coordinator agent.
     */
    public Scout() {
        super(AgentType.SCOUT);
    }

    /**
     * Agent setup method - called when it first come on-line. Configuration of
     * language to use, ontology and initialization of behaviours.
     */
    @Override
    protected void setup() {

        /* ** Very Important Line (VIL) ***************************************/
        this.setEnabledO2ACommunication(true, 1);
        /* ********************************************************************/

        // Register the agent to the DF
        ServiceDescription sd1 = new ServiceDescription();
        sd1.setType(AgentType.SCOUT.toString());
        sd1.setName(getLocalName());
        sd1.setOwnership(OWNER);
        
        DFAgentDescription dfd = new DFAgentDescription();
        dfd.addServices(sd1);
        dfd.setName(getAID());
        try {
            DFService.register(this, dfd);
            log("Registered to the DF");
        } catch (FIPAException e) {
            System.err.println(getLocalName() + " registration with DF unsucceeded. Reason: " + e.getMessage());
            doDelete();
        }

        // search SystemAgent
        ServiceDescription searchCriterion = new ServiceDescription();
        searchCriterion.setType(AgentType.SCOUT.toString());
        this.scout = UtilsAgents.searchAgent(this, searchCriterion);
        // searchAgent is a blocking method, so we will obtain always a correct AID

        /* ********************************************************************/
//        ACLMessage initialRequest = new ACLMessage(ACLMessage.REQUEST);
//        initialRequest.clearAllReceiver();
//        initialRequest.addReceiver(this.scout);
//        initialRequest.setProtocol(InteractionProtocol.FIPA_REQUEST);
//        log("Request message to agent");
//        try {
//            initialRequest.setContent(MessageContent.GET_MAP);
//            log("Request message content:" + initialRequest.getContent());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        //we add a behaviour that sends the message and waits for an answer
//        this.addBehaviour(new RequesterBehaviour(this, initialRequest));

        // setup finished. When we receive the last inform, the agent itself will add
        // a behaviour to send/receive actions
    }
}