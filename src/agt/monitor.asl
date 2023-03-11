// Agent monitor 

+!start : true <- .print("Monitor agent enabled.");.

+violation(Time,Id,IsReply,Performative,monitor,Receiver,Veredict)
	<- .print("####################### MY VIOLATION!!!! ##################### ").

+violation(Time,Id,IsReply,Performative,Sender,Receiver,Veredict)
   <- 
   	  .print("Message ",Id," from ",Sender," to ",Receiver," at ", Time, " has a violation.")
   	  .send(Receiver,assert,violation(Time,Id,IsReply,Performative,Sender,Receiver,Veredict)).

+!kqml_received(S,P,M,MsgId).

{ include("$jacamoJar/templates/common-cartago.asl") }