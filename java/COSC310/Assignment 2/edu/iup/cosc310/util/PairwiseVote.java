package edu.iup.cosc310.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * @author David Smith
 * 
 * Utility class to compute the pairwise winner of elections
 */
public class PairwiseVote {
        /**
         * Get the placement order of a candidate in a rank ordered list of candidates
         *
         * @param votersVotes an array of rank ordered candidates.  First has the highest
         *                    rank.
         */
	public static int getPlacement(int candidate, int[] votersVotes ) {
		for (int placement = 0; placement < votersVotes.length; placement++) {
			if (candidate == votersVotes[placement]) {
				return placement;
			}
		}
		return votersVotes.length + 1;
	}

	/**
	 * Get the candidate winner from a set of rank ordered ballots
         *
         * @param votes - a two dimensional array, first dimension is the voter, second dimension
         *                is the rank order ballot of candidates for the given voter
	 */
	public static int getPairwiseWinner(int[][] votes) {
		int noVoters = votes.length;
		
		if (noVoters == 0) {
			return -1;
		}
		
		int noCandidates = votes[0].length;

		if (noCandidates == 0) {
			return -1;
		}

		// Compare every pair of candidates
		for (int candidate1 = 0; candidate1 < noCandidates; candidate1++) {
			int noTimesCandidate1Wins = 0;
			for (int candidate2 = 0; candidate2 < noCandidates; candidate2++) {
				// Consider a candidate compared with themselves to be a winner
				if (candidate1 == candidate2) {
					noTimesCandidate1Wins++;
					continue;
				}
				
				// Determine count the ballots for each candidate
				int candidate1votes = 0;
				int candidate2votes = 0;
				for (int voter = 0; voter < noVoters; voter++) {
					int placement1 = getPlacement(candidate1, votes[voter]); 
					int placement2 = getPlacement(candidate2, votes[voter]); 
					if (placement1 < placement2) {
						candidate1votes++;;
					} else  {
						candidate2votes++;;						
					}
				}
				
				// determine if candidate1 is the winner if so increment the number of wins
				if (candidate1votes > candidate2votes) {
					noTimesCandidate1Wins++;
				}
			}
			
			// Determine if candidate 1 wins all
			if (noTimesCandidate1Wins == noCandidates) {
				return candidate1;
			}
		}

		// No winner
		return -1;
	}

	static int electionNo = 0;

        /**
         * Main - reads several test elections using the text file votes.txt. Each election
         * begins with two number, the number of voters and the number of candidates, all followed
         * by the ballots of each voter.
         */
	public static void main(String[] args) throws FileNotFoundException {
		int noVoters;
		int noCandidates;
		
		Scanner in = new Scanner(new FileInputStream("votes.txt"));
		
		// read ballots for each election
		for(;;) {
			noVoters = in.nextInt();
			noCandidates = in.nextInt();
			
			if (noVoters == 0 && noCandidates == 0) {
				break;
			}
			
			final int[][] votes = new int[noVoters][noCandidates];
			
                        // Read the ballots
			for (int i = 0; i < noVoters; i++) {
				for (int j = 0; j < noCandidates; j++) {
					votes[i][j] = in.nextInt();
				}
			}
			
			new TimeExec(new Runnable() {
				public void run() {
					int winner = getPairwiseWinner(votes);
					if (winner >= 0) {
						System.out.printf("Winner of election %d is candidate %d\n", electionNo, winner); 
					} else {
						System.out.printf("No winner for election %d\n", electionNo);
					}
				}
			}, "Election " + ++electionNo, System.out).start();		
		}
	}
}