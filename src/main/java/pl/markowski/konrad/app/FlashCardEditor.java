package pl.markowski.konrad.app;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class FlashCardEditor {
    private JTextArea question;
    private JTextArea answer;
    private ArrayList<FlashCard> cardList;
    private JFrame frame;

    public static void main(String[] args) {
        FlashCardEditor editor = new FlashCardEditor();
        editor.doAction();
    }


    public void doAction(){
        frame = new JFrame("Flash Card Editor");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel mainPanel = new JPanel();
        Font bigFont = new Font("sanserif",Font.BOLD,24);
        question = new JTextArea(6,20);
        question.setLineWrap(true);
        question.setWrapStyleWord(true);
        question.setFont(bigFont);

        JScrollPane scrollQuestion = new JScrollPane(question);
        scrollQuestion.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollQuestion.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        answer = new JTextArea(6,20);
        answer.setLineWrap(true);
        answer.setWrapStyleWord(true);
        answer.setFont(bigFont);

        JScrollPane scrollAnswer = new JScrollPane(answer);
        scrollAnswer.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollAnswer.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        JButton nextCardButton = new JButton("Next Card");

        cardList = new ArrayList<>();

        JLabel questionsLabel = new JLabel("Question");
        JLabel answersLabel = new JLabel("Answer");

        mainPanel.add(questionsLabel);
        mainPanel.add(scrollQuestion);
        mainPanel.add(answersLabel);
        mainPanel.add(scrollAnswer);
        mainPanel.add(nextCardButton);
        nextCardButton.addActionListener(new NextCardListener());
        JMenuBar menu = new JMenuBar();
        JMenu menuFile = new JMenu("File");
        JMenuItem optionNew = new JMenuItem("New");
        JMenuItem optionSave = new JMenuItem("Save");
        optionNew.addActionListener(new NewMenuListener());

        optionSave.addActionListener(new SaveMenuListener());
        menuFile.add(optionNew);
        menuFile.add(optionSave);
        menu.add(menuFile);
        frame.setJMenuBar(menu);
        frame.getContentPane().add(BorderLayout.CENTER, mainPanel);
        frame.setSize(500, 600);
        frame.setVisible(true);


    }

    public class NextCardListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent event) {
            FlashCard card = new FlashCard(question.getText(), answer.getText());
            cardList.add(card);
            clearCard();
        }
    }

    public class NewMenuListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            cardList.clear();
            clearCard();
        }
    }

    public class SaveMenuListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            FlashCard card = new FlashCard(question.getText(), answer.getText());
            cardList.add(card);

            JFileChooser dataFile = new JFileChooser();
            dataFile.showSaveDialog(frame);
            saveFile(dataFile.getSelectedFile());
        }
    }

    private void saveFile(File file) {
        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            for (FlashCard card : cardList){
                writer.write(card.getQuestion() + "/");
                writer.write(card.getAnswer() + "/n");
            }
            writer.close();
        } catch (IOException e){
            System.out.println("Can't save the file");
            e.printStackTrace();
        }
    }

    private void clearCard() {
        question.setText("");
        answer.setText("");
        question.requestFocus();
    }

}
