import sun.management.snmp.jvminstr.JvmThreadInstanceEntryImpl;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Author: hupenghui
 * Date: 2021/4/13 20:21
 * FileName: CASLockDemo
 * Description: CAS自旋锁
 */
public class CASLockDemo {

    AtomicReference<Thread> threadAtomicReference = new AtomicReference<>(null);

    public void mylock(){

        Thread currentThread = Thread.currentThread();
        System.out.println(currentThread.getName() + "come in......");
        while (!threadAtomicReference.compareAndSet(null, currentThread)){
            System.out.println(currentThread.getName() + "wait......");
        }

    }

    public void myUnlock(){
        Thread currentThread = Thread.currentThread();
        threadAtomicReference.compareAndSet(currentThread, null);
        System.out.println(currentThread.getName() + "come out......");
    }


    public static void main(String[] args) {
        CASLockDemo casLockDemo = new CASLockDemo();

        new Thread(()->{
            casLockDemo.mylock();
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            casLockDemo.myUnlock();
        }, "AAA").start();

        new Thread(()->{
            casLockDemo.mylock();
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            casLockDemo.myUnlock();
        }, "BBB").start();
    }
}
