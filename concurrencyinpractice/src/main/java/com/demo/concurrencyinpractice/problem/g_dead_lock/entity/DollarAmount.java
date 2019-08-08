package com.demo.concurrencyinpractice.problem.g_dead_lock.entity;

public class DollarAmount implements Comparable<DollarAmount>{
        private double amount;

        @Override
        public int compareTo(DollarAmount o) {
            return 0;
        }
    }