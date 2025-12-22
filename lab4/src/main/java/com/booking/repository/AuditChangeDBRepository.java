package com.booking.repository;
import com.booking.entity.AuditChangeDB;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditChangeDBRepository extends JpaRepository<AuditChangeDB, Integer> {
}
