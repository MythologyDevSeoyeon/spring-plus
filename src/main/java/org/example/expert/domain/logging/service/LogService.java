package org.example.expert.domain.logging.service;

import lombok.RequiredArgsConstructor;
import org.example.expert.domain.logging.entity.ManagerLog;
import org.example.expert.domain.logging.repository.LogRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LogService {

    private final LogRepository logRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void log(String message, Long todoId, Long managerId) {
        ManagerLog log = ManagerLog.of(message, todoId, managerId);
        logRepository.save(log);
    }
}
