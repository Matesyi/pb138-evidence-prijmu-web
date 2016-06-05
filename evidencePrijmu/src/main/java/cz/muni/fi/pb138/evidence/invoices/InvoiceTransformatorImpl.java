package cz.muni.fi.pb138.evidence.invoices;

import cz.muni.fi.pb138.evidence.entities.Employee;
import cz.muni.fi.pb138.evidence.entities.Invoice;
import cz.muni.fi.pb138.evidence.entities.Work;
import cz.muni.fi.pb138.evidence.xmlEdit.ExecuteQuery;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Map.Entry;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.transform.stream.StreamSource;
import net.sf.saxon.s9api.Processor;
import net.sf.saxon.s9api.QName;
import net.sf.saxon.s9api.SAXDestination;
import net.sf.saxon.s9api.SaxonApiException;
import net.sf.saxon.s9api.XdmAtomicValue;
import net.sf.saxon.s9api.XsltCompiler;
import net.sf.saxon.s9api.XsltExecutable;
import net.sf.saxon.s9api.XsltTransformer;
import org.apache.fop.apps.FOPException;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.xmlgraphics.util.MimeConstants;
import org.xmldb.api.base.XMLDBException;

/**
 * Implementation of InvoiceGenerator interface.
 * @author Miloš Šilhár
 */
public class InvoiceTransformatorImpl implements InvoiceTransformator {
    
    // base name for stored invoices in exist-db
    public static final String FILE_NAME = "invoice";
    
    // path to docbook fo xslt transformation file
    public static final String FO_PATH = "src/main/resources/docbook-xsl/fo/docbook.xsl";
    
    @Override
    public void storeInvoiceDocbook(Invoice invoice) {
        try {
            String invoiceDocbookXmlStr = transformToDocbook(invoice);
            String file = FILE_NAME + invoice.getId() + ".xml";
            ExecuteQuery.putResource(file, invoiceDocbookXmlStr);
        } catch (XMLDBException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            throw new InvoiceException("Error storing invoice docbook.", e);
        }
    }
    
    @Override
    public void storeInvoicePdf(Invoice invoice, File f) {
        try (FileOutputStream fos = new FileOutputStream(f)) {
            fos.write(transformToPdf(invoice));
        } catch (IOException e) {
            throw new InvoiceException("Error storing invoice pdf.", e);
        }
    }

    @Override
    public String getInvoiceDocbookUrl(Invoice invoice) {
        try {
            String dbFile = FILE_NAME + invoice.getId() + ".xml";

            if (ExecuteQuery.executeQuery("fn:doc-available(\"" + dbFile + "\")").equals("true")) {
                String url = "http://localhost:8080/exist/rest" + ExecuteQuery.collectionPath + "/" + dbFile;
                return url;
            }
            return null;
        }
        catch (XMLDBException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            throw new InvoiceException("Error getting invoice url.", e);
        }
    }

    @Override
    public String transformToDocbook(Invoice invoice) {
        long id = invoice.getId();
        
        /* invoice total */
        long totalSum = 0;
        double tax = 21.0;
        
        try (StringWriter sw = new StringWriter()){
            XMLOutputFactory xof = XMLOutputFactory.newInstance();
            XMLStreamWriter xsw = xof.createXMLStreamWriter(sw);

            xsw.writeStartDocument("UTF-8", "1.0");
            xsw.writeStartElement("article");
            
            xsw.writeDefaultNamespace("http://docbook.org/ns/docbook");
            xsw.writeNamespace("xlink", "http://www.w3.org/1999/xlink");
            xsw.writeNamespace("xi", "http://www.w3.org/2001/XInclude");
            xsw.writeNamespace("svg", "http://www.w3.org/2000/svg");
            xsw.writeNamespace("m", "http://www.w3.org/1998/Math/MathML");
            xsw.writeNamespace("html", "http://www.w3.org/1999/xhtml");
            xsw.writeNamespace("db", "http://docbook.org/ns/docbook");
            
            xsw.writeStartElement("info");
                xsw.writeStartElement("title");
                xsw.writeCharacters("Invoice #");
                    xsw.writeStartElement("guilabel");
                    xsw.writeCharacters(Long.toString(id));
                    xsw.writeEndElement(); // end guilabel
                    xsw.writeCharacters(" ");
                    xsw.writeStartElement("date");
                    xsw.writeCharacters(invoice.getMonth() + "/" + invoice.getYear());
                    xsw.writeEndElement(); // end date
                xsw.writeEndElement(); // end title
            xsw.writeEndElement(); // end info
            
            Employee employee = invoice.getEmployee();
            
            // employee section
            xsw.writeStartElement("section");
                xsw.writeStartElement("title");
                xsw.writeCharacters("Employee");
                xsw.writeEndElement(); // end title
                xsw.writeStartElement("address");
                    xsw.writeStartElement("personname");
                    xsw.writeCharacters(employee.getName() + " " + employee.getSurname() + "\n");
                    xsw.writeEndElement(); // end personname
                    xsw.writeStartElement("street");
                    xsw.writeCharacters(employee.getAddress() + "\n");
                    xsw.writeEndElement(); // end street
                    xsw.writeStartElement("city");
                    xsw.writeCharacters(employee.getCity() + "\n");
                    xsw.writeEndElement(); // end city
                    xsw.writeStartElement("postcode");
                    xsw.writeCharacters(employee.getPostCode() + "\n");
                    xsw.writeEndElement(); // end postcode
                xsw.writeEndElement(); // end address
            xsw.writeEndElement(); // end section
            
            // company section
            xsw.writeStartElement("section");
                xsw.writeStartElement("title");
                xsw.writeCharacters("Company");
                xsw.writeEndElement(); // end title
                xsw.writeCharacters(invoice.getEmployer());
            xsw.writeEndElement(); // end section
            
            // work sheet section
            xsw.writeStartElement("section");
                xsw.writeStartElement("title");
                xsw.writeCharacters("Works on record");
                xsw.writeEndElement(); // end title
                xsw.writeStartElement("informaltable");
                    xsw.writeStartElement("tgroup");
                    xsw.writeAttribute("cols", "4");
                        xsw.writeStartElement("thead");
                            xsw.writeStartElement("row");
                                xsw.writeStartElement("entry");
                                xsw.writeAttribute("align", "center");
                                xsw.writeCharacters("Job Type");
                                xsw.writeEndElement(); // end entry
                                xsw.writeStartElement("entry");
                                xsw.writeAttribute("align", "center");
                                xsw.writeCharacters("Hour cost");
                                xsw.writeEndElement(); // end entry
                                xsw.writeStartElement("entry");
                                xsw.writeAttribute("align", "center");
                                xsw.writeCharacters("Working hours");
                                xsw.writeEndElement(); // end entry
                                xsw.writeStartElement("entry");
                                xsw.writeAttribute("align", "center");
                                xsw.writeCharacters("SubTotal (w/o taxes)");
                                xsw.writeEndElement(); // end entry
                            xsw.writeEndElement(); // end row
                        xsw.writeEndElement(); // end thead
                        xsw.writeStartElement("tbody");
                            for (Entry<Work, Integer> work : invoice.getWorks().entrySet())
                            {
                                xsw.writeStartElement("row");
                                    xsw.writeStartElement("entry");
                                    xsw.writeCharacters(work.getKey().getWork_type());
                                    xsw.writeEndElement(); // end entry
                                    xsw.writeStartElement("entry");
                                    xsw.writeAttribute("align", "right");
                                    xsw.writeCharacters(String.format("%.2f", (double)work.getKey().getPrice()) + " ,-");
                                    xsw.writeEndElement(); // end entry
                                    xsw.writeStartElement("entry");
                                    xsw.writeAttribute("align", "right");
                                    xsw.writeCharacters(Integer.toString(work.getValue()));
                                    xsw.writeEndElement(); // end entry
                                    xsw.writeStartElement("entry");
                                    xsw.writeAttribute("align", "right");
                                    xsw.writeCharacters(String.format("%.2f", (double)work.getKey().getPrice() * work.getValue()) + " ,-");
                                    xsw.writeEndElement(); // end entry
                                xsw.writeEndElement(); // end row
                                totalSum += work.getKey().getPrice() * work.getValue();
                            }
                        xsw.writeEndElement(); // end tbody
                    xsw.writeEndElement(); // end tgroup
                xsw.writeEndElement(); // end informaltable
            xsw.writeEndElement(); // end section
            
            // total summary section
            xsw.writeStartElement("section");
                xsw.writeStartElement("title");
                xsw.writeCharacters("Summary");
                xsw.writeEndElement(); // end title
                xsw.writeStartElement("informaltable");
                    xsw.writeStartElement("tgroup");
                    xsw.writeAttribute("cols", "2");
                        xsw.writeStartElement("colspec");
                        xsw.writeAttribute("align", "center");
                        xsw.writeEndElement(); // end colspec
                        xsw.writeStartElement("tbody");
                            xsw.writeStartElement("row");
                                xsw.writeStartElement("entry");
                                xsw.writeCharacters("Subtotal all works");
                                xsw.writeEndElement(); // end entry
                                xsw.writeStartElement("entry");
                                xsw.writeAttribute("align", "right");
                                xsw.writeCharacters(String.format("%.2f", (double)totalSum) + " ,-");
                                xsw.writeEndElement(); // end entry
                            xsw.writeEndElement(); // end row
                            xsw.writeStartElement("row");
                                xsw.writeStartElement("entry");
                                xsw.writeCharacters("Tax Rate " + String.format("%.1f", tax) + "%");
                                xsw.writeEndElement(); // end entry
                                xsw.writeStartElement("entry");
                                xsw.writeAttribute("align", "right");
                                xsw.writeCharacters(String.format("%.2f", totalSum * (tax / 100.0)) + " ,-");
                                xsw.writeEndElement(); // end entry
                            xsw.writeEndElement(); // end row
                            xsw.writeStartElement("row");
                                xsw.writeStartElement("entry");
                                xsw.writeCharacters("Total balance");
                                xsw.writeEndElement(); // end entry
                                xsw.writeStartElement("entry");
                                xsw.writeAttribute("align", "right");
                                xsw.writeCharacters(String.format("%.2f", totalSum * (1.0 + tax / 100.0)) + " ,-");
                                xsw.writeEndElement(); // end entry
                            xsw.writeEndElement(); // end row
                        xsw.writeEndElement(); // end tbody
                    xsw.writeEndElement(); // end tgroup
                xsw.writeEndElement(); // end informaltable
            xsw.writeEndElement(); // end section

            xsw.writeEndElement(); // end article
            xsw.writeEndDocument();
            
            xsw.flush();
            xsw.close();
            
            String xmlString = sw.getBuffer().toString();
            return xmlString;
        } catch (Exception e) {
            throw new InvoiceException("Error transforming to docbook.", e);
        }
    }

    @Override
    public byte[] transformToPdf(Invoice invoice) {
        try {
            Processor p = new Processor(false);
            XsltCompiler xslt = p.newXsltCompiler();
            XsltExecutable xsltExec = xslt.compile(new StreamSource(new File(FO_PATH)));
            XsltTransformer xsltTrans = xsltExec.load();

            // FOP Handler
            FopFactory ff = FopFactory.newInstance(new File(".").toURI());
            
            // output stream with pdf content
            
            try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {

                Fop fop = ff.newFop(MimeConstants.MIME_PDF, baos);

                SAXDestination des = new SAXDestination(fop.getDefaultHandler());

                // disabling table of content
                xsltTrans.setParameter(new QName("generate.toc"), new XdmAtomicValue("article nop"));
                
                // transforming to docbook
                String docbook = transformToDocbook(invoice);
                
                // setting source and destination
                xsltTrans.setSource(new StreamSource(new StringReader(docbook)));
                xsltTrans.setDestination(des);

                // transforming 
                xsltTrans.transform();
                
                // returning result
                return baos.toByteArray();
            }
        }
        catch (SaxonApiException | IOException | FOPException e) {
            throw new InvoiceException("Error transforming to pdf.", e);
        }
    }
    
}
