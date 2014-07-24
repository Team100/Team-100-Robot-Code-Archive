import os

class init():
    path = os.getcwd() + "\\" + "Data.csv"
    state=1
    TitlesGotten=False
    neg=False
    titles=[]
    value=0
    name=''
    rawExcel=open(path,'r')
    data=rawExcel.read()
    rawExcel.close()

    dictionary={}
    data = data.split ('\n')
    data.pop()
    data.pop(0)
    for line in data:
        linelist=[]
        linelist = line.split (',')
        linelist.pop()     
        teamnum = linelist[1]
        if teamnum in dictionary:
            dictionary[teamnum].append (linelist)
        else:
            dictionary[teamnum] = []
            dictionary[teamnum].append(linelist)
        linelist.pop()
        linelist.pop(0)
        #print(linelist)

def MakenDaExcel(data):
    #print(data)
    exceldir = os.getcwd() #get current working directory
    path = exceldir + "\\" + "ExcelForJava.csv"
    try:
        file = open(path,mode='w')
    except:
        print ('Cannot open .csv file for writing')
    linea=[];lineb=[];linec=[];lined=[];linef=[];lineg=[];lineh=[];linei=[];linej=[];linek=[];linel=[];
    for index in data:
        linea.append(index[0])
        lineb.append(index[1])
        linec.append(index[2])
        lined.append(index[3])
        linef.append(index[4])
        lineg.append(index[5])
        lineh.append(index[6])
        linei.append(index[7])
        linej.append(index[9])
        linek.append(index[10])
        linel.append(index[11])
    for index in linea: file.write(str(index) + ',')
    file.write ('\n')
    for index in lineb: file.write(str(index) + ',')
    file.write ('\n')
    for index in linec: file.write(str(index) + ',')
    file.write ('\n')
    for index in lined: file.write(str(index) + ',')
    file.write ('\n')
    for index in linef: file.write(str(index) + ',')
    file.write ('\n')
    for index in lineg: file.write(str(index) + ',')
    file.write ('\n')
    for index in lineh: file.write(str(index) + ',')
    file.write ('\n')
    for index in linei: file.write(str(index) + ',')
    file.write ('\n')
    for index in linej: file.write(str(index) + ',')
    file.write ('\n')
    for index in linek: file.write(str(index) + ',')
    file.write ('\n')
    for index in linel: file.write(str(index) + ',')
    file.write ('\n')
    for index in linel: file.write('No Comment' + ',')
    print("Done")
    
def tens(toround):
    return (int(toround*10))/10
    
        
class TreeBeard():
    
    master=[]
    for index in init.dictionary:
        teamdict={}
        teamdata=[]
        teamdata=init.dictionary[index]
        teamdict[index]=teamdata
        
        
        for index in teamdata:
            index[0]=int(index[0])
            data=[]
            data=index

            autodisabled=data[1]; kinect=data[2]; autoscorebot=data[3]
            autoscoremid=data[4]; autoscoretop=data[5]; active=data[6]
            startposition=data[7]; driverrating=data[8]; manueverrating=data[9]
            defensiverating=data[10]; offensiverating=data[11]; botrange=data[12]
            robottype=data[13]; telescorebot=data[14]; telescoremid=data[15]
            telescoretop=data[16]; penalty=data[17]; bridge=data[18];
            redcard=data[19]
            
            
            robotname=0;robottelescore=0;robotautoscore=0;therange=0;
            robotname=index[0]
            robottelescore = int(telescorebot) + int(telescoremid)*2 + int(telescoretop)*3
            offensescore = offensiverating;
            defensescore = defensiverating;
            manuevering = manueverrating;
            driver = driverrating;
            third = offensiverating;
            if defensiverating > offensiverating:
                third = defensiverating       
            
            overrank = (int(driver)*int(manuevering)*int(third))/3;
            
            therange = botrange;
            
            autonomous= int(autoscorebot) + int(autoscoremid)*2 + int(autoscoretop)*3 + int(autoscorebot)*3 + int(autoscoremid)*3 + int(autoscoretop)*3
            useskinect=kinect
            
            data=[robotname,robottelescore,autonomous,offensescore,defensescore,manuevering,driver,useskinect,overrank,therange, penalty, bridge]
            
            master.append(data)
           
            
    
    
class beard_of_neptune:
    master = TreeBeard.master
    listofinfinity=[]
    neptune=[]
    poseidon=[]
    dictionaryfrog={}
    froglist=[]
    for index in master:   
        if index[0] in froglist:
            dictionaryfrog[index[0]].append(index)
        else:
            dictionaryfrog[index[0]] = []
            dictionaryfrog[index[0]].append(index)
            froglist.append(index[0])
    
    for index in dictionaryfrog:
        allthedata=dictionaryfrog[index]
        q=0
        team=0;score=0;ascore=0;offense=0;defense=0;manuev=0;driver=0;kinect=0;overscore=0;botrange=0;penalty=0;bridge=0;
        listy=[]
        bridgepoints = 0;
        for index in allthedata:
            data=allthedata[q]
            team=data[0];score=score+data[1];ascore=ascore+data[2];offense=offense+int(data[3])
            defense=defense+int(data[4]);manuev=manuev+int(data[5]);driver=driver+int(data[6])
            kinect=kinect+int(data[7]);overscore=overscore+int(data[8]);
            penalty = penalty + int(data[10]);bridge = int(data[11])
            
            if int(data[9]) > botrange:
                botrange = int(data[9])
            
            if bridge == 1:
                bridgepoints = bridgepoints+10
            elif bridge == 2:
                bridgepoints = bridgepoints+20
            elif bridge == 3:
                bridgepoints = bridgepoints+40
            elif bridge == 4:
                bridgepoints = bridgepoints+20
            
            q=q+1
        if q != 0:
            score=score/q; ascore=ascore/q; offense=offense/q; defense=defense/q; manuev=manuev/q;
            kinect=(kinect/q)*100;driver=driver/q; overscore=overscore/q;penalty = penalty/q;
            bridgepoints = bridgepoints/q;
            
            score=tens(score); ascore=tens(ascore);offnse=tens(offense);defense=tens(defense)
            manuev=tens(manuev); driver=tens(driver); kinect=int(kinect); bridgepoints=int(bridgepoints)
            
            
        
        #Processing
        finaloffense = 10-10/abs(score+ascore+bridgepoints+offense/2+driver/3+1)
        finaldefense = 10-10/abs(driver+manuev+defense*2-penalty*10+1);
        finalmanuev = 10-10/abs(manuev+driver+1)
        finalpickup = abs(driver+1)
        finalrange = botrange
        finalpenalty = penalty
        finalbridge = 10-10/abs(bridgepoints+1)       
        
        #Processing
        listy=[team,score,ascore,finaloffense,finaldefense,finalmanuev,finalpickup,kinect,overscore, finalrange, finalpenalty, finalbridge]
        trident=(overscore,team)
        neptune.append(listy)
        poseidon.append(trident)


class hedgehog_made_of_quartz:
    cider = []
    info=beard_of_neptune.neptune
    order = beard_of_neptune.poseidon
    
    order.sort()
    order.reverse()
    
    for index in order:
        teamtoget=index[1]
        overinfo=index
        for index in info:
            if index[0] == teamtoget:
                overinfo=(int(overinfo[0]*1000)/1000)
                #index.append(overinfo)
                cider.append(index)
       
    MakenDaExcel(cider)
    
    
