import javax.swing.JOptionPane;

public class Main {

    public static void main(String[] args) {
        Player human = new Player("You");
        Player computer = new Player("Computer");
        while (true) {
            while (human.getScore() < 5 && computer.getScore() < 5) {
                computer.random();
                // System.out.println(computer.getAttack()); // Uncomment to enable cheat
                human.ask();
                int result = human.attack(computer);
                if (result == 1) {
                    human.addScore();
                } else if (result == 2) {
                    computer.addScore();
                }
            }

            String result = "GAME OVER! " + (human.getScore() == 5 ? human.getName() : computer.getName()) + " wins!!!\n\n";
            result += human.getName() + ": " + human.getScore() + "\n";
            result += computer.getName() + ": " + computer.getScore();

            human.reset();
            computer.reset();

            int choice = JOptionPane.showConfirmDialog(null, result, "Play again?", JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.NO_OPTION) {
                break;
            }
        }

        JOptionPane.showMessageDialog(null, "Thank you for playing!");
    }

}

enum Attack {
    ROCK, PAPER, SCISSOR
}

interface PlayerPublicGetters {
    public abstract Attack getAttack();
    public int getScore();
    public String getName();
}

class Player implements PlayerPublicGetters {

    private final String name;
    private int score = 0;
    private Attack attack;

    public Player(String n) {
        name = n;
    }
    
    public Attack getAttack() {
        return attack;
    }

    public int getScore() {
        return score;
    }
    
    public String getName() {
        return name;
    }

    public void addScore() {
        score++;
    }

    public Player ask() {
        Object[] btn = {"ROCK", "PAPER", "SCISSOR"};

        int i = JOptionPane.showOptionDialog(
                null,
                "Select Attack: ",
                "Select",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                btn,
                btn[2]);

        if (i < 0 || i > 2) {
            int choice = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?", "Exit", JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.YES_OPTION) {
                System.exit(0);
            } else {
                return ask();
            }
        }
        attack = equiv(i + 1);
        return this;
    }

    public Player random() {
        int a = (int) Math.round(Math.random() * 3);
        if (a < 1 || a > 3) {
            return random();
        }
        attack = equiv(a);
        return this;
    }

    private Attack equiv(int n) {
        Attack a;
        switch (n) {
            case 1:
                a = Attack.ROCK;
                break;
            case 2:
                a = Attack.PAPER;
                break;
            case 3:
                a = Attack.SCISSOR;
                break;
            default:
                a = null;
                break;
        }
        return a;
    }

    public void reset() {
        score = 0;
    }

    public int attack(Player p) {
        boolean rock = attack == Attack.ROCK && p.getAttack() == Attack.SCISSOR;
        boolean paper = attack == Attack.PAPER && p.getAttack() == Attack.ROCK;
        boolean scissor = attack == Attack.SCISSOR && p.getAttack() == Attack.PAPER;
        boolean draw = attack == p.getAttack();

        if (draw) {
            JOptionPane.showMessageDialog(null, "Oops, it's a draw!\n\n" + name + ": " + score + "\n" + p.getName() + ": " + p.getScore());
            return 0;
        } else if (rock || paper || scissor) {
            JOptionPane.showMessageDialog(null, name + " win!\n\n" + name + ": " + (score + 1) + "\n" + p.getName() + ": " + p.getScore());
            return 1;
        } else {
            JOptionPane.showMessageDialog(null, p.getName() + " win!\n\n" + name + ": " + score + "\n" + p.getName() + ": " + (p.getScore() + 1));
            return 2;
        }
    }
}
