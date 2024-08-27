package com.pivotree.appzone.service;

import com.pivotree.appzone.entity.StatusLogger;
import com.pivotree.appzone.repository.StatusLoggerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatusLoggerService {
    @Autowired
    StatusLoggerRepository statusLoggerRepository;

    public StatusLogger save(StatusLogger statusLogger) {
        return statusLoggerRepository.save(statusLogger);
    }
}
