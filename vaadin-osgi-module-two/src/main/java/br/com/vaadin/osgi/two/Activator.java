package br.com.vaadin.osgi.two;

import br.com.vaadin.osgi.service.FragmentFactory;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

public class Activator implements BundleActivator {

    private ServiceRegistration serviceRegistration;
    private FragmentFactory service;

    @Override
    public void start(BundleContext context) throws Exception {
        System.out.println(this.getClass().getName() + ".start()");
        service = new FragmentFactoryImpl1();
        serviceRegistration = context.registerService(FragmentFactory.class, service, null);
        System.out.println(this.getClass().getName() + ".start()");
    }

    @Override
    public void stop(BundleContext context) throws Exception {
        System.out.println(this.getClass().getName() + ".stop()");
    }

}
