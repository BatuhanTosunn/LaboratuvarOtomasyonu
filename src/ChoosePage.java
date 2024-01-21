import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChoosePage extends JFrame{
    JButton chemicals = new JButton("Show Chemicals");
    JButton equipments = new JButton("Show Equipments");
    JButton signout = new JButton("Sign Out");
    ChoosePage(){
        this.setSize(270,175);
        this.setTitle("Choose Page");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(null);

        chemicals.setBounds(10, 10, 240, 30);
        equipments.setBounds(10, 50, 240, 30);
        signout.setBounds(10, 90, 240, 30);

        panel.add(chemicals);
        panel.add(equipments);
        panel.add(signout);

        chemicals.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ChoosePage.this.dispose();
                new ChemsPage();
            }
        });

        equipments.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent a) {
                ChoosePage.this.dispose();
                new EquipmentPage();
            }
        });

        signout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ChoosePage.this.dispose();
                new loginpage();
            }
        });

        setLocationRelativeTo(null);
        this.add(panel);
        this.setVisible(true);
    }
}
