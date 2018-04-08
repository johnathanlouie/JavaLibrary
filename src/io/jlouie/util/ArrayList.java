/*
 * Copyright (C) 2015 Johnathan Louie
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package io.jlouie.util;

/**
 *
 * @author Johnathan Louie
 */
public class ArrayList<E> implements List<E> {

    private E[] elements;
    private int size = 0;
    private static final int DEFAULT_CAPACITY = 8;

    public ArrayList() {
        elements = (E[]) new Object[DEFAULT_CAPACITY];
    }

    @Override
    public void add(E e, int index) {
        if (isOutOfBounds(index) && !isEnd(index)) {
            throw new IndexOutOfBoundsException();
        }
        if (isFull()) {
            resize();
        }
        if (!isEnd(index) && !isEmpty()) {
            shiftUp(index);
        }
        elements[index] = e;
        size++;
    }

    @Override
    public E get(int index) {
        if (isOutOfBounds(index)) {
            throw new IndexOutOfBoundsException();
        }
        return elements[index];
    }

    @Override
    public void set(E e, int index) {
        if (isOutOfBounds(index)) {
            throw new IndexOutOfBoundsException();
        }
        elements[index] = e;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void remove(int index) {
        if (isOutOfBounds(index)) {
            throw new IndexOutOfBoundsException();
        }
        shiftDown(index);
        size--;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private boolean isFull() {
        return size == elements.length;
    }

    private void resize() {
        E[] larger = (E[]) new Object[elements.length * 2];
        Arrays.copy(elements, larger);
        elements = larger;
    }

    private boolean isOutOfBounds(int index) {
        return index < 0 || index >= size;
    }

    private boolean isEnd(int index) {
        return index == size;
    }

    private void shiftUp(int index) {
        for (int i = size; i > index; i--) {
            elements[i] = elements[i - 1];
        }
    }

    private void shiftDown(int index) {
        for (int i = index; i < size; i++) {
            elements[i] = elements[i + 1];
        }
    }

}
