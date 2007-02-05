<%@ page contentType="text/xml; charset=UTF-8" %>
<%@ page import="net.zemberek.www.WebDemoYonetici" %>


<taconite-root>
    <taconite-replace-children contextNodeID="div" parseInBrowser="true">
        <div>
            <%
                String giris = (String) request.getParameter("giris");
                String islem = (String) request.getParameter("islem");
                out.write(WebDemoYonetici.getRef().islemUygula(islem,giris));
            %>
        </div>
    </taconite-replace-children>
</taconite-root>