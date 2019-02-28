<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Category List</title>
 
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles.css">
 
</head>
<body>
 
   <jsp:include page="_header.jsp" />
   <jsp:include page="_menu.jsp" />
  
   <fmt:setLocale value="en_US" scope="session"/>
 
   <div class="page-title"></div>
 
 
 <c:forEach  items="${categoryList}" var="list">
       <div class="product-preview-container">
           <ul>
               <li><img class="product-image"
                   src="${pageContext.request.contextPath}/categoryImage?name=${list.name}" /></li>
               <li><a style="color:#ffffff;"
                     href="${pageContext.request.contextPath}/productList?name=${list.name}">
                       ${list.name}</a></li>
          
               <!-- For Manager edit Product -->
               <security:authorize  access="hasRole('ROLE_MANAGER')">
                 <li><a style="color:#ffffff;"
                     href="${pageContext.request.contextPath}/category?name=${list.name}">
                       Edit Category</a></li>
                      
               </security:authorize>
             
             
               <security:authorize  access="hasRole('ROLE_MANAGER')">
                 <li><a style="color:#ffffff;"
                     href="${pageContext.request.contextPath}/product?category=${list.name}" >
                       Create Product</a></li>
               </security:authorize>
     
           </ul>
       </div>
       </c:forEach>
 

   <br/>
  
 
   <jsp:include page="_footer.jsp" />
 
</body>
</html>