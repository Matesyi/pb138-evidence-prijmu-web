package cz.muni.fi.pb138.evidence.servlet;

import cz.muni.fi.pb138.evidence.entities.Invoice;
import cz.muni.fi.pb138.evidence.entities.InvoiceManagerImpl;


import cz.muni.fi.pb138.evidence.invoices.InvoiceTransformatorImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;


/**
 * Created by lukas on 4.6.16.
 */
public class TransformationServlet extends HttpServlet {

    /**
     * TransformationServlet handle incoming requests to do transformation to docbook or pdf.
     *
     * @param req  data from java server page
     * @param resp response to java server page
     * @throws ServletException
     * @throws IOException
     */
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String url = req.getRequestURI();
            String urlParts[] = url.split("/", 4);

            InvoiceManagerImpl invoiceManager = new InvoiceManagerImpl();
            Invoice invoice = invoiceManager.getInvoiceById(Integer.parseInt(urlParts[3]));
            InvoiceTransformatorImpl invoiceTransformator = new InvoiceTransformatorImpl();

            if (urlParts[2].equals("pdf")) {
                // request to generate pdf

                generatePdf(invoiceTransformator, invoice);
                resp.sendRedirect("/transformations/invoice_pdf_tranformation.pdf");
            } else if (urlParts[2].equals("docbook")) {
                //request to generate docbook

                generateDocbook(invoiceTransformator, invoice);
                resp.sendRedirect("/transformations/invoice_docbook_tranformation.xml");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * generatePdf calls helper methods for generating pdf of invoice.
     *
     * @param invoiceTransformator transformator with functions for transformation
     * @param invoice              invoice to transform
     */
    private void generatePdf(InvoiceTransformatorImpl invoiceTransformator, Invoice invoice) {
        File file = new File("./src/main/webapp/transformations/invoice_pdf_tranformation.pdf");
        invoiceTransformator.storeInvoicePdf(invoice, file);
    }

    /**
     * generateDocbook calls helper methods for generating docbook of invoice
     *
     * @param invoiceTransformator transformator with functions for transformation
     * @param invoice              invoice to transform
     * @throws FileNotFoundException
     * @throws UnsupportedEncodingException
     */
    private void generateDocbook(InvoiceTransformatorImpl invoiceTransformator, Invoice invoice) throws FileNotFoundException, UnsupportedEncodingException {
        String invoiceDocbook = invoiceTransformator.transformToDocbook(invoice);
        PrintWriter writer = new PrintWriter("./src/main/webapp/transformations/invoice_docbook_tranformation.xml", "UTF-8");
        writer.print(invoiceDocbook);
        writer.close();
    }
}
