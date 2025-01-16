package a05.e2;

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
            if (logic.hit(this.cells.get(jb))) {
                redraw();
            }
            if (logic.isOver()) {
                this.cells.entrySet().forEach(entry -> entry.getKey().setEnabled(false));
            }	
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

        redraw();
    }
    
    private void redraw() {
        for (var entry : this.cells.entrySet()) {
            entry.getKey().setText(entry.getValue().equals(logic.getPlayer()) ? "P" : 
                entry.getValue().equals(logic.getEnemy()) ? "E" : "");
        }
    }
}
