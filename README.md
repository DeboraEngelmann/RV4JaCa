## RV4JaCa
A runtime verification approach for multi-agent systems using JaCaMo


### Dependencies

- Java 11
- Gradle 6.8.3
- JaCaMo 0.9

### To build

```
gradle build
```

### To run

First run the RML monitor [Instructions here](/rv/README.md).

Then, run
```
gradle run
```

## To use RV4JaCa in your oun JaCaMo project

Download the [RV4JaCa module](/lib/rv4JaCa.zip), unzip inside the `env` folder in your project.

Add the rv4JaCa package in your `classpath`.

Create a monitor agent inside the `agt` folder and add the code
```
// Agent monitor

+!start : true <- .print("Monitor agent enabled.").

+violation(Time,Id,IsReply,Performative,Sender,Receiver,Veredict)
   <- 
   	  .print("Message ",Id," from ",Sender," to ",Receiver," at ", Time, " has a violation.")
   	  .send(Receiver,assert,violation(Time,Id,IsReply,Performative,Sender,Receiver,Veredict)).

+!kqml_received(S,P,M,MsgId).

{ include("$jacamoJar/templates/common-cartago.asl") }

```

Add the new agent created and the RV4JaCa artefact in your `.jcm` file.
```
agent monitor:monitor.asl {
    	focus: rv4jaca
        ag-arch: rv4JaCa.SnifferCentralised
    }

    workspace wp{
		artifact rv4jaca:rv4JaCa.RV4JaCaArtifact("ws://127.0.0.1:8088") // Address where your monitor run
	}
```

You can use the RML monitor and create your own properties. For more information [click here](/rv/README.md).

You can also use another formal monitor as long as it is possible to establish a connection via websocket.
