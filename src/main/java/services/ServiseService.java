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

    @Autowired
    private AdministratorService administratorService;


    // Constructors -----------------------------------------------------------

    public ServiseService(){
        super();
    }

    // Simple CRUD methods ----------------------------------------------------

    public Servise create(){

        Servise result = new Servise();
        Manager manager =  this.managerService.findByPrincipal();
        Collection<Rendezvous> rendezvouses = new ArrayList<Rendezvous>();
        Assert.notNull(manager);
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
        Manager manager = this.managerService.findByPrincipal();
        Servise result;

        Assert.isTrue(this.actorService.isManager());
        Assert.notNull(servise);
        Assert.isTrue(servise.getManager().equals(manager));

        if(servise.getId()==0) {
            result = serviseRepository.save(servise);
            manager.getServises().add(result);
        }else{
            result = this.serviseRepository.save(servise);
        }

        return result;
    }

    public void delete(Servise servise) {

        Manager manager = managerService.findByPrincipal();
        Assert.isTrue(actorService.isManager());
        Assert.isTrue(servise.getManager().equals(manager));

        //TODO: Si falla eliminar request y rendezvous
        Assert.isTrue(servise.getRendezvouses().isEmpty());
        this.serviseRepository.delete(servise);

    }

    public Collection<Servise> findAll(){
        Assert.isTrue(actorService.isManager()|| actorService.isUser() || actorService.isAdministrator());
        return serviseRepository.findAll();
    }

    // Other business methods -------------------------------------------------

    public Servise findOneToEdit(final int serviseId) {
        Servise res = this.serviseRepository.findOne(serviseId);
        Manager manager = managerService.findByPrincipal();

        Assert.isTrue(manager.equals(res.getManager()));

        return res;
    }

    public void inappropriateDontRequest(final int serviseId){

        Servise servise;
        servise = this.serviseRepository.findOne(serviseId);

        Assert.isTrue(this.actorService.isAdministrator());

//        Administrator administrator = this.administratorService.findByPrincipal();
//        Assert.notNull(administrator);

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

    public void flush() {
        serviseRepository.flush();
    }

    public Collection<Double> avgMinMaxDevServisesPerRendezvous(){
        return serviseRepository.avgMinMaxDevServisesPerRendezvous();
    }
}