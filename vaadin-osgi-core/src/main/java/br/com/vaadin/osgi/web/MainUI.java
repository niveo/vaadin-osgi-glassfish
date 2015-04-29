package br.com.vaadin.osgi.web;

import br.com.vaadin.osgi.service.FragmentFactory;
import com.vaadin.annotations.Push;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.UI;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

@Theme("mytheme")
@Push
@Title("VAADIN OSGI")
public class MainUI extends UI implements
        ServiceTrackerCustomizer<FragmentFactory, FragmentFactory> {

    private TabSheet tabSheet;
    private ServiceTracker<FragmentFactory, FragmentFactory> tracker;

    @Override
    protected void init(VaadinRequest vaadinRequest) {

        tabSheet = new TabSheet();
        tabSheet.setSizeFull();
        setContent(tabSheet);

        tracker = new ServiceTracker<>(
                VaadinActivator.context, FragmentFactory.class, this);
        tracker.open();

        try {
            if (tracker.getTrackingCount() > 0) {

                /*ServiceReference<FragmentFactory>[] serviceReferences = tracker.getServiceReferences();
                 for (ServiceReference<FragmentFactory> serviceReference : serviceReferences) {
                    
                 Bundle bundle = serviceReference.getBundle();
                    
                 FragmentFactory ff = VaadinActivator.context.getService(serviceReference);
                    
                 TabSheet.Tab addTab = tabSheet.addTab(ff.getFragment());
                 addTab.setId("ID_" + bundle.getBundleId());
                 addTab.setCaption(String.valueOf(bundle.getBundleId()));
                    
                 }*/
            }

        } catch (Exception ex) {
            Logger.getLogger(MainUI.class.getName()).log(Level.SEVERE, null, ex);
        }

        addDetachListener(new DetachListener() {

            @Override
            public void detach(DetachEvent event) {
                tracker.close();
            }
        });
    }

    @Override
    public FragmentFactory addingService(ServiceReference<FragmentFactory> reference) {
        System.out.println(this.getClass().getName() + ".addingService()");
        FragmentFactory ff = VaadinActivator.context.getService(reference);
        TabSheet.Tab addTab = tabSheet.addTab(ff.getFragment());
        addTab.setId("ID_" + reference.getBundle().getBundleId());
        addTab.setCaption(String.valueOf(reference.getBundle().getBundleId()));

        return ff;
    }

    @Override
    public void modifiedService(ServiceReference<FragmentFactory> sr, FragmentFactory t) {
        System.out.println(this.getClass().getName() + ".modifiedService()");
    }

    @Override
    public void removedService(ServiceReference<FragmentFactory> sr, FragmentFactory t) {
        System.out.println(this.getClass().getName() + ".removedService()");

        int componentCount = tabSheet.getComponentCount();
        for (int i = 0; i < componentCount; i++) {
            TabSheet.Tab tab = tabSheet.getTab(i);
            if (tab.getId().equals("ID_" + sr.getBundle().getBundleId())) {
                tabSheet.removeTab(tab);
            }
        }

    }
}
