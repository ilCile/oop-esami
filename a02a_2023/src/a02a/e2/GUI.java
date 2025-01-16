package a02a.e2;

import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class GUI extends JFrame {
    
    private static final long serialVersionUID = -6218820567019985015L;
    private final Map<JButton, Pair<Integer, Integer>> cells = new HashMap<>();
    private final Logic logic;
    
    public GUI(int size) {
        logic = new LogicImpl();
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(100*size, 100*size);
        
        JPanel panel = new JPanel(new GridLayout(size,size));
        this.getContentPane().add(panel);
        
        ActionListener al = e -> {
            var jb = (JButton)e.getSource();
            jb.setText(logic.hit(this.cells.get(jb)) ? "o" : "*");
        	if (logic.isOver()) {
                System.exit(0);
            }
        };
                
        for (int i=1; i<size+1; i++){
            for (int j=1; j<size+1; j++){
            	var pos = new Pair<>(i,j);
                final JButton jb = new JButton(j % 2 == 0 || i % 2 == 0 ? "*" : "");
                if (jb.getText().equals("")) {
                    logic.start(pos);
                }
                this.cells.put(jb, pos);
                jb.addActionListener(al);
                panel.add(jb);
            }
        }

        this.setVisible(true);
    }
    
}
