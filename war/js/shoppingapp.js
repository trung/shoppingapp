function $(id) {
	return  document.getElementById(id);
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
	var queryString = toQueryString(params);
	debug("url=" + url  + ", params = " + queryString);
	transport.send(queryString);
}

function setupListener(transport, onSuccess, onFailure, onComplete) {
	transport.onreadystatechange = function() {
		if (transport.status == 200) {
			var jsonObject = eval('(' + transport.responseText + ')');
			onSuccess(jsonObject);
		} else if (transport.readystate == 2) {
			onComplete();
		} else {
			onFailure(transport.readyState, transport.status);
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

function toQueryString(obj) {
    var bits = [];
    for (var i in obj) {
        if (obj.hasOwnProperty(i)) {
            bits.push(encodeURIComponent(i) + "=" + encodeURIComponent(obj[i]));
        }
    }
    return bits.join("&");
}

function info(msg) {
	if (console) {
		if (console.info) {
			console.info(msg);
		} else {
			console.log(msg);
		}
	}
}

function debug(msg) {
	if (console) {
		if (console.debug) {
			console.debug(msg);
		} else {
			console.log(msg);
		}
	}
}

function warn(msg) {
	if (console) {
		if (console.warn) {
			console.warn(msg);
		} else {
			console.log(msg);
		}
	}
}

function checkFileExtension(elem) {
    var filePath = elem.value;


    if(filePath.indexOf('.') == -1)

        return false;


    var validExtensions = new Array();
    var ext = filePath.substring(filePath.lastIndexOf('.') + 1).toLowerCase();



    validExtensions[0] = 'png';


    for(var i = 0; i < validExtensions.length; i++) {

        if(ext == validExtensions[i])
            return true;
    }


    alert('The file extension ' + ext.toUpperCase() +
' is not allowed!');

    return false;
}