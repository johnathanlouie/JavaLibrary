/*
 * Copyright (C) 2016 Johnathan Louie
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
package io.jlouie.algorithm;

/**
 *
 * @author Johnathan Louie
 */
public class Job {

    private String name;
    private int burstTime;
    private int remainingTime;

    public Job(String name, int burstTime) {
        this.name = name;
        this.burstTime = burstTime;
        remainingTime = burstTime;
    }

    public String getName() {
        return name;
    }

    public int getBurstTime() {
        return burstTime;
    }

    public int getRemainingTime() {
        return remainingTime;
    }

    public void setRemainingTime(int remainingTime) {
        this.remainingTime = remainingTime;
    }

    public void resetRemainingTime() {
        remainingTime = burstTime;
    }

    public void decRemainingTime(int sub) {
        remainingTime -= sub;
    }
}
