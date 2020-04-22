package Utils;

import java.util.HashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;

//TODO remove class after talk to Yuval
public class OurHashMap<K,V> extends HashMap<K,V> {
    private ReentrantReadWriteLock lock;

    public OurHashMap() {
        super();
        this.lock = new ReentrantReadWriteLock();
    }

    @Override
    public V remove(Object key) {
        lock.writeLock().lock();
        V value=super.remove(key);
        lock.writeLock().unlock();
        return value;
    }

    @Override
    public V put(K key, V value) {
        lock.writeLock().lock();
        V val=super.put(key, value);
        lock.writeLock().unlock();
        return val;
    }

    @Override
    public V get(Object key) {
        lock.readLock().lock();
        V value=super.get(key);
        lock.readLock().unlock();
        return value;
    }

    @Override
    public boolean containsKey(Object key) {
        lock.readLock().lock();
        boolean found=super.containsKey(key);
        lock.readLock().unlock();
        return found;
    }
}
