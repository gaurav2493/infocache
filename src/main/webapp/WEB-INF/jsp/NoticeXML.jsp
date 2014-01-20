<%@ page import="com.infocentercache.manager.Notice" %>
<notice>
  <notice-id>${notice.getNoticeId()}</notice-id>
    <date>${notice.getFormatedDate()}</date>
    <subject>${notice.getSubject()}</subject>
    <postedby>${notice.getAuthor()}</postedby>
    <content>${notice.getContent()}</content>
</notice>