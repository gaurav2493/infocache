<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.infocentercache.manager.Notice" %>
<files>
<c:forEach items="${filesList}" var="element">
   <file>
    <file-id>${element.getFileId()}</file-id>
    <date>${element.getFormatedDate()}</date>
    <subject>${element.getSubject()}</subject>
    <description>${element.getDescription()}</description>
    <postedby>${element.getAuthor()}</postedby>
  </file>
</c:forEach>
</files>