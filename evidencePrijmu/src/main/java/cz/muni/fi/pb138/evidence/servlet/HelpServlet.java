package cz.muni.fi.pb138.evidence.servlet;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by lukas on 29.5.16.
 */
public class HelpServlet extends HttpServlet {


    /**
     * @param req  data from java server page
     * @param resp response to java server page
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // show help page
        RequestDispatcher viewWorks = req.getRequestDispatcher("/help.jsp");
        viewWorks.forward(req, resp);
    }
}
