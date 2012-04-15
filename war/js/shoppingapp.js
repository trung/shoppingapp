function $(id) {
	return document.getElementById(id);
}

function ajaxGet(url, onSuccess, onFailure, onComplete) {
	var transport = getTransport();
	transport.open("GET", url, true);
	setupListener(transport, onSuccess, onFailure, onComplete);
	transport.send();

}

function ajaxPost(url, params, onSuccess, onFailure, onComplete) {
	var transport = getTransport();
	transport.open("POST", url, true);
	transport.setRequestHeader("Content-type","application/x-www-form-urlencoded");
	setupListener(transport, onSuccess, onFailure, onComplete);
	transport.send(params);
}

function setupListener(transport, onSuccess, onFailure, onComplete) {
	transport.onreadystatechange = function() {
		if (transport.readystate == 4 && transport.status == 200) {
			var jsonObject = eval('(' + transport.responseText + ')');
			onSuccess(jsonObject);
		} else if (transport.readystate == 2) {
			onComplete();
		} else {
			onFailure(transport.readysate, transport.status);
		}
	};
}

function getTransport() {
	return createStandardXHR() || createActiveXHR();
}

function createStandardXHR() {
    try {
        return new window.XMLHttpRequest();
    } catch (e) {}
}

function createActiveXHR() {
    try {
        return new window.ActiveXObject("Microsoft.XMLHTTP");
    } catch (e) {}
}