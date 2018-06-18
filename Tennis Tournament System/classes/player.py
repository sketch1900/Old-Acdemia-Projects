#the lists below will contain the stats of the players
# e.g. round1 = ['MP01', MP11',1,0,3,2.5,1,TAC1,season1] where [0] = The player, [1] = who they played. [2] = win, [3]= lose, [4] = score, [5] = bonus, [6] = currentRound, [7] = current Torunament, [8] = current season,

#male player objects
class MalePlayer:
	name = ""
	tempCashPrize = 0
	tempRankingPoints = 0
	totalRank = 0
	prize = 0
	wins = 0
	losess = 0
	bonusPoints = 0
	TAC1 = []
	TAE21 = []
	TAW11 = []
	TBS2 = []
	season = {'TAC1':TAC1,'TAE21':TAE21,'TAW11':TAW11,'TBS2':TBS2,}

	def __init__(self, name):
		self.name = name

#female player objects
class FemalePlayer:
	name = ""
	tempCashPrize = 0
	tempRankingPoints = 0
	totalRank = 0
	prize = 0
	wins = 0
	losess = 0
	bonusPoints = 0
	TAC1 = []
	TAE21 = []
	TAW11 = []
	TBS2 = []
	season = {'TAC1':TAC1,'TAE21':TAE21,'TAW11':TAW11,'TBS2':TBS2,}

	def __init__(self, name):
		self.name = name