function RemoteRequest() {
    this.xhr = createXMLHttpRequest();
    this.queryString = "";
    this.requestURL = "";
    this.addNamedFormElements = _addNamedFormElements;
    this.addFormElements = _addFormElements;
    this.accumulateQueryString = _accumulateQueryString;
    this.sendRequest = _sendRequest;
    this.setUsePOST = _setUsePOST;
    this.setUseGET = _setUseGET;
    
    /** Default method to GET for Opera */
    this.method = RemoteRequest.GET;

    /* Pre and Post request function hooks */
    this.preRequest = "";
    this.postRequest = "";

    this.responseHandler = _handleStateChange;
}

RemoteRequest.GET = "GET";
RemoteRequest.POST = "POST";

function _setUsePOST() {
	this.method = RemoteRequest.POST;
}

function _setUseGET() {
	this.method = RemoteRequest.GET;
}

function _addFormElements(formID) {
	var formElements = document.getElementById(formID).elements;
	var values = toQueryString(formElements);
	this.accumulateQueryString(values);
}

function _accumulateQueryString(newValues) {
	if(this.queryString == "") {
		this.queryString = newValues; 
	}
	else {
		this.queryString = this.queryString + "&" +  newValues;
	}
}

function _addNamedFormElements() {
    var elementName = "";
    var namedElements = null;
    
    for(var i = 0; i < arguments.length; i++) {
        elementName = arguments[i];
        namedElements = document.getElementsByName(elementName);
        
		elementValues = toQueryString(namedElements);

		this.accumulateQueryString(elementValues);
    }
}


function _sendRequest() {
    eval(this.preRequest);
    var obj = this;

    this.xhr.onreadystatechange = function () { RemoteRequest.doHandle(obj); };
    
    this.requestURL = this.requestURL + "?ts=" + new Date().getTime();

    if(this.method == RemoteRequest.GET) {
        this.requestURL = this.requestURL + "&" + this.queryString;
        this.xhr.open(this.method, this.requestURL, true);
    	this.xhr.send(null);
    }
    else {
        this.xhr.open(this.method, this.requestURL, true);
        this.xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded"); 
    	this.xhr.send(this.queryString);
    }
}

RemoteRequest.doHandle = function(obj) {
    if(obj.xhr.readyState != 4) {
        return;
    }
    if(obj.xhr.status == 200) {
        eval(obj.xhr.responseText);
        eval(obj.postRequest);
    }

}
function createXMLHttpRequest() {
    if (window.ActiveXObject) {
        return new ActiveXObject("Microsoft.XMLHTTP");
    }
    else if (window.XMLHttpRequest) {
        return new XMLHttpRequest();
    }
}

function _handleStateChange() {
    if(_xhr.readyState != 4) {
        return;
    }
    if(_xhr.status == 200) {
        eval(_xhr.responseText);
//        eval(this.postRequest);
    }
}

function toQueryString(elements) {
    var node = null;
    var qs = "";
    var name = "";
    
    var tempString = "";
    for(var i = 0; i < elements.length; i++) {
        tempString = "";
        node = elements[i];
		name = node.getAttribute("name");
	
        if(node.tagName.toLowerCase() == "input") {
            if(node.type.toLowerCase() == "radio" || node.type.toLowerCase() == "checkbox") {
                if(node.checked) {
                    tempString = name + "=" + node.value;
                }
            }

            if(node.type.toLowerCase() == "text" || node.type.toLowerCase() == "hidden") {
                tempString = name + "=" + encodeURIComponent(node.value);
            }
        }
        else if(node.tagName.toLowerCase() == "select") {
            tempString = getSelectedOptions(node);
        }

        else if(node.tagName.toLowerCase() == "textarea") {
            tempString = name + "=" + encodeURIComponent(node.value);
        }

        if(tempString != "") {
            if(qs == "") {
                qs = tempString;
            }
            else {
                qs = qs + "&" + tempString;
            }
        }

    }

    return qs;

}

function getSelectedOptions(select) {
    var options = select.options;
    var option = null;
    var qs = "";
    var tempString = "";

    alert("number of options: " + options.length);
    for(var x = 0; x < options.length; x++) {
        tempString = "";
        option = options[x];

        if(option.selected) {
            tempString = select.name + "=" + option.value;
        }

        if(tempString != "") {
            if(qs == "") {
                qs = tempString;
            }
            else {
                qs = qs + "&" + tempString;
            }
        }
    }

    return qs;
}
