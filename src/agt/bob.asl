// Agent bob in project rv4jaca_sample

/* Initial beliefs and rules */

/* Initial goals */

!start.

/* Plans */

+!start : true <- .print("hello world."); !hiAlice.

+!hiAlice
<-  .wait(1000);
	.print("Hi Alice! How are you?");
	.send(alice,question,hi("Hi Alice! How are you?")).

/**
 * Violation
 */
+!kqml_received(monitor,assert,Violation,MsgId)
	<-	.print("Agent monitor reported a violation.");
		!verifyViolation(Violation).
			
+!verifyViolation(violation(Time,Id,IsReply,Performative,Sender,Receiver,Veredict))
<-  .print("I'm sorry ",Sender,", you have to answer my question before doing yours");
	.send(Sender,question,msg("I'm sorry, you have to answer my question before doing yours")).

+!verifyViolation(V).

+!kqml_received(S,P,M,MsgId)
	<-.print("Received - ", M," from - ",S).

{ include("$jacamoJar/templates/common-cartago.asl") }
{ include("$jacamoJar/templates/common-moise.asl") }

// uncomment the include below to have an agent compliant with its organisation
//{ include("$moiseJar/asl/org-obedient.asl") }
