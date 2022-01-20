package ui.graphical;

import model.Event;
import model.EventLog;
import model.Exercise;
import model.WorkoutRoutine;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Timer;
import java.util.TimerTask;

import static org.junit.jupiter.api.Assertions.fail;


//Represents visual user interface for workout routine app
public class GUI extends JFrame {
    private static final String WORKOUT_FILE = "./data/routine.txt";

    WorkoutRoutine wr = new WorkoutRoutine("Workout Routine");
    private Exercise currentExercise;


    //Panels
    private JPanel mainMenu;
    private JPanel mainLeftPanel;
    private JPanel mainRightPanel;
    private JPanel desPanel;
    private JPanel minPanel;
    private JPanel startPanel;
    protected JPanel startLeftPanel;
    protected JPanel startRightPanel;
    private JPanel overviewPanel;

    //Buttons
    private JButton b1;
    private JButton b2;
    private JButton b3;
    private JButton b4;
    private JButton load;
    private JButton save;
    private JButton startButton;
    private JButton backButton;

    //Labels
    private JLabel descriptionTFLabel;
    private JLabel minutesTFLabel;
    private JLabel spotTFLabel;
    private JLabel exerciseLabel;
    private JLabel minutesLabel;
    private JLabel currentExerciseDisplay;
    private JLabel timeCountdown;

    //TextFields
    private JTextField descriptionTextField;
    private JTextField minutesTextField;
    private JTextField spotTextField;

    //Lists
    private JList descriptionList;
    private JList minutesList;
    private JList overviewList;
    private DefaultListModel list1;
    private DefaultListModel list2;
    private DefaultListModel list3;

    //Frames
    private JFrame errorFrame;

    //Json
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    //Countdown Timer
    Timer timer2;
    private JLabel counterLabel;
    DecimalFormat decimalFormat = new DecimalFormat("00");
    String doubleSecond;
    String doubleMinute;

    int second;
    int minute;
    int updateIndex = 0;
    int totalMins = 0;

    //Makes a new JFrame with different attributes
    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    public GUI() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(650, 500));


        initializeMainPage();

        addElementsToMainPanel();

        addButtonAction();
        deleteButtonAction();
        nextPageButtonAction();
        clearButtonAction();
        try {
            loadButtonAction();
        } catch (IOException e) {
            fail("Load button failed");
        }
        try {
            saveButtonAction();
        } catch (FileNotFoundException e) {
            fail("Save button failed");
        }
        initializeMainPageLists();
        addMainPageLists();
        selectExerciseMouseEvent();
        printLog();
        mainMenu.setVisible(true);
    }

    //EFFECTS: initializes panels,text fields, and buttons
    private void initializeMainPage() {
        initializeLeftRightPanels();
        initializeMenu();
        initializeTextFields();
        initializeMenuButtons();
        errorFrame = new JFrame();
    }


    //EFFECTS: Divides main menu panel into left and right panels
    public void initializeLeftRightPanels() {
        mainLeftPanel = new JPanel();
        mainRightPanel = new JPanel();
    }

    //EFFECTS: Initializes main menus panel
    public void initializeMenu() {
        mainMenu = new JPanel(new GridLayout(1, 2));
        mainMenu.add(mainLeftPanel);
        mainMenu.add(mainRightPanel);
        add(mainMenu);
    }


    //EFFECTS: Initializes main menu buttons and gives them labels
    public void initializeMenuButtons() {
        b1 = new JButton("Add");
        b1.setPreferredSize(new Dimension(500, 50));
        b2 = new JButton("Delete");
        b2.setPreferredSize(new Dimension(500, 50));
        b3 = new JButton("Next");
        b3.setPreferredSize(new Dimension(500, 50));
        b4 = new JButton("Clear All");
        b4.setPreferredSize(new Dimension(500, 50));
        load = new JButton("Load Saved Routine");
        load.setPreferredSize(new Dimension(150, 50));
        save = new JButton("Save Routine");
        save.setPreferredSize(new Dimension(150, 50));
    }

    //MODIFIES: this
    //EFFECTS: adds this button to given panel
    public void addButtonToPanel(JButton button, JPanel panel) {
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setBackground(Color.BLUE);
        panel.add(button);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
    }


    //MODIFIES: this
    //EFFECTS: Calls addButtonToPanel method for each argument and adds buttons to main menu panel
    public void addButtonsToMainPanel(JButton b1, JButton b2, JButton b3, JButton b4, JButton save, JButton load) {
        addButtonToPanel(b1, mainLeftPanel);
        addButtonToPanel(b2, mainLeftPanel);
        addButtonToPanel(b4, mainLeftPanel);
        addButtonToPanel(b3, mainRightPanel);
        addButtonToPanel(save, mainLeftPanel);
        addButtonToPanel(load, mainLeftPanel);
    }

    public void addElementsToMainPanel() {
        addTextFields(descriptionTextField, minutesTextField, spotTextField);
        addButtonsToMainPanel(b1, b2, b3, b4, save, load);
    }

    //MODIFIES: this
    //EFFECTS: Sets main panel text fields to null
    public void setAllToNull() {
        descriptionTextField.setText(null);
        minutesTextField.setText(null);
        spotTextField.setText(null);
    }


    //EFFECTS: adds action listened to add button
    public void addButtonAction() {
        b1.addActionListener(new ActionListener() {
            //MODIFIES: this
            //EFFECTS: Adds exercise to work out when button is clicked on
            @Override
            public void actionPerformed(ActionEvent e) {
                if ((!descriptionTextField.getText().isEmpty()) && (!minutesTextField.getText().isEmpty())) {
                    checkTextFields();
                }
            }
        });
    }

    //MODIFIES: this
    //EFFECTS: Shows error windows for incorrect entry in text fields
    private void checkTextFields() {
        String desInput = descriptionTextField.getText();
        String minInput = minutesTextField.getText();
        String spotInput = spotTextField.getText();

        if ((desInput.matches(".*\\d.*")) && !(minInput.matches("[0-9]+"))) {
            errorWindow("Please enter valid exercise name and minutes");
        } else if ((desInput.matches(".*\\d.*")) && (minInput.matches("[0-9]+"))) {
            errorWindow("Please enter valid exercise name");
        } else if (!(desInput.matches(".*\\d.*")) && !(minInput.matches("[0-9]+"))) {
            errorWindow("Please enter valid number of minutes");
        } else if (!(desInput.matches(".*\\d.*")) && (minInput.matches("[0-9]+"))) {
            if (!spotInput.matches("[0-9]+")
                    || (Integer.parseInt(spotInput) - 1 > list1.size())) {
                errorWindow("Please enter valid spot in list");
            } else {
                addSuccessful(desInput, minInput, spotInput);
            }
        }
    }

    //MODIFIES: this
    //EFFECTS: adds exercise to work out list
    private void addSuccessful(String desInput, String minInput, String spotInput) {
        list1.add(Integer.parseInt(spotInput) - 1, desInput);
        list2.add(Integer.parseInt(spotInput) - 1, minInput);
        wr.addExercise(Integer.parseInt(spotInput) - 1,
                new Exercise(desInput, Integer.parseInt(minInput)));
        totalMins += Integer.parseInt(minInput);
        setAllToNull();
    }


    //EFFECTS: method for producing error window and setting text fields to null
    private void errorWindow(String string) {
        JOptionPane.showMessageDialog(errorFrame, string);
        setAllToNull();
    }

    //MODIFIES: this
    //EFFECTS: selects given exercise when button is clicked on
    //This method code is referenced from YouTube
    //Link:https://www.youtube.com/watch?v=xEsI-et9Xf4&t=1092s
    public void selectExerciseMouseEvent() {
        descriptionList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (e.getClickCount() == 2) {
                    updateIndex = descriptionList.getSelectedIndex();
                    descriptionTextField.setText((String) list1.get(updateIndex));
                    minutesTextField.setText((String) list2.get(updateIndex));
                    spotTextField.setText(null);
                }
            }
        });

    }

    //EFFECTS: adds actionListener to delete button
    public void deleteButtonAction() {
        b2.addActionListener(new ActionListener() {
            //MODIFIES: this
            //EFFECTS: deletes selected exercise from mouse event when button is clicked on
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!descriptionTextField.getText().isEmpty()
                        || !minutesTextField.getText().isEmpty()) {
                    String description = descriptionTextField.getText();
                    String minutes = minutesTextField.getText();
                    list1.set(updateIndex, description);
                    list1.remove(updateIndex);
                    list2.set(updateIndex, minutes);
                    list2.remove(updateIndex);
                    descriptionTextField.setText(null);
                    minutesTextField.setText(null);
                    wr.removeExercise(description);
                    totalMins = totalMins - Integer.parseInt(minutes);
                }

            }
        });
    }

    //MODIFIES: this
    //EFFECTS: brings user to new JPanel when button is pressed
    public void nextPageButtonAction() {
        b3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                initializeNextPanel();
                organizeNextPanel();
                initializeOverviewList();
                addExercise();
                addOverviewList();
                addElementsToNextPanel();
                initializeTimerLabels();
                addTimerLabels(currentExerciseDisplay, timeCountdown, counterLabel);
//
            }
        });
    }


    //EFFECTS: adds ActionListener to start timer button
    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    public void startButtonAction() {
        startButton.addActionListener(new ActionListener() {
            //MODIFIES: this
            //EFFECTS: Starts workout routine when button is pressed
            @Override
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < wr.length(); i++) {
                    int finalI = i;
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            Timer timer = new Timer();
                            timer.schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    currentExerciseDisplay.repaint();
                                    currentExercise = wr.getExerciseWithPosition(finalI);
                                    currentExerciseDisplay.setText("Current Exercise:"
                                            + currentExercise.getDescription());

                                }
                            }, 1000, 1000);

                        }
                    });

                }
                counterLabel.setText(wr.timeRemaining() + ":00");
                minute = wr.timeRemaining();
                second = 0;
                timer2 = new Timer();
                timer2.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        second--;
                        doubleSecond = decimalFormat.format(second);
                        doubleMinute = decimalFormat.format(minute);

                        counterLabel.setText(doubleMinute + ":" + doubleSecond);

                        if (second == -1) {
                            second = 59;
                            minute--;
                            doubleSecond = decimalFormat.format(second);
                            doubleMinute = decimalFormat.format(minute);
                            counterLabel.setText(doubleMinute + ":" + doubleSecond);
                        }
                        if (minute == 0 && second == 0) {
                            timer2.cancel();
                            counterLabel.setFont(new Font("Arial", Font.BOLD, 20));
                            counterLabel.setText("Workout Complete!");
                        }

                    }
                }, 1000, 1000);

            }
        });
    }

    //MODIFIES: this
    //EFFECTS: brings user back to main page when button is pressed
    public void backButtonAction() {
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainMenu.setVisible(true);
                startPanel.setVisible(false);
                if (timer2 != null) {
                    timer2.cancel();
                }
            }
        });
    }

    //MODIFIES: this
    //EFFECTS: clears all exercises in workout
    public void clearButtonAction() {
        b4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clear();
                wr.removeAll();
            }
        });
    }

    //EFFECTS: adds ActionListener to save button
    //Referenced code from CPSC210/JsonSerializationDemo repo
    //Link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    public void saveButtonAction() throws FileNotFoundException {
        save.addActionListener(new ActionListener() {
            //MODIFIES: this
            //EFFECTS: saves state of workout routine with the user added exercises
            @Override
            public void actionPerformed(ActionEvent e) {
                jsonWriter = new JsonWriter(WORKOUT_FILE);
                try {
                    jsonWriter.open();
                    jsonWriter.write(wr);
                    jsonWriter.close();
                    System.out.println("Saved " + wr.getName() + " to " + WORKOUT_FILE);
                } catch (FileNotFoundException exception) {
                    exception.printStackTrace();
                }
            }
        });
    }

    //MODIFIES: this
    //EFFECTS: initializes main page text fields
    public void initializeTextFields() {
        descriptionTFLabel = new JLabel("Enter Exercise:");
        descriptionTextField = new JTextField(20);
        minutesTFLabel = new JLabel("Enter Minutes:");
        minutesTextField = new JTextField(20);
        spotTFLabel = new JLabel("Enter Spot In List");
        spotTextField = new JTextField(20);
        exerciseLabel = new JLabel("           Exercises");
        minutesLabel = new JLabel("                               Minute(s)");
    }

    //MODIFIES: this
    //EFFECTS: method to add a text field to given panel
    public void addTextField(JTextField field, JPanel panel) {
        panel.add(field);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
    }

    //MODIFIES: this
    //EFFECTS: calls the addTextField method to add text fields to main page
    public void addTextFields(JTextField descriptionTextField, JTextField minutesTextField, JTextField spotTextField) {
        mainRightPanel.add(descriptionTFLabel);
        addTextField(descriptionTextField, mainRightPanel);
        mainRightPanel.add(minutesTFLabel);
        addTextField(minutesTextField, mainRightPanel);
        mainRightPanel.add(spotTFLabel);
        addTextField(spotTextField, mainRightPanel);
    }

    //EFFECTS: initializes main page lists
    public void initializeMainPageLists() {
        descriptionList = new JList();
        desPanel = new JPanel();
        list1 = new DefaultListModel();
        minutesList = new JList();
        minPanel = new JPanel();
        list2 = new DefaultListModel();
    }

    //MODIFIES: this
    //EFFECTS: adds lists to main page
    public void addMainPageLists() {
        mainLeftPanel.add(exerciseLabel); //label
        mainLeftPanel.add(minutesLabel); //label
        mainLeftPanel.add(desPanel);
        desPanel.add(new JScrollPane(descriptionList));
        descriptionList.setModel(list1);
        descriptionList.setFixedCellWidth(200);
        setVisible(true);

        mainLeftPanel.add(minPanel);
        minPanel.add(new JScrollPane(minutesList));
        minutesList.setModel(list2);
        minutesList.setFixedCellWidth(50);
        setVisible(true);
    }

    //EFFECTS: initializes labels for after workout starts
    public void initializeTimerLabels() {
        currentExerciseDisplay = new JLabel("");
        currentExerciseDisplay.setFont(new Font("Ariel", Font.BOLD, 20));
        timeCountdown = new JLabel("");
        timeCountdown.setFont(new Font("Ariel", Font.BOLD, 20));
        counterLabel = new JLabel("00:00");
        counterLabel.setFont(new Font("Ariel", Font.BOLD, 80));

    }

    //MODIFIES: this
    //EFFECTS: method to add label to given panel
    public void addTimerLabel(JLabel label, JPanel panel) {
        panel.add(label);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
    }

    //MODIFIES: this
    //EFFECTS: calls addTimerLabel to add labels to second panel
    public void addTimerLabels(JLabel msg, JLabel time, JLabel count) {
        addTimerLabel(msg, startRightPanel);
        addTimerLabel(time, startRightPanel);
        addTimerLabel(count, startRightPanel);
    }

    //MODIFIES: this
    //EFFECTS: adds text field to second panel
    public void addEntryTextField(JTextField entry) {
        addTextField(entry, startRightPanel);
    }

    //EFFECTS: initializes second panel
    public void initializeNextPanel() {
        startPanel = new JPanel(new GridLayout(1, 2));
        startRightPanel = new JPanel();
        startLeftPanel = new JPanel();
    }

    //MODIFIES: this
    //EFFECTS: organizes second panel by dividing it into 2
    public void organizeNextPanel() {
        startPanel.add(startLeftPanel, BorderLayout.WEST);
        startPanel.add(startRightPanel, BorderLayout.EAST);
        add(startPanel);
        startPanel.setVisible(true);
        mainMenu.setVisible(false);
    }

    //MODIFIES: this
    //EFFECTS: adds buttons to second panel
    public void addElementsToNextPanel() {
        startButton = new JButton("START!");
        startButton.setPreferredSize(new Dimension(200, 50));
        startLeftPanel.add(startButton);

        backButton = new JButton("Back");
        backButton.setPreferredSize(new Dimension(200, 50));
        startLeftPanel.add(backButton);

        startButtonAction();
        backButtonAction();


    }

    //EFFECTS: initializes overview list of workouts on second panel
    public void initializeOverviewList() {
        overviewList = new JList();
        overviewPanel = new JPanel();
        list3 = new DefaultListModel();
    }

    //MODIFIES: this
    //EFFECTS: adds string of exercise to overview list
    public void addExercise() {
        Exercise selected;
        String print;
        for (int i = 0; i < wr.length(); i++) {
            selected = wr.getExerciseWithPosition(i);
            print = selected.toString();
            list3.addElement(print);
        }

    }

    //MODIFIES: this
    //EFFECTS: adds overview list to second panel
    public void addOverviewList() {
        startLeftPanel.add(overviewPanel);
        overviewPanel.add(new JScrollPane(overviewList));
        overviewList.setModel(list3);
        overviewList.setFixedCellWidth(150);
        setVisible(true);
    }

    //MODIFIES: this
    //EFFECTS: loads the workout routine from file if it exists
    //Referenced code from CPSC210/JsonSerializationDemo repo
    //Link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    public void loadButtonAction() throws IOException {
        load.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clear();
                jsonReader = new JsonReader(WORKOUT_FILE);
                try {
                    wr = jsonReader.read();
                    WorkoutRoutine rr = new WorkoutRoutine("Replacement");
                    for (int i = 0; i < wr.length(); i++) {
                        Exercise currentE = wr.getExerciseWithPosition(i);
                        rr.addExerciseLoad(0, currentE);
                    }
                    wr = rr;
                    for (int i = 0; i < wr.length(); i++) {
                        Exercise currentE = wr.getExerciseWithPosition(i);
                        list1.add(i, currentE.getDescription());
                        list2.add(i, currentE.getMinutes());
                    }

                } catch (IOException ioException) {
                    JOptionPane.showMessageDialog(errorFrame, "No Exercises Added Yet");
                }
            }
        });

    }

    //MODIFIES: this
    //EFFECTS: clears main panel lists
    private void clear() {
        list1.clear();
        list2.clear();
    }

    public void printLog() {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                for (Event next : EventLog.getInstance()) {
                    System.out.println(next.getDate() + "\n" + next.getDescription() + "\n");
                }
            }
        });
    }

}
