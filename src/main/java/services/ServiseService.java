package services;

import domain.*;
import domain.Servise;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import repositories.RendezvousRepository;
import repositories.ServiseRepository;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class ServiseService {

    // Managed repository -----------------------------------------------------

    @Autowired
    private ServiseRepository serviseRepository;

    @Autowired
    private RendezvousRepository rendezvousRepository;

    // Managed services -----------------------------------------------------

    @Autowired
    private ActorService actorService;

    @Autowired
    private ManagerService managerService;


    // Constructors -----------------------------------------------------------

    public ServiseService(){
        super();
    }

    // Simple CRUD methods ----------------------------------------------------

    public Servise create(){

        Servise result = new Servise();
        Manager manager = (Manager) this.actorService.findByPrincipal();
        Collection<Rendezvous> rendezvouses = new ArrayList<Rendezvous>();

        result.setInappropriate(false);
        result.setAssigned(false);
        result.setManager(manager);
        result.setRendezvouses(rendezvouses);

        return result;
    }

    public Servise findOne(Integer serviseId){
        return serviseRepository.findOne(serviseId);
    }


    public Servise save(Servise servise){
        Manager manager;
        Servise result;

        Assert.isTrue(this.actorService.isManager() || actorService.isUser());
        Assert.notNull(servise);

        if(servise.getId()==0) {
            manager = (Manager) this.actorService.findByPrincipal();
            result = serviseRepository.save(servise);
            manager.getServises().add(result);
        }else{
            result = this.serviseRepository.save(servise);
        }

        return result;
    }

    public void delete(Servise servise) {

        Assert.isTrue(actorService.isManager());
        //TODO: Si falla eliminar request y rendezvous
        if(servise.getRendezvouses().isEmpty()) {
            this.serviseRepository.delete(servise);
        }
    }

    public Collection<Servise> findAll(){
        return serviseRepository.findAll();
    }

    // Other business methods -------------------------------------------------

    public Servise findOneToEdit(final int serviseId) {
        Servise res = this.serviseRepository.findOne(serviseId);
        Manager manager = res.getManager();

        Assert.isTrue(manager.equals(managerService.findByPrincipal()));

        return res;
    }

    public void inappropriateDontRequest(final int serviseId){

        Servise servise;

        servise = this.serviseRepository.findOne(serviseId);
        servise.setInappropriate(true);
        this.serviseRepository.save(servise);

    }

    public Collection<Servise> findServiceAppropriate(){
        return this.serviseRepository.findServiceAppropriate();
    }

    // top 5 Best Selling Services
    public Collection<Servise> topSellingServises(){
        List<Servise> result;

        result = new ArrayList<>(serviseRepository.topSellingServises());
        if(result.size()>5)
            result.subList(0,4);
        return result;
    }

}
