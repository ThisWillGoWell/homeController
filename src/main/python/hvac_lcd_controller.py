
#TODO 9/20/2016
#need to get this and the jar running on the pi.... 

import time
import Adafruit_CharLCD as LCD
import urllib.request as request
import json

#Program Constants
SERVER_ADDRESS = 'http://localhost:8080'
SERVER_ENDPOINTS = ['/getState','/setTemp','/getTemp']
lcd = LCD.Adafruit_CharLCDPlate()

UPDATE_RATE = 1 #update once a second

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

#Pre Program Functions
lcd.create_char(1, [4,10,4,0,0,0,0,0])
lcd.set_color(1.0, 1.0, 1.0)
lcd.clear()
lcd.message('Current Temp:\n 71\x01')

while True:
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

	
	#Update form sever
	if time.time() > nextUpdateTime
		url = SERVER_ADDRESS + SERVER_ENDPOINTS[0]
		response = request.urlopen(url)
		jsonStr = str(response.read())[2:-1]
		print(jsonStr)

		state = json.loads(jsonStr)
		systemTemp = state["settings"]["systemTemp"]
		change = True;


	if time.time() > timeoutTime and change:
		change = False
		tempTemp = systemTemp
		lcd.clear()
		lcd.message("Set " + str(systemTemp) + "\x01 at: " + str(roomTemp) + "\x01")


def getState():
	
