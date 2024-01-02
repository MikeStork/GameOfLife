package app;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class UpdateGameStateTask implements Runnable {

    private CyclicBarrier barrier;
    Projector projectorToUpdate;
    Integer START_X;
    Integer START_Y;
    Integer END_X;
    Integer END_Y;
    public UpdateGameStateTask(CyclicBarrier barrier, Projector projectorToUpdate, Integer START_X, Integer START_Y, Integer END_X, Integer END_Y) {
        this.barrier = barrier;
        this.projectorToUpdate = projectorToUpdate;
        this.START_X = START_X;
        this.START_Y = START_Y;
        this.END_X = END_X;
        this.END_Y = END_Y;
    }

    @Override
    public void run() {
        try {
            System.out.println(Thread.currentThread().threadId() +
                    " is waiting");
            projectorToUpdate.update(START_X,START_Y,END_X,END_Y);
            barrier.await();
            System.out.println(Thread.currentThread().threadId() +
                    " is released \nProcessed rows from: "+START_Y+" to "+(END_Y-1)+" Overall: "+(END_Y-START_Y)+ "\nProcessed cols from: "+START_X+" to "+(END_X-1)+" Overall: "+String.valueOf(END_X-START_X));
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }
    }

}