/* creation of Thread */
// Thread class implementation
class ThreadImpl extends Thread{
    
    @Override
    public void run(){
        
        // Output: Thread Class Running
        System.out.println("Thread Class Running");
    }
}

// Runnable interface implementation
class RunnableThread implements Runnable{
    
    @Override
    public void run(){
        
        // Output: Runnable Thread Running
        System.out.println("Runnable Thread Running");
    }
}

public class Geeks{
    
    public static void main(String[] args){
        
        // Create and start Thread class thread
        ThreadImpl t1 = new ThreadImpl();
        t1.start();

        // Create and start Runnable interface thread
        RunnableThread r = new RunnableThread();
        Thread t2 = new Thread(r);
        t2.start();

        // Wait for both threads to complete
        try {
            t1.join(); // Wait for t1
            t2.join(); // Wait for t2
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

/*------------------  Monitor Locks - using Synchronized block and method ------------------*/
public class MultiThreading {
  //Synchronized method
    public synchronized void task1(){
        try{
            System.out.println("Task 1 executed");
            Thread.sleep(10000);
        }catch (Exception e){
            //do something
        }
    }
    public void task2(){
        System.out.println("Task 2 before synchronization...");
        synchronized (this){ //Synchronized block
            System.out.println("Task 2 executed");
        }
    }
    public void task3(){
        System.out.println("Task 3 executed");
    }
}

import javax.swing.*;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;


public class Main {
    public static void main(String[] args) {
        MultiThreading Obj = new MultiThreading();
        Thread t1 = new Thread(()->Obj.task1());
        Thread t2 = new Thread(()-> Obj.task2());
        Thread t3 = new Thread(()-> Obj.task3());

        t1.start();
        t2.start();
        t3.start();

    }

}
