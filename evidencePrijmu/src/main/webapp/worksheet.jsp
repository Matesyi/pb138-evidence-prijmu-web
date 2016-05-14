<%-- 
    Document   : worksheet
    Created on : May 7, 2016, 4:15:05 PM
    Author     : Matej Karolyi
--%>

<%@page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
    <head>
        <%@include  file="components/header.html" %>
        <title>Worksheet | Income Recording Application</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    </head>
    <body>
        <%@include  file="components/navigation.html" %>

        <div class="container">
            <div class="row body-section">
                <div class="col-md-12">
                    <h1>
                        Create a Worksheet
                    </h1>
                </div>
            </div>
            <div class="body-section">
                <form action="" method="post">
                    <div class="row">
                        <div class="col-md-6 col-md-offset-3">
                            <label class="input-label">
                                <h4>Employee</h4>
                                <select name='employee' id='employee-select' class='form-control'> 
                                    <option value="" disabled selected>--- Select the employee ---</option>
                                </select>
                            </label>
                        </div>
                        <div class="col-md-6 col-md-offset-3">
                            <label class="input-label">
                                <label class="input-label">
                                    <h4>Date</h4>
                                    <input name="startDate" id="startDate" class="date-picker form-control" />
                                </label>
                            </label>
                        </div>
                    </div>
                    <div class="row devider" ></div>
                    <div class="row">
                        <div class="col-md-10 col-md-offset-2"> 
                            <h4>Works to record:</h4>
                        </div>
                        <div id="work-holder" class="col-md-12">
                            <div id="work-1" class="row">
                                <div class="col-xs-6 col-md-4 col-md-offset-2">
                                    <select id='work-type-1' name='work-type-1' class='form-control'> 
                                        <option value="" disabled selected>--- Select the work type ---</option>
                                    </select>
                                </div>
                                <div class="col-xs-6 col-md-4">
                                    <input type="text" name="work-amount-1" class="form-control" placeholder="amount of working hours" />
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row margin-box">
                        <div class="col-md-4 col-md-offset-2">
                            <a class="tile link-tile btn btn-block" onclick="addWorkRow()" ><i class="fa fa-plus-square" aria-hidden="true"></i>&nbsp;&nbsp;&nbsp;Add next work row</a>
                        </div>
                        <div class="col-md-4">
                            <a class="tile link-tile btn btn-block" onclick="deleteWorkRow()" ><i class="fa fa-minus-square" aria-hidden="true"></i>&nbsp;&nbsp;&nbsp;Delete last work row</a>
                        </div>
                    </div>
                    <div class="row devider" ></div>
                    <div class="row margin-box">
                        <div class="col-md-4 col-md-offset-2">
                            <button type="submit" class="tile link-tile btn btn-block" ><i class='fa fa-floppy-o' aria-hidden='true'></i>&nbsp;&nbsp;&nbsp;SAVE</button>
                        </div>
                        <div class="col-md-4">
                            <a href="index.jsp" class="tile link-tile btn btn-block" ><i class="fa fa-arrow-left" aria-hidden="true"></i>&nbsp;&nbsp;&nbsp;BACK</a>
                        </div>
                    </div>
                </form>
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
            var numberOfWorkRows = 1;
            
            //fill first select box
            fillWorkTypeSlectBox(1);

            //select box for employees
            $.each(employeesData, function (index, value) {
                $('#employee-select')
                        .append($("<option></option>")
                                .attr("value", value.name + " " + value.surname + " (" + value.personal_number + ")")
                                .text(value.name + " " + value.surname + " (" + value.personal_number + ")"));
            });

            //function for filling the work select box
            function fillWorkTypeSlectBox(id) {
                //select box for employees
                $.each(workTypesData, function (index, value) {
                    $('#work-type-' + id)
                            .append($("<option></option>")
                                    .attr("value", value.work_type + " (price: " + value.price + ")")
                                    .text(value.work_type + " (price: " + value.price + ")"));
                });
            }

            //function for adding work row
            function addWorkRow() {
                numberOfWorkRows++;
                $("#work-holder")
                        .append($("<div id='work-" + numberOfWorkRows + "' class='row'>\n\
                                        <div class='col-xs-6 col-md-4 col-md-offset-2'>\n\
                                            <select id='work-type-" + numberOfWorkRows + "' name='work-type-" + numberOfWorkRows + "' class='form-control'>\n\
                                                <option value='' disabled selected>--- Select the work type ---</option>\n\
                                            </select>\n\
                                        </div>\n\
                                        <div class='col-xs-6 col-md-4'>\n\
                                            <input type='text' name='work-amount-" + numberOfWorkRows + "' class='form-control' placeholder='amount of working hours' />\n\
                                        </div>\n\
                                    </div>"));
                //fill added select box
                fillWorkTypeSlectBox(numberOfWorkRows);
            }

            //function for adding work row
            function deleteWorkRow() {
                if (numberOfWorkRows === 1) {
                    return;
                }
                $("#work-" + numberOfWorkRows).remove();
                numberOfWorkRows--;
            }
        </script>
    </body>
</html>
