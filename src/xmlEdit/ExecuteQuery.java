package xmlEdit;

import org.exist.xmldb.XQueryService;
import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.Database;
import org.xmldb.api.base.Resource;
import org.xmldb.api.base.ResourceIterator;
import org.xmldb.api.base.ResourceSet;
import org.xmldb.api.base.XMLDBException;

/**
 * Executes an extended XQuery. 
 * @author Lumir
 */
public class ExecuteQuery {

    //database access parameters
    protected static String driver = "org.exist.xmldb.DatabaseImpl";
    protected static String URI = "xmldb:exist://localhost:8080/exist/xmlrpc";
    protected static String collectionPath = "/db/sample";
    protected static String resourceName = "workEvidence.xml";
    
    public static String executeQuery(String xquery) throws XMLDBException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        //initialize database drivers
        Class cl = Class.forName(driver);
        Database database = (Database) cl.newInstance();
        DatabaseManager.registerDatabase(database);
        Collection collection = DatabaseManager.getCollection(URI+collectionPath);

        //executes XQuery
        XQueryService service = (XQueryService) collection.getService("XQueryService", "1.0");
        service.setProperty("indent", "yes");
        ResourceSet result = service.query(xquery);
        ResourceIterator i = result.getIterator();
        String resultStr = "";
        while (i.hasMoreResources()) {
            Resource r = i.nextResource();
            String value = (String) r.getContent();
//            System.out.println(value);
            resultStr += value;
        }
        return resultStr;
    }
}
