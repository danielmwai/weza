<%@ include file="/WEB-INF/jsp/includes/includes.jsp"%>
<div class="fieldShadow" align="center">
<fieldset>
<legend>Create Company Instance</legend>
<form:form method="post" modelAttribute="createCloudDbaseBean">
    <div id="addcloud_errors" class="error"></div>
    <table id="table-form-input">
        <colgroup>
            <col class="oce-first" />
        </colgroup>
        <tbody>         
            <tr>
                <td colspan="2" align="center"><font color='red'>
                        <div id="register_errors" class="error"></div> </font>
                </td>
            </tr>
            <tr>
                <td  width="20%" valign="top">Company Name:</td>
                <td  width="79%" valign="top">
                <span style="color:red;">*</span>
                <form:input path="name" cssClass="forminput" />
                </td>
            </tr>
             <tr>
                <td width="20%" valign="top">Database Name:</td>
                <td width="79%" valign="top">
                    <span style="color:red;">*</span>
                    <form:input path="dbaseName" cssClass="forminput" />
                </td>
             </tr>
              <tr>
                <td width="20%" valign="top">Database Host:</td>
                <td width="79%" valign="top">
                    <span style="color:red;">*</span>
                    <form:input path="dbaseHost" cssClass="forminput" />
                </td>
             </tr>
             <tr>
                <td width="20%" valign="top">Database Username:</td>
                <td width="79%" valign="top">
                    <span style="color:red;">*</span>
                    <form:input path="dbUserName" cssClass="forminput" />
                </td>
             </tr>
             <tr>
                <td width="20%" valign="top">Database Password:</td>
                <td width="79%" valign="top">
                    <span style="color:red;">*</span>
                    <form:input path="dbPassword" cssClass="forminput" />
                </td>
             </tr>
            <tr>
                <td width="20%" valign="top">Email Address:</td>
                 <td width="79%" valign="top">
                    <span style="color:red;">*</span>
                    <form:input path="email" cssClass="forminput" />
                </td>
            </tr>
            <tr>
                <td width="20%" valign="top">Company Website: </td>
                <td width="79%" valign="top">
                <span style="color:red;">*</span>
                <form:input path="website" cssClass="forminput "/>
                </td>
            </tr>
            <tr>
                <td width="20%" valign="top">Company Location:  </td>
                <td width="79%" valign="top">
                <span style="color:red;">*</span>
                <form:input path="location" cssClass="forminput" />
            </tr>
            <tr>
                <td width="20%" valign="top">Tel Line 1:</td>
                <td width="79%" valign="top">
                <span style="color:red;">*</span>
                <form:input path="firstline" cssClass="forminput" />
            </tr>
            <tr>
                <td width="20%" valign="top">Tel Line 2:</td>
                <td width="79%" valign="top">
                <span style="color:red;">*</span>
               <form:input path="secondline" cssClass="forminput" />
               </td>
            </tr>
            <tr>
                <td width="20%" valign="top">Address: </td>
                <td width="79%" valign="top">
                <span style="color:red;">*</span>
                <form:input path="address" cssClass="forminput" />
                </td>
            </tr>
             <tr>
                <td width="20%" valign="top">Company Description: </td>
                <td width="79%" valign="top">
                <form:textarea id="description" path="description" />
                </td>
            </tr>
             <tr>
                <td width="20%">
                &nbsp;
                </td>
                <td width="70%" align="left">
                <input type="submit" value="Add Cloud Instance"/>&nbsp; &nbsp;
               <input type ="button" value="Cancel"   onClick="JJTEACH.ajaxLoad('/cloud/listinstances'); return false;">
                </td>
            </tr>
        </tbody>
    </table>
</form:form>
</fieldset>
</div>
<script type="text/javascript">
    $j().ready(
            function() 
            {
                $j("#createCloudDbaseBean").submit(
                        function() 
                        {
                            var account = $j(this).serializeObject();
                            $j('#adduser_errors').html(
                                    "Processing..........");
                            $j.postJSON($j('#createCloudDbaseBean').attr('action'),
                                        account, 
                                        function(data) 
                                        {
                                            doaction(data);
                                        });
                            return false;
                        });
            });

    function doaction(data) 
    {
        var message = data.u;
        $j('#addcloud_errors').html(message);
        if (message == "Saved") 
        {
            JJTEACH.ajaxLoad('/user/listusers');
        }
    }    
</script>
<script type="text/javascript">
    $j(function() {
        /* $j("#email")
        .validate(
                {
                    expression : "if (VAL.match(/^[^\\W][a-zA-Z0-9\\_\\-\\.]+([a-zA-Z0-9\\_\\-\\.]+)*\\@[a-zA-Z0-9_]+(\\.[a-zA-Z0-9_]+)*\\.[a-zA-Z]{2,4}$/)) return true; else return false;",
                    message : "Please enter a valid Email ID",
                    error_class : "errors",
                    error_field_class : "errors"
                }); */
                $j("#website").validate({
                    expression : "if (VAL) return true; else return false;",
                    message : "Please enter a valid url",
                    error_class : "errors",
                    error_field_class : "errors"
                });
                $j("#location").validate({
                    expression : "if (VAL) return true; else return false;",
                    message : "Please enter the Required field",
                    error_class : "errors",
                    error_field_class : "errors"
                });
                $j("#firstline").validate({
                    expression : "if (VAL) return true; else return false;",
                    message : "Please enter the Required field",
                    error_class : "errors",
                    error_field_class : "errors"
                });
                $j("#secondline").validate({
                    expression : "if (VAL) return true; else return false;",
                    message : "Please enter the Required field",
                    error_class : "errors",
                    error_field_class : "errors"
                });
                $j("#address").validate({
                    expression : "if (VAL) return true; else return false;",
                    message : "Please enter the Required field",
                    error_class : "errors",
                    error_field_class : "errors"
                });
        $j("#dbaseName")
                .validate(
                        {
                            expression : "if (VAL) return true; else return false;",
                            message : "Please enter the Database Name",
                            error_class : "errors",
                            error_field_class : "errors"
                        });
        $j("#dbUserName")
        .validate(
                {
                    expression : "if (VAL) return true; else return false;",
                    message : "Please enter the Required field",
                    error_class : "errors",
                    error_field_class : "errors"
                });
        $j("#dbPassword")
        .validate(
                {
                    expression : "if (VAL) return true; else return false;",
                    message : "Please enter the Database Username",
                    error_class : "errors",
                    error_field_class : "errors"
                });
</script>