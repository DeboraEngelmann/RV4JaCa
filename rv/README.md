## To run this example

In the monitor's folder run

```
sh online_monitor.sh ./../compiler/question_answer.pl 8088
```


## To complile a propertie

Create an RML propertie.
In the compiler jar's folder run

```
java -jar ./rml-compiler.jar --input <rml-propertie.rml> --output ./<propertie-name.pl>
```



## To run the monitor

In the monitor's folder run
```
sh online_monitor.sh <compiled-rml-propertie.pl> 8088
```
To stop the monitor press `CTRL+D`.


