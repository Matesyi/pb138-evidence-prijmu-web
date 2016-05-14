<%-- 
    Document   : index
    Created on : May 7, 2016, 3:12:05 PM
    Author     : Matej Karolyi
--%>

<%@page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
    <head>
        <%@include  file="components/header.html" %>
        <title>Income Recording Application</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    </head>
    <body>
        <div class="container">
            <div class="row body-section spaced-section indented">
                <div class="col-md-12">
                    <h1>
                        Income Recording Application
                    </h1>
                </div>
            </div>
            <div class="body-section spaced-section indented">
                <div class="row">
                    <div class="col-md-6 col-md-offset-3">
                        <a class="tile link-tile btn btn-block" href="database-browser.jsp" title="invoices browser"><i class="fa fa-search" aria-hidden="true"></i>&nbsp;&nbsp;&nbsp;Invoices Browser</a>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-6 col-md-offset-3">
                        <a class="tile link-tile btn btn-block" href="worksheet.jsp" title="create a worksheet"><i class="fa fa-plus-square" aria-hidden="true"></i>&nbsp;&nbsp;&nbsp;Create a Worksheet</a>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-3 col-md-offset-3">
                        <a class="tile link-tile btn btn-block" href="work-types.jsp" title="work types"><i class="fa fa-database" aria-hidden="true"></i>&nbsp;&nbsp;&nbsp;Work Types</a>
                    </div>
                    <div class="col-md-3">
                        <a class="tile link-tile btn btn-block" href="employees.jsp" title="employees"><i class="fa fa-database" aria-hidden="true"></i>&nbsp;&nbsp;&nbsp;Employees</a>
                    </div>
                </div>
                <div class="row margin-box">
                    <div class="col-md-2 col-md-offset-5">
                        <a class="tile link-tile btn btn-block" href="help.jsp" title="go to help page"><i class="fa fa-question-circle" aria-hidden="true"></i>&nbsp;&nbsp;&nbsp;Help</a>
                    </div>
                </div>
            </div>
        </div>

        <%@include  file="components/footer.html" %>
    </body>
</html>
