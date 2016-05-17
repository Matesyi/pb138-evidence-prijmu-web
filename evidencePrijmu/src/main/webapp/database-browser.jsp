<%-- 
    Document   : database-browser
    Created on : May 7, 2016, 4:12:05 PM
    Author     : Matej Karolyi
--%>

<%@page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
    <head>
        <%@include  file="components/header.html" %>
        <title>Browser | Income Recording Application</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    </head>
    <body>
        <%@include  file="components/navigation.html" %>

        <div class="container">
            <div class="row body-section">
                <div class="col-md-12">
                    <h1>
                        Invoices Browser
                    </h1>
                </div>
            </div>
            <div class="body-section">
                <h3 class="text-left">Filters:</h3>
                <form action="" method="post">
                    <div class="row">
                        <div class="col-md-6">
                            <label class="input-label">
                                <h4>Employee - personal number</h4>
                                <input type="text" name="personal_number" class="form-control" />
                            </label>
                        </div>
                        <div class="col-md-6">
                            <label class="input-label">
                                <h4>Employee - surname</h4>
                                <input type="text" name="surname" class="form-control" />
                            </label>
                        </div>
                        <div class="col-md-6">
                            <label class="input-label">
                                <h4>Date from</h4>
                                <input name="date_from" id="dateFrom" class="date-picker form-control" />
                            </label>
                        </div>
                        <div class="col-md-6">
                            <label class="input-label">
                                <h4>Date to</h4>
                                <input name="date_to" id="dateTo" class="date-picker form-control" />
                            </label>
                        </div>
                    </div>
                    <div class="row margin-box">
                        <div class="col-md-6 col-md-offset-3">
                            <button type="submit" class="tile link-tile btn btn-block" ><i class='fa fa-filter' aria-hidden='true'></i>&nbsp;&nbsp;&nbsp;FILTER</button>
                        </div>
                    </div>
                </form>
                <div class="row margin-box">
                    <div class="col-md-10 col-md-offset-1">
                        <table id="invoice-table" width="100%">
                            <col width="34%">
                            <col width="33%">
                            <col width="33%">
                            <thead>
                                <tr>
                                    <th>Employee</th>
                                    <th>Date</th>
                                    <th>Price</th>
                                </tr>
                            </thead>
                        </table>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-6 col-md-offset-1">
                        <p id="price-sum" class="lead"></p>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-6 col-md-offset-3">
                        <a class="tile link-tile btn btn-block" onClick="window.print()" title="generate output from showed invoices"><i class="fa fa-print" aria-hidden="true"></i>&nbsp;&nbsp;&nbsp;Export/print the page</a>
                    </div>
                    <div class="col-md-6 col-md-offset-3">
                        <a class="tile link-tile btn btn-block" onClick="" title="generate output from showed invoices"><i class="fa fa-external-link" aria-hidden="true"></i>&nbsp;&nbsp;&nbsp;Generate output from showed invoices</a>
                    </div>
                </div>
            </div>
        </div>

        <%@include  file="components/footer.html" %>

        <script>
            var invoiceData = [
                {
                    "employee": "11, Place Holder",
                    "date": "May 2016",
                    "price": "2500"
                },
                {
                    "employee": "130, John Tester",
                    "date": "May 2016",
                    "price": "4302"
                },
                {
                    "employee": "11, Place Holder",
                    "date": "June 2016",
                    "price": "3207"
                },
                {
                    "employee": "11, Place Holder",
                    "date": "July 2016",
                    "price": "3012"
                },
                {
                    "employee": "130, John Tester",
                    "date": "July 2016",
                    "price": "1240"
                },
                {
                    "employee": "40, Papa Mamma",
                    "date": "July 2016",
                    "price": "5023"
                },
                {
                    "employee": "11, Place Holder",
                    "date": "August 2016",
                    "price": "3210"
                }
            ];

            $('#invoice-table').simple_datagrid(
                    {
                        order_by: false,
                        data: invoiceData
                    }
            );

            //final sum counting and displaying
            var priceSum = 0;
            $.each(invoiceData, function (index, value) {
                priceSum += parseInt(value.price);
            });
            $('#price-sum').html('Total price summary: ' + priceSum);
        </script>

    </body>
</html>