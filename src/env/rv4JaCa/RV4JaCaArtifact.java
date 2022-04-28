// CArtAgO artifact code for project rv4JaCa

package rv4JaCa;

import java.util.logging.Level;

import com.google.gson.Gson;

import cartago.*;
import jason.asSemantics.Message;

public class RV4JaCaArtifact extends Artifact implements IMonitor{
	 RV4JaCaLog log = new RV4JaCaLog("rv4JaCaLog.txt");
	 private Gson gson = new Gson();
	 
	void init() {
		System.out.println("[RV4JaCa] Artifact enabled");
		SnifferCentralised.setListener(this);
		log.logger.setLevel(Level.INFO);
	}
	
	
	@Override
	public void propagateToMonitor(Message m) {
		String res = messageToJson(m); 
		System.out.println(res);
		log.logger.info(res);
	}
	
	String messageToJson(Message m) {
		MsgSent msgSent = new MsgSent();
		msgSent.setMsgId(m.getMsgId());
        if (m.getInReplyTo() == null) {
        	msgSent.setIsReply("nirt");
        } else {
        	msgSent.setIsReply(m.getInReplyTo());
        }
        msgSent.setPerformative(m.getIlForce());
        msgSent.setSender(m.getSender());
        msgSent.setReceiver(m.getReceiver());
        msgSent.setContent(m.getPropCont().toString());
		return gson.toJson(msgSent);
	}
}

