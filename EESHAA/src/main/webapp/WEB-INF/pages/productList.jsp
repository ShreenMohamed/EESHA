<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
 
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Product List</title>
 
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles.css">
 
</head>
<body>
 
   <jsp:include page="_header.jsp" />
   <jsp:include page="_menu.jsp" />
  
   <fmt:setLocale value="en_US" scope="session"/>
 
   <div class="page-title"></div>
 
 
 <c:forEach  items="${productList}" var="list">
       <div class="product-preview-container">
           <ul>
               <li><img class="product-image"
                   src="${pageContext.request.contextPath}/productImage?code=${list.code}" /></li>
               <li>Code: ${list.code}</li>
               <li>Name: ${list.name}</li>               
               <li>Price: <fmt:formatNumber value="${list.price}" type="currency"/></li>
               <li><a style="color:#ffffff;"
                   href="${pageContext.request.contextPath}/buyProduct?code=${list.code}">
                       Buy Now</a></li>
               <!-- For Manager edit Product -->
               <security:authorize  access="hasRole('ROLE_MANAGER')">
                 <li><a style="color:#ffffff;"
                     href="${pageContext.request.contextPath}/product?code=${list.code}">
                       Edit Product</a></li>
               </security:authorize>
           </ul>
           
       </div>
       
        
       </c:forEach>
       
  
   
     
      


   <br/>
  
 
   <jsp:include page="_footer.jsp" />
 
</body>
</html>