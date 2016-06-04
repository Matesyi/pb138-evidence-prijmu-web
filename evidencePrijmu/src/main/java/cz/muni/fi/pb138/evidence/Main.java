package cz.muni.fi.pb138.evidence;

import cz.muni.fi.pb138.evidence.xmlEdit.ExecuteQuery;
import java.net.URI;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

import java.awt.Desktop;

public class Main
{
    // Resource path pointing to where the WEBROOT is
    private static final String RESOURCE_BASE = "src/main/webapp";
    private static final String DESCRIPTOR_PATH = "src/main/webapp/WEB-INF/web.xml";
    private static Server server;

    public static void main(String[] args) throws Exception
    {
        Main main = new Main();

        int serverPort = 8090;
        main.startJetty(serverPort);
        
        // initializing exist-db
        ExecuteQuery.initCollection();
        
        if (Desktop.isDesktopSupported()) {
            Desktop.getDesktop().browse(new URI("http://localhost:" + serverPort + "/"));
        }
        
        // stops server with any key
        System.in.read();
        main.stopJetty();
    }

    private void startJetty(int port) throws Exception
    {
        server = new Server(port); // initialize server with port

        // set webapp context
        WebAppContext webApp = new WebAppContext();
        webApp.setDescriptor(DESCRIPTOR_PATH);
        webApp.setResourceBase(RESOURCE_BASE);

        // set the context path of the app
        webApp.setContextPath("/");
        server.setHandler(webApp);

        // start server
        server.start();
    }

    private void stopJetty() throws Exception
    {
        server.stop();
    }
}
