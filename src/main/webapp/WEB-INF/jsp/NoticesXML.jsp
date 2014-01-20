<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.infocentercache.manager.Notice" %>
<notices>
<c:forEach items="${noticesList}" var="element">
   <notice>
    <notice-id>${element.getNoticeId()}</notice-id>
    <date>${element.getFormatedDate()}</date>
    <subject>${element.getSubject()}</subject>
    <postedby>${element.getAuthor()}</postedby>
  </notice>
</c:forEach>
</notices>