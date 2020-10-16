import javax.swing.JOptionPane;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

public class Agent1 extends Agent {
	private boolean flag=true;
	
	protected  void setup() { 
	 addBehaviour(new CyclicBehaviour() {
		
		@Override
		public void action() {
			// TODO Auto-generated method stub
			ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
			
			if(flag) {
				msg.setContent("Ping").addReceiver(new AID("a2",AID.ISLOCALNAME));
				send(msg);
				flag=false;
			}
			
			String sentMsg = JOptionPane.showInputDialog("Message sent:\nAny question you want to ask?");
			msg.setContent(sentMsg);
			send(msg);
			
		    	ACLMessage receiveMsg = receive();
            		if (receiveMsg!=null) {
                		System.out.println( " - " + myAgent.getLocalName() + " <- " + receiveMsg.getContent() );
            		}
			
			block();
		}
	});
   }
} 
