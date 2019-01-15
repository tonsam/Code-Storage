const getUserChoice = userInput => {
  userInput.toLowerCase()
  if (userInput != 'rock' && userInput != 'paper' && userInput != 'scissors') {
    console.log('Invalid Input')} else {
      return userInput
    }
}

const getComputerChoice = computerInput => {
  let choice = Math.floor((Math.random() * 3))
	switch (choice) {
    case 0:
      return 'rock'
      break;
    case 1:
      return 'paper'
      break;
    case 2:
      return 'scissors'
      break;
  }
}

const determineWinner = (userChoice, computerChoice) => {
  if (computerChoice === 'rock') {
    switch (userChoice) {
      case 'rock':
        return 'tie'
        break
      case 'paper':
        return 'win'
        break
      case 'scissors':
        return 'lose'
        break
    }
  } else if (computerChoice === 'paper') {
  	switch (userChoice) {
      case 'paper':
        return 'tie'
        break
      case 'scissors':
        return 'win'
        break
      case 'rock':
        return 'lose'
        break
    }
  } else {
    switch (userChoice) {
      case 'scissors':
        return 'tie'
        break
      case 'rock':
        return 'win'
        break
      case 'paper':
        return 'lose'
        break
    }
  }
}

const playGame = (choice) => {
  let humanChoice = getUserChoice(choice)
  let computerChoice = getComputerChoice()
  let result = determineWinner(humanChoice, computerChoice)
  
  console.log(`You chose ${humanChoice}. The AI chose ${computerChoice}.`)
  
  switch (result) {
    case 'tie':
      console.log('It\'s a tie. Wow. That was anticlimactic.')
      break
    case 'win':
      console.log('You beat the AI. Humanity is saved.')
      break
    case 'lose':
      console.log('You have been terminated.')
      break
  }
  
}

// pick rock, paper, or scissors here
playGame('scissors')