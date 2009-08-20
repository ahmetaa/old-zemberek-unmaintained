<%--
~  ***** BEGIN LICENSE BLOCK *****
~
~  Version: MPL 1.1
~
~  The contents of this file are subject to the Mozilla Public License Version
~  1.1 (the "License"); you may not use this file except in compliance with
~  the License. You may obtain a copy of the License at
~  http://www.mozilla.org/MPL/
~
~  Software distributed under the License is distributed on an "AS IS" basis,
~  WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
~  for the specific language governing rights and limitations under the
~  License.
~
~  The Original Code is "Zemberek Web"
~
~  The Initial Developer of the Original Code is
~  Ahmet A. Akin, Mehmet D. Akin.
~  Portions created by the Initial Developer are Copyright (C) 2006
~  the Initial Developer. All Rights Reserved.
~
~  Contributor(s):
~
~  ***** END LICENSE BLOCK *****
--%>

<%@ page contentType="text/html" %>
<%@ page pageEncoding="UTF-8" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Zemberek Demo</title>

    <style type="text/css">
        body {
            padding: 20px;
            background-color: #FFF;
            font: 76% Verdana, Arial, sans-serif
        }

        div#menu {
            float: left;
            width: 800px;
            padding: 20px 0 20px 15px; /*background: url( navbk.jpg )*/
        }

        ul#nav, ul#nav li {
            list-style-type: none;
            margin: 0;
            padding: 0
        }

        ul#nav li {
            float: left;
            width: 7em;
            margin-right: 5px;
            text-align: center;
            cursor:pointer;
        }

        ul#nav a {
            display: block;
            text-decoration: none;
            padding: 2px 0;
            background: #9ABE99;
            color: #fff;
            font-weight: bold
        }

        ul#nav a:hover {
            color: #3F8A14
        }

        ul#nav li.activelink a, ul#nav li.activelink a:hover {
            color: #000
        }
    </style>

    <link rel="stylesheet" type="text/css" href="css/niftyCorners.css">
    <link rel="stylesheet" type="text/css" href="css/niftyPrint.css" media="print">

    <script type="text/javascript" src="js/nifty.js"></script>
    <script type="text/javascript" src="js/taconite-client.js"></script>
    <script type="text/javascript" src="js/taconite-parser.js"></script>

    <script>
// yuvarlatilmis kenarli dugmeler icin..
        window.onload = function() {
            if (!NiftyCheck())
                return;
            Rounded("div#nav li", "tr bl", "transparent", "#9ABE99", "border #3562B1");
        }

//Ajaxla temizledim mis gibi oldu...
        function doRequest(url, islem) {
            var ajaxRequest = new AjaxRequest(url);
            var hiddenField = document.getElementById("islem");
            hiddenField.value = islem;
            ajaxRequest.addNamedFormElements("giris", "islem");
            ajaxRequest.sendRequest();
        }
    </script>


</head>

<body>
<big>Zemberek Demo</big>
<small>(<a href="http://code.google.com/p/zemberek">Zemberek Proje Sitesi</a>)</small>
<div id="menu">
    <ul id="nav">
        <li id="denetle"><a onclick="doRequest('zemberek.jsp', 'YAZI_DENETLE');">Denetle</a></li>
        <li id="coz"><a onclick="doRequest('zemberek.jsp','YAZI_COZUMLE');">Cozumle</a></li>
        <li id="oner"><a onclick="doRequest('zemberek.jsp','ONER');">Oner</a></li>
        <li id="ascii2tr"><a onclick="doRequest('zemberek.jsp','ASCII_TURKCE');">Ascii->Tr</a></li>
        <li id="tr2ascii"><a onclick="doRequest('zemberek.jsp','TURKCE_ASCII');">Tr->ascii</a></li>
        <li id="hecele"><a onclick="doRequest('zemberek.jsp','HECELE');">Hecele</a></li>
        <li id="ayristir"><a onclick="doRequest('zemberek.jsp','SACMALA');">Sacmala</a></li>
    </ul>
</div>


<br>
<br>
<br>
<br>
<br>

<form id="form" action="#">
    <P align=center>
        <b>Islem yapilacak yaziyi asagidaki alana giriniz.</b><br>
        <textarea name="giris" rows="10" cols="60"></textarea>
        <input type="hidden" name="islem" id="islem"/>
    </P>
</form>

<br>

<div id="div">
</div>

</body>
</html>
