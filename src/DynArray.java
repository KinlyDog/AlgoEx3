import java.lang.reflect.Array;

public class DynArray<T> {
    public T[] array;
    public int count;
    public int capacity;
    Class clazz;

    public DynArray(Class clz) {
        this.clazz = clz;
        this.count = 0;
        makeArray(16);
    }

    public void makeArray(int new_capacity) {
        if (this.count == 0) {
            this.array = (T[]) Array.newInstance(this.clazz, new_capacity);
            this.capacity = new_capacity;

            return;
        }

        if (new_capacity < 16) {
            new_capacity = 16;
        }

        T[] tmp = this.array;
        this.array = (T[]) Array.newInstance(this.clazz, new_capacity);
        this.capacity = new_capacity;

        int n = this.capacity;
        if (tmp.length < this.capacity) {
            n = tmp.length;
        }

        System.arraycopy(tmp, 0, this.array, 0, n);
    }

    public T getItem(int index) {
        exception(index);

        if (array[index] != null) {
            return array[index];
        }

        return null;
    }

    public void append(T itm) {
        capacityTest();

        array[count++] = itm;
    }

    public void insert(T itm, int index) {
        if (index < 0 || index > count) {
            throw new ArrayIndexOutOfBoundsException("Index Out Of Bound");
        }

        capacityTest();

        if (index == count) {
            append(itm);
            return;
        }

        System.arraycopy(array, index, array, index + 1, count++ - index);
        array[index] = itm;
    }

    public void remove(int index) {
        exception(index);

        if (index == count - 1) {
            array[--count] = null;
        } else {

            System.arraycopy(array, index + 1, array, index, --count - index);
            array[count] = null;
        }

        if (capacity > 16 && count < (capacity / 2)) {
            makeArray((int)(capacity / 1.5));
        }
    }

    public void capacityTest() {
        if (count >= capacity) {
            makeArray(capacity * 2);
        }
    }

    public void exception(int index) {
        if (index < 0 || index >= count) {
            throw new ArrayIndexOutOfBoundsException("Index Out Of Bound");
        }
    }
}
