<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:useBean id="now" class="java.util.Date" />
<fmt:formatDate var="year" value="${now}" pattern="yyyy" />
<c:set var="language" value="${pageContext.response.locale}"/>
<c:set var="lang" value="en"/>
<c:if test="${language=='zh_HK' || language=='zh_CN'}">
<c:set var="lang" value="tc"/>
</c:if>
<div class="footer">
<div class="footer-left">
<a href="javascript:void(0)" target="_blank"><spring:message code="footer.policy"/></a> | <a href="javascript:void(0)" target="_blank"><spring:message code="footer.terms"/></a>
</div>
<div class="footer-right">
<spring:message code="r2fas.copyright"/>&nbsp;${year}&nbsp;<spring:message code="r2fas.name"/>&nbsp;<spring:message code="r2fas.rights"/>
</div>
</div>