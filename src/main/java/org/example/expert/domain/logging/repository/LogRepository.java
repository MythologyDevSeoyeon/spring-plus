package org.example.expert.domain.logging.repository;

import org.example.expert.domain.logging.entity.ManagerLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogRepository extends JpaRepository<ManagerLog, Long> {
}
