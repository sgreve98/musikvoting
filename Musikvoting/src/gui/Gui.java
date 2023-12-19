package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import sqlDummys.*;
import sqlAbfragen.*;

public class Gui {	
    private JFrame frame;
    private JPanel panel;
    private JPanel firstView;
    private JPanel secondView;
    private JPanel thirdView;
    private JPanel fourthView;
    private JPanel fifthView;
    private JPanel sixthView;
    private ArrayList<User> userList = new ArrayList<>();
    private ArrayList<Titel> titelList = new ArrayList<>();
    private User currentUser;

    public static void main(String[] args) {    	
        SwingUtilities.invokeLater(() -> {
            try {
                new Gui().initialize();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        
        SqlAbfragen testAbfragen = new SqlAbfragen();
        System.out.println(testAbfragen.getUser());
    }

    private void initialize() {
    	Titel title = new Titel("test", "test", "test");
    	titelList.add(title);
    	
        frame = new JFrame();
        frame.setBounds(100, 100, 450, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel = new JPanel(new CardLayout());
        frame.getContentPane().add(panel, BorderLayout.CENTER);

        createFirstView();
        createSecondView();
        createThirdView();
        createFourthView();
        createFifthView();
        createSixthView();

        showView("FirstView");

        frame.setVisible(true);
    }

    private void showView(String viewName) {
        CardLayout cardLayout = (CardLayout) panel.getLayout();
        cardLayout.show(panel, viewName);
    }
    
    public void titelWuenschen(JTextField titleTextField, JTextField interpretTextField, JTextField genreTextField){
    	String titleInput = titleTextField.getText().trim();
    	String interpretInput = interpretTextField.getText().trim();
    	String genreInput = genreTextField.getText().trim();
    	
    	if (!(titleInput.isEmpty() && interpretInput.isEmpty() && genreInput.isEmpty())) {
    		Titel newTitel = new Titel(titleInput, interpretInput, genreInput);
        	titelList.add(newTitel);
        	
        	JOptionPane.showMessageDialog(frame, "Titel erfolgreich hinzugef�gt!");
        	
        	titleTextField.setText("");
        	interpretTextField.setText("");
        	genreTextField.setText("");
    	}  
    	else{
    		JOptionPane.showMessageDialog(frame, "Nicht alle Felder ausgef�llt!");
    	}
    }

    public void registrieren(JTextField usernameTextField) {
        String usernameInput = usernameTextField.getText().trim();

        if (!usernameInput.isEmpty()) {
            boolean userExists = userList.stream().anyMatch(user -> user.getUsername().equals(usernameInput));

            if (userExists) {
                JOptionPane.showMessageDialog(frame, "Benutzername existiert bereits. Bitte einen anderen Benutzernamen w�hlen.");
            } else {
                boolean gastgeber = userList.isEmpty();

                User newUser = new User(usernameInput, gastgeber);
                userList.add(newUser);

                JOptionPane.showMessageDialog(frame, "Registrierung erfolgreich!");

                usernameTextField.setText("");
            }
        } else {
            JOptionPane.showMessageDialog(frame, "Bitte geben Sie ihren Benutzernamen ein!");
        }
    }


    public void login(JTextField usernameTextField) {
        String usernameInput = usernameTextField.getText().trim();

        if (!usernameInput.isEmpty()) {
            boolean userExists = userList.stream().anyMatch(user -> user.username.equals(usernameInput));

            if (userExists) {
                boolean isGastgeber = userList.stream()
                        .filter(user -> user.username.equals(usernameInput))
                        .findFirst()
                        .map(user -> user.gastgeber)
                        .orElse(false);

                showView(isGastgeber ? "ThirdView" : "SecondView");

                currentUser = userList.stream()
                        .filter(user -> user.username.equals(usernameInput))
                        .findFirst()
                        .orElse(null);
            } else {
                JOptionPane.showMessageDialog(frame, "User not found. Please register.");
            }
        }
    }

    public void abmelden() {
        currentUser = null;
        showView("FirstView");
    }

    private void createFirstView() {
        firstView = new JPanel();

        JTextField usernameTextField = new JTextField(15);
        firstView.add(usernameTextField);

        JButton registrierenButton = new JButton("Registrieren");
        registrierenButton.addActionListener(e -> this.registrieren(usernameTextField));
        firstView.add(registrierenButton);

        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(e -> this.login(usernameTextField));
        firstView.add(loginButton);

        panel.add(firstView, "FirstView");
    }

    private void createSecondView() {
        secondView = new JPanel();
        JButton abmeldenButton = new JButton("Abmelden");
        JButton musikwunschButton = new JButton("Musikwunsch");
        JButton votingButton = new JButton("Voting");

        abmeldenButton.addActionListener(e -> abmelden());
        musikwunschButton.addActionListener(e -> showView("FourthView"));
        votingButton.addActionListener(e -> showView("FifthView"));

        secondView.add(abmeldenButton);
        secondView.add(musikwunschButton);
        secondView.add(votingButton);
        panel.add(secondView, "SecondView");
    }

    private void createThirdView() {
        thirdView = new JPanel();
        thirdView.setLayout(new BoxLayout(thirdView, BoxLayout.Y_AXIS));
        JButton abmeldenButton = new JButton("Abmelden");
        JButton musikwunschButton = new JButton("Musikwunsch");
        JButton votingButton = new JButton("Voting");
        JButton playlistButton = new JButton("Playlist");
        abmeldenButton.addActionListener(e -> abmelden());
        musikwunschButton.addActionListener(e -> showView("FourthView"));
        votingButton.addActionListener(e -> showView("FifthView"));
        playlistButton.addActionListener(e -> showView("SixthView"));
        thirdView.add(abmeldenButton);
        thirdView.add(musikwunschButton);
        thirdView.add(votingButton);
        thirdView.add(playlistButton);
        panel.add(thirdView, "ThirdView");
    }

    private void createFourthView() {
        fourthView = new JPanel();
        
        JButton abgebenButton = new JButton("Abgeben");
        JButton mainMenuButton = new JButton("Main Menu");
        JButton abmeldenButton = new JButton("Abmelden");

        mainMenuButton.addActionListener(e -> showView("ThirdView"));
        abmeldenButton.addActionListener(e -> abmelden());

        JPanel row1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel titleLabel = new JLabel("Titel");
        JTextField titleTextField = new JTextField(15);
        row1.add(titleLabel);
        row1.add(titleTextField);

        JPanel row2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel interpretLabel = new JLabel("Interpret");
        JTextField interpretTextField = new JTextField(15);
        row2.add(interpretLabel);
        row2.add(interpretTextField);

        JPanel row3 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel genreLabel = new JLabel("Genre");
        JTextField genreTextField = new JTextField(15);
        row3.add(genreLabel);
        row3.add(genreTextField);
        
        abgebenButton.addActionListener(e -> this.titelWuenschen(titleTextField, interpretTextField, genreTextField));

        fourthView.add(abgebenButton);
        fourthView.add(mainMenuButton);
        fourthView.add(abmeldenButton);
        fourthView.add(row1);
        fourthView.add(row2);
        fourthView.add(row3);

        panel.add(fourthView, "FourthView");
    }

    private void createFifthView() {
        fifthView = new JPanel();

        JButton mainMenuButton = new JButton("Main Menu");
        JButton abmeldenButton = new JButton("Abmelden");

        mainMenuButton.addActionListener(e -> showView("ThirdView"));
        abmeldenButton.addActionListener(e -> abmelden());

        fifthView.add(mainMenuButton);
        fifthView.add(abmeldenButton);        

        panel.add(fifthView, "FifthView");
    }


    private void createSixthView() {
        sixthView = new JPanel();
        JButton mainMenuButton = new JButton("Main Menu");
        JButton abmeldenButton = new JButton("Abmelden");

        mainMenuButton.addActionListener(e -> showView("ThirdView"));
        abmeldenButton.addActionListener(e -> abmelden());

        sixthView.add(mainMenuButton);
        sixthView.add(abmeldenButton);
        panel.add(sixthView, "SixthView");
    }
}
