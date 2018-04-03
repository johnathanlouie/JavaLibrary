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
class ListNode<E> {

    private E element;
    private ListNode prevNode;
    private ListNode nextNode;

    public ListNode(E element) {
        this.element = element;
    }

    public void setElement(E element) {
        this.element = element;
    }

    public E getElement() {
        return element;
    }

    public ListNode getPrevNode() {
        return prevNode;
    }

    public ListNode getNextNode() {
        return nextNode;
    }

    public void attach(ListNode prev, ListNode next) {
        if (prev != null) {
            prev.nextNode = this;
            prevNode = prev;
        }
        if (next != null) {
            next.prevNode = this;
            nextNode = next;
        }
    }

    public void detach() {
        if (prevNode != null) {
            prevNode.nextNode = nextNode;
        }
        if (nextNode != null) {
            nextNode.prevNode = prevNode;
        }
        prevNode = null;
        nextNode = null;
    }

}
