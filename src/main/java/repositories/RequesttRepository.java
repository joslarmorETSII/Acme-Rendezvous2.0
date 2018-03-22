package repositories;

import domain.Requestt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface RequesttRepository extends JpaRepository<Requestt,Integer> {


    @Query("select r from Requestt r join r.servise rs where rs.manager.id = ?1")
    Collection<Requestt> requestsByManagerId(int managerId);
}