package com.demo.concurrencyinpractice.problem.g_dead_lock.entity;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Account{
        private DollarAmount amount = new DollarAmount();

        public DollarAmount getBalance() {
            return amount;
        }

        public void debit(DollarAmount amount) {
            log.info("{}:debit",Thread.currentThread().getName());
        }

        public void credit(DollarAmount amount) {
            log.info("{}:credit",Thread.currentThread().getName());
        }
    }