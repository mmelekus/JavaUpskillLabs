package com.example.transconsumer.service;

import com.example.transconsumer.model.TransactionStatistic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ConsoleStatisticsCollector implements StatisticsCollector {

    private static final Logger log = LoggerFactory.getLogger(ConsoleStatisticsCollector.class);

    @Override
    public void collect(TransactionStatistic statistic) {
        log.info("Statistic -> {} {}",
                String.format("%-15s", statistic.type()),
                statistic.amount());
    }
}