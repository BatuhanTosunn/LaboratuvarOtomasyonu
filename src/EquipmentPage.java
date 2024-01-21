import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;

public class EquipmentPage extends JFrame{
    JLabel lab = new JLabel("Equipment / Quantity");
    ArrayList<String[]> equips = new ArrayList<>();
    DefaultListModel<String> equipmodels = new DefaultListModel<>();
    JList<String> equiplist = new JList<>(equipmodels);
    JTextField name = new JTextField();
    JTextField quantity = new JTextField();
    JButton add = new JButton("Add Equip");
    JButton remove = new JButton("Remove");
    JButton back = new JButton("Back");

    EquipmentPage(){
        this.setSize(400, 400);
        this.setTitle("Equipments");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);

        JPanel panel = new JPanel();
        panel.setLayout(null);

        lab.setBounds(10, 5, 150, 30);
        equiplist.setBounds(10, 35, 365, 200);
        name.setBounds(10, 240, 180, 30);
        quantity.setBounds(195, 240, 100, 30);
        add.setBounds(10, 270, 100, 30);
        remove.setBounds(115, 270, 100, 30);
        back.setBounds(218, 270, 100, 30);

        try {
            File file = new File("src/equips.txt");
            FileReader reader1 = new FileReader(file);
            BufferedReader reader2 = new BufferedReader(reader1);

            String line;
            while ((line = reader2.readLine()) != null) {
                if (!(line.isEmpty())) {
                    String[] adding;
                    adding = line.split("/");
                    equips.add(adding);
                }
            }
            reader2.close();
            reader1.close();
        } catch (FileNotFoundException e){
            JOptionPane.showMessageDialog(null, "File can not be found!");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "IO Exception");
        }

        for (String[] str : equips){
            String row = str[0] + "/" + str[1];
            equipmodels.addElement(row);
        }

        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                back.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        EquipmentPage.this.dispose();
                        new ChoosePage();
                    }
                });
            }
        });

        remove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = equiplist.getSelectedIndex();
                if (selectedIndex != -1) {
                    // Remove from the ArrayList
                    equips.remove(selectedIndex);

                    // Remove from the DefaultListModel
                    equipmodels.remove(selectedIndex);

                    // Update the file
                    try {
                        FileWriter writer = new FileWriter("src/equips.txt");
                        BufferedWriter writer2 = new BufferedWriter(writer);

                        for (String[] equipData : equips) {
                            String row = equipData[0] + "/" + equipData[1];
                            writer2.write(row + "\n");
                        }

                        writer2.close();
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(null, "IO Exception while updating the file");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Please select an item to remove");
                }
            }
        });

        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    File file = new File("src/equips.txt");
                    FileWriter writer = new FileWriter(file, true);
                    BufferedWriter writer2 = new BufferedWriter(writer);
                    String equipname = name.getText();
                    validatename(equipname, equips);
                    int Quant = Integer.parseInt(quantity.getText());

                    if (equipname.isEmpty()){
                        JOptionPane.showMessageDialog(null, "Enter a name for chemical!");
                    } else {
                        String row = equipname + "/" + Quant;
                        String[] row2 = row.split("/");
                        equips.add(row2);
                        equipmodels.addElement(row);

                        writer2.write(row + "\n");

                        writer2.close();
                    }
                } catch (FileNotFoundException p){
                    JOptionPane.showMessageDialog(null, "Equips file can not be found!");
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Enter a number!");
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "IO Exception");
                } catch (ValueError ex) {
                    JOptionPane.showMessageDialog(null, "Invalid entry!");
                }
                name.setText("");
                quantity.setText("");
            }
        });

        panel.add(lab);
        panel.add(equiplist);
        panel.add(name);
        panel.add(quantity);
        panel.add(add);
        panel.add(remove);
        panel.add(back);

        setLocationRelativeTo(null);
        this.add(panel);
        this.setVisible(true);
    }

    public void validatename(String name, ArrayList<String[]> list) throws ValueError{
        for (String[] str : list){
            if (str[0].equals(name)){
                throw new ValueError("This equip already in list!");
            }
        }
    }
}
