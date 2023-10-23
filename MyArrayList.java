import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import static java.lang.Math.floor;
import static java.lang.System.arraycopy;

public class MyArrayList<E> {
    private Object[] elementData;
    private int size; // размер списка, не массива!

    public MyArrayList() {
        elementData = new Object[10];
    }

    public boolean add(E e) {
        if (elementData.length == size) {
            expandElementData((int) floor(1.5 * size + 1));
        }

        elementData[size] = e;
        size++;
        return true;
    }

    public void add(int index, E element) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }

        if (elementData.length == size) {
            expandElementData((int) floor(1.5 * size + 1));
        }

        System.arraycopy(elementData, index, elementData, index + 1, size - index);
        elementData[index] = element;
        size++;
    }

    public boolean addAll(Collection<? extends E> c) {
        if (c.size() == 0) {
            return false;
        }

        // Если нужно, расширяем elementData.
        if (size + c.size() >= elementData.length) {
            int newSize = size + c.size();
            int newArraySize = size;

            while (newArraySize < newSize) {
                newArraySize = (int) floor(1.5 * newArraySize + 1);
            }

            expandElementData(newArraySize);
        }

        int index = size;
        for (var element: c) {
            elementData[index] = element;
            index++;
        }
        size = size + c.size();

        return true;
    }

    public void clear() {
        elementData = new Object[10];
        size = 0;
    }

    public E get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }

        return (E) elementData[index];
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public E remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }

        E element = (E) elementData[index];
        System.arraycopy(elementData, index + 1, elementData, index, size - index - 1);
        elementData[size - 1] = null;
        size--;

        return element;
    }

    public boolean remove(Object o) {
        for (int i = 0; i < size; i++) {
            if (elementData[i].equals(o)) {
                System.arraycopy(elementData, i + 1, elementData, i, size - i - 1);
                elementData[size - 1] = null;
                size--;
                return true;
            }
        }

        return false;
    }

    public void sort(Comparator<? super E> c) {
        Object[] aux = new Object[size];
        mergeSort(c, elementData, aux, 0, size - 1);
    }

    @Override
    public String toString() {
        return Arrays.toString(Arrays.copyOf(elementData, size));
    }

    private void expandElementData(int newSize) {
        Object[] newArray = new Object[newSize];
        arraycopy(elementData, 0, newArray, 0, size);
        elementData = newArray;
    }

    private void mergeSort(Comparator<? super E> c, Object[] a, Object[] aux, int lo, int hi) {
        if (hi <= lo) return;
        int mid = lo + ((hi - lo)/ 2);

        mergeSort(c, a, aux, lo, mid);
        mergeSort(c, a, aux, mid + 1, hi);
        merge(c, a, aux, lo, mid, hi);
    }

    private void merge(Comparator<? super E> c, Object[] a, Object[] aux, int lo, int mid, int hi) {
        int i = lo, j = mid + 1;
        System.arraycopy(a, lo, aux, lo, hi + 1 - lo);

        for (int k = lo; k <= hi; k++) {
            if (i > mid) {
                a[k] = aux[j++];
            } else if (j > hi) {
                a[k] = aux[i++];
            } else if (c.compare((E) aux[j], (E) aux[i]) < 0) {
                a[k] = aux[j++];
            } else {
                a[k] = aux[i++];
            }
        }
    }
}
