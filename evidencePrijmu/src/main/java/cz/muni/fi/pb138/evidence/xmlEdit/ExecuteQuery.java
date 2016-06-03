package cz.muni.fi.pb138.evidence.xmlEdit;

import java.io.File;
import org.exist.xmldb.XQueryService;
import org.exist.xmldb.XmldbURI;
import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.Database;
import org.xmldb.api.base.Resource;
import org.xmldb.api.base.ResourceIterator;
import org.xmldb.api.base.ResourceSet;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.CollectionManagementService;
import org.xmldb.api.modules.XMLResource;

/**
 * Executes an extended XQuery. 
 * @author Lumir, Milos
 */
public class ExecuteQuery {

    // required parameters
    protected static String driver = "org.exist.xmldb.DatabaseImpl";
    
    //database access parameters
    protected static String URI = "xmldb:exist://localhost:8080/exist/xmlrpc";
    protected static String username = "admin";
    protected static String password = "";
    
    // path to initial workevidence xml
    protected static String initResource = "../workEvidenceInit.xml";
    
    // collection path
    protected static String collectionPath = "/db/sample";
  
    private static Collection getDbCollection()
            throws XMLDBException, ClassNotFoundException, InstantiationException, IllegalAccessException
    {
        Class cl = Class.forName(driver);
        Database database = (Database) cl.newInstance();
        DatabaseManager.registerDatabase(database);
        Collection collection = 
                DatabaseManager.getCollection(URI+collectionPath, username, password);
        
        return collection;
    }
    
    public static String executeQuery(String xquery)
            throws XMLDBException, ClassNotFoundException, InstantiationException, IllegalAccessException
    {
        Collection col = getDbCollection();

        //executes XQuery
        XQueryService service = (XQueryService) col.getService("XQueryService", "1.0");
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
    
    public static void initCollection()
            throws XMLDBException, ClassNotFoundException, InstantiationException, IllegalAccessException
    {		
        // try to get collection
        Collection col = getDbCollection();
        
        if(col == null) {
            // collection does not exist: get root collection and create.
            // for simplicity, we assume that the new collection is a
            // direct child of the root collection, e.g. /db/test.
            // the example will fail otherwise.
            Collection root = DatabaseManager.getCollection(URI + XmldbURI.ROOT_COLLECTION, username, password);
            CollectionManagementService mgtService = 
                (CollectionManagementService)root.getService("CollectionManagementService", "1.0");
            mgtService.createCollection(collectionPath.substring((XmldbURI.ROOT_COLLECTION + "/").length()));
        }
        
        // puts default resource to db
        putResource(initResource);
    }
    
    public static void putResource(String fileName)
            throws XMLDBException, ClassNotFoundException, InstantiationException, IllegalAccessException
    {
        Collection col = getDbCollection();
            
        File f = new File(fileName);
        // create new XMLResource
        XMLResource document = (XMLResource)col.createResource(f.getName(), "XMLResource");
        document.setContent(f);
        System.out.print("storing document " + document.getId() + "...");
        col.storeResource(document);
        System.out.println("ok.");
        
    }
}
