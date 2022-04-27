!start(1).

+!start(X) : true
   <- .println("Sending tell vl(10)");
      .send(maria, tell, vl(X));

      .println("Sending achieve goto(10,2)");
      .send(maria, achieve, goto(10,2));

      .wait(500);
      !start(X+1).