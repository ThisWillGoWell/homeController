$(function() {

	var degreeSymbol = "°";
	var tempVal = 76;
	var tempView = $('#current-temp-view');
	var tempInc = $('.temp-inc');
	var tempDec = $('.temp-dec');
	var acButton = $('.ac-toggle');
	var heatButton = $('.heat-toggle');
    var currentMode = "off";

	var localSystemTemp = 0;
	var serverState = {};
	var mode = "off";
	var changed = false;
    var serverSyncTime = 2000;
	var SERVER_URL = "http://192.168.1.153:8080"
	var SYSTEM_NAME = "HVAC"
	var SERVER_ENDPOINTS = {
		"getState" : "/get?system=" + SYSTEM_NAME + "&what=state",
		"setSystemTemp" : "/set?system="+ SYSTEM_NAME + "&what=systemTemp&to=",
		"setMode" : "/set?system="+ SYSTEM_NAME + "&what=mode&to=",

	}
	
	acButton.click(function() {
		acButton.toggleClass('ac-on');
	});

	heatButton.click(function() {
		heatButton.toggleClass('heat-on');
	});
	
	tempInc.click(function() {
		setNewTemp(localSystemTemp + 1);
	});
	
	tempDec.click(function() {
		setNewTemp(localSystemTemp - 1);
	});
	
	
	var nextSendTime;
	function setNewTemp(val) {
		localSystemTemp = val;
		tempView.text(localSystemTemp+degreeSymbol);
		nextSendTime = (new Date).getTime()  + 1000;
		changed = true;
	}

	//sends the system temp to the server
	//
	function setServerTemp(){
		put(SERVER_URL + SERVER_ENDPOINTS["setSystemTemp"] + localSystemTemp, success)
	}

	function setServerState(){
	    put(SERVER_URL + SERVER_ENDPOINTS["setMode"])
	}

	function


    function success(responseText)
    {

    }

	//Will wait 1 second of inactivity until the
	//server temp is sent to sever, 
	//prevent rapid changes ans unesscarry calls
	function timeout(){
		if((changed == true) && (new Date).getTime() > nextSendTime)
		{
			setServerTemp();
			changed = false;
		}

	}
	//check evry half second if we need to send 
	//stuff to the
	setInterval(timeout, 500) 

	//Update the client with the server
	//Gets current system temp and what mode (Ac/off/heat)
	//called on return of the getState
	function serverSync()
	{
        if(!changed)
        {
            get(SERVER_URL + SERVER_ENDPOINTS["getState"], serverSyncResponse)
        }
	}

	function serverSyncResponse(responseText)
	{
		if(!changed){
				serverState = JSON.parse(responseText)[SYSTEM_NAME];
				if(serverState["systemTemp"] != localSystemTemp){
					setNewTemp(serverState["systemTemp"]);

					if(serverState["mode"] == "off"){

					}
					else if((serverState["mode"] == "cool")){
					}

					else if((serverState["mode"] == "heat")){

					}
                    else if((serverState["mode"] == "fan")){

                    }

					changed = false;
				}

			}
	}
	
	function get(theUrl, callback)
	{
	    var xmlHttp = new XMLHttpRequest();
	    xmlHttp.onreadystatechange = function() { 
	        if (xmlHttp.readyState == 4 && xmlHttp.status == 200)
	            callback(xmlHttp.responseText);
	    }
	    xmlHttp.open("GET", theUrl, true); 
	    xmlHttp.send(null);
	}

	function put(theUrl, callback)
	{
	    var xmlHttp = new XMLHttpRequest();
	    xmlHttp.onreadystatechange = function() { 
	        if (xmlHttp.readyState == 4 && xmlHttp.status == 200)
	            callback(xmlHttp.responseText);
	    }
	    xmlHttp.open("GET", theUrl, true);
	    xmlHttp.send(null);
	}

	setInterval(serverSync, serverSyncTime);

    window.addEventListener("load", function() {
    		get(SERVER_URL + SERVER_ENDPOINTS["getState"], serverSync)
    	});

});