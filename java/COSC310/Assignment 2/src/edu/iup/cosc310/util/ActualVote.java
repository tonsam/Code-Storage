package edu.iup.cosc310.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * @author David Smith and David Kornish
 * 
 *         Utility class to compute the pairwise winner of elections
 */
public class ActualVote {
	/**
	 * Get the placement order of a candidate in a rank order list of candidates
	 *
	 * @param votersVotes
	 *            an array of rank order candidates. First has the highest rank.
	 */
	public static int getPlacement(int candidate, int[] votersVotes) {
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
	 * @param votes
	 *            - a two dimensional array, first dimension is the voter, second
	 *            dimension is the rank ordered ballot of candidates for the given
	 *            voter
	 */
	public static int getPairwiseWinner(int[][] votes) {
		int noVoters = votes.length;

		if (noVoters == 0) {
			return -1;
		}

		int noCandidates = votes[0].length;
		int[] candidateScores = new int[noCandidates];

		if (noCandidates == 0) {
			return -1;
		}

		// Cycle through all votes, adding the scores of rankings.
		// Since candidates are in numerical order, we can keep track of them
		// by their index in the candidatesScore array.
		// (their number is their index + 1).

		for (int v = 0; v < noVoters; v++) {
			for (int b = 0; b < noCandidates; b++) {
				// candidate in question is the value stored in array
				int candidate = votes[v][b];
				// add the points (based on index) to candidate's score
				candidateScores[candidate] += (noCandidates-b);
			}
		}

		// Find winner. In a pairwise vote, the winner is the one who's score is lower than the most other candidates, not 

		int highScore = 0;
		boolean tied = true;
		int winningCandidate = -1;

		for (int i = 0; i < noCandidates; i++) {
			int score = candidateScores[i];

			if (score >= highScore) {
				if (score == highScore) {
					tied = true;
				} else {
					winningCandidate = i;
					highScore = score;
					tied = false;
				}
			}
		}

		if (tied == false) {
			return winningCandidate;
		}
		// No winner
		return -1;
	}

	static int electionNo = 0;

	/**
	 * Main - reads several test elections using the text file votes.txt. Each
	 * election begins with two number, the number of voters and the number of
	 * candidates, all followed by the ballots of each voter.
	 */
	public static void main(String[] args) throws FileNotFoundException {
		int noVoters;
		int noCandidates;

		Scanner in = new Scanner(new FileInputStream("votes.txt"));

		// read ballots for each election
		for (;;) {
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