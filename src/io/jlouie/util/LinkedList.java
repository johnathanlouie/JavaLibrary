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
public class LinkedList<E> implements List<E> {

    private ListNode<E> head;
    private ListNode<E> tail;
    private int size;

    public LinkedList() {
        size = 0;
    }

    @Override
    public boolean add(E e, int index) {
        if (!isIndexAtEnd(index) && isIndexOutOfBounds(index)) {
            return false;
        }
        ListNode temp = new ListNode(e);
        if (index == 0) {
            temp.attach(null, head);
            head = temp;
        }
        if (isIndexAtEnd(index)) {
            temp.attach(tail, null);
            tail = temp;
        } else if (index != 0) {
            ListNode old = getNode(index);
            temp.attach(old, old.getNextNode());
        }
        size++;
        return true;
    }

    @Override
    public E get(int index) {
        if (isIndexOutOfBounds(index)) {
            throw new IndexOutOfBoundsException();
        }
        return getNode(index).getElement();
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
        ListNode<E> delete = getNode(index);
        if (index == 0) {
            head = delete.getNextNode();
        }
        if (index == size - 1) {
            tail = delete.getPrevNode();
        }
        delete.detach();
        size--;
        return true;
    }

    private boolean isIndexOutOfBounds(int index) {
        return index < 0 || index >= size;
    }

    private boolean isIndexAtEnd(int index) {
        return index == size;
    }

    private ListNode<E> getNode(int index) {
        if (index == size - 1) {
            return tail;
        }
        ListNode node = head;
        for (int i = 0; node != null; i++) {
            if (i == index) {
                return node;
            }
            node = node.getNextNode();
        }
        return null;
    }

}
