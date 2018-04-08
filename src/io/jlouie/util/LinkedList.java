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

    private Node<E> head = null;
    private Node<E> tail = null;
    private int size = 0;

    public LinkedList() {
    }

    @Override
    public void add(E e, int index) {
        if (!isEnd(index) && isOutOfBounds(index)) {
            throw new IndexOutOfBoundsException();
        }
        Node node = new Node(e);
        if (index == 0) {
            node.attach(null, head);
            head = node;
        } else if (isEnd(index)) {
            node.attach(tail, null);
            tail = node;
        } else {
            Node x = getNode(index);
            node.attach(x.prev(), x);
        }
        size++;
    }

    @Override
    public E get(int index) {
        if (isOutOfBounds(index)) {
            throw new IndexOutOfBoundsException();
        }
        return getNode(index).get();
    }

    @Override
    public void set(E e, int index) {
        if (isOutOfBounds(index)) {
            throw new IndexOutOfBoundsException();
        }
        getNode(index).set(e);
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
        Node<E> node = getNode(index);
        if (index == 0) {
            head = node.next();
        } else if (index == size - 1) {
            tail = node.prev();
        }
        node.detach();
        size--;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private boolean isOutOfBounds(int index) {
        return index < 0 || index >= size;
    }

    private boolean isEnd(int index) {
        return index == size;
    }

    private Node<E> getNode(int index) {
        Node node;
        if (index <= size / 2) {
            node = head;
            for (int i = 0; i < size; i++) {
                if (i == index) {
                    return node;
                }
                node = node.next();
            }
        } else {
            node = tail;
            for (int i = size - 1; i >= 0; i--) {
                if (i == index) {
                    return node;
                }
                node = node.prev();
            }
        }
        throw new IndexOutOfBoundsException();
    }

    private class Node<E> {

        private E e = null;
        private Node prev = null;
        private Node next = null;

        public Node(E e) {
            this.e = e;
        }

        public void set(E e) {
            this.e = e;
        }

        public E get() {
            return e;
        }

        public Node prev() {
            return prev;
        }

        public Node next() {
            return next;
        }

        public void attach(Node prev, Node next) {
            if (prev != null) {
                prev.next = this;
            }
            if (next != null) {
                next.prev = this;
            }
            this.prev = prev;
            this.next = next;
        }

        public void detach() {
            if (prev != null) {
                prev.next = this.next;
            }
            if (next != null) {
                next.prev = this.prev;
            }
            prev = null;
            next = null;
        }

    }
}
