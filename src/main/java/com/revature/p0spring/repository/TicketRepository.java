package com.revature.p0spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.revature.p0spring.model.Ticket;
import com.revature.p0spring.model.Ticket.ReimbursementType;
import com.revature.p0spring.model.Ticket.Status;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long>{
    @Query("SELECT t FROM Ticket t WHERE t.status = ?1")
    List<Ticket> findAllByStatus(Status status);

    @Query("SELECT t FROM Ticket t WHERE t.employeeID = ?1")
    List<Ticket> findAllByEmployeeID(long employeeID);

    @Query("SELECT t FROM Ticket t WHERE t.employeeID = ?1 AND t.status = ?2")
    List<Ticket> findAllByEmployeeIDAndStatus(long employeeID, Status status);

    @Query("SELECT t FROM Ticket t WHERE t.employeeID = ?1 AND t.reimbursementType = ?2")
    List<Ticket> findAllByEmployeeIDAndType(long employeeID, ReimbursementType reimbursementType);

    @Query("SELECT t FROM Ticket t WHERE t.employeeID = ?1 AND t.status = ?2 AND  t.reimbursementType = ?3")
    List<Ticket> findAllByEmployeeIDAndStatusAndType(long employeeID, Status status, ReimbursementType reimbursementType);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Ticket t SET t.status = ?1 WHERE t.id = ?2")
    int updateTicketStatusById(Status status, long id);

    @Query("SELECT t FROM Ticket t ORDER BY t.id DESC")
    List<Ticket> findAllOrderByIdDesc();

    @Query("SELECT t FROM Ticket t WHERE t.status = ?1 ORDER BY t.id DESC")
    List<Ticket> findAllByStatusOrderByIdDesc(Status status);
}
