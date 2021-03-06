
import os

ComponentList = []
MaxTime = 0

class init():    
    #Import data from log file
    path = os.getcwd() + "\\" + "log.csv"
    state=1
    TitlesGotten=False
    neg=False
    titles=[]
    value=0
    name=''
    rawExcel=open(path,'r')
    data=rawExcel.read()
    rawExcel.close()
    ComponentList.append("Time")

class CreateComponentList():
    #Populates FullData and ComponentList and finds the MaxTime
    
    FullData = {}
    #Splits the data by line and then removes the first and last line(descriptions and a blank space)
    #Each line is then split by comma, put into its own list, and entered into the dictionary with
    #the name of its time(first part of the list)
    data = init.data.split('\n')
    data.pop(0)
    data.pop(len(data) - 1)

    for index in data:
        line = []
        line = index.split(',')
        
        #replace true and false with 0/1
        if line[2] == '"true"':
            line[2] = 1
        elif '"false"' == line[2]:
            line[2] = 0
            
        #if it is a new component add it to the component list  
        if not ComponentList.__contains__(line[1]):
            ComponentList.append(line[1])
            
        #Set MaxTime to time if the time is greater
        if int(line[0]) > int(MaxTime):
            MaxTime = line[0]

        #enter list into dictionary
        FullData[line[0]] = index

MaxTime = int(CreateComponentList.MaxTime)

def PointInComponentList(Component):
    for i in range(0,list.__len__(ComponentList)):
        if Component == ComponentList[i]:
            return i

class CreateFinalDictionary:
    FinalDictionary = {}
    FullData = CreateComponentList.FullData
    BlankList=[]
    FinalDictionary[0] = ComponentList
    Components = list.__len__(ComponentList)
    
    for q in range(0, Components):
        BlankList.append("X")
    
    for i in range(0, MaxTime+1):
        FinalDictionary[i+1] = BlankList
        
        
    for index in FullData:
        DataPoint = []
        DataPoint = FullData[index].split(',')
        Time = int(DataPoint[0])
        Component = PointInComponentList(DataPoint[1])
        Value = DataPoint[2]
        
        DataForTime = FinalDictionary[Time]
        DataForTime[Component] = Value
        FinalDictionary[Time] = DataForTime
        
    
print(CreateFinalDictionary.FinalDictionary)
    
    
    
    
    
    
    
    
    
    
    
    
