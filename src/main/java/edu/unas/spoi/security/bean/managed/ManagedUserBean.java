/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unas.spoi.security.bean.managed;

import gkfire.auditory.Auditory;
import edu.unas.spoi.bean.NavigationBean;
import edu.unas.spoi.bean.SessionBean;
import edu.unas.spoi.oei.service.interfac.IDependencyService;
import edu.unas.spoi.security.model.Involved;
import edu.unas.spoi.security.model.Person;
import edu.unas.spoi.security.model.Rol;
import edu.unas.spoi.security.model.User;
import edu.unas.spoi.security.service.interfac.IInvolvedService;
import edu.unas.spoi.security.service.interfac.IPersonService;
import edu.unas.spoi.security.service.interfac.IRolService;
import edu.unas.spoi.security.service.interfac.IUserService;
import edu.unas.spoi.util.PasswordGenerator;
import edu.unas.spoi.util.SmartMessage;
import gkfire.hibernate.AliasList;
import gkfire.hibernate.CriterionList;
import gkfire.hibernate.OrderList;
import gkfire.web.bean.AManagedBean;
import gkfire.web.bean.ILoadable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

/**
 * The type Managed user bean.
 *
 * @author Jhoan Brayam
 */
@ManagedBean
@SessionScoped
public class ManagedUserBean extends AManagedBean<User, IUserService> implements ILoadable {

    @ManagedProperty(value = "#{userService}")
    private IUserService mainService;
    @ManagedProperty(value = "#{personService}")
    private IPersonService personService;
    @ManagedProperty(value = "#{involvedService}")
    private IInvolvedService involvedService;
    @ManagedProperty(value = "#{dependencyService}")
    private IDependencyService dependencyService;
    @ManagedProperty(value = "#{rolService}")
    private IRolService rolService;
    @ManagedProperty(value = "#{sessionBean}")
    private SessionBean sessionBean;
    @ManagedProperty(value = "#{navigationBean}")
    private NavigationBean navigationBean;

    private PersonSearcher personSearcher;
    private DependencySearcher dependencySearcher;
    private RolSearcher rolSearcher;

    private List<Rol> rols;
    private Involved involved;
    private String nick;
    private String password;
    private Date dueDate;

    private Integer idDependencySelected;

    /**
     * Init.
     */
    @PostConstruct
    public void init() {
        personSearcher = new PersonSearcher();
        dependencySearcher = new DependencySearcher();
        rolSearcher = new RolSearcher();
    }

    @Override
    public boolean save() {
        String content = getSelected().getId() != null ? "Se ha actualizado un usuario" : "Se ha creado un usuario";
        super.save(); //To change body of generated methods, choose Tools | Templates.
        involvedService.saveOrUpdate(selected.getInvolved());
        personService.saveOrUpdate(selected.getInvolved().getPerson());
        new SmartMessage("Datos guardadados", content, SmartMessage.SmartColor.SUCCESS, 3000L, "fa fa-save shake animated").execute();
        return true;
    }

    /**
     * Gets id dependency selected.
     *
     * @return the id dependency selected
     */
    public Integer getIdDependencySelected() {
        return idDependencySelected;
    }

    /**
     * Sets id dependency selected.
     *
     * @param idDependencySelected the id dependency selected
     */
    public void setIdDependencySelected(Integer idDependencySelected) {
        this.idDependencySelected = idDependencySelected;
    }

    @Override
    protected void clearFields() {
    }

    @Override
    public void onLoad(boolean allowAjax) {
    }

    @Override
    public void create() {
        super.create();
        dependencySearcher.getData();
        dependencySearcher.update();
        rols = new ArrayList();
        personSearcher.setDni("");
        personSearcher.setPerson(null);
    }

    @Override
    protected void fillFields() {
        involved = selected.getInvolved();


        if (involved == null) {
            involved = new Involved();
            involved.setPerson(new Person());
        } else {
            personSearcher.setDni(selected.getInvolved().getPerson().getDni());
            personSearcher.setPerson(selected.getInvolved().getPerson());
//dependencySearcher.setSelected(selected.getInvolved().getDependency().getId());
        }
        dependencySearcher.getData();
        dependencySearcher.update();
        nick = selected.getUsername();
        password = selected.getPassword();
        dueDate = selected.getDueDate();
        createAgain = selected.getId() == null;
        if (selected.getId() != null) {
            AliasList aliasList = new AliasList();
            aliasList.add("users", "u");
            CriterionList criterionList = new CriterionList();
            criterionList.add(Restrictions.eq("u.id", selected.getId()));
            OrderList orderList = new OrderList();
            orderList.add(Order.desc("id"));
            rols = rolService.addRestrictionsVariant(Arrays.asList(aliasList, criterionList, orderList));
        } else {
            rols = new ArrayList();
        }

        rolSearcher.update();
    }

    @Override
    protected void fillSelected() {
        if (selected.getId() == null) {
            involved.setPerson(personSearcher.getPerson());
            if (personSearcher.getPerson().getId() != null) {
                Involved i = involvedService.getBy(personSearcher.getPerson(), involved.getDependency().getId());
                if (i != null) {
                    involved = i;
                }
            }
            if (involved.getPerson().getId() == null) {
                Auditory.make(involved.getPerson(), sessionBean.getCurrentUser());
                personService.saveOrUpdate(involved.getPerson());
            }
            if (involved.getId() == null) {
                Auditory.make(involved, sessionBean.getCurrentUser());
                involvedService.saveOrUpdate(involved);
            }
        }
        selected.setInvolved(involved);
        selected.setUsername(nick);
        selected.setPassword(password);
        selected.setDueDate(dueDate);
        rols = new ArrayList();
        for (Rol item : rolSearcher.getData()) {
            if (rolSearcher.getSelecteds().get(item.getId())) {
                rols.add(item);
            }
        }
        selected.setRols(rols);
    }

    /**
     * Generate.
     */
    public void generate() {
        generateUserName();
        generatePassword();
    }

    /**
     * Generate user name.
     */
    public void generateUserName() {
        Person person = personSearcher.getPerson();
        nick = person.getPattern().charAt(0) + person.getName().split(" ")[0] + "." + mainService.getNextId();
        nick = nick.toLowerCase();
    }

    /**
     * Generate password.
     */
    public void generatePassword() {
        password = PasswordGenerator.getPinNumber();
    }

    /**
     * @return the mainService
     */
    @Override
    public IUserService getMainService() {
        return mainService;
    }

    /**
     * @param mainService the mainService to set
     */
    @Override
    public void setMainService(IUserService mainService) {
        this.mainService = mainService;
    }

    /**
     * Gets person service.
     *
     * @return the personService
     */
    public IPersonService getPersonService() {
        return personService;
    }

    /**
     * Sets person service.
     *
     * @param personService the personService to set
     */
    public void setPersonService(IPersonService personService) {
        this.personService = personService;
    }

    /**
     * Gets involved service.
     *
     * @return the involvedService
     */
    public IInvolvedService getInvolvedService() {
        return involvedService;
    }

    /**
     * Sets involved service.
     *
     * @param involvedService the involvedService to set
     */
    public void setInvolvedService(IInvolvedService involvedService) {
        this.involvedService = involvedService;
    }

    /**
     * Gets dependency service.
     *
     * @return the dependencyService
     */
    public IDependencyService getDependencyService() {
        return dependencyService;
    }

    /**
     * Sets dependency service.
     *
     * @param dependencyService the dependencyService to set
     */
    public void setDependencyService(IDependencyService dependencyService) {
        this.dependencyService = dependencyService;
    }

    /**
     * @return the sessionBean
     */
    public SessionBean getSessionBean() {
        return sessionBean;
    }

    /**
     * @param sessionBean the sessionBean to set
     */
    public void setSessionBean(SessionBean sessionBean) {
        this.sessionBean = sessionBean;
    }

    /**
     * @return the navigationBean
     */
    public NavigationBean getNavigationBean() {
        return navigationBean;
    }

    /**
     * @param navigationBean the navigationBean to set
     */
    public void setNavigationBean(NavigationBean navigationBean) {
        this.navigationBean = navigationBean;
    }

    /**
     * Gets person searcher.
     *
     * @return the personSearcher
     */
    public PersonSearcher getPersonSearcher() {
        return personSearcher;
    }

    /**
     * Sets person searcher.
     *
     * @param personSearcher the personSearcher to set
     */
    public void setPersonSearcher(PersonSearcher personSearcher) {
        this.personSearcher = personSearcher;
    }

    /**
     * Gets dependency searcher.
     *
     * @return the dependencySearcher
     */
    public DependencySearcher getDependencySearcher() {
        return dependencySearcher;
    }

    /**
     * Sets dependency searcher.
     *
     * @param dependencySearcher the dependencySearcher to set
     */
    public void setDependencySearcher(DependencySearcher dependencySearcher) {
        this.dependencySearcher = dependencySearcher;
    }

    /**
     * Gets involved.
     *
     * @return the involved
     */
    public Involved getInvolved() {
        return involved;
    }

    /**
     * Sets involved.
     *
     * @param involved the involved to set
     */
    public void setInvolved(Involved involved) {
        this.involved = involved;
    }

    /**
     * Gets nick.
     *
     * @return the nick
     */
    public String getNick() {
        return nick;
    }

    /**
     * Sets nick.
     *
     * @param nick the nick to set
     */
    public void setNick(String nick) {
        this.nick = nick;
    }

    /**
     * Gets password.
     *
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets password.
     *
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets due date.
     *
     * @return the dueDate
     */
    public Date getDueDate() {
        return dueDate;
    }

    /**
     * Sets due date.
     *
     * @param dueDate the dueDate to set
     */
    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    /**
     * Gets rol service.
     *
     * @return the rolService
     */
    public IRolService getRolService() {
        return rolService;
    }

    /**
     * Sets rol service.
     *
     * @param rolService the rolService to set
     */
    public void setRolService(IRolService rolService) {
        this.rolService = rolService;
    }

    /**
     * Gets rols.
     *
     * @return the rols
     */
    public List<Rol> getRols() {
        return rols;
    }

    /**
     * Sets rols.
     *
     * @param rols the rols to set
     */
    public void setRols(List<Rol> rols) {
        this.rols = rols;
    }

    /**
     * Gets rol searcher.
     *
     * @return the rolSearcher
     */
    public RolSearcher getRolSearcher() {
        return rolSearcher;
    }

    /**
     * Sets rol searcher.
     *
     * @param rolSearcher the rolSearcher to set
     */
    public void setRolSearcher(RolSearcher rolSearcher) {
        this.rolSearcher = rolSearcher;
    }

    /**
     * The type Dependency searcher.
     */
    public class DependencySearcher implements java.io.Serializable {

        private List<Object[]> data;

        /**
         * Update.
         */
        public void update() {
            data = dependencyService.getListForUserAssigment(
                    personSearcher.getPerson() == null ? null : personSearcher.getPerson().getId(),
                    involved.getDependency() == null ? null : involved.getDependency().getId(),sessionBean.getOperationYear());
        }

        /**
         * Sets selected.
         *
         * @param id the id
         */
        public void setSelected(Integer id) {
            try {
                getInvolved().setDependency(getDependencyService().getById(id));
            } catch (Exception e) {
                getInvolved().setDependency(null);
            }
        }

        /**
         * Gets selected.
         *
         * @return the selected
         */
        public Integer getSelected() {
            try {
                return getInvolved().getDependency().getId();
            } catch (Exception e) {
                return null;
            }
        }

        /**
         * Gets data.
         *
         * @return the data
         */
        public List<Object[]> getData() {
            return data;
        }

        /**
         * Sets data.
         *
         * @param data the data to set
         */
        public void setData(List<Object[]> data) {
            this.data = data;
        }

    }

    /**
     * The type Person searcher.
     */
    public class PersonSearcher implements java.io.Serializable {

        private String dni;

        private Person person;
        private boolean saveSuccess;

        /**
         * Save dni.
         */
        public void saveDni() {
            dni = person.getDni();
            try {
                personService.saveOrUpdate(person);
                new SmartMessage("Datos guardados!!", "Se ha actualizado los datos personales!", SmartMessage.SmartColor.SUCCESS, 3000L, "fa fa-save shake animated").execute();
            } catch (Exception e) {
                new SmartMessage("Error en guardado!!", "Consulte el log del servidor!", SmartMessage.SmartColor.DANGER, 3000L, "fa fa-warning shake animated").execute();
            }
        }

        /**
         * Save data.
         */
        public void saveData() {
            dni = person.getDni();
            try {
                personService.saveOrUpdate(person);
                new SmartMessage("Datos guardados!!", "Se ha actualizado los datos personales!", SmartMessage.SmartColor.SUCCESS, 3000L, "fa fa-save shake animated").execute();
            } catch (Exception e) {
                new SmartMessage("Error en guardado!!", "Consulte el log del servidor!", SmartMessage.SmartColor.DANGER, 3000L, "fa fa-warning shake animated").execute();
            }
        }

        /**
         * Search.
         */
        public void search() {
            person = getPersonService().getBy(dni);
            if (person == null) {
                person = new Person();
                person.setDni(dni);
            } else {
                try {
                    Involved newInvolved = involvedService.getBy(person, involved.getDependency().getId());
                    if (newInvolved != null) {
                        involved = newInvolved;
                    }
                } catch (Exception e) {

                }
                generate();
            }
            getDependencySearcher().update();
        }

        /**
         * Gets dni.
         *
         * @return the dni
         */
        public String getDni() {
            return dni;
        }

        /**
         * Sets dni.
         *
         * @param dni the dni to set
         */
        public void setDni(String dni) {
            this.dni = dni;
        }

        /**
         * Gets person.
         *
         * @return the person
         */
        public Person getPerson() {
            return person;
        }

        /**
         * Sets person.
         *
         * @param person the person to set
         */
        public void setPerson(Person person) {
            this.person = person;
        }

        /**
         * Is save success boolean.
         *
         * @return the saveSuccess
         */
        public boolean isSaveSuccess() {
            return saveSuccess;
        }

        /**
         * Sets save success.
         *
         * @param saveSuccess the saveSuccess to set
         */
        public void setSaveSuccess(boolean saveSuccess) {
            this.saveSuccess = saveSuccess;
        }

    }

    /**
     * The type Rol searcher.
     */
    public class RolSearcher implements java.io.Serializable {

        private Map<Integer, Boolean> selecteds;
        private List<Rol> data;

        /**
         * Update.
         */
        public void update() {
            OrderList orderList = new OrderList();
            orderList.add(Order.desc("id"));
            CriterionList criterionList = new CriterionList();
            criterionList.add(Restrictions.eq("active", true));
            for (Rol o : getRols()) {
                criterionList.add(Restrictions.ne("id", o.getId()));
            }
            data = getRolService().addRestrictionsVariant(Arrays.asList(criterionList, orderList));
            selecteds = new HashMap();
            for (Rol item : data) {
                selecteds.put(item.getId(), ManagedUserBean.this.rols.contains(item));
            }
            for (Rol r : ManagedUserBean.this.rols) {
                if (!data.contains(r)) {
                    data.add(r);
                    selecteds.put(r.getId(), true);
                }
            }
        }

        /**
         * Add.
         */
        public void add() {
            for (Rol o : data) {
                if (selecteds.get(o.getId())) {
                    getRols().add(o);
                }
            }
        }

        /**
         * Remove.
         *
         * @param o the o
         */
        public void remove(Rol o) {
            getRols().remove(o);
        }

        /**
         * Gets selecteds.
         *
         * @return the selecteds
         */
        public Map<Integer, Boolean> getSelecteds() {
            return selecteds;
        }

        /**
         * Sets selecteds.
         *
         * @param selecteds the selecteds to set
         */
        public void setSelecteds(Map<Integer, Boolean> selecteds) {
            this.selecteds = selecteds;
        }

        /**
         * Gets data.
         *
         * @return the data
         */
        public List<Rol> getData() {
            return data;
        }

        /**
         * Sets data.
         *
         * @param data the data to set
         */
        public void setData(List<Rol> data) {
            this.data = data;
        }
    }
}
