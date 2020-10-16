import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

public class Agent2 extends Agent{

	// URL to search on google
	public static final String GOOGLE_SEARCH_URL = "https://www.google.com/search";
	protected void setup() {
	
	addBehaviour(new CyclicBehaviour(this) {
              public void action() {
                 ACLMessage msg= receive();
		      
                 if (msg!=null) {
                     System.out.println( " - " + myAgent.getLocalName() + " <- " + msg.getContent() );
                     
               		if(!msg.getContent().toString().equals("No") || !msg.getContent().toString().equals("quit")) {
                  		// Exception handling
				try {  
					String searchURL = GOOGLE_SEARCH_URL + "?q="+msg.getContent().toString()+"&num="+10;
					
					//without proper User-Agent, we will get 403 error
					Document doc = Jsoup.connect(searchURL).userAgent("Mozilla/5.0").get();

					Elements results = doc.select("a[href]");
				    	int tmp = 0;             		
                   		 	for (Element result : results) {
             					if(tmp>=3 & tmp<=12) {
							String linkHref = result.attr("abs:href");
							String linkText = result.text();
							System.out.println("Text::" + linkText + ", URL::" + linkHref);
             					}
                           			tmp++;
             				}
             	
				   }catch (Exception e) {
					// TODO: handle exception
					System.out.println(e);
				    }
			      }

			   ACLMessage reply = new ACLMessage( ACLMessage.INFORM );
			   reply.setContent( "Thanks you!!" );
			   reply.addReceiver( msg.getSender() );
			   send(reply);
			}
		      
		 block();
              } 
         });
     }
}
