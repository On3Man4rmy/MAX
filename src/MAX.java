
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MAX extends JFrame implements KeyListener {
    public static final int END_X = 8;
    public static final int END_Y = 8;
    public static final int START_X = 1;
    public static final int START_Y = 1;
    public static final Fraction SCORE_TARGET = new Fraction(80, 1);
    public static Fraction sum = new Fraction(0,1);
    Player p1 = new Player(new Position(4, 4), "red", "R");
    Player p2 = new Player(new Position(5, 5), "green", "G");
    Player currentPlayer = p1;
    Player otherPlayer = p2;
    Matrix<Fraction> mat;
    JPanel panel;
    JLabel label;

    public MAX(){
        JFrame frame=new JFrame("Spiel");
        mat = initMatrix();
        panel = new JPanel();
        label = new JLabel(Board.draw(p1,p2,currentPlayer,mat));
        label.setFont(new Font(Font.MONOSPACED,Font.PLAIN, 40));
        panel.addKeyListener(this);
        panel.setFocusable(true);
        panel.requestFocusInWindow();

        panel.add(label);

        frame.add(panel);
        frame.setVisible(true);
        frame.setLayout(new FlowLayout());
        frame.setSize(1500,1000);
        frame.setExtendedState(Frame.MAXIMIZED_BOTH);

    }


    public static Fraction getGameFraction() {
        int minNumerator, maxNumerator, randomNumerator;
        int minDenominator, maxDenominator, randomDenominator;

        minNumerator = 10;
        maxNumerator = 99;
        randomNumerator = MathUtil.randomRange(minNumerator, maxNumerator);

        minDenominator = randomNumerator / 10;
        maxDenominator = randomNumerator;
        randomDenominator = MathUtil.randomRange(minDenominator, maxDenominator);

        Fraction frac = new Fraction(randomNumerator, randomDenominator);
        sum = sum.add(frac);
        return frac;
    }
    public static Matrix<Fraction> initMatrix() {
        int rows = END_X - START_X + 1;
        int columns = END_Y - START_Y + 1;

        Matrix<Fraction> mat = new Matrix<>(rows, columns, Fraction.DEFAULT);

        mat.map(MAX2::getGameFraction);
        System.out.println(mat.reduce((acc, curr) -> acc.add(curr), Fraction.DEFAULT).intValue());
        return mat;
    }
    public static Boolean isSamePosition(Player p1, Player p2) {
        return p1.position.equals(p2.position);
    }

    @Override
    public void keyTyped(KeyEvent e) {


    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        switch(key) {
            case KeyEvent.VK_UP:
                if(currentPlayer.position.y > START_Y
                        && !isSamePosition(currentPlayer.peekDirection(Direction.UP), otherPlayer)) {
                    currentPlayer.moveDirection(Direction.UP);
                }
                break;
            case KeyEvent.VK_DOWN:
                if(currentPlayer.position.y < END_Y
                        && !isSamePosition(currentPlayer.peekDirection(Direction.DOWN), otherPlayer)) {
                currentPlayer.moveDirection(Direction.DOWN);
}

                break;
            case KeyEvent.VK_LEFT:
                if(currentPlayer.position.x > START_X
                    && !isSamePosition(currentPlayer.peekDirection(Direction.LEFT), otherPlayer)) {
                currentPlayer.moveDirection(Direction.LEFT);
            }
                break;
            case KeyEvent.VK_RIGHT :
                if(currentPlayer.position.x < END_X
                        && !isSamePosition(currentPlayer.peekDirection(Direction.RIGHT), otherPlayer)) {
                    currentPlayer.moveDirection(Direction.RIGHT);
                }
                break;
        }
        // Update player score
        currentPlayer.score = currentPlayer.score.add(mat.getValue(currentPlayer.position.x, currentPlayer.position.y));
        // Update score of remaining playing field points
        sum = sum.subtract(mat.getValue(currentPlayer.position.x, currentPlayer.position.y));
        // When player arrives field set field value to 0
        mat.setValue(currentPlayer.position.x, currentPlayer.position.y, Fraction.ZERO);

        // Rotate current player
        if (currentPlayer == p1) {
            currentPlayer = p2;
            otherPlayer = p1;
        } else {
            currentPlayer = p1;
            otherPlayer = p2;
        }

        label=new JLabel(Board.draw(p1, p2, currentPlayer, mat));
        label.setFont(new Font(Font.MONOSPACED,Font.PLAIN, 40));
        panel.add(label);

        repaint();

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
