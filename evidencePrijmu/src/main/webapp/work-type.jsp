<%-- 
    Document   : work-type
    Created on : May 7, 2016, 4:56:53 PM
    Author     : Matej Karolyi
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <%@include  file="components/header.html" %>
        <title>Work Type | Income Recording Application</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    </head>
    <body>
        <%@include  file="components/navigation.html" %>

        <div class="container">
            <div class="row body-section">
                <div class="col-md-12">
                    <h1>
                        Edit/Create a Work Type
                    </h1>
                </div>
            </div>
            <div class="body-section">
                <form action="work-type/create" method="post">
                    <div class="row">
                        <div class="col-md-6 col-md-offset-3">
                            <label class="input-label">
                                <h4>Work Type</h4>
                                <input type="text" name="work_type" class="form-control" required />
                            </label>
                        </div>
                        <div class="col-md-6 col-md-offset-3">
                            <label class="input-label">
                                <h4>Price</h4>
                                <input type="number" min="0" step="1" name="price" class="form-control" required />
                            </label>
                        </div>
                        <input type="hidden" name="work_id" value="" />
                    </div>
                    <div class="row margin-box">
                        <div class="col-md-4 col-md-offset-2">
                            <button type="submit" class="tile link-tile btn btn-block" ><i class='fa fa-floppy-o' aria-hidden='true'></i>&nbsp;&nbsp;&nbsp;SAVE</button>
                        </div>
                        <div class="col-md-4">
                            <a href="work-types" class="tile link-tile btn btn-block" ><i class="fa fa-arrow-left" aria-hidden="true"></i>&nbsp;&nbsp;&nbsp;BACK</a>
                        </div>

                    </div>
                </form>
            </div>
        </div>

        <%@include  file="components/footer.html" %>

        <script>
            //filling the work type form with data
            if (window.location.search !== "") {
                var parameters = parseGetParameters(window.location.search);

                $("input[name=work_type]").val(parameters.work_type);
                $("input[name=price]").val(parameters.price);
                $("input[name=work_id]").val(parameters.work_id);
            }
        </script>
    </body>
</html>
