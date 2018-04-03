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

    private E[] elementData;
    private int size;
    private static final int DEFAULT_CAPACITY = 8;

    public ArrayList() {
        size = 0;
        elementData = (E[]) new Object[DEFAULT_CAPACITY];
    }

    @Override
    public boolean add(E e, int index) {
        if (isIndexOutOfBounds(index) && !isIndexAtEnd(index)) {
            return false;
        }
        if (isFull()) {
            newArray();
        }
        if (!isIndexAtEnd(index) && !isEmpty()) {
            shiftElementsUp(index);
        }
        elementData[index] = e;
        size++;
        return true;
    }

    @Override
    public E get(int index) {
        if (isIndexOutOfBounds(index)) {
            throw new IndexOutOfBoundsException();
        }
        return elementData[index];
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean remove(int index) {
        if (isIndexOutOfBounds(index)) {
            return false;
        }
        shiftElementsDown(index);
        size--;
        return true;
    }

    private boolean isFull() {
        return size == elementData.length;
    }

    private void newArray() {
        E[] temp = (E[]) new Object[elementData.length * 2];
        copyAll(elementData, temp);
        elementData = temp;
    }

    private void copyAll(E[] src, E[] dest) {
        for (int i = 0; i < size; i++) {
            dest[i] = src[i];
        }
    }

    private boolean isIndexOutOfBounds(int index) {
        return index < 0 || index >= size;
    }

    private boolean isIndexAtEnd(int index) {
        return index == size;
    }

    private void shiftElementsUp(int index) {
        for (int i = size; i > index; i--) {
            elementData[i] = elementData[i - 1];
        }
    }

    private void shiftElementsDown(int index) {
        for (int i = index; i < size; i++) {
            elementData[i] = elementData[i + 1];
        }
    }

    private boolean isEmpty() {
        return size == 0;
    }

}
