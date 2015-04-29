package br.com.vaadin.osgi.web;

import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.DeploymentConfiguration;
import com.vaadin.server.ServiceException;
import com.vaadin.server.VaadinServlet;
import com.vaadin.server.VaadinServletService;

@VaadinServletConfiguration(productionMode = false,
        ui = MainUI.class,
        widgetset = "br.com.vaadin.osgi.web.MainWidgetset")
public class MainUIServlet extends VaadinServlet {

    @Override
    protected VaadinServletService createServletService(DeploymentConfiguration deploymentConfiguration) throws ServiceException {

        VaadinServletService service = new VaadinServletService(this, deploymentConfiguration) {

            @Override
            public ClassLoader getClassLoader() {
                return super.getClassLoader();
            }

        };

        service.init();

        return service;
    }
}
