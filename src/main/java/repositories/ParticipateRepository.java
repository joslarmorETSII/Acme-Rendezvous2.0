package repositories;

import domain.Participate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ParticipateRepository extends JpaRepository<Participate, Integer> {

    @Query("select p from Participate p where p.attendant.id=?1 and p.rendezvous.id=?2")
     Participate participate (int attendantId,int rendezvousId);
}
