import re
import os


AUTOratings=[] #list for autonomous
TELEratings=[] #list for teleop
MINIratings=[] #list for minibots
root = None
ConfigTypes = ["PlainText", "Radio", "Updown",
               "Rating", "Checkbox", "DropDown",
               "Counter" "Timer"]

def readHeaders (path):
    try:
        file=open(path,'r')
    except:
        print ('FileNotFound')
        return
    data=file.read()
    file.close()
    data = data.split('\n')
    pattern = re.compile(r'(\w+), ([0-9]+)')
    headers = []
    for line in data:
        result = pattern.findall(line)
        if result: # make sure result is not empty before adding to dictionary          
            for vals in result:
                #key,val = vals.split(', ')
                key,val = vals
                headers.append(key)
            headers.append ('ds')
            break #out of loop - headers found
    return headers

    
def readOneFile (path, dsnumm, botsdict):
    try:
        file=open(path,'r')
    except:
        print ('FileNotFound')
        return
    data=file.read()
    file.close()
    data = data.split('\n')
    pattern = re.compile(r'(\w+), ([0-9]+)')
    needHeaders = True
    for line in data:
        result = pattern.findall(line)
        if result: # make sure result is not empty before adding to dictionary          
            linedict = {} # create a new dictionary for each data
            linedict ['ds'] = dsnumm
            for vals in result:
                #key,val = vals.split(', ')
                key,val = vals
                linedict [key] = int(val)
            teamnumb = linedict.pop('teamnum') #reads and removes team number
            teamnumb = int (teamnumb)
            if teamnumb in botsdict:
                botsdict[teamnumb].append (linedict)
            else:
                botsdict[teamnumb] = list()
                botsdict[teamnumb].append(linedict)
    return botsdict

def sortScoutFiles ():
    botsdict = {}
    dir = os.getcwd() #get current working directory
    path = dir + "\\" + "Red1" + "\\file.out"
    headers = readHeaders (path)

    for dsnumm in ["Red1","Red2","Red3","Blue4","Blue5","Blue6"]:
        path = dir + "\\" + dsnumm + "\\file.out" #get the path relative to the current working directory
        readOneFile (path, dsnumm, botsdict)
    return headers, botsdict  
 

def makeRawExcelFile (fname, headers, botsdict):
    dir = os.getcwd() #get current working directory
    path = dir + "\\" + fname + ".csv"
    try:
        file = open(path,mode='w')
    except:
        print ('Cannot open .csv file for writing')
    for heading in headers:
        file.write (heading + ',')
    file.write ('\n')

    for teamnumb in botsdict:
        for match in botsdict[teamnumb]:
            for heading in headers:
                if heading == 'teamnum':
                    val = str(teamnumb)
                else:
                    try:
                        val = str (match[heading])
                    except KeyError:
                        val = "0"
                file.write (val + ',')
            file.write ('\n')
    file.close()
    print("done")
            
if __name__ == '__main__':
    headers, botsdict = sortScoutFiles()
    makeRawExcelFile ("Data", headers, botsdict)

