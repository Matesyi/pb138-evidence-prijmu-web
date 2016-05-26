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
                        <select name='employee' id='personal_number-select' class='form-control'>
                            <option value="" disabled selected>--- Filter by personal number ---</option>
                        </select>
                    </label>
                </div>
                <div class="col-md-6">
                    <label class="input-label">
                        <h4>Employee - surname</h4>
                        <select name='employee' id='surname-select' class='form-control'>
                            <option value="" disabled selected>--- Filter by surname ---</option>
                        </select>
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
        <div class="devider"></div>
        <div class="row">
            <div class="col-md-7">
                <h3 class="text-left" style="margin: 0;">Result:</h3>
            </div>
            <div class="col-md-4">
                <a class="tile link-tile btn btn-block" onclick="detailOfSelectedInvoice('#invoice-table')" title="detail of selected invoice"><i class="fa fa-search" aria-hidden="true"></i>&nbsp;&nbsp;&nbsp;Detail of selected invoice</a>
            </div>
        </div>
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
        <div class="devider"></div>
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

    //select box for employees
    $.each(employeesData, function (index, value) {
        $('#personal_number-select')
                .append($("<option></option>")
                        .attr("value", value.personal_number)
                        .text(value.personal_number + " - (" + value.name + " " + value.surname + ")"));
        $('#surname-select')
                .append($("<option></option>")
                        .attr("value", value.surname)
                        .text(value.surname));
    });

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

    function detailOfSelectedInvoice(target) {
        var row = $(target).simple_datagrid('getSelectedRow');
        if (typeof row !== 'undefined' && row !== null) {
            $.redirect('invoice-detail', {}, 'GET');
        }
    };
</script>

</body>
</html>
