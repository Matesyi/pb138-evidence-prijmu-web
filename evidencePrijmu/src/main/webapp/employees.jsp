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
                        <a class="tile link-tile btn btn-block" href="employee.jsp" title="add new employee"><i class="fa fa-plus-square" aria-hidden="true"></i>&nbsp;&nbsp;&nbsp;Add new Employee</a>
                    </div>
                    <div class="col-md-4">
                        <a class="tile link-tile btn btn-block" onclick="editSelectedEmployee('#employees-table')" title="edit the employee"><i class="fa fa-pencil-square" aria-hidden="true"></i>&nbsp;&nbsp;&nbsp;Edit selected Employee</a>
                    </div>
                    <div class="col-md-4">
                        <a class="tile link-tile btn btn-block" href="#" title="delete the employee"><i class="fa fa-minus-square" aria-hidden="true"></i>&nbsp;&nbsp;&nbsp;Delete selected Employee</a>
                    </div>
                </div>
                <div class="row margin-box">
                    <div class="col-md-8 col-md-offset-2">
                        <table personal_number="employees-table" width="100%">
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
                    "surname": "Testovaci",
                    "address": "Test 1",
                    "city": "Brno",
                    "post_code": "60200"
                },
                {
                    "personal_number": 186,
                    "name": "John",
                    "surname": "Placeholder",
                    "address": "Test 1",
                    "city": "Brno",
                    "post_code": "60200"
                },
                {
                    "personal_number": 56,
                    "name": "Peter",
                    "surname": "Parker",
                    "address": "Test 1",
                    "city": "Brno",
                    "post_code": "60200"
                },
                {
                    "personal_number": 11,
                    "name": "Place",
                    "surname": "Holder",
                    "address": "Test 1",
                    "city": "Brno",
                    "post_code": "60200"
                },
                {
                    "personal_number": 9586,
                    "name": "Sky",
                    "surname": "Scraper",
                    "address": "Test 1",
                    "city": "Brno",
                    "post_code": "60200"
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
                    $.redirect('employee.jsp', {personal_number: row.personal_number, name: row.name, surname: row.surname, address: row.address, city: row.city, post_code: row.post_code}, 'GET');
                }
            };
        </script>
    </body>
</html>
