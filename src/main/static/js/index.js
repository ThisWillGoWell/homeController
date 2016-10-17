$(function() {

	var degreeSymbol = "°";
	var tempVal = 76;
	var tempView = $('#current-temp-view');
	var tempInc = $('.temp-inc');
	var tempDec = $('.temp-dec');
	var acButton = $('.ac-toggle');
	var heatButton = $('.heat-toggle');
    var currentMode = "off";

	var heatState = 0;
	var acState = 0;


	var localSystemTemp = 0;
	var serverState = {};
	var mode = "off";
	var tempChanged = false;
	var modeChanged = false;
    var serverSyncTime = 2000;
	var SERVER_URL = "http://localhost:8080"
	var SYSTEM_NAME = "HVAC"
	var SERVER_ENDPOINTS = {
		"getState" : "/get?system=" + SYSTEM_NAME + "&what=state",
		"setSystemTemp" : "/set?system="+ SYSTEM_NAME + "&what=systemTemp&to=",
		"setMode" : "/set?system="+ SYSTEM_NAME + "&what=mode&to=",

	}
	
	acButton.click(toggleAc);

	function toggleAc()
	{
		modeChanged = true;
		nextModeSendTime = (new Date).getTime()  + 1000
		if(!heatState) {
			acState = !acState;
			acButton.toggleClass('ac-on');
			acButton.attr("data-acState", acState);
		} else {
			heatState = !heatState;
			heatButton.toggleClass('heat-on');
			heatButton.attr("data-heatState", heatState);
			acState = !acState;
			acButton.toggleClass('ac-on');
			acButton.attr("data-acState", acState);
		}

	}

	heatButton.click(toggleHeat);

	function toggleHeat()
	{
		modeChanged = true;
		nextModeSendTime = (new Date).getTime()  + 1000;
		if(!acState) {
			heatState = !heatState;
			heatButton.toggleClass('heat-on');
			heatButton.attr("data-heatState", heatState);
		} else {
			acState = !acState;
			acButton.toggleClass('ac-on');
			acButton.attr("data-acState", acState);
			heatState = !heatState;
			heatButton.toggleClass('heat-on');
			heatButton.attr("data-heatState", heatState);
		}

	}
	
	tempInc.click(function() {
		setNewTemp(localSystemTemp + 1);
	});
	
	tempDec.click(function() {
		setNewTemp(localSystemTemp - 1);
	});
	
	
	var nextTempSendTime;
	function setNewTemp(val) {
		localSystemTemp = val;
		tempView.text(localSystemTemp+degreeSymbol);
		nextTempSendTime = (new Date).getTime()  + 1000;
		tempChanged = true;
	}

	//sends the system temp to the server
	//
	function setServerTemp(){
		put(SERVER_URL + SERVER_ENDPOINTS["setSystemTemp"] + localSystemTemp, success)
	}


	function setServerMode(){
		var mode = "off";
		if(acState)
			mode = "cool"
		else if(heatState)
			mode = "heat"


	    put(SERVER_URL + SERVER_ENDPOINTS["setMode"] + mode, success)
	}




    function success(responseText)
    {

    }



	//Will wait 1 second of inactivity until the
	//server temp is sent to sever, 
	//prevent rapid changes ans unesscarry calls
	function timeout(){
		if((tempChanged) && (new Date).getTime() > nextTempSendTime)
		{
			setServerTemp();
			tempChanged = false;
		}
		if(modeChanged && (new Date).getTime() > nextModeSendTime)
		{
			setServerMode();
			modeChanged = false;
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
        if(!tempChanged && !modeChanged)
        {
            get(SERVER_URL + SERVER_ENDPOINTS["getState"], serverSyncResponse)
        }
	}

	function serverSyncResponse(responseText)
	{
		if(!tempChanged){
			serverState = JSON.parse(responseText)[SYSTEM_NAME];
			if(serverState["systemTemp"] != localSystemTemp){
				setNewTemp(serverState["systemTemp"]);
				tempChanged = false;
			}

			if(serverState["mode"] == "off"){
				if(acState)
					toggleAc();
				if(heatState)
					toggleHeat();

			}
			else if((serverState["mode"] == "cool")){
				if(!acState)
					toggleAc();
			}

			else if((serverState["mode"] == "heat")){
				if(!heatState)
					toggleHeat();

			}
            else if((serverState["mode"] == "fan")){
            	if(acState)
					toggleAc();
				if(heatState)
					toggleHeat();

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