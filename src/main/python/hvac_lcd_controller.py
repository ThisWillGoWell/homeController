
#TODO 9/20/2016
#need to get this and the jar running on the pi.... 

import time
import Adafruit_CharLCD as LCD
import Adafruit_MCP9808.MCP9808 as MCP9808
import urllib.request as request
import json




#Program Constants
SERVER_ADDRESS = 'http://localhost:8080'
SERVER_ENDPOINTS = SERVER_ENDPOINTS = ['/getState','/setSystemTemp','/setRoomTemp', '/setAc', '/setHeat', '/setFan', '/setPower']
lcd = LCD.Adafruit_CharLCDPlate()
sensor = MCP9808.MCP9808()

UPDATE_RATE = 1 #server update once a second
READ_RATE = 2	#Read rate for sensor evry 2 seconds

#Update times for gettting system Info
nextUpdateTime = time.time()

roomTemp = 0

systemTemp = 68
tempTemp = 68
toggle = True;
pressedButton = 0
buttons = [LCD.SELECT, LCD.UP, LCD.DOWN]
timeout = 5
timeoutTime = 0
change = True
state = []
def buttons():

	if not toggle:
		if(not lcd.is_pressed(buttons[pressedButton])):
			change = True
			toggle = True
	else:
		if(lcd.is_pressed(LCD.UP)):
			tempTemp = tempTemp + 1
			pressedButton = 1
			toggle = False
		 	lcd.clear()
			lcd.message( str(tempTemp) + '\x01')
			timeoutTime = time.time() + timeout

		if(lcd.is_pressed(LCD.DOWN)):
			tempTemp = tempTemp -1
			pressedButton = 2
			toggle = False
			lcd.clear()
			lcd.message(str(tempTemp) + '\x01')
			timeoutTime = time.time() + timeout

		if(lcd.is_pressed(LCD.SELECT)):
			systemTemp = tempTemp
			pressedButton = 0
			toggle = False
			lcd.clear()
			lcd.message("Setting temp to: ")
			timeoutTime = time.time() + timeout

def getState():
	url = SERVER_ADDRESS + SERVER_ENDPOINTS[0]
	response = request.urlopen(url)
	jsonStr = str(response.read())[2:-1]
	print(jsonStr)

	state = json.loads(jsonStr)
	systemTemp = state["settings"]["systemTemp"]
	pass

def setSystemTemp(temp):
	url = SERVER_ADDRESS + SERVER_ENDPOINTS[1]
	response = request.urlopen(url + '?temp=' + str(temp))
	pass

def setRoomTemp(temp):
	url = SERVER_ADDRESS + SERVER_ENDPOINTS[2]
	response = request.urlopen(url + '?temp=' + str(temp))
	pass

def setPower(state):
	url = SERVER_ADDRESS + SERVER_ENDPOINTS[6]
	response = request.urlopen(url+'?state=' + str(state))
	pass

def setFan(state):
	url = SERVER_ADDRESS + SERVER_ENDPOINTS[5]
	response = request.urlopen(url+'?state=' + str(state))
	pass

def setHeat(state):
	url = SERVER_ADDRESS  +SERVER_ENDPOINTS[4]
	response = request.urlopen(url + '?state=' + str(state))
	pass

def setAc(state):
	url =  SERVER_ADDRESS + SERVER_ENDPOINTS[3]
	response = request.urlopen(url + '?state=' + str(state))
	pass


def update():
	if time.time() > nextUpdateTime:
		url = SERVER_ADDRESS + SERVER_ENDPOINTS[0]
		response = request.urlopen(url)
		jsonStr = str(response.read())[2:-1]
		print(jsonStr)

		state = json.loads(jsonStr)
		systemTemp = state["settings"]["systemTemp"]
		roomTemp	= state["settings"]["roomTemp"]
		change = True;

def sensor():
	if time.time() > nextReadTime:
		roomTemp = sensor.read()
		setRoomTemp(roomTemp)

def timeout():
	if time.time() > timeoutTime and change:
		change = False
		tempTemp = systemTemp
		lcd.clear()
		lcd.message("Set " + str(systemTemp) + "\x01 at: " + str(roomTemp) + "\x01")

#Pre Program Functions
lcd.create_char(1, [4,10,4,0,0,0,0,0])
lcd.set_color(1.0, 1.0, 1.0)
lcd.clear()
lcd.message('Current Temp:\n 71\x01')
#init sensor
sensor.begin()

while True:
	sensor()
	buttons()
	update()
	timeout()
	#Update form sever

	


def getState():
	
