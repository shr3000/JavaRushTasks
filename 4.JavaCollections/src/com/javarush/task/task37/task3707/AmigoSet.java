package com.javarush.task.task37.task3707;

import java.io.IOException;
import java.io.Serializable;
import java.util.*;

public class AmigoSet<E> extends AbstractSet<E> implements Serializable, Cloneable, Set<E>{

    private static final Object PRESENT = new Object();
    private transient HashMap<E,Object> map;

    public AmigoSet() {
        map = new HashMap<>();
    }

    private void writeObject(java.io.ObjectOutputStream s)
            throws java.io.IOException {
        s.defaultWriteObject();
        s.writeInt(HashMapReflectionHelper.callHiddenMethod(map, "capacity"));
        s.writeFloat(HashMapReflectionHelper.callHiddenMethod(map,"loadFactor"));
        s.writeInt(map.size());
        for (E e : map.keySet())
            s.writeObject(e);
    }

    private void readObject(java.io.ObjectInputStream s) throws IOException, ClassNotFoundException {
        s.defaultReadObject();
        map = new HashMap<>(s.readInt(), s.readFloat());
        for (int i=0; i<s.readInt(); i++) {
            E e = (E) s.readObject();
            map.put(e, PRESENT);
        }
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        try {
            AmigoSet<E> copy = (AmigoSet) super.clone();
            copy.map = (HashMap) map.clone();
            return copy;
        } catch (Exception e) {
            throw new InternalError();
        }
    }

    @Override
    public Iterator<E> iterator() {
        return map.keySet().iterator();
    }
    @Override
    public int size() {
        return map.size();
    }
    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }
    @Override
    public boolean contains(Object o) {
        return map.containsKey(o);
    }
    @Override
    public boolean remove(Object o) {
        return map.remove(o)==PRESENT;
    }
    @Override
    public void clear() {
        map.clear();
    }

    public AmigoSet(Collection<? extends E> collection) {
        map = new HashMap<>(Integer.max(16, (int)(Math.ceil(collection.size()/.75f))));
        this.addAll(collection);
    }

    @Override
    public boolean add(E e) {
        return null == map.put(e, PRESENT);
    }


}
