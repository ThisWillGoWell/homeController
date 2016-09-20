#this is the pi Simulator
#will handel cross talk communication between the Springboot and python
#simulating the Temp and LCD input

#Mabe? Run only on linux

#Pyhton =>	Current Temp 	=> Java
#		<=  SetTemp			=>
#		<=  Mode			=> 
#


import urllib.request as request
import json

SERVER_ADDRESS = 'http://localhost:8080'

SERVER_ENDPOINTS = ['/getState','/setTemp','/getTemp']


'''
get the current state of the system
'''

def getState():
	url = SERVER_ADDRESS + SERVER_ENDPOINTS[0]
	response = request.urlopen(url)
	jsonStr = str(response.read())[2:-1]
	print(jsonStr)

	state = json.loads(jsonStr)
	systemTemp = state["settings"]["systemTemp"]



def setServerTemp():
		pass	

getState()