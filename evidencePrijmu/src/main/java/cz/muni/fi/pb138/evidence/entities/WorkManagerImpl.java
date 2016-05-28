package cz.muni.fi.pb138.evidence.entities;

import cz.muni.fi.pb138.evidence.xmlEdit.ExecuteQuery;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.xmldb.api.base.XMLDBException;

/**
 *
 * @author L
 */
public class WorkManagerImpl implements WorkManager {

    //Submethod used in createWork() to get ID. Next ID is stored in xml file. This method gets
    //this ID and updates value in xml file (autoincrement).
    public static int countID() {
        String query = "for $x in /root/works/nextID return $x/text()";
        int currentID;
        try {
            currentID = Integer.parseInt(ExecuteQuery.executeQuery(query));
            int newID = currentID + 1;
            String updateQuery = "update value /root/works/nextID with \"" + newID + "\"";
            Logger.getLogger(WorkManagerImpl.class.getName()).log(Level.INFO, updateQuery);
            ExecuteQuery.executeQuery(updateQuery);
        } catch (XMLDBException | ClassNotFoundException | InstantiationException | IllegalAccessException | NumberFormatException ex) {
            String msg = "Error when manipulating workID";
            Logger.getLogger(WorkManagerImpl.class.getName()).log(Level.SEVERE, msg, ex);
            throw new DatabaseAccessException(msg, ex);
        }
        return currentID;
    }

    //Stores new work type to xml database.
    @Override
    public void createWork(Work work) {
        work.setWorkID(countID());
        String query = "update insert <work>\n"
                + "<id>" + work.getWorkID() + "</id>\n"
                + "<type>" + work.getWork_type() + "</type>\n"
                + "<price>" + work.getPrice() + "</price>\n"
                + "</work>\n"
                + "into /root/works";
        Logger.getLogger(WorkManagerImpl.class.getName()).log(Level.INFO, query);
        try {
            ExecuteQuery.executeQuery(query);
        } catch (XMLDBException | ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
            String msg = "Error when storing new work type to database";
            Logger.getLogger(WorkManagerImpl.class.getName()).log(Level.SEVERE, msg, ex);
            throw new DatabaseAccessException(msg, ex);
        }
    }

    //Chooses a work by id. Id remains, other values are updated
    //from values of object given in parameter.
    @Override
    public void updateWork(Work work) {
        int workID = work.getWorkID();
        String query = "update replace /root/works/work[./id = \""
                + workID + "\"] with <work>\n"
                + "<id>" + work.getWorkID() + "</id>\n"
                + "<type>" + work.getWork_type() + "</type>\n"
                + "<price>" + work.getPrice() + "</price>\n"
                + "</work>";
        Logger.getLogger(WorkManagerImpl.class.getName()).log(Level.INFO, query);
        try {
            ExecuteQuery.executeQuery(query);
        } catch (XMLDBException | ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
            String msg = "Error when updating new work type in database";
            Logger.getLogger(WorkManagerImpl.class.getName()).log(Level.SEVERE, msg, ex);
            throw new DatabaseAccessException(msg, ex);
        }
    }

    //If work with given id is not in database, it runs normally but nothing is deleted.
    @Override
    public void deleteWork(Work work) {
        int workID = work.getWorkID();
        String query = "update delete /root/works/work[./id = \"" + workID + "\"]";
        Logger.getLogger(WorkManagerImpl.class.getName()).log(Level.INFO, query);
        try {
            ExecuteQuery.executeQuery(query);
        } catch (XMLDBException | ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
            String msg = "Error when deleting work type from database";
            Logger.getLogger(WorkManagerImpl.class.getName()).log(Level.SEVERE, msg, ex);
            throw new DatabaseAccessException(msg, ex);
        }
    }

    @Override
    public Work getWorkById(int id) {
        String query = "for $p in /root/works/work where ($p/id) = \""
                + Integer.toString(id) + "\" return (data($p/id), \";\" , data($p/type), \";\""
                + ", data($p/price))";
        String result = "";
        try {
            result = ExecuteQuery.executeQuery(query);
        } catch (XMLDBException | ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
            String msg = "Error when retrieving work type by ID from database";
            Logger.getLogger(WorkManagerImpl.class.getName()).log(Level.SEVERE, msg, ex);
        }
//        System.out.println(result);
        return resultToWork(result);
    }

    @Override
    public List<Work> findAllWorks() {
        List<Work> result = new ArrayList<>();
        String query = "for $p in /root/works/work"
                + " return (\":\", data($p/id), \";\" , data($p/type), \";\""
                + ", data($p/price))";
        String resultStr = "";
        try {
            resultStr = ExecuteQuery.executeQuery(query);
        } catch (XMLDBException | ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
            String msg = "Error when retrieving all work types from database";
            Logger.getLogger(WorkManagerImpl.class.getName()).log(Level.SEVERE, msg, ex);
        }
        String[] resultArray = resultStr.split(":");
        //Iteration from 1., not from 0. element (0. is empty String)
        for (int i = 1; i < resultArray.length; i++) {
            result.add(resultToWork(resultArray[i]));
        }
        return result;
    }

    public Work resultToWork(String result) {
        Work work = new Work();
        String[] resultArray = result.split(";");
        work.setWorkID(Integer.parseInt(resultArray[0]));
        work.setWork_type(resultArray[1]);
        work.setPrice(Integer.parseInt(resultArray[2]));
//        System.out.println(work.toString());
        return work;
    }
}
