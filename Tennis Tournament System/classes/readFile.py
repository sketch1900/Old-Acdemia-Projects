#time effiency
#from timeit import default_timer as timer
from classes import player
from tkinter import filedialog, messagebox
from tkinter import *
import os
import re
import json
import csv
#import tracemalloc

#class containing most of the read file function calls
class ReadFile:

	def __init__(self):
		print("")

	#read player file(csv) python
	def readPlayerNames(self, gender):
		choice = ""
		name = ""
		objList = []
		mbjList = []

		## get relative path for the project	
		root = Tk()
		root.filename =  filedialog.askopenfilename(initialdir = "./",title = "Select file",filetypes = (("csv files","*.csv"),("all files","*.*")))

		#gets name from window
		if root.filename !=  "":
			name = os.path.relpath(root.filename)

		root.withdraw()

		if name != "" and ".csv" in name:
			choice = name
					
			#open file listed
			with open(choice, "r") as f:
				for line2 in f:
					objList.append(line2.split())

			for i in objList:
				mName = ''.join(i)
				if gender == "male":
					mbjList.append(player.MalePlayer(mName))
			
				elif gender == "female":
					mbjList.append(player.FemalePlayer(mName))

			return mbjList
		
	# read files for the matches
	def file(self):
		name = ""

		root = Tk()
		root.filename =  filedialog.askopenfilename(initialdir = "./",title = "Select file",
			filetypes = (("csv files","*.csv"),("all files","*.*")))
		
		if root.filename !=  "":
			name = os.path.relpath(root.filename)
	
		root.withdraw()

		if name != "" and ".csv":
			return name
		else:
			return ""

	# read the stat files by using the name of the save file to find it. for the matches
	def statFile(self,fileName):
		statFile = ""
		l = []
		l = fileName.split(" ")

		if "male" in l[0] or "male" in l[1]:
			if 'Master' in l[2]:
				statFile = l[0] + " " + l[1] + " " + l[2] + " stat.csv"

			elif 'Round' in l[3] or 'Round' in l[4]:
				statFile = "statistics\\male " + l[2] + " " + l[3] + " " + l[4] + " " + l[5] + " stat.csv"

			else:
				statFile =  "statistics\\male " + l[1] + " " + l[2] + " Final stat.csv"
				
		elif "female" in l[0] or "female" in l[1]:
			if 'Master' in l[2]:
				statFile = l[0] + " " + l[1] + " " + l[2] + " stat.csv"

			elif 'Round' in l[3] or 'Round' in l[4]:
				statFile = "statistics\\female " + l[2] + " " + l[3] + " " + l[4] + " " + l[5] + " stat.csv"
			else:
				statFile =  "statistics\\female " + l[1] + " " + l[2] + " " +  " Final stat.csv"
		else:
			statFile = " "

		return statFile	

	#returns the stat file name
	def getRoundNumber(self, season, fileName):
		roundList = []

		with open(fileName, "r") as f:
			for line2 in f:
				string = line2.split(',')
				roundList.append(string[0])
	
		for i in range(0, len(roundList)):
			if season + "__" in roundList[i]:
				result = roundList[i].split('__')
				res = result[1].split('\n')
				return int(res[0])
			else: 
				return 0

	#returns the scores	
	def getScores(self, fileName, objList):
		with open(fileName, "r") as f:
			next(f)
			for line2 in f:
				string = line2.split(',')
					
				for m in objList:
					if m.name == string[0]:
						m.tempRankingPoints = float(int(string[1]))
						m.wins = int(string[2])
						m.losess = int(string[3])
								
	#return the stats of players from the stat save files.
	def getStats(self, fileName, objList):
		with open(fileName, "r") as f:
			for line2 in f:
				string = line2.split(',')

				for m in objList:
					if m.name == string[0]:
						#create a list then append to the list the stats, then append to the dictionarys from the player objects
						roundInfo = [string[0], string[1], string[2], string[3], string[4], string[5], string[6], string[7], string[8]]
						if string[7] == 'TAC1':
							m.TAC1.append(roundInfo)
						elif string[7] == 'TAE21':
							m.TAE21.append(roundInfo)
						elif string[7] == 'TAW11':
							m.TAW11.append(roundInfo)
						elif string[7] == 'TBS2':
							m.TBS2.append(roundInfo)

	#defines the ranking points
	def readRankingPoints(self):
		alist = []
		ranks = []
		
		with open("./tornament files/RANKING POINTS.dat", "r") as s:
			for line2 in s:
				line2.split(" ")
				alist = re.split(r"(\s+)",line2)
				if "Points" not in alist[2]:
					ranks.append(int(alist[2]))
		return ranks

	#reads in prize money file
	def readPrize(self):
		with open("./tornament files/PRIZE MONEY.json", "r") as r:
			a = json.load(r) 
		return a

	#assigns prize money based on the tournement
	def getPrize(self, json, tournement):
		prize = []
		number = 0
		c = ""

		if tournement == json["Prize Money ($)"][0]["tournement"]:
			number = 0
		elif tournement == json["Prize Money ($)"][1]["tournement"]:
			number = 1
		elif tournement == json["Prize Money ($)"][2]["tournement"]:
			number = 2
		elif tournement == json["Prize Money ($)"][3]["tournement"]:
			number = 3
			
		#loops through 8 times and takes the values	and appends to moneyList based on tournment name in tournementList
		for x in range(1, 9):
			c = str(x)
			prize.append(json["Prize Money ($)"][number][c])
		return prize	

	#defines the current tournement
	def readTournement(self):
		tournement = []
		with open("./tornament files/PRIZE MONEY.json", "r") as t:
		
			a = json.load(t) 

		for i in range(0, 4):
			tournement.append(a["Prize Money ($)"][i]["tournement"])
		return tournement

	#returns the difficulty
	def getTournamentDiff(self, name, tournament):
		difficulty = ""

		if name == tournament[0]:
			difficulty = 2.7
		elif name == tournament[1]:
			difficulty = 2.3
		elif name == tournament[2]:
			difficulty = 3.1
		elif name == tournament[3]:
			difficulty = 3.25
		return difficulty

	#quicksort function
	def quicksort(self, array):
		if len(array) < 2:
			return array

		else:
			pivot= array[0]
			less = [i for i in array[1:] if i <= pivot]
			greater = [i for i in array[1:] if i > pivot]

			return self.quicksort(less) + [pivot] + self.quicksort(greater)

	#combines all 4 tournament winners files into 1 leader board based on cash prize
	def mergeFiles(self, season, gender, objList):
		name = ""
		outputList = []
		alist = []
		count = 0
		o = 0
		fileName = season + " " + gender + " Leader Board.csv"

		for i in range(0, 4):
			messagebox.showinfo("selection", "select a Tournamenet save file e.g. season1-TAC1-male, located within the Save Files folder")
			root = Tk()
			root.filename =  filedialog.askopenfilename(initialdir = "./",title = "Select file",filetypes = (("csv files","*.csv"),("all files","*.*")))

			if root.filename !=  "":
				name = os.path.relpath(root.filename)

			root.withdraw()

			if name != "":
				if ".csv" in name or ".dat" in name or ".txt" in name:
					with open(name, "r") as f:
						next(f)

						for line2 in f:
							string = line2.strip().split(',')
							for m in objList:
								if m.name == string[0]:
									m.prize += int(string[2])
									m.totalRank += float(string[1])
			elif name == "":
				return ""
		# once all the points are added to the files merge them here.
		outputList.append("Names,Rank,Prize")

		for e in objList:
			if e.prize >= 0:
				count += 1
				alist.append(e.prize)

		for passesLeft in range(len(alist)-1, 0, -1):
			for i in range(passesLeft):
				if alist[i] < alist[i + 1]:
					alist[i], alist[i + 1] = alist[i + 1], alist[i]

		while o < count-1:
			for m in objList:
				if m.prize == alist[o] and m.totalRank > 0:
					outputList.append(m.name + "," + str(int(m.totalRank)) + "," + str(m.prize))
					if o < count-1:
						o += 1
					else:
						break

		with open(fileName, "w", newline='') as f:
			writer = csv.writer(f, delimiter=' ')
			for item in outputList:
				writer.writerow([item])

		messagebox.showinfo("Success", "Successfully merged the files.")

	#quick sort then writes to file using the sort as decending order *the save files
	def writeSaveFile(self, gender, rounds, season, tournament, playerList):
		#test time execution:
		#start = timer()
		#tracemalloc.start()

		outputList = []
		inputList = []
		alist = []
		file_name = "./Save Files/" + gender + " " + season + " " + tournament + " Round " + str(rounds) + " save File.csv"
		count = 0
		o = 0

		outputList.append((tournament + "__" + str(rounds) + ",RankPoints,Wins,losess," ))

		for e in playerList:
			count += 1
			alist.append(e.tempRankingPoints)

		inputList = self.quicksort(alist)	

		while o < count-1:
			for m in playerList:
				if m.tempRankingPoints == inputList[o]:
					outputList.append(m.name + "," +str(int(m.tempRankingPoints)) + "," + str(m.wins) + "," + str(m.losess) + "," )
					if o < count-1:
						o += 1
					else:
						break

		with open(file_name, "w", newline='') as f:
			writer = csv.writer(f, delimiter=' ')
			for item in outputList:
				writer.writerow([item])
		
		#time test
		#end = timer()
		#print(end - start)
		#print("Current: %d, Peak %d" % tracemalloc.get_traced_memory())
		return file_name

	#insertion sort then writes to file using the sort as decending order
	def writeFile(self, fileName, playerList):
		outputList = []
		alist = []
		count = 0
		o = 0

		outputList.append("Names,Rank,Prize")

		for e in playerList:
			if e.prize >= 0 and e.wins >= 1:
				count += 1
				alist.append(e.prize)

		for passesLeft in range(len(alist)-1, 0, -1):
			for i in range(passesLeft):
				if alist[i] < alist[i + 1]:
					alist[i], alist[i + 1] = alist[i + 1], alist[i]

		while o < count-1:
			for m in playerList:
				if m.prize == alist[o] and m.totalRank > 0 and m.wins >= 1:
					outputList.append(m.name + "," + str(int(m.totalRank)) + "," + str(m.prize))

					if o < count-1:
						o += 1
					else:
						break	

		with open(fileName, "w", newline='') as f:
			writer = csv.writer(f, delimiter=' ')
			for item in outputList:
				writer.writerow([item])

	#writes the stat file 
	def writeStatSaveFile(self, gender, rounds, season, tournament, playerList, mode):
		l = []
		x = []
		r = []
		counter = 0

		if mode == "save":
			fileName = "./statistics/" + gender + " " + season + " " + tournament + " Round " + str(rounds) + " stat.csv"
		else:
			fileName = "./statistics/" + gender + " " + season + " " + tournament + " Final stat.csv"
			
		#[player, played, win, lose, bonus, score, currentRounds, tournament, season]
		for r in playerList:
			if tournament == 'TAC1':
				for l in r.TAC1:
					if(l[0]  == r.name):
						x.append(l[0] + "," + l[1] + "," + str(l[2]) + "," + str(l[3]) + "," + str(l[4]) + "," + str(l[5]) + "," + str(l[6]) + "," + l[7] + "," + l[8] + ",")
			elif tournament == 'TAE21':
				for l in r.TAE21:
					if(l[0]  == r.name):
						x.append(l[0] + "," + l[1] + "," + str(l[2]) + "," + str(l[3]) + "," + str(l[4]) + "," + str(l[5]) + "," + str(l[6]) + "," + l[7] + "," + l[8] + ",") 

			elif tournament == 'TAW11':
				for l in r.TAW11:
					if(l[0]  == r.name):
						x.append(l[0] + "," + l[1] + "," + str(l[2]) + "," + str(l[3]) + "," + str(l[4]) + "," + str(l[5]) + "," + str(l[6]) + "," + l[7] + "," + l[8] + ",")   

			elif tournament == 'TBS2':
				for l in r.TBS2:
					if(l[0]  == r.name):
						x.append(l[0] + "," + l[1] + "," + str(l[2]) + "," + str(l[3]) + "," + str(l[4]) + "," + str(l[5]) + "," + str(l[6]) + "," + l[7] + "," + l[8] + ",") 

		with open(fileName, "w", newline='\n') as g:
			writer = csv.writer(g, delimiter=' ')
			for item in x:
				writer.writerow([item])

	#writes the stat file after merging them all.
	def masterStatFile(self, season, gender, playerList):
		fileName = gender + " " + season + " Master stat.csv"
		count = 0
		o = 0
		counter = 0
		outputList = []
		alist = []
		l = []
		x = []
		r = []
		y = []
		
		for i in range(0, 4):
			messagebox.showinfo("selection", "select a Tournamenet Final stat file to merge located within the statistics folder")
			root = Tk()
			root.filename =  filedialog.askopenfilename(initialdir = "./",title = "Select file",filetypes = (("csv files","*.csv"),("all files","*.*")))

			if root.filename !=  "":
				name = os.path.relpath(root.filename)

			root.withdraw()

			if name != "":
				if ".csv" in name or ".dat" in name or ".txt" in name:
					with open(name, "r") as f:

						for line2 in f:
							s = line2.strip().split(',')
							
							for x in playerList:
								if x.name == s[0]:
									#roundInfo = [player, played, win, lose, bonus, score, currentRounds, tournament, season]
									roundInfo = [s[0], s[1], s[2], s[3], s[4], s[5], s[6], s[7], s[8]]

									if s[7] == 'TAC1':
										x.TAC1.append(roundInfo)
									elif  s[7] == 'TAE21':
										x.TAE21.append(roundInfo)
									elif  s[7] == 'TAW11':
										x.TAW11.append(roundInfo)
									elif  s[7] == 'TBS2':
										x.TBS2.append(roundInfo)
			elif name == "":
				return ""
		y = []
		#merge them into one file since we dont care about a specific tournament just append them loop by loop to each player.
		for r in playerList:
			for l in r.TAC1:
				if(l[0]  == r.name):
					y.append(l[0] + "," + l[1] + "," + str(l[2]) + "," + str(l[3]) + "," + str(l[4]) + "," + str(l[5]) + "," + str(l[6]) + "," + l[7] + "," + l[8] + ",")
			for l in r.TAE21:
				if(l[0]  == r.name):
					y.append(l[0] + "," + l[1] + "," + str(l[2]) + "," + str(l[3]) + "," + str(l[4]) + "," + str(l[5]) + "," + str(l[6]) + "," + l[7] + "," + l[8] + ",") 
			for l in r.TAW11:
				if(l[0]  == r.name):
					y.append(l[0] + "," + l[1] + "," + str(l[2]) + "," + str(l[3]) + "," + str(l[4]) + "," + str(l[5]) + "," + str(l[6]) + "," + l[7] + "," + l[8] + ",") 
			for l in r.TBS2:
				if(l[0]  == r.name):
					y.append(l[0] + "," + l[1] + "," + str(l[2]) + "," + str(l[3]) + "," + str(l[4]) + "," + str(l[5]) + "," + str(l[6]) + "," + l[7] + "," + l[8] + ",")

		with open(fileName, "w", newline='\n') as g:
			writer = csv.writer(g, delimiter=' ')
			for item in y:
				writer.writerow([item])
		messagebox.showinfo("Completion","Merge Complete!")