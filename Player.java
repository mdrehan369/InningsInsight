import java.util.Scanner;
//import org.apache.poi.hssf.usermodel.HSSFWorkbook;
public class Player implements Batsman, Bowler{
    private String name;
    private Team team;
    private int runs = 0;
    private int[] typeOfRuns = new int[]{0, 0, 0, 0, 0, 0, 0}; //0, 1, 2, 3, 4, 5, 6
    private int oversBowled = 0;
    private int wickets = 0;
    private int runsGiven = 0;
    private int ballsPlayed = 0;
    public boolean isOut = false;
    public boolean dnb = true;
//    public boolean bowledLast = false;

    public Player(String name, Team team) {
        this.name = name;
        this.team = team;
    }

    public static Player createPlayer(Team team) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the name of the player of team " + team.name + ": ");
        String name = sc.nextLine();
        return new Player(name, team);
    }

    public void runsGivenBowler(int runs) {
        this.runsGiven += runs;
    }
    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void overBowled() {
        this.oversBowled++;
    }

    @Override
    public void playedBall(){
        this.ballsPlayed++;
    }

    @Override
    public int getBallsPlayed(){
        return this.ballsPlayed;
    }

    public void getOut(){
        this.isOut = true;
    }

    @Override
    public int getOversBowled() {
        return this.oversBowled;
    }

    @Override
    public void scoreRuns(int runs) {
        this.runs += runs;
        this.typeOfRuns[runs]++;
    }

    @Override
    public int getRuns() {
        return this.runs;
    }

    @Override
    public void wicketTaken() {
        this.wickets++;
    }

}
