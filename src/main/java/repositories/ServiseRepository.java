package repositories;

import domain.Servise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiseRepository extends JpaRepository<Servise, Integer> {


}
