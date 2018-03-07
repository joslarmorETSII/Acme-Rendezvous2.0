package repositories;

import domain.Requestt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequesttRepository extends JpaRepository<Requestt,Integer> {
}