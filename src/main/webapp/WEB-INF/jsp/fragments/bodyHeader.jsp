<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<div class="navbar navbar-inverse navbar-fixed-top" role="navigation">
    <div class="container">
        <a href="meals" class="navbar-brand"><spring:message code="app.title"/></a>

        <div class="collapse navbar-collapse">
            <ul class="nav navbar-nav navbar-right">
                <li>
            <form:form class="navbar-form navbar-right" action="logout" method="post">
                <sec:authorize access="isAuthenticated()">
                    <sec:authorize access="hasRole('ROLE_ADMIN')">
                        <a class="btn btn-info" href="users"><spring:message code="users.title"/></a>
                    </sec:authorize>
                    <a class="btn btn-info" role="button" href="profile">${userTo.name} <spring:message code="app.profile"/></a>
                    <button class="btn btn-primary" type="submit">
                        <span class="glyphicon glyphicon-log-out" aria-hidden="true"></span>
                    </button>
                </sec:authorize>
            </form:form>
                </li>
            <li class="dropdown">
                <a href="#" class="dropdown-toggle" data-toggle="dropdown" aria-expanded="false">${pageContext.response.locale}<b class="caret"></b></a>
                <ul class="dropdown-menu">
                    <li><a onclick="show('en')">English</a></li>
                    <li><a onclick="show('ru')">Русский</a></li>
                </ul>
            </li>
            <script type="text/javascript">
                var localeCode="ru";
                function show(lang) {
                    window.location.href = window.location.href.split('?')[0] + '?lang=' + lang;
                }
            </script></ul>
        </div>
    </div>
</div>