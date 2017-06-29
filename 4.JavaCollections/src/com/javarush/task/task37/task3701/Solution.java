package com.javarush.task.task37.task3701;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/* 
Круговой итератор
*/
public class Solution<T> extends ArrayList<T> {
    public static void main(String[] args) {
        Solution<Integer> list = new Solution<>();
        list.add(1);
        list.add(2);
        list.add(3);

        int count = 0;
        for (Integer i : list) {
            //1 2 3 1 2 3 1 2 3 1
            if (i == 2) list.remove(i);
            System.out.print(i + " ");

            count++;
            if (count == 10) {
                break;
            }
        }
    }


    @Override
    public Iterator<T> iterator() {
        return new RoundIterator();
    }

    public class RoundIterator implements Iterator<T>{
        private Iterator<T> iterator;

        public RoundIterator() {
            this.iterator = Solution.super.iterator();
        }

        @Override
        public boolean hasNext() {
            return Solution.this.size() > 0;
        }

        @Override
        public T next() {
            if (!iterator.hasNext()) iterator = Solution.super.iterator();
            return iterator.next();
        }

        @Override
        public void remove() {
            iterator.remove();
        }
//        int cursor;
//        int lastRet = -1;
////        int expectedModCount = modCount;
//
//        @Override
//        public boolean hasNext() {
//            return Solution.this.size() > 0;
//        }
//
//        @Override
//        public T next() {
////            checkForComodification();
//            if (cursor == Solution.this.size()) cursor = 0;
//            int i = cursor;
//            if (i >= Solution.this.size())
//                throw new NoSuchElementException();
//            Object[] elementData = Solution.this.toArray();
//            if (i >= elementData.length)
//                throw new ConcurrentModificationException();
//            cursor = i + 1;
//            return (T) elementData[lastRet = i];
//        }
//
//        @Override
//        public void remove() {
//            if (lastRet < 0)
//                throw new IllegalStateException();
////            checkForComodification();
//
//            try {
//                Solution.this.remove(lastRet);
//                cursor = lastRet;
//                lastRet = -1;
////                expectedModCount = modCount;
//
//            } catch (IndexOutOfBoundsException ex) {
//                throw new ConcurrentModificationException();
//            }
//        }
////        final void checkForComodification() {
////            if (modCount != expectedModCount)
////                throw new ConcurrentModificationException();
////        }
    }
}
