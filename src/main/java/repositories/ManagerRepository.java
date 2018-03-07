package repositories;

import domain.Manager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ManagerRepository extends JpaRepository<Manager, Integer> {

    @Query("select u from Manager u where u.userAccount.id = ?1")
    Manager findByUserAccountId(int userAccountId);

    @Query("select m from Manager m where m.vat=?1")
    Manager findByVat(String vat);

}
