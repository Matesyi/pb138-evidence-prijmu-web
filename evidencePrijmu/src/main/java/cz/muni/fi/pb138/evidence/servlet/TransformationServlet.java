package cz.muni.fi.pb138.evidence.servlet;

import antlr.DocBookCodeGenerator;
import cz.muni.fi.pb138.evidence.entities.Invoice;
import cz.muni.fi.pb138.evidence.entities.InvoiceManagerImpl;
import cz.muni.fi.pb138.evidence.entities.Work;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.Map;

/**
 * Created by lukas on 4.6.16.
 */
public class TransformationServlet extends HttpServlet {
    /**
     * @param req  data from java server page
     * @param resp response to java server page
     * @throws ServletException
     * @throws IOException
     */
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String url = req.getRequestURI();
            String urlParts[] = url.split("/", 4);
            if (urlParts[2].equals("docbook")) {
                InvoiceManagerImpl invoiceManager = new InvoiceManagerImpl();
                Invoice invoice = invoiceManager.getInvoiceById(Integer.parseInt(urlParts[3]));

                StringWriter stringWriter = new StringWriter();
                XMLOutputFactory xMLOutputFactory = XMLOutputFactory.newInstance();
                XMLStreamWriter xMLStreamWriter = xMLOutputFactory.createXMLStreamWriter(stringWriter);

                xMLStreamWriter.writeStartDocument();
                xMLStreamWriter.writeStartElement("article");

                xMLStreamWriter.writeStartElement("articleinfo");

                xMLStreamWriter.writeStartElement("title");
                xMLStreamWriter.writeCharacters("Invoice");
                xMLStreamWriter.writeEndElement();

                xMLStreamWriter.writeStartElement("author");
                xMLStreamWriter.writeCharacters(invoice.getEmployer());
                xMLStreamWriter.writeEndElement();

                xMLStreamWriter.writeEndElement();
                generateInvoice(xMLStreamWriter, invoice);
                xMLStreamWriter.writeEndElement();
                xMLStreamWriter.writeEndDocument();

                xMLStreamWriter.flush();
                xMLStreamWriter.close();

                String xmlString = stringWriter.getBuffer().toString();

                stringWriter.close();
                PrintWriter writer = new PrintWriter("./src/main/webapp/transformations/invoice_docbook_tranformation.xml", "UTF-8");
                writer.print(xmlString);
                writer.close();

                resp.sendRedirect("/transformations/invoice_docbook_tranformation.xml");
            } else if (urlParts[2].equals("docbooks")) {

                InvoiceManagerImpl invoiceManager = new InvoiceManagerImpl();

                int personalNumber = 0;
                if (!req.getParameter("personal_number_hidden").isEmpty())
                    personalNumber = Integer.parseInt(req.getParameter("personal_number_hidden"));
                String surname = req.getParameter("employee_surname_hidden");
                int yearFrom = 1;
                if (!req.getParameter("year_from_hidden").isEmpty())
                    yearFrom = Integer.parseInt(req.getParameter("year_from_hidden"));
                int monthFrom = 0;
                if (!req.getParameter("month_from_hidden").isEmpty())
                    monthFrom = Integer.parseInt(req.getParameter("month_from_hidden"));
                int yearTo = 99999;
                if (!req.getParameter("year_to_hidden").isEmpty())
                    yearTo = Integer.parseInt(req.getParameter("year_to_hidden"));
                int monthTo = 0;
                if (!req.getParameter("month_to_hidden").isEmpty())
                    monthTo = Integer.parseInt(req.getParameter("month_to_hidden"));

                List<Invoice> invoices = invoiceManager.findInvoicesByFilter(personalNumber, surname, yearFrom, monthFrom, yearTo, monthTo);

                if (invoices.size() <= 0) {
                    resp.sendRedirect("/database-browser");
                }
                StringWriter stringWriter = new StringWriter();
                XMLOutputFactory xMLOutputFactory = XMLOutputFactory.newInstance();
                XMLStreamWriter xMLStreamWriter = xMLOutputFactory.createXMLStreamWriter(stringWriter);

                xMLStreamWriter.writeStartDocument();
                xMLStreamWriter.writeStartElement("article");

                xMLStreamWriter.writeStartElement("articleinfo");

                xMLStreamWriter.writeStartElement("title");
                xMLStreamWriter.writeCharacters("Invoice");
                xMLStreamWriter.writeEndElement();

//                xMLStreamWriter.writeStartElement("author");
//                xMLStreamWriter.writeCharacters(invoices.get(0).getEmployer());
//                xMLStreamWriter.writeEndElement();

                xMLStreamWriter.writeEndElement();
                for (Invoice invoice : invoices) {
                    generateInvoice(xMLStreamWriter, invoice);
                }

                xMLStreamWriter.writeEndElement();
                xMLStreamWriter.writeEndDocument();

                xMLStreamWriter.flush();
                xMLStreamWriter.close();

                String xmlString = stringWriter.getBuffer().toString();

                stringWriter.close();
                PrintWriter writer = new PrintWriter("./src/main/webapp/transformations/invoice_docbook_tranformation.xml", "UTF-8");
                writer.print(xmlString);
                writer.close();

                resp.sendRedirect("/transformations/invoice_docbook_tranformation.xml");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void generateInvoice(XMLStreamWriter xMLStreamWriter, Invoice invoice) throws XMLStreamException {

        xMLStreamWriter.writeStartElement("chapter");

        xMLStreamWriter.writeStartElement("title");
        xMLStreamWriter.writeCharacters("Invoice detail");
        xMLStreamWriter.writeEndElement();

        xMLStreamWriter.writeStartElement("para");
        xMLStreamWriter.writeCharacters("Invoice id: " + Long.toString(invoice.getId()));
        xMLStreamWriter.writeEndElement();


        xMLStreamWriter.writeStartElement("para");
        xMLStreamWriter.writeCharacters("Invoice date: " + Integer.toString(invoice.getMonth()) + "/" + Integer.toString(invoice.getYear()));
        xMLStreamWriter.writeEndElement();

        int priceSum = 0;
        for (Map.Entry<Work, Integer> entry : invoice.getWorks().entrySet()) {
            priceSum += entry.getValue() * entry.getKey().getPrice();
        }

        xMLStreamWriter.writeStartElement("para");
        xMLStreamWriter.writeCharacters("Invoice total price: " + Integer.toString(priceSum));
        xMLStreamWriter.writeEndElement();

        xMLStreamWriter.writeStartElement("section");

        xMLStreamWriter.writeStartElement("title");
        xMLStreamWriter.writeCharacters("Employee Information");
        xMLStreamWriter.writeEndElement();

        xMLStreamWriter.writeStartElement("para");
        xMLStreamWriter.writeCharacters("Personal Number: " + Integer.toString(invoice.getEmployee().getPersonal_number()));
        xMLStreamWriter.writeEndElement();

        xMLStreamWriter.writeStartElement("para");
        xMLStreamWriter.writeCharacters("Name: " + invoice.getEmployee().getName());
        xMLStreamWriter.writeEndElement();

        xMLStreamWriter.writeStartElement("para");
        xMLStreamWriter.writeCharacters("Surname: " + invoice.getEmployee().getSurname());
        xMLStreamWriter.writeEndElement();

        xMLStreamWriter.writeStartElement("para");
        xMLStreamWriter.writeCharacters("Address: " + invoice.getEmployee().getAddress());
        xMLStreamWriter.writeEndElement();

        xMLStreamWriter.writeStartElement("para");
        xMLStreamWriter.writeCharacters("Post code: " + invoice.getEmployee().getPostCode());
        xMLStreamWriter.writeEndElement();

        xMLStreamWriter.writeStartElement("para");
        xMLStreamWriter.writeCharacters("City: " + invoice.getEmployee().getCity());
        xMLStreamWriter.writeEndElement();

        xMLStreamWriter.writeEndElement();

        xMLStreamWriter.writeStartElement("section");

        xMLStreamWriter.writeStartElement("title");
        xMLStreamWriter.writeCharacters("Works Information");
        xMLStreamWriter.writeEndElement();

        for (Map.Entry<Work, Integer> entry : invoice.getWorks().entrySet()) {
            xMLStreamWriter.writeStartElement("section");
            xMLStreamWriter.writeStartElement("title");
            xMLStreamWriter.writeCharacters("Work type: " + entry.getKey().getWork_type());
            xMLStreamWriter.writeEndElement();

            xMLStreamWriter.writeStartElement("para");
            xMLStreamWriter.writeCharacters("Cost per hour: " + entry.getKey().getPrice());
            xMLStreamWriter.writeEndElement();

            xMLStreamWriter.writeStartElement("para");
            xMLStreamWriter.writeCharacters("Hours: " + entry.getValue());
            xMLStreamWriter.writeEndElement();
            xMLStreamWriter.writeEndElement();
        }

        xMLStreamWriter.writeEndElement();

        xMLStreamWriter.writeEndElement();

    }
}
