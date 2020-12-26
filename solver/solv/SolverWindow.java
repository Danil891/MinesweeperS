package solv;

import elementForGame.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SolverWindow extends JFrame implements ActionListener, Solver.CoordAction {
    private  SweepeR gameWindow;
    private JButton nextMoveButton;
    private JButton solveButton;
    private JLabel lastStepLabel;
    private Solver solver = new Solver(this);

    private boolean solving = false;

    private Timer timer = new Timer(100, this);

    public static void main(String[] args) {
        new SolverWindow();
    }

    private SolverWindow(){
        super("Решатель");
        JPanel panel = new JPanel(new GridLayout(3, 1));
        nextMoveButton = new JButton("Следующий ход");
        nextMoveButton.addActionListener(this);
        solveButton = new JButton("");
        solveButton.addActionListener(this);
        lastStepLabel = new JLabel("Шагов ещё не было");
        lastStepLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(nextMoveButton);
        panel.add(solveButton);
        panel.add(lastStepLabel);
        add(panel);
        panel.setVisible(true);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(250, 150);
        gameWindow = new SweepeR();
        setLocationRelativeTo(gameWindow);
        solved();
        timer.start();
        setVisible(true);
    }

    private void solved() {
        nextMoveButton.setEnabled(true);
        solving = false;
        solveButton.setText("Начать решение");
    }


    @Override
    public void actionPerformed(ActionEvent e){
        Game state = gameWindow.getGame();
        solver.update(state);
        Object source = e.getSource();
        if (source == nextMoveButton) {
            lastStepLabel.setText(solver.step().getMessage());
        } else if (source == solveButton) {
            if (solving) {
                solved();
            } else {
                nextMoveButton.setEnabled(false);
                solveButton.setText("Остановить решателя");
                solving = true;
            }
        } else if (source == timer) {
            if (solving) {
                lastStepLabel.setText(solver.step().getMessage());
                if (state.getState() != GameState.PLAYED) {
                    solved();
                }
            }
        }
    }


    @Override
    public void performCoordAction(Coordinates coord) {
        gameWindow.drawClickShadow(coord, Color.RED);
        try {
            Thread.sleep(800);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        gameWindow.getPanel().repaint();
    }
}