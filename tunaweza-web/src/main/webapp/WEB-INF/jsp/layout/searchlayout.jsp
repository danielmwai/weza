<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/WEB-INF/jsp/includes/includes.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head></head>
<body>
    <div id="srch_box">
        <tiles:insertAttribute name="search_box"/>
    </div>
    <div id="srch_rslts">
        <tiles:insertAttribute name="search_results"/>
    </div>
</body>
</html>