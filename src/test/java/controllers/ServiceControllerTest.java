package controllers;

import controllers.Manager.ServiseManagerController;
import domain.Manager;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.util.NestedServletException;
import services.CategoryService;
import services.ManagerService;
import services.ServiseService;
import utilities.AbstractTest;

import javax.transaction.Transactional;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/datasource.xml",
        "classpath:spring/config/packages.xml","classpath:spring/junit.xml"})
@WebAppConfiguration
@Transactional
public class ServiceControllerTest extends AbstractTest{

    @Mock
    private ServiseService serviseService;

    @Autowired
    private ServiseManagerController controller;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ManagerService managerService;

    private MockMvc mvc;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.mvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void create() throws Exception {
        authenticate("manager1");
        mvc.perform(get("/servise/manager/create.do")).andExpect(status().isOk()).
                andExpect(view().name("servise/edit")).
                andExpect(model().attribute("actionUri", "servise/manager/edit.do"));
        unauthenticate();
     }

    @Test
    public void list() throws Exception {
        authenticate("manager1");
        Manager principal = managerService.findByPrincipal();
        mvc.perform(get("/servise/manager/list")).andExpect(status().isOk()).
                andExpect(view().name("servise/list")).
                andExpect(model().attribute("servises",principal.getServises()));
        unauthenticate();
    }


    // Editing a Service logged in as Manager
    @Test
    public void editPositive() throws Exception {
        authenticate("manager1");
        mvc.perform(get("/servise/manager/edit").param("serviseId","315")).
                andExpect(status().isOk()).andExpect(forwardedUrl("servise/edit")).
                andExpect(model().attribute("categories",categoryService.findAll()));
        unauthenticate();
    }

    // Editing a Service without being logged in
    @Test(expected = NestedServletException.class)
    public void editNegative() throws Exception {
        mvc.perform(get("/servise/manager/edit").param("serviseId","315"))
        .andExpect(status().isUnauthorized());
    }


    // Deleting a Service
    @Test
    public void deletePositive() throws Exception {
        authenticate("manager2");
        mvc.perform(get("/servise/manager/editDelete").param("serviseId","317"))
        .andExpect(forwardedUrl("servise/list"));
        unauthenticate();
    }

    // Deleting a Service being authenticated as a User
    @Test(expected = NestedServletException.class)
    public void deleteNegative() throws Exception {
        authenticate("user1");
        mvc.perform(get("/servise/manager/editDelete").param("serviseId","317"))
                .andExpect(status().isUnauthorized());
        unauthenticate();
    }
}
