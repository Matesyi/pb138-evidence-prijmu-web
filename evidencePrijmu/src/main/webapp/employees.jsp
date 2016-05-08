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
                        <a class="tile link-tile btn btn-block" onclick="editSelectedEmployee('#employees-table')" title="edit the employee">Edit selected Employee</a>
                    </div>
                    <div class="col-md-4">
                        <a class="tile link-tile btn btn-block" href="#" title="delete the employee">Delete selected Employee</a>
                    </div>
                </div>
                <div class="row margin-box">
                    <div class="col-md-8 col-md-offset-2">
                        <table id="employees-table" width="100%">
                            <col width="26%">
                            <col width="37%">
                            <col width="37%">
                            <thead>
                                <tr>
                                    <th>Personal number</th>
                                    <th>Name</th>
                                    <th>Surname</th>
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

            $('#employees-table').simple_datagrid(
                    {
                        order_by: false,
                        data: employeesData
                    }
            );
    
            function editSelectedEmployee(target) {
                var row = $(target).simple_datagrid('getSelectedRow');
                if (typeof row !== 'undefined' && row !== null) {
                    $.redirect('employee.jsp', {personal_number: row.personal_number, name: row.name, surname: row.surname}, 'GET');
                }
            };
        </script>
    </body>
</html>
