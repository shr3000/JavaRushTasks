package com.javarush.task.task30.task3004;

import com.sun.jmx.remote.internal.ArrayQueue;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

public class BinaryRepresentationTask extends RecursiveTask<String> {

    private int i;
    public BinaryRepresentationTask(int i) {
        this.i = i;
    }

    @Override
    protected String compute() {
        int a = i % 2;
        int b = i / 2;
        List<BinaryRepresentationTask> list = new LinkedList<>();
        String result = String.valueOf(a);
        if (b > 0) {
            BinaryRepresentationTask binaryRepresentationTask = new BinaryRepresentationTask(b);
            binaryRepresentationTask.fork();
            list.add(binaryRepresentationTask);
        }
        for (BinaryRepresentationTask bb: list) {
            result = bb.join() + result;
        }
        return result;
    }
}
