
import os


ComponentList = []

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

class CreateComponentList():
    print(init.data)
