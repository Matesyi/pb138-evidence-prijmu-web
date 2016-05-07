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
                <form action="" method="post">
                    <div class="row">
                        <div class="col-md-6 col-md-offset-3">
                            <label class="input-label">
                                <h4>Work Type</h4>
                                <input type="text" name="type" class="form-control" />
                            </label>
                        </div>
                        <div class="col-md-6 col-md-offset-3">
                            <label class="input-label">
                                <h4>Price</h4>
                                <input type="text" name="price" class="form-control" />
                            </label>
                        </div>
                        <div class="col-md-4 col-md-offset-4 margin-box">
                            <input type="submit" class="tile link-tile btn btn-block" value="SAVE" />
                        </div>
                    </div>
                </form>
            </div>
        </div>

        <%@include  file="components/footer.html" %>
    </body>
</html>
