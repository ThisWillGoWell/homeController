ONPI = True


import time

import requests #http://docs.python-requests.org/en/master/
import json
import math
import random


if ONPI:
    from rgbmatrix import Adafruit_RGBmatrix
    import Image #http://effbot.org/imagingbook/pil-index.htm
    import ImageSequence
    import ImageDraw
    import thread
else:
    from PIL import Image #http://effbot.org/imagingbook/pil-index.htm
    from PIL import ImageSequence
    from PIL import ImageDraw
    import threading as thread
from socket import timeout



if(ONPI):
    matrix = Adafruit_RGBmatrix(32, 3)

SYSTEM ="clock"
SERVER_ADDRESS = 'http://192.168.1.84:8080'
SERVER_ENDPOINTS = ['/get?system=' + SYSTEM + "&what=imageUpdate",'/get?system=' + SYSTEM + "&what=imageStart", '/get?system=' + SYSTEM + "&what=resourceImage" ]


#Get Iamge
#http://www.effbot.org/imagingbook/pil-index.htm
resources = []


def getImage():
    r = requests.get(SERVER_ADDRESS + SERVER_ENDPOINTS[2]);
    with open("resources.gif", 'wb') as fd:
        for chunk in r.iter_content(100):
            fd.write(chunk)
    fd.close()

    
getImage()

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
interval = 100
canUpdate= True
lostConnection = True
imageTimeline   = []
imageTimeline= {'interval' :100}
reloadImage = True
blankImage = Image.new("RGBA",(96,32), (0,0,0,255))
layers = {};
masks = {};
fills = {};

def getImageUpdate():
    global imageTimeline, nextTimelineUpdateTime, nextStart, nextStop, canUpdate, lostConnection, queryTime, interval, reloadImage
    if time.time() * 1000 > nextTimelineUpdateTime and canUpdate:
        if reloadImage:
            url = SERVER_ADDRESS + SERVER_ENDPOINTS[0]
        else:
            url = SERVER_ADDRESS + SERVER_ENDPOINTS[1]

        try:
            response = requests.get(url + "&t1=%d&t2=%d&interval=%d" % (nextStart, nextStop, interval), timeout = 5)
            imageTimeline = response.json();
            canUpdate=False
            lostConnection = False
            reloadImage = False
        except requests.exceptions.Timeout:
            lostConnection = True
            reloadImage = True
        
        nextTimelineUpdateTime = nextStop - imageTimeline['interval'] * 2
        nextStart = nextTimelineUpdateTime
        nextStop = nextTimelineUpdateTime + queryTime * 1000



redImage = Image.new("RGBA",(96,32), "red")

def imageToMask(image, frameNumber=-1):
    #take in a resouce image and return a mask of that image.
    #Store that mask in a dict refrenced by source frame number 
    #so dont have to regenerate it each time.
    rows = image.size[1]
    cols = image.size[0] 

    p= 0
    emptyPixel = (0,0,0,255)
    #mask = Image.new("RGBA", image.size,(255,255,255,255))
    #pmask = Image.new("RGBA", (1,1), (0,0,0,0));
    pixels = image.getdata()

    if ONPI:
        m = []
        for p in pixels:
            if(p[0] == p[1] == p[2] == 0):
                m.append(0x00)
                m.append(0x00)
                m.append(0x00)
                m.append(0x00)
            else:
                m.append(0x00)
                m.append(0x00)
                m.append(0x00)
                m.append(0xff)
        maskData = bytearray(m)


    else:
        maskData = bytes([])
        maskOn = bytes([0xff,0x00,0xff,0xff])
        maskOff = bytes([0x00,0x00,0x00,0x00])
        
        for p in range(0, len(pixels)):
            if pixels[p][0] == pixels[p][1] == pixels[p][2] == 0:
                maskData =  maskData + maskOff
            else:
                maskData = maskData + maskOn;


    mask = Image.frombuffer("RGBA", image.size, maskData)
    mask = mask.transpose(Image.ROTATE_180).transpose(Image.FLIP_LEFT_RIGHT)

    return mask


def updateImage():
    global layers, blankImage, fills, nextImageUpdateTime, imageTimeline, canUpdate, lostConnection
    currentFrame = None
    if lostConnection == True:
        im = random.choice(resources)
        im.load()
        if ONPI:
            matrix.SetImage(im.im.id, 0, 0)
        time.sleep(0.5)
       
        nextImageUpdateTime = math.floor(time.time()) * 1000  
    else:
        currentImageTimeline    = imageTimeline 
        if time.time() * 1000 >= nextImageUpdateTime:
            for frame in currentImageTimeline["frames"]:  
                if len(frame["elements"])!=0:
                    if frame["elements"][0]["t"] - (math.floor(time.time() * 1000 /currentImageTimeline["interval"]) * currentImageTimeline["interval"]) == 0:
                        currentFrame = frame["elements"]                  
                        break

        if currentFrame != None:
            #im = Image.new("RGBA", resources[0].size)
            for element in currentFrame:
                currentLayer = Image.new("RGBA", (96,32))
                if element["fill"]["fill"]:
                    key = "%d%d%d%d" % (element["fill"]["r"],element["fill"]["g"],element["fill"]["b"],element["fill"]["a"])
                    if key not in fills:
                        fills[key] = Image.new("RGBA", (96,32), (element["fill"]["r"],element["fill"]["g"],element["fill"]["b"],element["fill"]["a"]))

                    fillImage = fills[key]
                    for frame in element["f"]:  
                        currentLayer.paste(fillImage.crop((0,0,frame["w"], frame["h"])), (frame["c"], frame["r"]),imageToMask(resources[frame["n"]].crop((0,0,frame["w"], frame["h"])), frame["n"]))
                else:
                    for frame in element["f"]:
                        currentLayer.paste(resources[frame["n"]].crop((0,0,frame["w"], frame["h"])), (frame["c"], frame["r"]), imageToMask(resources[frame["n"]].crop((0,0,frame["w"], frame["h"])),frame["n"] ))
                layers[element["l"]] = currentLayer
            
            im = Image.new("RGBA", (96,32))
            for i in range(len(layers)):
                im.paste(layers[i],(0,0),imageToMask(layers[i]))

            #im = Image.blend(im, blankImage, currentImageTimeline["alpha"])
            if ONPI:
                im.load()
                matrix.SetImage(im.im.id,0,0);
            else:
                pass
                #showImage(image)

            nextImageUpdateTime = nextImageUpdateTime + imageTimeline["interval"]
    canUpdate = True

def showImage(image):
    largeImage = image.resize((image.size[0]*10, image.size[1] * 10))
    largeImage.load()
    largeImage.show()


def updateThread(threadName):
    while True :
        getImageUpdate()
        time.sleep(imageTimeline["interval"]/1000.0/2)

processImage("resources.gif")
imageToMask(resources[0], 0)
start = time.time() * 1000
if ONPI:
    thread.start_new_thread(updateThread, ("Update",))
else:
    t = thread.Thread(target=updateThread, args=("update",))
    t.start()

im = Image.new("RGBA", resources[0].size)
for i in range(10):
    layers[i] = blankImage

while(True  ):
    updateImage()
    time.sleep(0.01)

