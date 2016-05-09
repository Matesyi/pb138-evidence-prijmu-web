package xmlEdit;

import org.exist.xmldb.XQueryService;
import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.Database;
import org.xmldb.api.base.Resource;
import org.xmldb.api.base.ResourceIterator;
import org.xmldb.api.base.ResourceSet;

/**
 * Retrieve a document from the database. To run this example enter:
 *
 * java -jar start.jar org.exist.examples.xmldb.Retrieve collection document
 *
 * in the root directory of the distribution.
 *
 * @author Wolfgang Meier <meier@ifs.tu-darmstadt.de>
 * @edit L
 */
public class Retrieve {

    protected static String driver = "org.exist.xmldb.DatabaseImpl";
    protected static String URI = "xmldb:exist://localhost:8080/exist/xmlrpc";
    protected static String collectionPath = "/db/sample";
    protected static String resourceName = "workEvidence.xml";

    public static void main(String args[]) throws Exception {
        // initialize database drivers
        Class cl = Class.forName(driver);
        Database database = (Database) cl.newInstance();
        DatabaseManager.registerDatabase(database);
        Collection collection = DatabaseManager.getCollection(URI+collectionPath);

        String xquery = "update insert <nextID>1</nextID> into /root/records";
        XQueryService service = (XQueryService) collection.getService("XQueryService", "1.0");
        service.setProperty("indent", "yes");
        ResourceSet result = service.query(xquery);
        ResourceIterator i = result.getIterator();
        while (i.hasMoreResources()) {
            Resource r = i.nextResource();
            String value = (String) r.getContent();
            System.out.println(value);
        }
    }
}
