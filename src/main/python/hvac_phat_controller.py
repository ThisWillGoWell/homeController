
#TODO 9/20/2016
#need to get this and the jar running on the pi.... 
ON_PI = False
import time
if ON_PI:
	from microdotphat import write_string, clear, show
	import Adafruit_MCP9808.MCP9808 as MCP9808
	import RPi.GPIO as GPIO
	
import requests as request
import json

import math



#Program Constants
SERVER_ADDRESS = 'http://localhost:8080'
SERVER_ENDPOINTS = ['/get?system=HVAC&what=state','/set?system=HVAC&what=systemTemp','/set?system=HVAC&what=roomTemp', '/set?system=HVAC&what=ac', '/set?system=HVAC&what=heat', '/set?system=HVAC&what=fan', '/set?system=HVAC&what=systemPower']


UPDATE_RATE = 1 #server update once a second
READ_RATE = 2	#Read rate for sensor evry 2 seconds


#Update times for gettting system Info
nextUpdateTime = time.time()
nextReadTime   = time.time()
roomTemp = 0

systemTemp = 68
tempTemp = 68

pressedButton = 0
buttonList = []
buttonStatus = [False, False, False, False]
timeoutLen = 5
timeoutTime = 0
change = True
state = []
toggle = True

#GPIO Map
AC_PIN = 20
FAN_PIN = 4
HEAT_PIN = 21
#Should these be on
ac = False
heat = False
fan = False

#What mode are we in?
acPower = False
heatPower = False
fanPower = False
power = False
startTime = time.time();

if ON_PI:
	sensor = MCP9808.MCP9808()
	#GPIO_DIRECTION
	GPIO.setmode(GPIO.BCM)

	GPIO.setup(AC_PIN, GPIO.OUT)
	GPIO.setup(FAN_PIN, GPIO.OUT)
	GPIO.setup(HEAT_PIN, GPIO.OUT)

	GPIO.output(AC_PIN,False)
	GPIO.output(FAN_PIN,False)
	GPIO.output(HEAT_PIN,False)

def getState():
	global	state	
	url = SERVER_ADDRESS + SERVER_ENDPOINTS[0]
	response = request.get(url)
	state = response.json();
	systemTemp = state["systemTemp"]
	pass

def setSystemTemp(temp):
	url = SERVER_ADDRESS + SERVER_ENDPOINTS[1]
	response = request.put(url + '&to=' + str(temp))
	pass

def setRoomTemp(temp):
	url = SERVER_ADDRESS + SERVER_ENDPOINTS[2]
	response = request.put(url + '&to=' + str(temp))
	print(response.text)
	pass

def setPower(state):
	url = SERVER_ADDRESS + SERVER_ENDPOINTS[6]
	response = request.put(url+'&to=' + str(state))
	pass

def setFan(state):
	url = SERVER_ADDRESS + SERVER_ENDPOINTS[5]
	response = request.put(url+'&to=' + str(state))
	pass

def setHeat(state):
	url = SERVER_ADDRESS  +SERVER_ENDPOINTS[4]
	response = request.put(url + '&to=' + str(state))
	pass

def setAc(state):
	url =  SERVER_ADDRESS + SERVER_ENDPOINTS[3]
	response = request.put(url + '&to=' + str(state))
	pass


def update():
	global	roomTemp
	global	systemTemp	
	global	change	
	global state
	global nextUpdateTime
	if time.time() > nextUpdateTime:
		url = SERVER_ADDRESS + SERVER_ENDPOINTS[0]
		response = request.get(url)
		state = response.json()["HVAC"]

		if systemTemp != state["systemTemp"]:
			systemTemp = state["systemTemp"]
			change = True

		if float(roomTemp)	 != round(float(state["roomTemp"])):
			roomTemp = str(round(float(state["roomTemp"])))
			change = True
		nextUpdateTime += 1

def readSensor():
	global startTime
	global nextReadTime
	if time.time() > nextReadTime:
		if ON_PI:
			setRoomTemp(sensor.readTempC())
		else:
			setRoomTemp( time.time() - startTime /60)
		nextReadTime += 1


def toggleGPIO():
	global state
	global ac
	global fan
	global heat
	global AC_PIN
	global FAN_PIN
	global HEAT_PIN
	global change
	if ON_PI:
		if (state['ac'] == 'true') != ac:
			ac = not ac
			GPIO.output(AC_PIN,ac)
			change = True
			
		if (state['fan'] == 'true') != fan:
			fan = not fan
			GPIO.output(FAN_PIN, fan)
			change = True

		if (state['heat'] == 'true') != heat:
			heat = not heat
			GPIO.output(HEAT_PIN, heat)
			change = True

def display():
	global roomTemp
	global systemTemp
	if ON_PI:
		write_string( str(systemTemp)[0:2]+"  " + str(roomTemp)[0:2] , kerning=False)
		show()


#Pre Program Functions
DEGREE_SYMBOL = '\x01'
#lcd.create_char(1, [0,8,20,8,0,0,0,0]) #DEGREE
#PLAY_PAUSE_SYMBOL = '\x02'
#lcd.create_char(2, [0,20,22,23,22,20,0,0]) #PLAY_PAUSE
THEMO_SYMBOL = '\x03'
#lcd.create_char(3,[0,4,4,4,4,10,4,0]) #THEMO


#lcd.set_color(1.0, 1.0, 1.0)
#lcd.clear()
#lcd.message('Waiting for connection')
#init sensor
if ON_PI:
	sensor.begin()

update()
tempTemp = float(systemTemp)


while True:
	readSensor()
	#buttons()
	update()
	toggleGPIO()
	display()
	#timeout()
	#Update form sever

	



	
