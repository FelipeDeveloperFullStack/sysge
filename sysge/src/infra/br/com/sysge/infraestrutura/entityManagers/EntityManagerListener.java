package br.com.sysge.infraestrutura.entityManagers;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
@Named
public class EntityManagerListener implements ServletContextListener{

	@Inject
	private EntityManager manager; 
	
	@Inject
	private EntityManagerProducer managerProducer;
	
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		managerProducer.closeEntityManager(manager);
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		managerProducer.createEntityManager();
	}

}
