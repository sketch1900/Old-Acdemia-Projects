from tkinter import *
from tkinter import Tk, ttk, messagebox, simpledialog
from classes import readFile
from random import randint

objList = []
tournamentList = []
gender = ""
read = readFile.ReadFile()
json = read.readPrize()
ranking = read.readRankingPoints()
tournamentList = read.readTournement()

class App:
    def __init__(self, master):
        self.master = master
        self.homeLayout(master)

    #Home tab layout
    def homeLayout(self, root):
        t = StringVar()

        self.label0 = self.createLabel('label0', "DADSAA Tournement System.", 0, 0, tab0)
        self.label1 = self.createLabel('label1', "Enter Tournament Data.", 2, 0, tab0)
        self.label1.grid(pady=(20,20))
        self.enter_button1 = self.createButton('enter_button1', 'Enter', 2, 1, tab0)
        self.enter_button1['command'] = lambda: self.choice()
        
        self.label2 = self.createLabel('label2', "Merge and sort match data", 3, 0, tab0)
        self.label2.grid(pady=(20,20))
        self.enter_button2 = self.createButton('enter_button2', 'Enter', 3, 1, tab0)
        self.enter_button2['command'] = lambda: self.merge()

        self.statsLabel = self.createLabel('statsLabel', "Player Statistics Page", 4, 0, tab0)
        self.statsLabel.grid(pady=(20,20))
        self.enter_button3 = self.createButton('close_button0', 'Enter', 4, 1, tab0)
        self.enter_button3['command'] = lambda: self.getPlayerStats()
        
        self.label3 = self.createLabel('label3', "Exit", 5, 0, tab0)
        self.label3.grid(pady=(20,20))
        self.close_button0 = self.createButton('close_button0', 'Close', 5, 1, tab0)
        self.close_button0['command'] = lambda: root.quit()

        self.label4 = self.createLabel('label4', "Instructions:", 6, 0, tab0)
        self.label4.grid(pady=(20,20))
        self.label5 = Label(tab0, textvariable=t)
        self.label5.grid(row=7, column=0)

        text = """0. All output files will be .csv and save files winners files have the format:
        gender, Tournamenet or statistic (leaderboard or save File) .csv\n
        1. Player name file can be called anything but must contain the word 'PLAYERS' and be in .csv format (comma seperated values).\n
        2. Matches can also be called anything but must have the format of
        Player A set Player B set. Also be in the format of .csv.\n
        3. Manual input must be in the same format of Player A set Player B set.\n
        4. In order to find specific player statistics, the user must have completed 1 tournament minimum.\n
        5. In order to find season specific player statistics the user must have completed the season of 4 tournaments
        and procceed to merge both the save files and statistics into 1 file retrospectivly.\n
        6. In order to start season 2 As stated in 5. season 1 needs to have been completed and merged in order to move on."""
        t.set(text) 

    #defines the choices for the choice input tab
    def choice(self):
        v = StringVar()
        y = StringVar()
        z = StringVar()
        a = StringVar()
        z.set("season1")
        a.set("TAC1")
        v.set("male")
        y.set("new")

        self.enableTab(0,1)

        self.label6 = self.createLabel('label6', "Data Entry:", 0, 0, tab1)
        self.label6.grid(padx=(350,0))

        self.label7 = self.createLabel('label7', "Select Gender:", 2, 0, tab1)
        self.label7.grid(pady=(20,20))
        self.r0 = self.createRadio('r0',"Male", v, "male", 2, 1, tab1)
        self.r1 = self.createRadio('r1',"Female", v, "female", 2, 2, tab1)

        self.label8 = self.createLabel('label8', "Continue from save file?", 4, 0, tab1)
        self.r2 = self.createRadio('r2',"Yes", y, "save", 4, 1, tab1)
        self.r3 = self.createRadio('r3',"No", y, "new", 4, 2, tab1)
        self.r3.grid(pady=(20,20))
        
        self.label9 = self.createLabel('label9', "Please select a Season:", 5,0,tab1)
        self.r4 = self.createRadio('r4',"Season 1", z, "season1", 5, 1, tab1)
        self.r5 = self.createRadio('r5',"Season 2", z, "season2", 5, 2, tab1)
        self.r5.grid(pady=(20,20))

        self.label10 = self.createLabel('label10', "Please select a Tournamenet:", 6,0,tab1)
        self.r6 = self.createRadio('r6',"TAC1", a, "TAC1", 6, 1, tab1) 
        self.r7 = self.createRadio('r7',"TAE21", a, "TAE21", 6, 2, tab1)
        self.r8 = self.createRadio('r8',"TAW11", a, "TAW11", 6, 3, tab1)
        self.r9 = self.createRadio('r9',"TBS2", a, "TBS2", 6, 4, tab1)
        self.r9.grid(pady=(20,20))

        self.button1 = self.createButton('button1', "Submit", 9, 0, tab1)
        self.button1.grid(pady=(20,20))
        self.button1['command'] = lambda: self.dataEntry(v, y, a, z)

        self.back_button0 = self.createButton('back_button0', "Back", 9,1,tab1)
        self.back_button0['command']= command= lambda: self.enableTab(1,0)

    #returns objlist
    def dataEntry(self, v, y, a, z):
        gender = v.get()
        saveFile = y.get()
        tournament = a.get()
        season = z.get()

        messagebox.showinfo("Player Selection", "select a file containing the 32 player Names e.g. MALE PLAYERS and is located within the tournament files folder")

        try:
            objList = read.readPlayerNames(gender)
            if(len(objList) == 32 and "MP" in objList[0].name or "FP" in objList[0].name):
            
                if season == 'season1':
                    self.modeInput(season,tournament, saveFile, objList, gender, "")
                else:
                    messagebox.showinfo("Statistics File Selection", "Please select the Master stat file to read in for a season. e.g male season1 Master stat.csv")
                    fileName = read.file()
                    statFile = read.statFile(fileName)
                    read.getStats(statFile, objList)

                    for n in objList:
                        n.season['TAC1'] = n.TAC1
                        n.season['TAW11'] = n.TAW11
                        n.season['TAE21'] = n.TAE21
                        n.season['TBS2'] = n.TBS2

                        n.TBS2 = []
                        n.TAW11 = []
                        n.TAC1 = []
                        n.TAE21 = []

                    self.modeInput(season,tournament, saveFile, objList, gender, "seedings")
            else:
                messagebox.showinfo("Error", "Incorrect player name file.\n Redirecting... Please try again")
                self.enableTab(1,0)
                
        #error message and backbutton
        except TypeError as e:
            messagebox.showerror("Error", "Incorrect player name file.\n Redirecting... Please try again")
            self.enableTab(1,0)

        except PermissionError as e:
            messagebox.showerror("Error", "File is open please shut the file and try again.")
            self.enableTab(1,0)

        except FileNotFoundError as p:
            messagebox.showerror("Error", "File is Not found Please try again.")
            self.enableTab(1,0)

        except IndexError as g:
            messagebox.showerror("Error", "Wrong File loaded or the file does not contain the correct statistics, Please try again.")
            self.enableTab(1,0)

    #define tournment choices
    def modeInput(self, season,tournament, save, objList, gender, mode):
        self.rounds = 1
        self.usedSave = 0
        self.enableTab(1,2)

        #tab title label
        self.label11 = self.createLabel('label11', 'Mode Selection Menu', 0,1, tab2)
        self.label11.grid(padx=(200, 0), pady=(20,20))
        self.submitButton = self.createButton('submitButton', "", 1,0, tab2)
        self.submitButton.grid(padx=(20,20))
        self.enter_button3 = self.createButton('enter_button3', "",1,1, tab2)
        self.enter_button3.grid(padx=(20,20))
        self.stat_button1 = self.createButton('stat_button1', "",1,2, tab2)
        self.stat_button1.grid(padx=(20,20))

        #returns the difficulty
        diff = read.getTournamentDiff(tournament, tournamentList)

        #gets the prizes based on the season
        prizeList = read.getPrize(json, tournament)

        if save == "save":
            messagebox.showinfo("Save File", "Please select a save file. e.g male season1 TAC1 Round1 save File.csv located within the Save Files folder")
            try:
                saveFile = read.file()
                statFile = read.statFile(saveFile)

                self.rounds = read.getRoundNumber(tournament, saveFile)
                read.getScores(saveFile, objList)
                read.getStats(statFile, objList)

                self.usedSave = 1

                file = gender + ".csv has been loaded"
                tour = season + " " + tournament + " Has been selected and the loaded save file round is currently at: " + str(self.rounds + self.usedSave)
                
                messagebox.showinfo("Notice", file + "\r" + tour)

            except FileNotFoundError as f:
                messagebox.showerror("Error", "StatFile Does not exist\rPlease load in the file or manual input first before loading saves.")
                self.enableTab(2,1)
            
            except IndexError as d:
                messagebox.showerror("Error", "Incorrect file selected, Redirecting back.")
                self.enableTab(2,1)

            except PermissionError as e:
                messagebox.showerror("Error", "File open, Redirecting back.")
                self.enableTab(2,1)
        else:
            file = gender + ".csv has been loaded"
            tour = season + " " + tournament + " Has been selected"
               
            messagebox.showinfo("Notice", file + "\r" + tour)

        if mode == "":
            if self.rounds == 5:
                self.postMatch(objList, ranking, prizeList, gender, self.rounds, season, tournament, diff, "")

            self.submitButton['text'] = "Read Files"
            self.submitButton['command'] = lambda: self.matchSetupReadFile(objList, ranking, prizeList, gender, season, tournament, diff)
            self.enter_button3['text'] = "Manual Input"
            self.enter_button3['command'] = lambda: self.matchSetupManual(objList, ranking, prizeList, gender, season, tournament, diff)
            self.stat_button1['text'] = "Player Statistics"
            self.stat_button1['command'] = lambda: self.statistics(objList, 0, tournament)
        
        else:
            if self.rounds == 5:
                self.postMatch(objList, ranking, prizeList, gender, self.rounds, season, tournament, diff, "")

            self.submitButton.destroy()
            self.enter_button3['text'] = "Continue"
            self.enter_button3['command'] = lambda: self.getSeedings(objList, ranking, prizeList, gender, self.rounds, season, tournament, diff)
            self.stat_button1['text'] = "Player Statistics"
            self.stat_button1['command'] = lambda: self.statistics(objList, 0, tournament)  


    #read file option match setup and output/file make
    def matchSetupReadFile(self, objList, ranking, prizeList, gender, season, tournament, diff):
        tempList = []
        g = IntVar()
        g.set(1)

        self.enableTab(2,5)

        messagebox.showinfo("Match  Information", "Please select the round " + str(self.rounds + self.usedSave) + " " + tournament + " file.")

        mMatchFile = read.file()

        if mMatchFile != "":
            try:
                #read the match file.
                mMatches = []

                #open the file and read the first round matches
                with open(mMatchFile, "r") as m:
                    next(m)
                                
                    for line in m:
                        mMatches  = line.split(",")
                        #take a copy of the matchs MP01 2 MP02 3 and then add them to a list to count there length
                        tempList.append(mMatches[0] + " " + mMatches[1] + " " + mMatches[2] + " " + mMatches[3])
                    m.close()

                self.enableTab(5,3)
                self.preMatch(objList, ranking, prizeList, gender, season, tournament, diff, tempList, "")

            #if the file is open already and then is trying to be accessed from here
            except PermissionError as e:
                messagebox.showinfo("File error", "File is open, please close the file containing the scores.")

            #if the file is not the correct file.
            except IndexError as d:
                messagebox.showinfo("File error", "Incorrect file selected.\rPlease select the correct file and try again.")
        else:
            messagebox.showinfo("File error", "No File selected please select another and try again.")
            self.enableTab(5,2)
          
            
    #defines the matches manual input mode.
    def matchSetupManual(self, objList, ranking, prizeList, gender, season, tournament, diff):
        tempList = []

        messagebox.showinfo("Match data input", "Please enter the matches for the round " + str(self.rounds + self.usedSave) + " " + tournament)
        self.enableTab(2,4)

        self.titleLabel = self.createLabel('titleLabel', "Enter the matches as e.g: MP01 2 MP02 3 in the fields below", 0,0, tab4)

        totalRound = self.rounds + self.usedSave

        if totalRound == 1:
            self.e1 = self.createEntryField('e1', 2, tab4)
            self.e2 = self.createEntryField('e2', 3, tab4)
            self.e3 = self.createEntryField('e3', 4, tab4)
            self.e4 = self.createEntryField('e4', 5, tab4)
            self.e5 = self.createEntryField('e5', 6, tab4)
            self.e6 = self.createEntryField('e6', 7, tab4)
            self.e7 = self.createEntryField('e7', 8, tab4)
            self.e8 = self.createEntryField('e8', 9, tab4)
            self.e9 = self.createEntryField('e9', 10, tab4)
            self.e10 = self.createEntryField('e10', 11, tab4)
            self.e11 = self.createEntryField('e11', 12, tab4)
            self.e12 = self.createEntryField('e12', 13, tab4)
            self.e13 = self.createEntryField('e13', 14, tab4)
            self.e14 = self.createEntryField('e14', 15, tab4)
            self.e15 = self.createEntryField('e15', 16, tab4)
            self.e16 = self.createEntryField('e16', 17, tab4)
            labelList = [self.e1,self.e2,self.e3,self.e4,self.e5,self.e6,self.e7,self.e8,self.e9,self.e10,self.e11,self.e12,self.e13,self.e14,self.e15,self.e16]

        elif totalRound == 2:
            self.e1 = self.createEntryField('e1', 2, tab4)
            self.e2 = self.createEntryField('e2', 3, tab4)
            self.e3 = self.createEntryField('e3', 4, tab4)
            self.e4 = self.createEntryField('e4', 5, tab4)
            self.e5 = self.createEntryField('e5', 6, tab4)
            self.e6 = self.createEntryField('e6', 7, tab4)
            self.e7 = self.createEntryField('e7', 8, tab4)
            self.e8 = self.createEntryField('e8', 9, tab4)
            labelList = [self.e1,self.e2,self.e3,self.e4,self.e5,self.e6,self.e7,self.e8]

        elif totalRound == 3:
            self.e1 = self.createEntryField('e1', 2, tab4)
            self.e2 = self.createEntryField('e2', 3, tab4)
            self.e3 = self.createEntryField('e3', 4, tab4)
            self.e4 = self.createEntryField('e4', 5, tab4)
            labelList = [self.e1,self.e2,self.e3,self.e4]

        elif totalRound == 4:
            self.e1 = self.createEntryField('e1', 2, tab4)
            self.e2 = self.createEntryField('e2', 3, tab4)
            labelList = [self.e1,self.e2]

        elif totalRound == 5:
            self.e1 = self.createEntryField('e1', 2, tab4)
            labelList = [self.e1]

        self.submit = self.createButton('submit', 'Submit',18,0, tab4)
        self.submit['command'] = lambda: self.getOutput(labelList, tempList, objList, ranking, prizeList, gender, season, tournament, diff)

        self.back_button2 = self.createButton('back_button10', "Back",18,2, tab4)
        self.back_button2['command'] = lambda: self.enableTab(5,3)   
 
    #returns the input to the text field on the submit button
    def getOutput(self, labelList, tempList, objList, ranking, prizeList, gender, season, tournament, diff):
        for i in labelList:
            #append to templist and move on
            if i.get() != "":
                tempList.append(i.get())
                i.destroy()

            #empty the list and retry
            else:
                messagebox.showinfo("Input Error", "Incorrect number of entries or entries are not entered correctly for the round selected.\rPlease go back and try again.")
                self.back_button2['text'] = "Back"
                self.back_button2.grid(row=4, column=3)
                tempList = []
                #delete the labels
                for l in labelList:
                    l.destroy()
        
        self.submit.destroy()
        self.back_button2.destroy()

        #if the list was filed which means it was succcessful.
        if tempList != []:
            self.enableTab(4,3)
            self.preMatch(objList, ranking, prizeList, gender, season, tournament, diff, tempList, "")

    #if second season is loaded this function will get the seedings of and generate them for the round.
    def getSeedings(self, objList, ranking, prizeList, gender, currentRounds, season, tournament, diff):
        templist = []
        winners = []
        top = []
        bottom = []
        losers = []
        d = []
        c = []
        i = []

        d = self.readAllStats(tournament, objList)
        #split the list and then retrieve the top and lowest 16/8/4/2
        for g in d:
            dg = g.split(" ")
            #print(dg[0])
   
            if str(currentRounds) == str(dg[1]):
                if int(dg[2]) == 1 and int(dg[3]) != 1:
                    top.append(dg[0])
                else:
                    bottom.append(dg[0])

        #get the players the players that have no losess this round.
        for h in objList:
            if h.losess == 0:
                c.append(h.name)

        if len(c) == 32:
            currentRounds = 1
        elif len(c) == 16:
            currentRounds = 2
        elif(len(c) == 8):
            currentRounds = 3
        elif(len(c) == 4):
            currentRounds = 4
        elif(len(c) == 2):
            currentRounds = 5

        #check if top contains the player that made it to the top set and then append to winners from c 
        #the idea behind this is because there will be players that were never in the top or bottom in the lator rounds and we need
        #to append them to a list the doesnt matter if there not in either top or bottom lists.
        for i in c:
            if i in top:
                winners.append(i)
            elif i in bottom:
                losers.append(i)
            
        for l in c:
            if l not in winners and l not in losers:
                if currentRounds == 2:
                    if len(winners) < 8:
                            winners.append(l)
                    elif len(losers) < 8:
                            losers.append(l)
                elif currentRounds == 3:
                    if len(winners) < 4:
                            winners.append(l)
                    elif len(losers) < 4:
                            losers.append(l)
                elif currentRounds == 4:
                    if len(winners) < 2:
                            winners.append(l)
                    elif len(losers) < 2:
                            losers.append(l)
                elif currentRounds == 5:
                    if len(winners) < 1:
                            winners.append(l)
                    elif len(losers) < 1:
                            losers.append(l)
       # print(c)

        win = len(winners)
        lose = len(losers)

        try:
            #loop through and append the match ups and generate a random score
            for y in range(0, win):
                scoreA = randint(0,3)
                scoreB = randint(0,3)

                if scoreA  == 3 or scoreB == 3:
                    if scoreA != scoreB and (scoreA > scoreB or scoreA < scoreB):
                        templist.append(winners[y] + " " + str(scoreA) + " " + losers[y] + " " + str(scoreB))
                    else:
                        scoreB = randint(0,2)
                        scoreA = 3
                        templist.append(winners[y] + " " + str(scoreA) + " " + losers[y] + " " + str(scoreB))
                #to prevent failed loops
                else:
                    scoreA = randint(0,2)
                    scoreB = 3
                    templist.append(winners[y] + " " + str(scoreA) + " " + losers[y] + " " + str(scoreB))
        
        except IndexError as ind:
            messagebox.showerror("Error", "Redirecting back to the Options page.")
            templist = []
            self.enableTab(5,3)

        self.enableTab(5,3)
        self.preMatch(objList, ranking, prizeList, gender, season, tournament, diff, templist, "seed")

    #function performs the one of the above pre match settings regardless of mode.
    def preMatch(self, objList, ranking, prizeList, gender, season, tournament, diff, tempList, mode):
        currentRounds = 1
        b = StringVar()
        c = StringVar()
        d = StringVar()
        c.set("Winners: ")

        #take templists size to determine the current round.
        if(len(tempList) == 16):
            currentRounds = 1
        elif(len(tempList) == 8):
            currentRounds = 2
        elif(len(tempList) == 4):
            currentRounds = 3
        elif(len(tempList) == 2):
            currentRounds = 4
        elif(len(tempList) == 1):
            currentRounds = 5    

        b.set(" Round: " + str(currentRounds) + " Results: ")
                
        self.l1 = Label(tab3, textvariable=b)
        self.l1.grid(row=2,column=0, padx=(50,50), pady=(20,20))
                
        self.l2 = Label(tab3, textvariable=c)
        self.l2.grid(row=2, column=1, padx=(50,50))

        if season == "season1":
            self.match(objList, ranking, prizeList, gender, tempList, currentRounds, season, tournament, "")
            self.postMatch(objList, gender, currentRounds, season, tournament, diff, "")

        elif season == "season2":
            self.match(objList, ranking, prizeList, gender, tempList, currentRounds, season, tournament, "seed")
            self.postMatch(objList, gender, currentRounds, season, tournament, diff, "seed")

    #matches the players and scores and assigns a win and points
    def match(self, objList, ranking, prizeList, gender, tempList, currentRounds, season, tournament, mode):
        t2 = StringVar()
        t3 = StringVar()
        t2.set(" ")
        t3.set(" ")
        i = ""
        t1 = ""
        winner = ""
        loser = ""
        result = False
        bonus = 1
        winScore = 0
        loseScore = 0

        #test time execution:
        #start = timer()

        self.l4 = self.createTextField('l4', 20, 18, 3, 0, tab3)
        self.l5 = self.createTextField('l5', 20, 18, 3, 1, tab3)
    
        for l in tempList:
            
            t = l.split(" ")
            
            if(t[0] != ""):
                i += t[0] + " " + t[1] + " " + t[2] + " " + t[3]
                i += "\n"

                a = int(t[1])
                b = int(t[3])

                try:
                    if gender == "male":
                        resultList = self.getResult(a, b, t[0], t[2], gender)
                    elif gender == "female":
                        resultList = self.getResult(a, b, t[0], t[2], gender)
         
                    winner = resultList[0]
                    winScore = resultList[1]
                    bonus = resultList[2]
                    loser = resultList[3]
                    loseScore = resultList[4]


                    t1 += winner + "\n"
                    self.assignStats(objList, winner, loser, "win", 1, 0, bonus, int(winScore), int(currentRounds), tournament, season)
                    self.assignStats(objList, loser, winner, "lose", 0, 1, 0, int(loseScore), int(currentRounds), tournament, season)

                except IndexError as p:
                    messagebox.showerror("Matches", "Failed to provide score of player and whether or not player forfieted.\rReturning to the start of the matches")
                    tempList = []
                    self.match(objList, ranking, prizeList, gender, tempList, currentRounds, season, tournament)
      
        t2.set(i)
        t3.set(t1)
        self.l4.insert(INSERT, t2.get())
        self.l5.insert(INSERT, t3.get())

        self.assignPoints(objList, ranking, prizeList, bonus)
        
        #end = timer()
        #print(end - start)

    #to control the after match directions for the user
    def postMatch(self, objList, gender, currentRounds, season, tournament, diff, mode):
        fileName2 = "./Save Files/" + season + "-" + tournament + "-" + gender + ".csv"
        check = False

        self.enter_button5 = self.createButton('enter_button5', '',4,5,tab3)
        self.enter_button5.grid(padx=(20,20), pady=(20,20))
        self.enter_button6 = self.createButton('enter_button6', '',4,6,tab3)
        self.enter_button6.grid(padx=(20,20))
        self.back_button7 = self.createButton('back_button7', '',4,7,tab3)
        self.back_button7.grid(padx=(20,20))

        #if any of the rounds are less then 5 
        if self.rounds < 5:
            self.rounds += 1

        #aditional validation
        for m in objList:
            if m.wins >= 5:
                check = True

        #check the above is true or false. this is basic season 1 rounds.
        if check == False and mode == "":
            self.enter_button5['text'] = "Save and Exit"
            self.enter_button5['command'] = lambda: self.save_or_And_exit(gender, currentRounds, season, tournament, objList, "exit")
            
            self.enter_button6['text'] = "Save"
            self.enter_button6['command'] = lambda: self.save_or_And_exit(gender, currentRounds, season, tournament, objList, '')

            self.back_button7['text'] = "return to options"
            self.back_button7['command'] = lambda: self.enableTab(3,2)

        #season 1 and at round 5
        elif check == True and mode == "":
            self.l1.destroy()
            self.l2.destroy()

            messagebox.showinfo("Tournament Completion", "Success! writing save: " + fileName2)

            self.asignCashPrize(diff, tournament, objList, " ")
                        
            read.writeFile(fileName2, objList)
            read.writeStatSaveFile(gender, currentRounds, season, tournament, objList, ' ')

            self.enter_button5['text'] = "Tournament Statistics"
            self.enter_button5['command'] = lambda: self.statistics(objList, currentRounds, tournament)

            self.enter_button6['text'] = ""
            self.enter_button6['command'] = ""
            self.enter_button6.destroy()    

            self.back_button7['text'] = "exit to main menu"
            self.back_button7['command'] = lambda: self.startNew(objList, 3)

        #season 2 and and at round 5
        elif check == True and mode == "seed":
            self.l1.destroy()
            self.l2.destroy()
            self.l3.destroy()

            messagebox.showinfo("Tournament Completion", "Success! writing save: " + fileName2)

            self.asignCashPrize(diff, tournament, objList, "seed")
                       
            read.writeFile(fileName2, objList)
            read.writeStatSaveFile(gender, currentRounds, season, tournament, objList, ' ')

            self.enter_button5['text'] = "Tournament Statistics"
            self.enter_button5['command'] = lambda: self.statistics(objList, currentRounds, tournament)

            self.enter_button6['text'] = ""
            self.enter_button6['command'] = ""

            self.back_button7['text'] = "exit to main menu"
            self.back_button7['command'] = lambda: self.startNew(objList, 3)

        #season 2 pre round 5
        elif check == False and mode == "seed":
            self.enter_button5['text'] = "Save and Exit"
            self.enter_button5['command'] = lambda: self.save_or_And_exit(gender, currentRounds, season, tournament, objList, "exit")
            
            self.enter_button6['text'] = "Save"
            self.enter_button6['command'] = lambda: self.save_or_And_exit(gender, currentRounds, season, tournament, objList, '')

            self.back_button7['text'] = "return to options"
            self.back_button7['command'] = lambda: self.enableTab(3,2)


    #def the layout of the merge file tab
    def merge(self):
        v = StringVar()
        z =  StringVar()
        z.set("season1")
        v.set("male")

        self.enableTab(0,6)
        self.label6 = self.createLabel('labe6', "Merge the Tournamenet files to create a master season file containing\n Wins, losess,ranking points cash prizes and who played who:", 0,0, tab6)
        self.label6.grid(padx=(150,0), pady=(20,20))
        
        self.label7 = self.createLabel('label7', "Select Gender:", 1,0, tab6)
        self.label7.grid(pady=(20,20))
        self.r10 = self.createRadio('r10',"Male", v, "male", 1, 1, tab6)
        self.r11 = self.createRadio('r11',"Female", v, "female", 1, 2, tab6)

        self.label10 =  self.createLabel('label10', "Select season:",2,0, tab6)
        self.label10.grid(pady=(20,20))
        self.r12 = self.createRadio('r12', "Season 1", z, "season1", 2,1, tab6)
        self.r13 = self.createRadio('r13', "Season 2", z, "season2", 2,2, tab6)

        self.label8 = self.createLabel('label8', "Merge Tournamenet files:",3,0,tab6)
        self.label8.grid(pady=(20,20))
        self.button1 = self.createButton('button1', "Enter",3,1,tab6)
        self.button1['command'] = lambda: self.mergeFiles(z.get(), v.get(), "tournament")

        self.label9 = self.createLabel('label8', "Merge Tournamenet statistics files:",4,0,tab6)
        self.label9.grid(pady=(20,20))
        self.button2 = self.createButton('button1', "Enter", 4,1,tab6)
        self.button2['command'] = lambda: self.mergeFiles(z.get(), v.get(), "stat")

        self.back_button4 = self.createButton('back_button4', "Back",5,1,tab6)
        self.back_button4['command'] = lambda: self.enableTab(6,0)
        self.back_button4.grid(pady=(20,20))

    #defs the 2nd set of options for merging files.
    def mergeFiles(self, season, gender, mode):
        objList = []

        messagebox.showinfo("Selection", "select a file containing the 32 player Names e.g. MALE PLAYERS and is located within the tournament files folder")
        try:
            objList = read.readPlayerNames(gender)

            if len(objList) == 32:

                if mode == "tournament":
                    result = read.mergeFiles(season, gender, objList)
                
                else:
                    read.masterStatFile(season, gender, objList)

            elif len(objList) < 32 or len(objList) > 32:
               self.label7['text'] = "Incorrect player names please reload the file containing the player names in a csv file"
        
        except TypeError as p:
            messagebox.showerror("Error", "Please select player name file.")

        except PermissionError as f:
            messagebox.showerror("Error", "Please Close the file your trying to load and try again")

        except FileNotFoundError as d:
            messagebox.showerror("Error", "Please make sure that you have loaded or entered all the matches before trying to merge the files.")

        self.enableTab(6,0)

    #show the player statistics
    def statistics(self, objList, currentRounds, tournament):
        t5 = StringVar()
        t4 = StringVar()
        t6 = StringVar()
        t5.set(" ")
        t4.set(" ")
        t6.set(" ")
        x = ""
        y = ""
        z = ""
        counter = 0
        alist = []
        alist2 = []
        blist = []
        clist = []
        count = 0
        count2 = 0
        o = 0
        o2 = 0

        self.label9 = self.createLabel('label9',"Tournament Statistics:", 0, 0, tab7)
        self.label9.grid(pady=(20,20))
        self.label10 = self.createLabel('label10', "Winners:", 0,1,tab7)
        self.label11 = self.createLabel('label11', "Losers: ", 0,2,tab7)

        self.stats1 = self.createTextField('stats1', 60, 35, 3, 0, tab7)
        self.stats1.grid(padx=(5,5))
        self.stats2 = self.createTextField('stats2', 15, 35, 3, 1, tab7)
        self.stats2.grid(padx=(5,5))
        self.stats3 = self.createTextField('stats3', 15, 35, 3, 2, tab7)

        self.label12 = self.createLabel('label12', 'Key:\r\rRP = Temporary rank Points\r\rPlayed = Opponent of that round ', 6, 0, tab7)
        self.label12.grid(padx=(300,0), pady=(20,20))
        self.back_button = self.createButton('back_button',"Back",7,0,tab7)
        self.label12.grid(padx=(300,0))

        x += "player\tName Wins  RP\n"
        y += "Top Winners:\n"
        y += "Names  Wins \n"
        z += "Top Losers:\n"
        z += "Names  Loses \n"

        self.enableTab(2,7)
        
        for n in objList:
            counter += 1
            x += str(counter) + ":\t" + n.name + "  " + str(n.wins) + "\t   " + str(n.tempRankingPoints) + "\n"

            l = []

            if tournament == 'TAC1':
                for l in n.TAC1:
                    if(l[0]  == n.name):
                        x += "\t\t" + tournament + " Round " + str(l[6]) + ": Played: " + str(l[1]) + " Won: " + str(l[2]) + " Score: " + str(l[5]) + "\n"

            elif tournament == 'TAE21':
                for l in n.TAE21:
                    if(l[0]  == n.name):
                        x += "\t\t" + tournament + " Round " + str(l[6]) + ":  Played: " + str(l[1]) + " Won: " + str(l[2]) + " Score: " + str(l[5]) + "\n"   

            elif tournament == 'TAW11':
                for l in n.TAW11:
                    if(l[0]  == n.name):
                        x += "\t\t" + tournament + " Round " + str(l[6]) + ":  Played: " + str(l[1]) + " Won: " + str(l[2]) + " Score: " + str(l[5]) + "\n"  

            elif tournament == 'TBS2':
                for l in n.TBS2:
                    if(l[0]  == n.name):
                        x += "\t\t" + tournament + " Round " + str(l[6]) + ":  Played: " + str(l[1]) + " Won: " + str(l[2]) + " Score: " + str(l[5]) + "\n"  

            x += "---------------------------------------------------------\n"
        #winners list:
        for e in objList:
            if e.wins >= 1:
                count += 1
                alist.append(e.wins)

        blist = self.insertSort(alist)

        while o < count-1:
            for m in objList:
                if m.wins == blist[o]:
                    y += m.name + "    " + str(m.wins) + "\n"

                    if o < count-1:
                        o += 1
                    else:
                        break   
        #losers
        for g in objList:
            if g.losess >= 1:
                count2 += 1
                alist2.append(g.losess)

        clist = self.insertSort(alist2)

        while o2 < count2-1:
            for n in objList:
                if n.losess == clist[o2]:
                    z += n.name + "    " + str(n.losess) + "\n"

                    if o2 < count2-1:
                        o2 += 1
                    else:
                        break   
            
        if(currentRounds >= 5):
            self.back_button['command'] = lambda: self.enableTab(7,1)
        else:
            self.back_button['command'] = lambda: self.enableTab(7,2)

        t4.set(x)
        t5.set(y)    
        t6.set(z)        

        self.stats3.insert(INSERT, t6.get())
        self.stats2.insert(INSERT, t5.get())
        self.stats1.insert(INSERT, t4.get())
    
    #function for defining individual stats
    def getPlayerStats(self):
        v = StringVar()
        v.set("male")
        info = ""
        player = ""
        self.enableTab(0,8)
    
        self.label1 = self.createLabel('label1', 'Required Information:', 0,0, tab8)
        self.label1.grid(padx=(300,0), pady=(20,20))

        self.label7 = self.createLabel('label7', "Select Gender:", 1, 0, tab8)
        self.label7.grid(pady=(20,20))
        self.r12 = self.createRadio('r12',"Male", v, "male", 1, 1, tab8)
        self.r13 = self.createRadio('r1',"Female", v, "female", 1, 2, tab8)

        self.label8 = self.createLabel('label8', 'Enter Player Name:',3,0,tab8)
        self.e21 = self.createEntryField('e21', 4, tab8)
        self.e21.grid(row=3, column=1)

        self.label9 = self.createLabel('label9', '1.Enter individual tournament or season with player name\n 2. Or all and a player name to search Through a specified file\n3.Also can enter topAll for top winner and loser of the season\r but must leave the Enter player name empty', 5,0,tab8)
        self.label9.grid(pady=(20,20))
        self.e22 = self.createEntryField('e22', 6, tab8)
        self.e22.grid(row=5, column=1)

        self.label10 = self.createLabel('label9',"Tournament Statistics:", 9, 0, tab8)
        self.label10.grid(pady=(20,20))
        self.label11 = self.createLabel('label10', "Perctage of Wins:", 9,1,tab8)
        self.stats4 = self.createLabel('stats4', " ", 10, 0, tab8)
        
        self.stats5 = self.createLabel('stats5', '', 10, 1, tab8)
        self.stats6 = self.createLabel('stats6', '', 10,2, tab8)

        self.enter_button = self.createButton('enter_button', 'Enter',7,0,tab8)
        self.enter_button['command'] = lambda: self.playerStats(v.get(), str(self.e21.get()), str(self.e22.get()))
        self.enter_button.grid(pady=(20,20))
           
        self.back_button = self.createButton('back_button', 'Back',7,1,tab8)
        self.back_button['command'] = lambda: self.enableTab(8,0)
    
    #returns player stats
    def playerStats(self, gender, player, selection):
        objList = []
        y = StringVar()
        x = " "
        y.set(" ")
        wins = 0

        try:
            messagebox.showinfo("File Selection", "Please select a file containing the 32 male or female player names")
            objList = read.readPlayerNames(gender)

            messagebox.showinfo("Statistics File", "Please select a stat file to read in.")
            fileName = read.file()
            statFile = read.statFile(fileName)
            read.getStats(statFile, objList)
            
        except PermissionError as e:
            messagebox.showerror("Error", "File is open please shut the file and try again.")
            self.enableTab(0,8)

        except FileNotFoundError as p:
            messagebox.showerror("Error", "File is Not found Please try again.")
            self.enableTab(0,8)

        except IndexError as g:
            messagebox.showerror("Error", "Wrong File loaded or the file does not contain the correct statistics, Please try again.")
            self.enableTab(0,8)

        #get all of season1s tournament stats for the player
        if selection == "all":
            for m in objList:
                l = []

                if(m.name  == player):
                    for l in m.TAC1: 
                        if(l[0]  == player):
                            x +=  'TAC1' + " Round " + str(l[6]) + ": Played: " + str(l[1]) + " Won: " + str(l[2]) + " Score: " + str(l[5]) + "\n"
                            wins += int(l[2])

                    for l in m.TAE21:
                        if(l[0]  == player):
                            x += 'TAE21' + " Round " + str(l[6]) + ":  Played: " + str(l[1]) + " Won: " + str(l[2]) + " Score: " + str(l[5]) + "\n"
                            wins += int(l[2])  

                    for l in m.TAW11:
                        if(l[0]  == player):
                            x += 'TAW11' + " Round " + str(l[6]) + ":  Played: " + str(l[1]) + " Won: " + str(l[2]) + " Score: " + str(l[5]) + "\n"
                            wins += int(l[2])

                    for l in m.TBS2:
                        if(l[0]  == player):
                            x += 'TBS2' + " Round " + str(l[6]) + ":  Played: " + str(l[1]) + " Won: " + str(l[2]) + " Score: " + str(l[5]) + "\n"
                            wins += int(l[2])
            
            wins = int((wins / 20) * 100)

        #get the top winners and losers for the season.
        elif selection == "topAll" and player == "":
            j = []
            p = []
            n = []

            for m in objList:
                l = []
               
                wins = 0

                for l in m.TAC1: 
                    if l[0] == m.name:
                        wins += int(l[2])

                for l in m.TAE21:
                    if l[0] == m.name:
                        wins += int(l[2])  

                for l in m.TAW11:
                    if l[0] == m.name:
                        wins += int(l[2])

                for l in m.TBS2:
                    if l[0] == m.name:
                        wins += int(l[2])

                j.append(wins)
                p.append(m.name)
           
            maxValue = max(j)
            maxIndex = j.index(maxValue)
            minValue = min(j)
            minIndex = j.index(minValue)
            #print(n[maxIndex])

            x += "Top Winner and Top Loser: \n\n"
            x += "Player with most wins:     " + p[maxIndex] + " with Wins: " + str(j[maxIndex]) + " out of 20\n\n\n"
            x += "Player with the most loses:     " + p[minIndex] + " at wins: " + str(j[minIndex]) + "\nTherefore has the most loses at of 20"

        #get the specified players stats for 1 tournament
        else:
            for m in objList:
                l = []
                if selection == 'TAC1':
                    for l in m.TAC1: 
                        if(l[0]  == player):
                            x +=  'TAC1' + " Round " + str(l[6]) + ": Played: " + str(l[1]) + " Won: " + str(l[2]) + " Score: " + str(l[5]) + "\n"
                            wins += int(l[2])
                    break

                elif selection == 'TAE21':
                    for l in m.TAE21:
                        if(l[0]  == player):
                            x += 'TAE21' + " Round " + str(l[6]) + ":  Played: " + str(l[1]) + " Won: " + str(l[2]) + " Score: " + str(l[5]) + "\n"
                            wins += int(l[2])
                    break

                elif selection == 'TAW11':
                    for l in m.TAW11:
                        if(l[0]  == player):
                            x += 'TAW11' + " Round " + str(l[6]) + ":  Played: " + str(l[1]) + " Won: " + str(l[2]) + " Score: " + str(l[5]) + "\n"
                            wins += int(l[2])
                    break  

                elif selection == 'TBS2':
                    for l in m.TBS2:
                        if(l[0]  == player):
                            x += 'TBS2' + " Round " + str(l[6]) + ":  Played: " + str(l[1]) + " Won: " + str(l[2]) + " Score: " + str(l[5]) + "\n"
                            wins += int(l[2])
                    break
            
            wins = int((wins / 5) * 100)

        self.stats4['text'] = x
        
        #if the top player isint called.
        if player != "":
            y.set(str(wins) + "%")
            self.stats5['text'] = y.get()
        
    #function for the asignment of stats from a match
    def assignStats(self, objList, player, played, status, win, lose, bonus, score, currentRounds, tournament, season):
        roundInfo = []
        for n in objList:
            if n.name == player:
                if status == "win":
                    n.wins += 1 
                    n.bonusPoints = bonus
                else:
                    n.losess = 1

        #create a list then append to the list the stats, then append to the dictionarys from the player objects
        for x in objList:
            roundInfo = [player, played, win, lose, bonus, score, currentRounds, tournament, season]
            if x.name == player:
                if tournament == 'TAC1':
                    x.TAC1.append(roundInfo)
                elif tournament == 'TAE21':
                    x.TAE21.append(roundInfo)
                elif tournament == 'TAW11':
                    x.TAW11.append(roundInfo)
                elif tournament == 'TBS2':
                    x.TBS2.append(roundInfo)

    #reuse a read all statistics functoin from the objlist.
    def readAllStats(self, tournament, objList):
        d = []
        n = []
        m = []

        try:
            for n in objList:

                for m  in n.season[tournament]:
                    #name, round, win, lose, tournament
                    if n.name == m[0]:
                        d.append(m[0] + " " + str(m[6]) + " " + str(m[2]) + " " + str(m[3]))
            #print(d)
        except IndexError as p:
            messagebox.showerror("Erorr","Error with statistics file.")  
        return d

    #defines a matches result.
    def getResult(self, a, b, playerA, playerB, gender):
        resultList = []
        result = False
        bonus = 1
        winScore = 0
        loseScore = 0
        genscore = 0

        if gender == "male":
            genscore = 3
        else:
            genscore = 2

        #if injury
        if a != genscore and b != genscore:
            result = messagebox.askyesno("Injury?", "Is this entry an injury: " + playerA+ " " + str(a) + " " + playerB + " " + str(b))
                   
            if result == True:
                inputA = messagebox.askyesno("Question", "Was " + playerA + " injured?")
                if inputA:
                    loser = playerA
                    loseScore = a
                    winner = playerB
                    winScore = b
                else:
                    loser = playerB
                    loseScore = b
                    winner = playerA
                    winScore = a

            #else if the player isnt injured and this a mistake.
            elif result == False:
                answer = self.question(playerA, playerB)

                a = answer[0]
                b = answer[1]

                if a > b and a == genscore:
                    winner = playerA
                    winScore = a
                    loser = playerB
                    loseScore = b

                    if b == 0:
                        bonus = 2.5;
                    elif gender == "male" and b == 1:
                        bonus = 1.5;

                elif b > a and b == genscore:
                    loser = playerA
                    winner = playerB
                    winScore = b
                    loseScore = a

                    if a == 0:
                        bonus = 2.5;
                    elif gender == "male" and a == 1:
                        bonus = 1.5;
            
        #if a wins
        elif a > b and a == genscore:
            winner = playerA
            winScore = a
            loser = playerB
            loseScore = b

            if b == 0:
                bonus = 2.5;
            elif gender == "male" and b == 1:
                bonus = 1.5;

        #if b wins
        elif b > a and b == genscore:
            loser = playerA
            loseScore = a
            winner = playerB
            winScore = b

            if a == 0:
                bonus = 2.5;
            elif gender == "male" and a == 1:
                bonus = 1.5;

        #if draw
        elif b == a or a == b:
            messagebox.showerror("Matches", playerA + " and " + playerB + " resulted in a draw.")
            answer = self.question(playerA, playerB)

            a = answer[0]
            b = answer[1]

            if a > b and a == genscore:
                winner = playerA
                winScore = a
                loser = playerB
                loseScore = b

                if b == 0:
                    bonus = 2.5;
                elif gender == "male" and b == 1:
                    bonus = 1.5;

            elif b > a and b == genscore:
                winner = playerB
                winScore = b
                loser = playerA
                loseScore = a

                if a == 0:
                    bonus = 2.5;
                elif gender == "male" and a == 1:
                    bonus = 1.5;

        resultList.append(winner)
        resultList.append(winScore)
        resultList.append(bonus)
        resultList.append(loser)
        resultList.append(loseScore)
     
        return resultList

    #assign rank points and loops through each set of postions to set athe players ranking points and positions
    def assignPoints(self, objList, ranking, prizeMoneyList, bonus):
        for m in objList:
            if m.wins == 0:
                m.tempCashPrize = 0

            elif m.wins == 1 and m.losess == 0:
                m.tempRankingPoints = m.tempRankingPoints + (int(ranking[9]) * m.bonusPoints)
        
            elif m.wins == 2 and m.losess == 0:
                m.tempRankingPoints = m.tempRankingPoints + (int(ranking[5]) * m.bonusPoints)

            elif m.wins == 3 and m.losess == 0:
                m.tempRankingPoints = m.tempRankingPoints + (int(ranking[2]) * m.bonusPoints)
                
            elif m.wins == 4 and m.losess == 0:
                m.tempRankingPoints = m.tempRankingPoints + (int(ranking[1]) * m.bonusPoints)

            elif m.wins == 5 and m.losess == 0:
                m.tempRankingPoints = m.tempRankingPoints + (int(ranking[0]) * m.bonusPoints)

        for p in objList:
            if p.wins == 2:
                p.tempCashPrize = int(prizeMoneyList[4])

            elif p.wins == 3:
                p.tempCashPrize = int(prizeMoneyList[2])
                
            elif p.wins == 4:
                p.tempCashPrize = int(prizeMoneyList[1])

            elif p.wins == 5:
                p.tempCashPrize = int(prizeMoneyList[0])

    #returns cast prize
    def asignCashPrize(self, diff, tournament, objList, mode):
        d = []
        check = False
        maxWins = 0
        oldwins = 0

        #season 2 and beyone
        if mode == "seed":
            d = self.readAllStats(tournament, objList)

            #split the list and then retrieve the top and lowest 16/8/4/2
            for n in objList:
                maxWins = n.wins
                oldwins = 0
                for g in d:
                    dg = g.split(" ")
           
                    if n.name == str(dg[0]):
                        oldwins += int(dg[2])
                        #print(oldwins) 
                #print(n.name + " " + str(oldwins) + " " + str(maxWins))

                #need to loop through the tournament list and find out where they came
                #then compare this to there current wins and then give or not give diff bonus
                if(maxWins > oldwins):
                    check = True
                else:
                    check = False                       

                if n.tempCashPrize > 0:
                    n.prize = n.tempCashPrize
                    n.tempCashPrize = 0

                if n.tempRankingPoints > 0:
                    if check:
                        number = n.tempRankingPoints * diff
                        n.totalRank = number
                    else:
                        number = n.tempRankingPoints
                        n.totalRank = number
        #season 1
        else:
            for m in objList:
                if m.tempCashPrize > 0:
                    m.prize = m.tempCashPrize
                    m.tempCashPrize = 0

                if m.tempRankingPoints > 0:
                    number = m.tempRankingPoints * diff
                    m.totalRank = number

     #defines mid round match questions
    def question(self, playerA, playerB):
        resultList = []

        scoreA = simpledialog.askstring("ScoreA", 'What is ' + playerA + " score?")
        scoreB = simpledialog.askstring("ScoreB", 'What is ' + playerB + " score?")

        a = int(scoreA)
        b = int(scoreB)

        if((a <= 3 and a >= 0) and (b <= 3 and b >= 0)):
            resultList.append(a)
            resultList.append(b)
        else:
            messagebox.showerror("Error", "Incorrect player score entries.\nPlease re-enter them.")
            self.question(playerA, playerB)
        return resultList

    #deine insert sort function
    def insertSort(self, alist):
        for passesLeft in range(len(alist)-1, 0, -1):
            for i in range(passesLeft):
                if alist[i] < alist[i + 1]:
                    alist[i], alist[i + 1] = alist[i + 1], alist[i]
        return alist

    #save and exit function that can be used for auto save or pass "exit" to save and quit
    def save_or_And_exit(self, gender, currentRounds, season, tournament, objList, exit):
        self.enter_button5.destroy()
        self.enter_button6.destroy()
        try:
            fileName = read.writeSaveFile(gender, currentRounds, season, tournament, objList)
            read.writeStatSaveFile(gender, currentRounds, season, tournament, objList, "save")
            messagebox.showinfo("Saving", "save Successful! writing save: " + fileName)
            
            if(exit == "exit"):
                root.quit()
        except PermissionError as f:
            messagebox.showinfo("Saving", "Error Failed to save, please shut the file and try again.")

    #delete current info and return to main menu
    def startNew(self, objList, tab):
        objList = []
        if tab == 3:
            self.enter_button5.destroy()
            self.enter_button6.destroy()
            self.back_button7.destroy()
            self.submitButton.destroy()
            self.enter_button3.destroy()
            self.stat_button1.destroy()
        self.enableTab(tab,0)

    #create entry fields
    def createEntryField(self, entryName, pos, tab):
        self.entryName = Entry(tab)
        self.entryName.grid(row=pos, column=0)
        return self.entryName
    
    #shortens button creation dowbn
    def createButton(self, buttonName, bText, bRow, bColumn, tab):
        self.buttonName = Button(tab, text=bText, cursor="hand2")
        self.buttonName.grid(row=bRow, column=bColumn)
        return self.buttonName

    #shortens label creation dowbn
    def createLabel(self, labelName, lText, lRow, lColumn, tab):
        self.labelName = Label(tab, text=lText)
        self.labelName.grid(row=lRow, column=lColumn)
        return self.labelName

    #shortens radio button creation down.
    def createRadio(self, name, rText, rVariable, rValue, rRow, rColumn, tab):
        self.name = Radiobutton(tab, text=rText, variable=rVariable, value=rValue, cursor="hand2")
        self.name.grid(row=rRow, column=rColumn)
        return self.name

    #shortens text field creation down
    def createTextField(self, name, tWidth, tHeight, tRow, tColumn, tab):
        self.name = Text(tab, width=tWidth, height=tHeight)
        self.name.grid(row=tRow, column=tColumn)
        return self.name

    #function to enable tabs and then change to it.
    def enableTab(self, oldtab, newtab):
        tabs = notebook.tabs()
        notebook.tab(newtab, state='normal')
        notebook.tab(newtab).update()
        notebook.tab(oldtab).update()
        #this will select the tab new tab
        notebook.select(newtab)
        notebook.tab(oldtab, state='disabled')

#call the system
root = Tk()
root.title('Tenis Tournament System')

#a menu bar
menubar = Menu(root)
filemenu = Menu(menubar, tearoff=0, cursor="hand2")
filemenu.add_command(label="Exit", command= lambda: root.quit())
menubar.add_cascade(label="File", menu=filemenu)

#heart of the tabbed gui.
notebook = ttk.Notebook(root, width=800, height=800)
notebook.pack(fill='both', expand="yes")

#Home tab
tab0 = ttk.Frame()
#Choice Input Tab
tab1 = ttk.Frame()
#Data Entry file Tab
tab2 = ttk.Frame()
#Matches file Tab
tab3 = ttk.Frame()
#manualEntry Tab
tab4 = ttk.Frame()
#read file entry Tab
tab5 = ttk.Frame()
#Merge results tab
tab6 = ttk.Frame()
#tournament statistics tab
tab7 = ttk.Frame()
#player statistics tab
tab8 = ttk.Frame()

#add the tabs to the notebook.
notebook.add(tab0, text='Home', state='normal')
notebook.add(tab1, text='Choice Input', state='disabled')
notebook.add(tab2, text='Data Entry', state='disabled')
notebook.add(tab3, text='Results', state='disabled')
notebook.add(tab4, text='Manual Entry', state='disabled')
notebook.add(tab5, text='ReadFile Entry', state='disabled')
notebook.add(tab6, text='Merge Results', state='disabled')
notebook.add(tab7, text='Tournament Statistics', state='disabled')
notebook.add(tab8, text='Player Statistics', state='disabled')

#configure the app and run.
root.config(menu=menubar)
myGui = App(root)

root.mainloop()
root.quit()