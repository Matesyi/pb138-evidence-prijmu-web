<%-- 
    Document   : help
    Created on : May 7, 2016, 4:28:10 PM
    Author     : Matej Karolyi
--%>

<%@page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
    <head>
        <%@include  file="components/header.html" %>
        <title>Help | Income Recording Application</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    </head>
    <body>
        <%@include  file="components/navigation.html" %>
        
        <div class="container">
            <div class="row body-section">
                <div class="col-md-12">
                    <h1>
                        Help Page
                    </h1>
                </div>
            </div>
            <div class="body-section">
                <div class="row">
                    <div class="col-md-12">
                        <h4>Features</h4>
                        <ul>
                            <li>Application allows creating, editing and removing Employees and Work Types</li>
                            <li>Users can produce and store Working sheets using interactive forms</li>
                            <li>Tool for automatic producing invoices is provided</li>
                            <li>The working history is visible through integrated browser which is enhanced by set of filters</li>
                        </ul>
                    </div>
                </div>
            </div>
            <div class="body-section">
                <div class="row">
                    <div class="col-md-12">
                        <h4>Creating employees and work types</h4>
                        <p>
                            Application allows you create and edit employees and work types. Just go to relevant pages (Employees, Work Types)
                            and you will see all eployees/work types listed in data grid. After clicking to "Add new ..." and filling the form which appears,
                            new entity will be created. For edit existing employees/work types choose the desired row and click to "Edit selected ...". To delete select
                            the row again and click to "Delete ...".
                        </p>
                    </div>
                </div>
            </div>
            <div class="body-section">
                <div class="row">
                    <div class="col-md-12">
                        <h4>Creating worksheets</h4>
                        <p>
                            If you want to create worksheet for your employer got to page Worksheets and fill provided form.
                            Select employee (you), date (in months) and one or more rows with stored work types with amount of
                            time you spent to do this work. When your worksheet is ready save it. It will be stored in our database
                            for next usage and export of invoice.
                        </p>
                    </div>
                </div>
            </div>
            <div class="body-section">
                <div class="row">
                    <div class="col-md-12">
                        <h4>Data browsing</h4>
                        <p>
                            System provides feature called Invoice Browser. Using it you are able to list and filter existing
                            records of worksheets. Use set of filters to specify the selection. Information about worksheets 
                            (name of employee, date, price) will be shown in data grid. You can print it or export information about
                            whole selection to docbook format. If you want to see detail of invoice, select the row with desired
                            record and click to "Detail of selected invoice".
                        </p>
                    </div>
                </div>
            </div>
        </div>

        <%@include  file="components/footer.html" %>
    </body>
</html>
