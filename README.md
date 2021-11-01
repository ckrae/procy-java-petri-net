# procy-java-petri-net
 A simple Java Petri net

### Create Petri net
 ```java  
  	PetriNet net = new PetriNet();
        
        Transition t1 = net.transition(); 
        Place p1 = net.place();
        Place p2 = net.place();
        
        Arc a1 = net.arc(p1, t1);
        Arc a2 = net.arc(t1, p2);     

```

### Fire Petri net
 ```java  
  	Marking marking = new Marking(p1);        
        Marking result = net.fire(marking)      

```