package repositories;

import domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("select u from User u where u.userAccount.id = ?1")
    User findByUserAccountId(int userAccountId);

    @Query("select p.attendant from Participate p where p.rendezvous.id=?1")
    Collection<User> rendezvousAttendant(int rendevoudId);

    @Query("select avg(u.participates.size),sqrt(sum(u.participates.size *u.participates.size)/ count(u) - (avg(u.participates.size) *avg(u.participates.size))) from  User u")
    Object[] avgDevRendezvousPerUser();

    @Query("select count(u)*1.0 / (select count(u1)*1.0 from User u1 where u1.rendezvouses.size = 0 ) from User u where u.rendezvouses.size > 0")
    Double RatioCreatorsVsNoCreators();

}
