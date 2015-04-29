package br.com.vaadin.osgi.web;

import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.DeploymentConfiguration;
import com.vaadin.server.ServiceException;
import com.vaadin.server.VaadinServlet;
import com.vaadin.server.VaadinServletService;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;

@WebServlet(name = "vaadin", value = "/*", asyncSupported = true, initParams = {
    @WebInitParam(name = "UI", value = "br.com.vaadin.osgi.web.MainUI"),
    @WebInitParam(name = "productionMode", value = "false")})
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
