
import Image
import ImageSequence
import ImageDraw
import time
import cStringIO
import requests #http://docs.python-requests.org/en/master/
import json
import math
import random
import thread
from rgbmatrix import Adafruit_RGBmatrix
from socket import timeout
matrix = Adafruit_RGBmatrix(32, 2)

SERVER_ADDRESS = 'http://192.168.1.84:8080'
SERVER_ENDPOINTS = ['/getImageTimeline']
#Get Iamge
#http://www.effbot.org/imagingbook/pil-index.htm
resources = []

#file = cStringIO.StringIO(request.urlopen("http://192.168.1.84:8080/getImage").read());
#resourceImage = Image.open(file);

def analyseImage(path):
    '''
    Pre-process pass over the image to determine the mode (full or additive).
    Necessary as assessing single frames isn't reliable. Need to know the mode 
    before processing all frames.
    '''
    im = Image.open(path)
    results = {
        'size': im.size,
        'mode': 'full',
    }
    try:
        while True:
            if im.tile:
                tile = im.tile[0]
                update_region = tile[1]
                update_region_dimensions = update_region[2:]
                if update_region_dimensions != im.size:
                    results['mode'] = 'partial'
                    break
            im.seek(im.tell() + 1)
    except EOFError:
        pass
    return results


def processImage(path):
    '''
    Iterate the GIF, extracting each frame.
    '''
    mode = analyseImage(path)['mode']
    
    im = Image.open(path)

    i = 0
    p = im.getpalette()
    last_frame = im.convert('RGBA')
    
    try:
        while True:
                      
            '''
            If the GIF uses local colour tables, each frame will have its own palette.
            If not, we need to apply the global palette to the new frame.
            '''
            if not im.getpalette():
                im.putpalette(p)
            
            new_frame = Image.new('RGBA', im.size)
            
            '''
            Is this file a "partial"-mode GIF where frames update a region of a different size to the entire image?
            If so, we need to construct the new frame by pasting it on top of the preceding frames.
            '''
            if mode == 'partial':
                new_frame.paste(last_frame)
            
            new_frame.paste(im, (0,0), im.convert('RGBA'))
            resources.append(new_frame);
            i += 1
            last_frame = new_frame
            im.seek(im.tell() + 1)
    except EOFError:
        pass


queryTime = 2
nextStart = math.floor(time.time()) * 1000  
nextStop = nextStart  + 2000
nextTimelineUpdateTime = nextStart
nextImageUpdateTime = nextStart
  
canUpdate= True
lostConnection = True
imageTimeline   = []
imageTimeline= {'interval' :100}
def getImageUpdate():
    global imageTimeline, nextTimelineUpdateTime, nextStart, nextStop, canUpdate, lostConnection, queryTime
    if time.time() * 1000 > nextTimelineUpdateTime and canUpdate:
        url = SERVER_ADDRESS + SERVER_ENDPOINTS[0]
        try:
            response = requests.get(url + "?start=%d&end=%d" % (nextStart, nextStop), timeout = 5)
            print(response.elapsed )

            jsonStr = str(response.text)

            imageTimeline = json.loads(jsonStr)
            canUpdate=False
            lostConnection = False
        except requests.exceptions.Timeout, e:
            lostConnection = True
        
        nextTimelineUpdateTime = nextStop - imageTimeline['interval'] * 2
        nextStart = nextTimelineUpdateTime
        nextStop = nextTimelineUpdateTime + queryTime * 1000

    
def updateImage():
    global nextImageUpdateTime, imageTimeline, canUpdate, lostConnection
    currentFrame = None
    if lostConnection == True:
        im = random.choice(resources)
        im.load()
        matrix.SetImage(im.im.id, 0, 0)
       
        nextImageUpdateTime = math.floor(time.time()) * 1000  
    else:
        currentImageTimeline    = imageTimeline 
        if time.time() * 1000 >= nextImageUpdateTime:
            for frame in currentImageTimeline["frames"]:  
                if frame["elements"][0]["t"] - (math.floor(time.time() * 1000 /currentImageTimeline["interval"]) * currentImageTimeline["interval"]) == 0:
                    currentFrame = frame["elements"]                  
                    break


        if currentFrame != None:
            im = Image.new("RGBA", resources[0].size)
            for element in currentFrame:
                for frame in element["f"]:  
                    im.paste(resources[frame["n"]].crop((0,0,frame["w"], frame["h"])), (frame["c"], frame["r"]))
            im.load()
            matrix.SetImage(im.im.id,0,0);
            nextImageUpdateTime = nextImageUpdateTime + imageTimeline["interval"]
    canUpdate = True

'''
class updateThread(threading.Thread):
    def __init__(self, threadID, funciton)
        threading.Thread.__init__(self)
        self.threadID= threadID
        self.funciton = funciton
    def run(self)

    def getTimeline
'''


def updateThread(threadName):
    while True :
        getImageUpdate()
        time.sleep(imageTimeline["interval"]/1000.0/2)


processImage("resources.gif")   
start = time.time() * 1000
thread.start_new_thread(updateThread, ("Update",))

while(True  ):
    updateImage()
    time.sleep(0.01)

