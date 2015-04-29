package br.com.vaadin.osgi.web;

import br.com.vaadin.osgi.service.FragmentFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.http.HttpService;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

public class VaadinActivator implements BundleActivator,
        ServiceTrackerCustomizer<FragmentFactory, FragmentFactory> {

    private static final Logger log = Logger.getLogger(VaadinActivator.class.getCanonicalName());

    private static final List<FragmentFactory> factories = new ArrayList<>(); //not sure if it should be done this way…

    static BundleContext context;
    HttpService httpService;

    ServiceTracker<FragmentFactory, FragmentFactory> fragmentFactoriesTracker;

    @Override
    public void start(BundleContext context) throws Exception {
        System.out.println(this.getClass().getName() + ".start()");

        VaadinActivator.context = context;

        fragmentFactoriesTracker = new ServiceTracker<>(
                context, FragmentFactory.class, this);

        fragmentFactoriesTracker.open();

        System.out.println(this.getClass().getName() + ".start()");
    }

    @Override
    public void stop(BundleContext context) throws Exception {
        System.out.println(this.getClass().getName() + ".stop()");

        fragmentFactoriesTracker.close();

        context = null;

        System.out.println(this.getClass().getName() + ".stop()");
    }

    @Override
    public FragmentFactory addingService(
            ServiceReference<FragmentFactory> reference) {

        System.out.println(this.getClass().getName() + ".addingService()");

        FragmentFactory ff = context.getService(reference);

        System.out.println(this.getClass().getName() + ".addingService(): " + ff);
        if (!factories.contains(ff)) {
            factories.add(ff);
        }

        System.out.println(this.getClass().getName() + ".addingService()");

        return ff;
    }

    @Override
    public void modifiedService(ServiceReference<FragmentFactory> reference,
            FragmentFactory service) {
        System.out.println(this.getClass().getName() + ".modifiedService()");
    }

    @Override
    public void removedService(ServiceReference<FragmentFactory> reference,
            FragmentFactory ff) {
        System.out.println(this.getClass().getName() + ".removedService()");
        System.out.println(this.getClass().getName() + ".removedService(): " + ff);
        factories.remove(ff);
    }
}
