<%-- 
    Document   : employee
    Created on : May 7, 2016, 4:36:17 PM
    Author     : Matej Karolyi
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <%@include  file="components/header.html" %>
        <title>Employee | Income Recording Application</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    </head>
    <body>
        <%@include  file="components/navigation.html" %>

        <div class="container">
            <div class="row body-section">
                <div class="col-md-12">
                    <h1>
                        Edit/Create an Employee
                    </h1>
                </div>
            </div>
            <div class="body-section">
                <form action="employee/create" method="post">
                    <div class="row">
                        <div class="col-md-6 col-md-offset-3">
                            <label class="input-label">
                                <h4>Personal number</h4>
                                <input type="text" name="personal_number" class="form-control" readonly />
                            </label>
                        </div>
                        <div class="col-md-6 col-md-offset-3">
                            <label class="input-label">
                                <h4>Name</h4>
                                <input type="text" name="name" class="form-control" required />
                            </label>
                        </div>
                        <div class="col-md-6 col-md-offset-3">
                            <label class="input-label">
                                <h4>Surname</h4>
                                <input type="text" name="surname" class="form-control" required />
                            </label>
                        </div>
                        <div class="col-md-6 col-md-offset-3">
                            <label class="input-label">
                                <h4>Address (street name & number)</h4>
                                <input type="text" name="address" class="form-control" required />
                            </label>
                        </div>
                        <div class="col-md-3 col-md-offset-3">
                            <label class="input-label">
                                <h4>City</h4>
                                <input type="text" name="city" class="form-control" required />
                            </label>
                        </div>
                        <div class="col-md-3">
                            <label class="input-label">
                                <h4>Post Code</h4>
                                <input type="text" name="post_code" class="form-control" required />
                            </label>
                        </div>
                    </div>
                    <div class="row margin-box">
                        <div class="col-md-4 col-md-offset-2">
                            <button type="submit" class="tile link-tile btn btn-block" ><i class='fa fa-floppy-o' aria-hidden='true'></i>&nbsp;&nbsp;&nbsp;SAVE</button>
                        </div>
                        <div class="col-md-4">
                            <a href="employees" class="tile link-tile btn btn-block" ><i class="fa fa-arrow-left" aria-hidden="true"></i>&nbsp;&nbsp;&nbsp;BACK</a>
                        </div>
                    </div>
                </form>
            </div>
        </div>

        <%@include  file="components/footer.html" %>

        <script>
            //filling the employee form with data from URL
            if (window.location.search !== "") {
                var parameters = parseGetParameters(window.location.search);

                $("input[name=personal_number]").val(parameters.personal_number);
                $("input[name=name]").val(parameters.name);
                $("input[name=surname]").val(parameters.surname);
                $("input[name=address]").val(parameters.address);
                $("input[name=city]").val(parameters.city);
                $("input[name=post_code]").val(parameters.post_code);
            }

            if ($("input[name=personal_number]").val() === "") {
                $("input[name=personal_number]").attr("placeholder", "Will be added automatically");
            }
        </script>
    </body>
</html>
