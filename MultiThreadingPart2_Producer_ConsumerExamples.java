//Shared Resource
public class SharedResource {
    boolean isItemAvailable = false;

    public synchronized void addItem(){
        isItemAvailable = true;
        System.out.println("Item added by"+Thread.currentThread().getName());
        notifyAll();
    }
    public synchronized void consumeItem(){
        System.out.println("Consume method invoked by:"+Thread.currentThread().getName());
        while(!isItemAvailable){
            try{
                System.out.println("waiting now "+Thread.currentThread().getName());
                wait();
            }catch (Exception e){
                //do something
            }
        }
        System.out.println("Item consumed by:"+Thread.currentThread().getName());
        isItemAvailable = false;
    }
}

// Produce Item
public class ProduceItem implements Runnable{
    SharedResource sharedResource;
    ProduceItem(SharedResource sharedResource){
       this.sharedResource = sharedResource;
    }
    @Override
    public void run() {
        System.out.println("Producer thread:"+Thread.currentThread().getName());
        try{
            Thread.sleep(5000L);
        }catch (Exception e){
            //
        }
        sharedResource.addItem();
    }
}

// Consume Item
public class ConsumeItem implements Runnable{
    SharedResource sharedResource;
    ConsumeItem(SharedResource sharedResource){
        this.sharedResource =  sharedResource;
    }
    @Override
    public void run() {
        System.out.println("Consumer Thread:"+Thread.currentThread().getName());
        sharedResource.consumeItem();
    }
}

// main method
import javax.swing.*;
import java.util.*;


public class Main {
    public static void main(String[] args) {
        SharedResource resource = new SharedResource();
        Thread ProducerThread = new Thread(new ProduceItem(resource));
        Thread ConsumerThread = new Thread(new ConsumeItem(resource));

        ProducerThread.start();
        ConsumerThread.start();

    }

}

//using Lambda Expressions no need to implement ProductItema and ConsumeItem

import javax.swing.*;
import java.util.*;


public class Main {
    public static void main(String[] args) {
        SharedResource resource = new SharedResource();
        Thread ProducerThread = new Thread(()->{
            System.out.println("Producer Thread:"+Thread.currentThread().getName());
            try{
                Thread.sleep(5000L);
            }catch (Exception e){
                //
            }
            resource.addItem();
        });
        Thread ConsumerThread = new Thread(()->{
            System.out.println("Consumer Thread:"+Thread.currentThread().getName());
            resource.consumeItem();
        });

        ProducerThread.start();
        ConsumerThread.start();

    }

}

