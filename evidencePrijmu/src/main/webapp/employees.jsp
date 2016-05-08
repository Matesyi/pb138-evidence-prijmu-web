<%-- 
    Document   : employees
    Created on : May 7, 2016, 4:17:22 PM
    Author     : Matej Karolyi
--%>

<%@page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
    <head>
        <%@include  file="components/header.html" %>
        <title>Employees | Income Recording Application</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    </head>
    <body>
        <%@include  file="components/navigation.html" %>

        <div class="container">
            <div class="row body-section">
                <div class="col-md-12">
                    <h1>
                        List of Employees
                    </h1>
                </div>
            </div>
            <div class="body-section">
                <div class="row">
                    <div class="col-md-4">
                        <a class="tile link-tile btn btn-block" href="employee.jsp" title="add new employee">Add new Employee</a>
                    </div>
                    <div class="col-md-4">
                        <a class="tile link-tile btn btn-block" href="employee.jsp" title="add new employee">Edit selected Employee</a>
                    </div>
                    <div class="col-md-4">
                        <a class="tile link-tile btn btn-block" href="employee.jsp" title="add new employee">Delete selected Employee</a>
                    </div>
                </div>
                <div class="row margin-box">
                    <div class="col-md-8 col-md-offset-2">
                        <table id="employees-table" width="100%">
                            <thead>
                                <tr>
                                    <th style="width:20%">Personal number</th>
                                    <th style="width:40%">Name</th>
                                    <th style="width:40%">Surname</th>
                                </tr>
                            </thead>
                        </table>
                    </div>
                </div>
            </div>
        </div>

        <%@include  file="components/footer.html" %>
        
        <script>
            var employeesData = [
                {
                    "personal_number": 15,
                    "name": "Tester",
                    "surname": "Testovaci"
                },
                {
                    "personal_number": 186,
                    "name": "John",
                    "surname": "Placeholder"
                },
                {
                    "personal_number": 56,
                    "name": "Peter",
                    "surname": "Parker"
                },
                {
                    "personal_number": 11,
                    "name": "Place",
                    "surname": "Holder"
                },
                {
                    "personal_number": 9586,
                    "name": "Sky",
                    "surname": "Scraper"
                }
            ];
            
            function generate_footer($footer, datagrid, data) {
                $footer.append(
                    "<tr style='text-align: center'><td colspan='3'>my footer for this table</td></tr>"
                );
            };

            $('#employees-table').simple_datagrid(
                {
                    order_by: true,
                    data: employeesData,
                    on_generate_footer: generate_footer
                }
            );
        </script>
    </body>
</html>
