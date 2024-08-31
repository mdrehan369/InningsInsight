import java.util.Scanner;

public class Team {
    public String name;
    public Player[] players;
    public int wickets = 0;
    int totalPlayers;
    int runs = 0;
    Player lastBowler;

    public Team(String name, int totalPlayers) {
        this.name = name;
        this.totalPlayers = totalPlayers;
        this.players = new Player[totalPlayers];

        for(int i = 0; i < totalPlayers; i++) {
            this.players[i] = Player.createPlayer(this);
        }
    }

    public static Team createTeam() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the name of the team: ");
        String name = sc.nextLine();
        System.out.print("Enter the total number of players: ");
        int n = sc.nextInt();
        return new Team(name, n);
    }

    private void showPlayers() {
        for(int i = 0; i < this.totalPlayers; i++) {
            if(!this.players[i].isOut && this.players[i].dnb) {
                System.out.println((i + 1) + ". " + this.players[i].getName());
            }
        }
    }

    public Player chooseBatsman(String msg) { //1 for batsman and 0 for bowler
        Scanner sc = new Scanner(System.in);
        System.out.println(msg);
        this.showPlayers();
        int choice = sc.nextInt();
        if(choice <= 0 || choice > this.totalPlayers || this.players[choice - 1].isOut || !this.players[choice - 1].dnb) {
            System.out.println("Wrong Input");
            return this.chooseBatsman(msg);
        }else{
            this.players[choice-1].dnb = false;
            return this.players[choice-1];
        }
    }

    public Player chooseBowler(String msg) { //1 for batsman and 0 for bowler
        Scanner sc = new Scanner(System.in);
        System.out.println(msg);
        for(int i = 0; i < this.totalPlayers; i++) {
            if(this.players[i] != lastBowler) {
                System.out.println((i + 1) + ". " + this.players[i].getName());
            }
        }
        int choice = sc.nextInt();
        if(choice <= 0 || choice > this.totalPlayers || this.players[choice - 1] == this.lastBowler) {
            System.out.println("Wrong Input");
            return this.chooseBowler(msg);
        }else{
            this.lastBowler = this.players[choice-1];
            return this.players[choice-1];
        }
    }
}
