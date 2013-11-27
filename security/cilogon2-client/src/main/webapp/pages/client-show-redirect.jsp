<%--
  User: Jeff Gaynor
  Date: 9/27/11
  Time: 4:37 PM
  Modified by Viknes Balasubramanee to automatically proceed with redirection
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<title>CILogon2 delegation request</title>
<link rel="stylesheet" type="text/css" media="all"
      href="static/cilogon.css"/>
<head><title></title></head>

<body onload="window.location.href='${redirectUrl}'">
<div id="topimgfill">
    <div id="topimg"></div>
</div>

<br clear="all"/>

<div class="main">

    <p>Success!<br><br>
        The redirect uri is<br><br> <font font="modern, arial, veranda"> <A href="${redirectUrl}">${redirectUrl}</A></font>
        <br><br>
        Click to go there!
     </p>


    <div class="footer">

        <p>
            For questions about this site, please see the
            <a target="_blank" href="http://www.cilogon.org/portal-delegation">Portal
                Delegation FAQ</a> or send email to <a
                href="mailto:help@cilogon.org">help&nbsp;@&nbsp;cilogon.org</a>.
        </p>

        <p>
            This material is based upon work supported by the <a target="_blank"
                                                                 href="http://www.nsf.gov/">National Science
            Foundation</a> under grant
            number <a target="_blank"
                      href="http://www.nsf.gov/awardsearch/showAward.do?AwardNumber=0943633">0943633</a>.
        </p>

        <p>
            Any opinions, findings, and conclusions or recommendations expressed in this
            material are those of the authors and do not necessarily reflect the views
            of the National Science Foundation.
        </p>
    </div>
</div>
</body>
</html>