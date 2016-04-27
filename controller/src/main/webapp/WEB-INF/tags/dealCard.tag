<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute name="deals" required="true" type="java.util.List" %>
<td>
    <c:forEach items="${deals}" var="deal">
        <div class="deal-card">
            <h5 class="text-center">${deal.name}</h5>
            <br>${deal.budget} $

            <c:if test="${not empty deal.contacts}">
                <br>Contacts:
                <c:forEach items="${deal.contacts}" var="contact">
                    <br>${contact.name}
                </c:forEach>
            </c:if>

            <br>Company:
            <br>${deal.company.name}
        </div>
    </c:forEach>
</td>

