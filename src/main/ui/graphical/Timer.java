//package ui.graphical;
//
//import model.Exercise;
//
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.util.TimerTask;
//
//public class Timer {
//    public void startButtonAction() {
//        startButton.addActionListener(new ActionListener() {
//
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                java.util.Timer timer = new java.util.Timer();
//                timer.scheduleAtFixedRate(new TimerTask() {
//                    public void run() {
//                        for (int i = 0; i < wr.length(); i++) {
//
//                            currentExercise = wr.getExerciseWithPosition(i);
//                            msg.setText("Current Exercise:" + currentExercise.getDescription()); //Current exercise
//
//                            //minutes = currentExercise.getMinutesString(); //Number the countdown starts at
//                            //entry.setText(minutes); //The text field
//
//                            //count = Integer.parseInt(minutes);
//
//                            //display.setFont(new Font("Ariel", Font.BOLD, 45)); // Countdown display
//                            if ((i + 1) < wr.length()) {
//                                Exercise next = wr.getExerciseWithPosition(i + 1);
//                                nextExercise.setText("Next Exercise:" + next.getDescription());
//                                nextExercise.repaint();
//
//                            } else {
//                                nextExercise.setText("Workout Complete!");
//                            }
//
//                        }
//
//
//                    }
//                }, 0, 60000);
////                t.scheduleAtFixedRate(new TimerTask() {
////                    int anInt = Integer.parseInt(minutes);
////
////                    @Override
////                    public void run() {
////                        if ((anInt) > -1) {
////                            display.setText("" + (anInt--));
////                        }
////                    }
////                }, 0, 1000);
//
//            }
//
//        });
//
//        backButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                mainMenu.setVisible(true);
//                startPanel.setVisible(false);
//            }
//        });
//    }


//public void initializeTimerLabels() {
//        msg = new JLabel("");
//        msg.setFont(new Font("Ariel", Font.BOLD, 20));
//        nextExercise = new JLabel("");
//        nextExercise.setFont(new Font("Ariel", Font.BOLD, 20));
//        entry = new JTextField(1);
//        entry.setMaximumSize(new Dimension(1, 1));
//        display = new JLabel("");
//        display.setFont(new Font("Ariel", Font.BOLD, 30));
//        current = new JLabel();
//
//    }
//}
