<%-- 
    Document   : invoice detail
    Created on : May 21, 2016, 1:50:05 PM
    Author     : Matej Karolyi
--%>

<%@page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
    <head>
        <%@include file="components/header.html" %>
        <title>Invoice detail | Income Recording Application</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    </head>
    <body>
        <%@include file="components/navigation.html" %>

        <div class="container">
            <div class="row body-section">
                <div class="col-md-12">
                    <h1 id="invoice_id"></h1>
                    <h3 id="invoice_date"></h3>
                </div>
            </div>
            <div class="row body-section">
                <div class="col-md-6">
                    <h3>
                        Employee:
                    </h3>
                    <table class="invoice-table">
                        <col width="30%">
                        <col width="70%">
                        <tbody>
                            <tr>
                                <td>Personal number</td>
                                <td id="employee-personal_number"></td>
                            </tr>
                            <tr>
                                <td>Name</td>
                                <td id="employee-name"></td>
                            </tr>
                            <tr>
                                <td>Surname</td>
                                <td id="employee-surname"></td>
                            </tr>
                            <tr>
                                <td>Address</td>
                                <td id="employee-address"></td>
                            </tr>
                            <tr>
                                <td>City</td>
                                <td id="employee-city"></td>
                            </tr>
                            <tr>
                                <td>Post code</td>
                                <td id="employee-post_code"></td>
                            </tr>
                        </tbody>
                    </table>
                </div>
                <div class="col-md-6">
                    <h3>
                        Company:
                    </h3>
                    <table class="invoice-table">
                        <col width="30%">
                        <col width="70%">
                        <tbody>
                            <tr>
                                <td>Name</td>
                                <td id="employer-name">Testing company</td>
                            </tr>
                            <tr>
                                <td>ID</td>
                                <td id="employer-id">12431456244</td>
                            </tr>
                            <tr>
                                <td>Address</td>
                                <td id="employer-address">Testing street 42</td>
                            </tr>
                            <tr>
                                <td>City</td>
                                <td id="employer-city">Brno</td>
                            </tr>
                            <tr>
                                <td>Post code</td>
                                <td id="employer-post_code">60200</td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>
            <div class="row body-section">
                <div class="col-md-6">
                    <h3>
                        Works on record:
                    </h3>
                    <table class="invoice-table">
                        <col width="40%">
                        <col width="20%">
                        <col width="20%">
                        <col width="20%">
                        <thead>
                            <tr>
                                <th>Work type</th>
                                <th>Hour cost</th>
                                <th>Hours</th>
                                <th>SubTotal</th>
                            </tr>
                        </thead>
                        <tbody id="works-table">
                        </tbody>
                    </table>
                </div>
                <div class="col-md-6">
                    <h3>
                        Total balance:
                    </h3>
                    <table class="invoice-table">
                        <col width="30%">
                        <col width="70%">
                        <tbody>
                            <tr>
                                <td>SubTotal</td>
                                <td id="sub_total"></td>
                            </tr>
                            <tr>
                                <td>Tax rate (21%)</td>
                                <td id="tax_rate"></td>
                            </tr>
                            <tr>
                                <td><strong>Total balance</strong></td>
                                <td id="total_balance"></td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>
            <div class="row body-section">
                <div class="col-md-4 col-md-offset-2">
                    <form id="generate-form-docbook" method="post" action="/transformation/docbook/${invoiceId}">
                        <input type="text" name="invoice_id" hidden/>
                        <button type="submit" class="tile link-tile btn btn-block"><i class='fa fa-external-link'
                                                                                      aria-hidden='true'></i>&nbsp;&nbsp;&nbsp;Generate
                            to DocBook
                        </button>
                    </form>
                    <form id="generate-form-pdf" method="post" action="/transformation/pdf/${invoiceId}">
                        <input type="text" name="invoice_id" hidden/>
                        <button type="submit" class="tile link-tile btn btn-block"><i class='fa fa-external-link'
                                                                                      aria-hidden='true'></i>&nbsp;&nbsp;&nbsp;Generate
                            to PDF
                        </button>
                    </form>
                </div>
                <div class="col-md-4">
                    <a onclick="window.history.back()" class="tile link-tile btn btn-block"><i class="fa fa-arrow-left"
                                                                                               aria-hidden="true"></i>&nbsp;&nbsp;&nbsp;BACK</a>
                </div>
            </div>
        </div>

        <%@include file="components/footer.html" %>
        <script>
            /**
             * data for invoice detail output
             * @type JSON
             */
            var invoiceData = ${invoiceJson};

            /**
             * function for filling the form of invoice
             * @returns {void}
             */
            function fillInvoiceData() {
                $("#invoice_id").html("Invoice #" + invoiceData[1].invoice_id + " - detail");
                $("#invoice_date").html("from " + invoiceData[1].date);

                $("#employee-address").html(invoiceData[0].address);
                $("#employee-city").html(invoiceData[0].city);
                $("#employee-surname").html(invoiceData[0].surname);
                $("#employee-post_code").html(invoiceData[0].post_code);
                $("#employee-name").html(invoiceData[0].name);
                $("#employee-personal_number").html(invoiceData[0].personal_number);

                $("#sub_total").html(invoiceData[1].price + ",-");
                $("#tax_rate").html(invoiceData[1].price * 0.21 + ",-");
                $("#total_balance").html(invoiceData[1].price * 1.21 + ",-");

                $.each(invoiceData[2].works, function (index, value) {
                    $("#works-table").append("<tr><td>"
                            + value.work_type + "</td><td>" +
                            +value.work_price + "</td><td>" +
                            +value.work_amount + "</td><td>" +
                            +(value.work_price * value.work_amount) +
                            "</td></tr>");
                });
                
                $("#generate-form").attr("action", "/transformation/docbook/" + invoiceData[1].invoice_id);
            }
            
            //call when it is ready
            $( document ).ready(function() {
                fillInvoiceData();
            });
        </script>
    </body>
</html>
