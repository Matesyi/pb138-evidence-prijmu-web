package cz.muni.fi.pb138.evidence.servlet;

import com.google.gson.Gson;
import cz.muni.fi.pb138.evidence.entities.Work;
import cz.muni.fi.pb138.evidence.entities.WorkManagerImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by lukas on 28.5.16.
 */
public class WorkTypesServlet extends HttpServlet {

    /**
     * @param req  data from java server page
     * @param resp response to java server page
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String url = req.getRequestURI();
        WorkManagerImpl workManager = new WorkManagerImpl();
        switch (url) {
            case "/work-types":
                List<Work> works = workManager.findAllWorks();
                String workJson = new Gson().toJson(works);
                req.setAttribute("worksJson", workJson);
                RequestDispatcher viewWorks = req.getRequestDispatcher("/work-types.jsp");
                viewWorks.forward(req, resp);
                break;
            case "/work-type":
                RequestDispatcher viewWork = req.getRequestDispatcher("/work-type.jsp");
                viewWork.forward(req, resp);
                break;
            default:
                String urlParts[] = url.split("/", 4);
                Work work = workManager.getWorkById(Integer.parseInt(urlParts[3]));
                workManager.deleteWork(work);
                resp.sendRedirect("/work-types");
                break;
        }
    }

    /**
     * @param req  data from java server page
     * @param resp response to java server page
     * @throws ServletException
     * @throws IOException
     */
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String url = req.getRequestURI();
        switch (url) {
            case "/work-type/create":
                Work work = new Work();
                work.setWork_type(req.getParameter("work_type"));
                work.setPrice(Integer.parseInt(req.getParameter("price")));
                WorkManagerImpl workManager = new WorkManagerImpl();

                if (req.getParameter("work_id").isEmpty()) {
                    workManager.createWork(work);
                } else {
                    work.setWork_id(Integer.parseInt(req.getParameter("work_id")));
                    workManager.updateWork(work);
                }
        }
        resp.sendRedirect("/work-types");
    }
}
