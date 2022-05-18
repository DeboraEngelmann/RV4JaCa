// CArtAgO artifact code for project rv4JaCa

package rv4JaCa;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;

import cartago.*;
import jason.asSemantics.Message;
import jason.asSyntax.ListTermImpl;
import jason.asSyntax.Literal;
import jason.asSyntax.Term;

public class RV4JaCaArtifact extends Artifact implements IMonitor{
	 RV4JaCaLog log = new RV4JaCaLog("rv4JaCaLog-"+(new Date()).getTime()+".txt");
	 private Gson gson = new Gson();
	 
	void init() {
		System.out.println("[RV4JaCa] Artifact enabled");
		SnifferCentralised.setListener(this);
	}
	
	
	@Override
	public void propagateToMonitor(Message m) {
		String res = messageToJson(m); 
		log.add(res);
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
		msgSent.setContent(mapContent((Term) m.getPropCont()));
		return gson.toJson(msgSent);
	}

	HashMap<String, Object> mapContent(Term c) {
		HashMap<String, Object> content = new HashMap<String, Object>();
		if (c.isLiteral()) {
			content.putAll(literalToJSON((Literal) c));
		} else {
			content.putAll((Map<? extends String, ? extends Object>) content.entrySet());
		}
		return content;
	}

	public HashMap<String, Object> literalToJSON(Literal l) {
		HashMap<String, Object> content = new HashMap<String, Object>();
		content.put("name", l.getFunctor());
		content.put("isNegated", l.negated());
		if (l.hasTerm()) {
			int i = 0;
			for (Term term : l.getTerms()) {
				if (term.isAtom()) {
					if (!((Literal) term).getFunctor().equals(l.getFunctor())) {
						if ((!((Literal) term).getFunctor().equals("default"))) {
							i++;
							content.put("prop" + i, term);
						}
					}
				} else if (term.isLiteral()) {
					if (!((Literal) term).getFunctor().equals(l.getFunctor())) {
						if ((!((Literal) term).getFunctor().equals("default"))) {
							i++;
							content.put("prop" + i, literalToJSON((Literal) term));
						}
					}

				} else if (term.isList()) {
					List<Object> lt = listTermToJSON((ListTermImpl) term);

					i++;
					HashMap<String, Object> arrObject = new HashMap<String, Object>();
					arrObject.put("length", lt.size());
					if (lt.size() > 0) {
						arrObject.put("arr", lt);
					}
					content.put("prop" + i, arrObject);

				} else {
					i++;
					content.put("prop" + i, term.toString());
				}

			}
		}
		return content;
	}

	public List<Object> listTermToJSON(ListTermImpl terms) {
		List<Object> content = new ArrayList<Object>();
		int i = 0;
		for (Term t : terms) {
			if (t.isAtom()) {
				if ((!((Literal) t).getFunctor().equals("default"))) {
					i++;
					content.add(t);
				}
			} else if (t.isLiteral()) {
				Literal l = (Literal) t;
				i++;
				content.add(literalToJSON(l));
			} else if (t.isList()) {
				ListTermImpl lt = (ListTermImpl) t;
				List<Object> ltf = listTermToJSON(lt);
				if (ltf.size() > 0) {
					i++;
					content.add(ltf);
				}
			} else {
				i++;
				content.add(t.getAsJSON(""));
			}
		}
		return content;
	}
}

