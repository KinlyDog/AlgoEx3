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

        if (this.array[index] != null) {
            return this.array[index];
        }

        return null;
    }

    public void append(T itm) {
        capacityTest();

        this.array[this.count++] = itm;
    }

    public void insert(T itm, int index) {
        if (index < 0 || index > this.count) {
            throw new ArrayIndexOutOfBoundsException("Index Out Of Bound");
        }

        capacityTest();

        if (index == this.count) {
            append(itm);
            return;
        }

        System.arraycopy(this.array, index, this.array, index + 1, this.count++ - index);
        this.array[index] = itm;
    }

    public void remove(int index) {
        exception(index);

        if (index == this.count - 1) {
            this.array[--this.count] = null;
        } else {

            System.arraycopy(this.array, index + 1, this.array, index, --this.count - index);
            this.array[this.count] = null;
        }

        if (this.capacity > 16 && this.count < (this.capacity / 2)) {
            makeArray((int)(this.capacity / 1.5));
        }
    }

    public void capacityTest() {
        if (this.count >= this.capacity) {
            makeArray(this.capacity * 2);
        }
    }

    public void exception(int index) {
        if (index < 0 || index >= this.count) {
            throw new ArrayIndexOutOfBoundsException("Index Out Of Bound");
        }
    }
}
