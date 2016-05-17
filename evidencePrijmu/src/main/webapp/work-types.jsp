<%-- 
    Document   : work-types
    Created on : May 7, 2016, 4:18:17 PM
    Author     : Matej Karolyi
--%>

<%@page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
    <head>
        <%@include  file="components/header.html" %>
        <title>Work Types | Income Recording Application</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    </head>
    <body>
        <%@include  file="components/navigation.html" %>

        <div class="container">
            <div class="row body-section">
                <div class="col-md-12">
                    <h1>
                        List of Work Types
                    </h1>
                </div>
            </div>
            <div class="body-section">
                <div class="row">
                    <div class="col-md-4">
                        <a class="tile link-tile btn btn-block" href="work-type.jsp" title="add new work type"><i class="fa fa-plus-square" aria-hidden="true"></i>&nbsp;&nbsp;&nbsp;Add new Work Type</a>
                    </div>
                    <div class="col-md-4">
                        <a class="tile link-tile btn btn-block" onclick="editSelectedWorkType('#work-types-table')" title="edit the work type"><i class="fa fa-pencil-square" aria-hidden="true"></i>&nbsp;&nbsp;&nbsp;Edit selected Work Type</a>
                    </div>
                    <div class="col-md-4">
                        <a class="tile link-tile btn btn-block" href="#" title="delete the work type"><i class="fa fa-minus-square" aria-hidden="true"></i>&nbsp;&nbsp;&nbsp;Delete selected Work Type</a>
                    </div>
                </div>
                <div class="row margin-box">
                    <div class="col-md-8 col-md-offset-2">
                        <table id="work-types-table" width="100%">
                            <col width="70%">
                            <col width="30%">
                            <thead>
                                <tr>
                                    <th>Work Type</th>
                                    <th>Price</th>
                                </tr>
                            </thead>
                        </table>
                    </div>
                </div>
            </div>
        </div>

        <%@include  file="components/footer.html" %>

        <script>
            var workTypesData = [
                {
                    "work_type": "Coding",
                    "price": 200
                },
                {
                    "work_type": "Programming",
                    "price": 300
                },
                {
                    "work_type": "Testing",
                    "price": 150
                },
                {
                    "work_type": "Coffee breaking",
                    "price": -40
                },
                {
                    "work_type": "Creating documentation",
                    "price": 150
                }
            ];

            $('#work-types-table').simple_datagrid(
                    {
                        order_by: false,
                        data: workTypesData
                    }
            );

            function editSelectedWorkType(target) {
                var row = $(target).simple_datagrid('getSelectedRow');
                if (typeof row !== 'undefined' && row !== null) {
                    $.redirect('work-type.jsp', {work_type: row.work_type, price: row.price}, 'GET');
                }
            }
            ;
        </script>
    </body>
</html>