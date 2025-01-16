package a06.e2;

import javax.swing.*;

import java.util.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class GUI extends JFrame {
    
    private static final long serialVersionUID = -6218820567019985015L;
    private final Map<JButton, Position> cells = new HashMap<>();
    private final Logic logic;
    
    public GUI(int width) {
        logic = new LogicImpl(width);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(70*width, 70*width);
        
        JPanel panel = new JPanel(new GridLayout(width,width));
        this.getContentPane().add(panel);
        
        ActionListener al = e -> {
            var jb = (JButton)e.getSource();
            if (logic.hasTwoCards()) {
                if (logic.win()) {
                    for (var pos : logic.disable()) {
                        this.cells.entrySet().stream().filter(entry -> entry.getValue().equals(pos))
                        .findFirst().get().getKey().setEnabled(false);
                    }
                } else {
                    this.cells.entrySet().forEach(entry -> entry.getKey().setText(""));
                }
                logic.reset();
            }
            if (logic.isOver()) {
                System.exit(0);
            }
            jb.setText(logic.getCard(this.cells.get(jb)));
        };
                
        for (int i=0; i<width; i++){
            for (int j=0; j<width; j++){
            	var pos = new Position(j,i);
                final JButton jb = new JButton();
                this.cells.put(jb, pos);
                jb.addActionListener(al);
                panel.add(jb);
            }
        }
        this.setVisible(true);

        logic.start();
    }
}
