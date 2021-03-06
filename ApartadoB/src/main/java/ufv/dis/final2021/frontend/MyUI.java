package ufv.dis.final2021.frontend;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import ufv.dis.final2021.backend.GeoIP;
import ufv.dis.final2021.backend.Utils;

import java.util.ArrayList;

/**
 * This UI is the application entry point. A UI may either represent a browser window 
 * (or tab) or some part of an HTML page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be 
 * overridden to add component to the user interface and initialize non-component functionality.
 */
@Theme("mytheme")
public class MyUI extends UI {

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        final VerticalLayout layout = new VerticalLayout();
        
        final TextField name = new TextField();
        name.setCaption("Introduzca una IP:"); //150.245.255.255"

        Button button = new Button("Enviar");
        button.addClickListener(e -> {

            Utils utils = new Utils();
            ArrayList<GeoIP> jsonFile= utils.getFile();
            //System.out.println(jsonFile.get(0));
            GeoIP geoInfo = null;
            try {
                geoInfo = utils.locateIP(name.getValue(), jsonFile);
                System.out.println(geoInfo);

                layout.addComponent(new Label("La información de geolocalización asociada a la IP introducida es: " + geoInfo));

            } catch (Exception exception) {
                System.out.println(exception.getMessage());
            }
        });
        
        layout.addComponents(name, button);
        
        setContent(layout);
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
