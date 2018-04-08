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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import static java.lang.Integer.min;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Johnathan Louie
 */
public class SchedulingAlgorithms {

    private static ArrayList<Job> buffer = new ArrayList<>();

    public static void resetBuffer() {
        for (Job i : buffer) {
            i.resetRemainingTime();
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        File file = new File("job.txt");
        if (args.length == 1) {
            file = new File(args[0]);
        }
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                String name = line;
                int burst = Integer.parseInt(bufferedReader.readLine());
                buffer.add(new Job(name, burst));
            }
            bufferedReader.close();
            firstComeFirstServe();
            shortestJobFirst();
            roundRobin(3);
            roundRobin(4);
        } catch (FileNotFoundException ex) {
            System.err.println("File not found.");
        } catch (IOException ex) {
            Logger.getLogger(SchedulingAlgorithms.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void firstComeFirstServe() {
        resetBuffer();
        System.out.println();
        System.out.println("========First Come First Serve========");
        int timeElapsed = 0;
        int turnaroundTotal = 0;
        System.out.println(timeElapsed);
        for (Job i : buffer) {
            timeElapsed += i.getBurstTime();
            turnaroundTotal += timeElapsed;
            run(i, i.getBurstTime());
            System.out.println(timeElapsed);
        }
        System.out.println("average turn around time: " + ((double) turnaroundTotal / buffer.size()));
    }

    public static void shortestJobFirst() {
        resetBuffer();
        System.out.println();
        System.out.println("========Shortest Job First========");
        int timeElapsed = 0;
        int turnaroundTotal = 0;
        System.out.println(timeElapsed);
        while (true) {
            int min = Integer.MAX_VALUE;
            Job selected = null;
            for (Job i : buffer) {
                if (i.getRemainingTime() > 0 && i.getRemainingTime() < min) {
                    min = i.getRemainingTime();
                    selected = i;
                }
            }
            if (selected == null) {
                break;
            }
            timeElapsed += selected.getBurstTime();
            turnaroundTotal += timeElapsed;
            run(selected, selected.getBurstTime());
            System.out.println(timeElapsed);
        }
        System.out.println("average turn around time: " + ((double) turnaroundTotal / buffer.size()));
    }

    public static void roundRobin(int timeSlice) {
        resetBuffer();
        System.out.println();
        System.out.println("========Round Robin with time slice = " + timeSlice + "========");
        int timeElapsed = 0;
        int turnaroundTotal = 0;
        System.out.println(timeElapsed);
        int i = 0;
        boolean action = false;
        while (true) {
//			System.out.println("i: " + i);
            if (i == 0) {
                action = false;
            }
            Job selected = buffer.get(i);
            i++;
            if (selected.getRemainingTime() > 0) {
                action = true;

                int runtime = min(selected.getRemainingTime(), timeSlice);
                run(selected, runtime);
                timeElapsed += runtime;
                if (selected.getRemainingTime() == 0) {
                    turnaroundTotal += timeElapsed;
                }
                System.out.println(timeElapsed);
            }
            if (i >= buffer.size()) {
                if (action) {
                    i = 0;
                } else {
                    break;
                }
            }
        }
        System.out.println("average turn around time: " + ((double) turnaroundTotal / buffer.size()));
    }

    public static void run(Job job, int runtime) {
        job.decRemainingTime(runtime);
        System.out.println("\t" + job.getName());
        System.out.println("\ttime spent: " + runtime);
        System.out.println("\tremaining: " + job.getRemainingTime());
    }

}
