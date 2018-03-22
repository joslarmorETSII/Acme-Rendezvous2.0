package repositories;

import domain.Servise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface ServiseRepository extends JpaRepository<Servise, Integer> {

    @Query("select s from Servise s where s.inappropriate=false")
    Collection<Servise> findServiceAppropriate();

    @Query("select s from Servise s order by s.rendezvouses.size")
    Collection<Servise> topSellingServises();

    //The average, the minimum, the maximum, and the standard deviation of services requested per rendezvous.

    @Query("select avg(r.servises.size), min(r.servises.size), max(r.servises.size), sqrt(sum(r.servises.size * r.servises.size)/count(s) - (avg(r.servises.size) * avg(r.servises.size))) from Rendezvous r, Servise s")
    Collection<Double> avgMinMaxDevServisesPerRendezvous();

    @Query(" select s from Servise s where s.rendezvouses.size >= (select max(s.rendezvouses.size) from Servise s)")
    Collection<Servise> bestSellingServises();

    @Query("select avg(c.servises.size) from Category c")
    Double avgServisesInEachCategory();
}
