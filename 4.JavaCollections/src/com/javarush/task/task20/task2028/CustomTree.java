package com.javarush.task.task20.task2028;

import java.io.Serializable;
import java.util.AbstractList;
import java.util.Collection;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/* 
Построй дерево(1)
*/
public class CustomTree extends AbstractList<String> implements Cloneable, Serializable{

    Entry<String> root = new Entry<>("0");

    public static void main(String[] args) {
        List<String> list = new CustomTree();
        for (int i = 1; i < 16; i++) {
            list.add(String.valueOf(i));
        }
        System.out.println("Expected 3, actual is " + ((CustomTree) list).getParent("8"));
        list.remove("5");
        System.out.println("Expected null, actual is " + ((CustomTree) list).getParent("11"));
//        list.add(String.valueOf(16));
//        System.out.println("Expected , actual is " + ((CustomTree) list).getParent("16"));
    }

//    1. метод add(String s) — добавляет элементы дерева, в качестве параметра принимает имя элемента (elementName),
//    искать место для вставки начинаем слева направо.
//    @Override
    public boolean add(String s) {
        Queue<Entry<String>> queue = new ConcurrentLinkedQueue<>();
        queue.add(root);
        Entry<String> newEntry = new Entry<>(s);
        while (!queue.isEmpty()) {
            Entry<String> tempRoot = queue.poll();
            if (tempRoot.isAvailableToAddChildren()) {
                if (tempRoot.availableToAddLeftChildren) {
                    tempRoot.leftChild = newEntry;
                } else {
                    tempRoot.rightChild = newEntry;
                }
                newEntry.lineNumber = root.lineNumber++;
                newEntry.parent = tempRoot;
                tempRoot.checkChildren();
                break;
            } else {
                if (tempRoot.leftChild != null) queue.add(tempRoot.leftChild);
                if (tempRoot.rightChild != null) queue.add(tempRoot.rightChild);
            }
        }
        return true;
    }

//    2. метод remove(String s) — удаляет элемент дерева имя которого было полученного в качестве параметра.
//    @Override
    public boolean remove(Object s){
        Queue<Entry<String>> queue = new ConcurrentLinkedQueue<>();
        queue.add(root);
        while (!queue.isEmpty()){
            Entry<String> tempRoot = queue.poll();
            if (tempRoot.leftChild != null){
                if (tempRoot.leftChild.elementName.equals(s)) {
                    tempRoot.leftChild = null;
                    break;
                } else queue.add(tempRoot.leftChild);
            }
            if (tempRoot.rightChild != null){
                if (tempRoot.rightChild.elementName.equals(s)) {
                    tempRoot.rightChild = null;
                    break;
                } else queue.add(tempRoot.rightChild);
            }
        }
        return true;
    }
//    3. метод size() — возвращает текущее количество элементов в дереве.
//    @Override
    public int size() {
        int s = 0;
        Queue<Entry<String>> queue = new ConcurrentLinkedQueue<>();
        queue.add(root);
        while (!queue.isEmpty()){
            Entry<String> tempRoot = queue.poll();
            if (tempRoot.leftChild != null){
                s++;
                queue.add(tempRoot.leftChild);
            }
            if (tempRoot.rightChild != null){
                s++;
                queue.add(tempRoot.rightChild);
            }
        }
        return s;
    }
//    4. метод getParent(String s) — возвращает имя родителя элемента дерева, имя которого было полученного в качестве параметра.
    public String getParent(String s){
        Queue<Entry<String>> queue = new ConcurrentLinkedQueue<>();
        queue.add(root);
        while (!queue.isEmpty()){
            Entry<String> tempRoot = queue.poll();
            if (tempRoot.leftChild != null){
                if (tempRoot.leftChild.elementName.equals(s)) {
                   return tempRoot.elementName;
                } else queue.add(tempRoot.leftChild);
            }
            if (tempRoot.rightChild != null){
                if (tempRoot.rightChild.elementName.equals(s)) {
                    return tempRoot.elementName;
                } else queue.add(tempRoot.rightChild);
            }
        }
        return null;
    }

    static class Entry<T> implements Serializable{
        String elementName;
        int lineNumber;
        boolean availableToAddLeftChildren, availableToAddRightChildren;
        Entry<T> parent, leftChild, rightChild;

        public Entry(String elementName) {
            this.elementName = elementName;
            availableToAddLeftChildren = true;
            availableToAddRightChildren = true;
        }
        public void checkChildren(){
            if (leftChild != null) availableToAddLeftChildren = false;
            if (rightChild != null) availableToAddRightChildren = false;
        }
        public boolean isAvailableToAddChildren(){
            return availableToAddLeftChildren | availableToAddRightChildren;
        }
    }


    @Override
    public String get(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String set(int index, String element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void add(int index, String element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String remove(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(int index, Collection<? extends String> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<String> subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected void removeRange(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException();
    }
}