<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Category</title>
 
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles.css">
 
</head>
<body>
 
   <jsp:include page="_header.jsp" />
   <jsp:include page="_menu.jsp" />
 
   <div class="page-title"></div>
  
   <c:if test="${not empty errorMessage }">
     <div class="error-message">
         ${errorMessage}
     </div>
   </c:if>
 
   <form:form modelAttribute="categoryForm" method="POST" enctype="multipart/form-data">
       <table style="text-align:left;">
       
         <tr>
               <td>Name</td>
               <td style="color:red;">
                  <c:if test="${not empty categoryForm.name}">
                       <form:hidden path="name"/>
                       ${categoryForm.name}
                  </c:if>
                  <c:if test="${empty categoryForm.name}">
                       <form:input path="name" />
                       <form:hidden path="newCategory" />
                  </c:if>
               </td>
               <td><form:errors path="name" class="error-message" /></td>
           </tr>
         
 
           <tr>
               <td>Image</td>
               <td>
               <img src="${pageContext.request.contextPath}/categoryImage?name=${categoryForm.name}" width="100"/></td>
               <td> </td>
           </tr>
           <tr>
               <td>Upload Image</td>
               <td><form:input type="file" path="fileData"/></td>
               <td> </td>
           </tr>
 
           <tr>
               <td>&nbsp;</td>
               <td><input type="submit" value="Submit" /> 
               <input type="reset" value="Reset" /></td>
           </tr>
          
       </table>
   </form:form>
 
 
 
 
   <jsp:include page="_footer.jsp" />
 
</body>
</html>