// Agent alice in project rv4jaca_sample

/* Initial beliefs and rules */

/* Initial goals */

!start.

/* Plans */

+!start : true <- .print("hello world.").


+!kqml_received(bob,question,hi(Msg),MsgId)
<- 	.wait(100);
	.print("How are you bob?");
	.send(bob,question,hi("How are you bob?")).
	

+!kqml_received(bob,question,Msg,MsgId)
<- 	.wait(100);
	.print("Oh, sorry bob. I'm fine!");
	.send(bob,assert,hi("Oh, sorry bob. I'm fine!")).
	
+!kqml_received(S,P,M,MsgId)
	<-.print("Received - ", M," from - ",S).

{ include("$jacamoJar/templates/common-cartago.asl") }
{ include("$jacamoJar/templates/common-moise.asl") }

// uncomment the include below to have an agent compliant with its organisation
//{ include("$moiseJar/asl/org-obedient.asl") }
