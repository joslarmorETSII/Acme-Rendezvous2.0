package repositories;

import domain.Manager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.text.CollationElementIterator;
import java.util.Collection;

@Repository
public interface ManagerRepository extends JpaRepository<Manager, Integer> {

    @Query("select u from Manager u where u.userAccount.id = ?1")
    Manager findByUserAccountId(int userAccountId);

    @Query("select m from Manager m where m.vat=?1")
    Manager findByVat(String vat);

    @Query("select m from Manager m where m.servises.size >= (select avg(m.servises.size) from Manager m )")
    Collection<Manager> managersWithMoreServisesThanAvg();

    @Query("select s.manager from Servise s where s.inappropriate=true group by s.manager order by count(s) DESC")
    Collection<Manager> managersWithMoreServisesCancelled();
}
