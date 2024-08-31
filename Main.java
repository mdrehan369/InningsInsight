import java.util.Scanner;

public class Main {

    private Team teamA;
    private Team teamB;

    private Team battingTeam;
    private Team bowlingTeam;

    private Batsman striker;
    private Batsman nonStriker;
    private Bowler bowler;
    private int target = 100000;


    private int overs;

    private Main() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the number of overs: ");
        int overs = sc.nextInt();
        this.overs = overs;
    }

    private void Toss() {

        Scanner sc = new Scanner(System.in);
        System.out.println("Which Team is Batting\n1. " + this.teamA.name + "\n2. " + this.teamB.name);
        int toss = sc.nextInt();
        if(toss != 1 && toss != 2) {
            System.out.println("Wrong Input");
            this.Toss();
        }else{
            if(toss == 1) {
                this.battingTeam = teamA;
                this.bowlingTeam = teamB;
            }else{
                this.battingTeam = teamB;
                this.bowlingTeam = teamA;
            }
        }

    }

    private void changeStrike() {
        Batsman temp = this.striker;
        this.striker = this.nonStriker;
        this.nonStriker = temp;
    }

    private void show(int balls) {
        System.out.println("*************************************");
        System.out.println(this.striker.getName() + " " + this.striker.getRuns() +"(" +this.striker.getBallsPlayed() + ")*" + "\t\t" + this.nonStriker.getName() + " " + this.nonStriker.getRuns() +"(" +this.nonStriker.getBallsPlayed() + ")");
        System.out.println(this.battingTeam.name + " : " + this.battingTeam.runs + "/" + this.battingTeam.wickets + "\t\t" + this.bowler.getName() + " " + bowler.getOversBowled() + "." + balls);
        System.out.println("*************************************");

    }

    private void changeSide() {
        Team temp = this.battingTeam;
        this.battingTeam = this.bowlingTeam;
        this.bowlingTeam = temp;
    }

    private void innings() {

        this.striker = this.battingTeam.chooseBatsman("Select the Striker");
        this.nonStriker = this.battingTeam.chooseBatsman("Select the Non-Striker");

        this.bowler = this.bowlingTeam.chooseBowler("Select the Bowler");

        int overs = 0;
        int balls = 0;
        Scanner sc = new Scanner(System.in);
        while(this.battingTeam.runs < target && overs < this.overs && this.battingTeam.wickets + 1 != this.battingTeam.totalPlayers) {
            this.show(balls);
            System.out.println("1. Runs scored\n2. Wickets taken\n3. Extras");
            int choice = sc.nextInt();
            if(choice < 1 || choice > 3) {
                System.out.println("Wrong Input");
                continue;
            }
            switch (choice) {
                case 1:
                    System.out.println("How much runs scored?");
                    int run = sc.nextInt();
                    this.striker.scoreRuns(run);
                    this.striker.playedBall();
                    this.battingTeam.runs += run;
                    if(run%2 == 1) {
                        this.changeStrike();
                    }
                    this.bowler.runsGivenBowler(run);
                    break;

                case 2:
                    System.out.println("1. Bowled\n2. Caught\n3. Run Out\n4. LBW");
                    this.battingTeam.wickets++;
                    int choice2 = sc.nextInt();
                    if(choice2 == 3) {
                        System.out.println("Who got out?\n1. Striker\n2. Non Striker");
                        int choice3 = sc.nextInt();
                        if(choice3 < 1 || choice3 > 2) {
                            System.out.println("Wrong Input");
                            continue;
                        }else{
                            if(choice3 == 1) {
                                this.striker.getOut();
                                this.striker.playedBall();
                                if(this.battingTeam.wickets + 1 != this.battingTeam.totalPlayers) {
                                    this.striker = this.battingTeam.chooseBatsman("Choose next Batsman");
                                }
                            }else{
                                this.nonStriker.getOut();
                                if(this.battingTeam.wickets + 1 != this.battingTeam.totalPlayers) {
                                    this.nonStriker = this.battingTeam.chooseBatsman("Choose next Batsman");
                                }
                                this.striker.playedBall();
                            }
                        }
                    } else if (choice2 == 1 || choice2 == 2 || choice2 == 4) {
                        this.striker.playedBall();
                        this.striker.getOut();
                        if(this.battingTeam.wickets + 1 != this.battingTeam.totalPlayers) {
                            this.striker = this.battingTeam.chooseBatsman("Choose next Batsman");
                        }
                        this.bowler.wicketTaken();
                    }else{
                        System.out.println("Wrong input");
                        continue;
                    }
                    break;

                case 3:
                    System.out.println("1. Wide\n2. No Ball\n3. Byes");
                    choice2 = sc.nextInt();
                    if(choice2 < 1 || choice2 > 3) {
                        System.out.println("Wrong Input");
                        continue;
                    }
                    System.out.println("Did batsman take extra runs(type 0 for no extra runs)");
                    int choice3 = sc.nextInt();
                    this.striker.scoreRuns(choice3);
                    if(choice3%2 == 1) {
                        this.changeStrike();
                    }
                    this.battingTeam.runs += choice3;
                    if(choice2 == 1 || choice2 == 2) {
                        this.battingTeam.runs++;
                        this.bowler.runsGivenBowler(choice3+1);
                        continue;
                    }
                    this.bowler.runsGivenBowler(choice3);
            }

            balls++;
            if(balls%6 == 0) {
                overs++;
                balls = 0;
                this.bowler.overBowled();
//                this.bowlingTeam.lastBowler = this.bowler;
                this.bowler = this.bowlingTeam.chooseBowler("Choose next bowler");
                this.changeStrike();
            }
        }
    }

    public static void main(String[] args) {
        Main match = new Main();

        match.teamA = Team.createTeam();
        match.teamB = Team.createTeam();

        match.Toss();

        //Start The Match
        match.innings();
        System.out.println(match.battingTeam.runs + " is the target");
        match.target = match.battingTeam.runs + 1;

        match.changeSide();

        match.innings();

        if(match.battingTeam.runs < match.target) {
            System.out.println(match.bowlingTeam.name + " won by " + (match.target - match.battingTeam.runs) + " run(s)");
        }else{
            System.out.println(match.battingTeam.name + " won by " + (match.battingTeam.totalPlayers - match.battingTeam.wickets) + " wicket(s)");
        }

    }
}
