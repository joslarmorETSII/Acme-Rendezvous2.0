package repositories;


import domain.Rendezvous;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface RendezvousRepository extends JpaRepository<Rendezvous,Integer> {
    @Query("select avg(u.rendezvouses.size),sqrt(sum(u.rendezvouses.size *u.rendezvouses.size)/ count(u) - (avg(u.rendezvouses.size) *avg(u.rendezvouses.size))) from  User u")
    Object[] avgDevRendezvousesPerUser();

    @Query("select avg(u.participated.size),stddev(u.participated.size) from Rendezvous u where u.participated.size>1")
    Object[] avgDevRendezvousParticipatePerUser();

    @Query("select r from Rendezvous r order by r.participated.size desc")
    Collection<Rendezvous> top10RendezvousParticipated();

    @Query("select p.rendezvous from Participate p where p.attendant.id = ?1")
    Collection<Rendezvous> userParticipate(int userId);

    @Query("select r from Rendezvous r where r.forAdults=false")
    Collection<Rendezvous> rendezvousForAnonymous();

    @Query("select r from Rendezvous r where r.announcements.size > (select avg(r1.announcements.size)*1.75 from Rendezvous r1)")
    Collection<Rendezvous> rendezvousPlus75AvgAnnouncements();

    @Query("select r from Rendezvous r where r.associated.size > (select avg(r1.associated.size)*1.1 from Rendezvous r1)")
    Collection<Rendezvous> rendezvousPlus10AvgAssociated();


}
