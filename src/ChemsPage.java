import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;

public class ChemsPage extends JFrame implements funcs{
    private ArrayList<String[]> chems = new ArrayList<>();
    private JLabel lab = new JLabel("Chemical Name / Expiration Date / Quantity");
    private DefaultListModel<String> chemicalsModel = new DefaultListModel<>();
    private JList<String> chemicalslist = new JList<>(chemicalsModel);
    JTextField name = new JTextField("Chemical Name");
    JTextField expdateDay = new JTextField();
    JTextField expdateMonth = new JTextField();
    JTextField expdateYear = new JTextField();
    JTextField Quantity = new JTextField();
    private JButton add = new JButton("Add");
    private JButton remove = new JButton("Remove");
    private JButton back = new JButton("Back");
    ChemsPage(){
        this.setSize(435, 400);
        this.setTitle("Chemical List");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);

        JPanel panel = new JPanel();
        panel.setLayout(null);

        Inheritance x = new Inheritance();
        try {
            x.reader("chems");
            x.reader(chems);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        for(String[] str : chems){
            String row = str[0] + "/" + str[1] + "/" + str[2];
            chemicalsModel.addElement(row);
        }

        lab.setBounds(10, 10, 400, 30);
        chemicalslist.setBounds(10, 40, 400, 200);
        name.setBounds(10, 250, 180, 30);
        expdateDay.setBounds(10, 285, 50, 30);
        expdateMonth.setBounds(70, 285, 50, 30);
        expdateYear.setBounds(130, 285, 50, 30);
        Quantity.setBounds(10, 320, 180, 30);
        add.setBounds(200, 250, 210, 30);
        remove.setBounds(200, 285, 210, 30);
        back.setBounds(200, 320, 210, 30);

        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ChemsPage.this.dispose();
                new ChoosePage();
            }
        });

        remove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = chemicalslist.getSelectedIndex();
                if (selectedIndex != -1) {
                    chems.remove(selectedIndex);

                    chemicalsModel.remove(selectedIndex);
                    try {
                        FileWriter writer = new FileWriter("src/chems.txt");
                        BufferedWriter writer2 = new BufferedWriter(writer);

                        for (String[] chemData : chems) {
                            String row = chemData[0] + "/" + chemData[1] + "/" + chemData[2];
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
                    File file = new File("src/chems.txt");
                    FileWriter writer = new FileWriter(file, true);
                    BufferedWriter writer2 = new BufferedWriter(writer);
                    String chemname = name.getText();
                    validatename(chemname, chems);
                    int day = Integer.parseInt(expdateDay.getText());
                    validateDay(day);
                    int month = Integer.parseInt(expdateMonth.getText());
                    validateMonth(month);
                    int year = Integer.parseInt(expdateYear.getText());
                    validateYear(year);
                    int quantity = Integer.parseInt(Quantity.getText());
                    validateQuantity(quantity);

                    if (chemname.isEmpty()){
                        JOptionPane.showMessageDialog(null, "Enter the name!");
                    }else {
                        String row = chemname + "/" + day + "." + month + "." + year + "/" + quantity;
                        String[] row2 = row.split("/");
                        chems.add(row2);
                        chemicalsModel.addElement(row);

                        writer2.write(row + "\n");
                        writer2.close();
                    }

                } catch (FileNotFoundException p){
                    JOptionPane.showMessageDialog(null, "Chems file can not be found!");
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Enter a number!");
                } catch (ValueError x){
                    JOptionPane.showMessageDialog(null, "Invalid entry!");
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "IO Exception");
                }
                name.setText("");
                expdateDay.setText("");
                expdateMonth.setText("");
                expdateYear.setText("");
                Quantity.setText("");
            }
        });

        panel.add(lab);
        panel.add(chemicalslist);
        panel.add(name);
        panel.add(expdateDay);
        panel.add(expdateMonth);
        panel.add(expdateYear);
        panel.add(Quantity);
        panel.add(add);
        panel.add(remove);
        panel.add(back);

        setLocationRelativeTo(null);
        this.add(panel);
        this.setVisible(true);
    }

    public void validatename(String name, ArrayList<String[]> list) throws ValueError{
        for (String[] str : list){
            if (str[0].equals(name) || name.isEmpty()){
                throw new ValueError("This chemical already added!");
            }
        }
    }

    public void validateDay(int day) throws ValueError {
        if (day < 1 || day > 30) {
            throw new ValueError("Enter day between 1-30");
        }
    }

    public void validateMonth(int month) throws ValueError {
        if (month < 1 || month > 12) {
            throw new ValueError("Enter month between 1-12");
        }
    }

    public void validateYear(int Year) throws ValueError{
        if (Year < 0){
            throw new ValueError("Enter an invalid value for year!");
        }
    }

    public void validateQuantity(int quantity) throws ValueError{
        if(quantity < 1){
            throw new ValueError("Invalid quantity entry!");
        }
    }
}