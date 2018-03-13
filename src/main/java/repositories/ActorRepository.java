package repositories;

import domain.Actor;
import domain.CreditCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ActorRepository extends JpaRepository<Actor, Integer>{

    @Query("select a from Actor a where a.userAccount.id = ?1")
    Actor findByUserAccountId(int userAccountId);

}
