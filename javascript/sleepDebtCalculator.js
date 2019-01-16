/* 
	Program to calculate sleep debt, whatever that is.
  Codecademy assigment
*/

/* 
	Get the hours slept in a given week. 
  Values are hard coded for each day as this program is pretty basic 
  Wait did somebody say "for each" 
  Oh man I just triggered my Java PTSD
  What even is an iterator anyway
  Whatever on to the code
*/

const getSleepHours = day => {
  day = day.toLowerCase()
  switch (day) {
    case 'monday':
      return 7
      break
    case 'tuesday':
      return 8
      break
    case 'wednesday':
      return 9
      break
    case 'thursday':
      return 6
      break
    case 'friday':
      return 13
      break
    case 'saturday':
      return 4
      break
    case 'sunday':
      return 8
      break
    default:
      console.log("That's not a valid day, silly!")
      break
  }
}

// get the hours you slept in a given week.
// basically calls getSleepHours for each day of week.
const getActualSleepHours = nothing => {
  let totalHours = 0
  totalHours += getSleepHours('monday')
  totalHours += getSleepHours('tuesday')
  totalHours += getSleepHours('wednesday')
  totalHours += getSleepHours('thursday')
  totalHours += getSleepHours('friday')
  totalHours += getSleepHours('saturday')
  totalHours += getSleepHours('sunday')
  return totalHours
}

const getIdealSleepHours = nothing => {
  let idealHours = 8
 	return idealHours*7
}

const calculateSleepDebt = nothing => {
  let actualSleep = getActualSleepHours()
  let idealSleep = getIdealSleepHours()
  
  if (actualSleep == idealSleep) {
    console.log('Wow! You got the perfect sleep! Good for you.')
  } else if (actualSleep > idealSleep) {
    console.log('Hey, you more sleep than you needed! Thats... good?')
    let extra = actualSleep - idealSleep
    console.log(`${extra} extra hours never killed anyone. Or at least you would hope...`)
  } else {
    console.log('You need some more sleep my dude.')
    let needed = idealSleep - actualSleep
    console.log(`Namely, ${needed} extra hours per week.`)
  }
}

calculateSleepDebt()
