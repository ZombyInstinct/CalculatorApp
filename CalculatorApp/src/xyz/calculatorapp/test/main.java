package xyz.calculatorapp.test;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class main{
	
	private JFrame frame;
	private JTextField textEntry = new JTextField();
	private String currInput = "";
	private double result = 0.0;
	private JButton[] numberButtons;
	private JButton[] operationButtons;
	private JPanel buttonPanel;
	
	
	
	public main()
	{
		//creates JFrame
		frame = new JFrame("Calculator App");
		frame.setSize(700, 700);
		frame.setResizable(false);
		//frame.setLayout(null);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(frame.DISPOSE_ON_CLOSE);
		
		//where the numbers appear
		Font textEntryFont = new Font("helvetica", Font.PLAIN, 30);
		JTextField textEntry = new JTextField();
		textEntry.setBounds(4, 10, 352, 60);
		textEntry.setFont(textEntryFont);

		//array of buttons [0-9] and a decimal button
		numberButtons = new JButton[11];
		operationButtons = new JButton[5];
		
		//array of operators including '='
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(4, 4));
		
		//populate all the buttons
		for (int i = 0; i < 10; i ++)
		{
			numberButtons[i] = new JButton(String.valueOf(i));
            numberButtons[i].addActionListener(new NumberButtonListener());
		}
		
		numberButtons[10] = new JButton("\u002E");
		
		operationButtons[0] = new JButton("\u2795");//addition
		operationButtons[1] = new JButton("\u2796");//subtraction
		operationButtons[2] = new JButton("\u2716");//multiplication
		operationButtons[3] = new JButton("\u2797");//division
		operationButtons[4] = new JButton("\u003D");//equals
		
		//add buttons in specific order
		buttonPanel.add(numberButtons[7]);
		buttonPanel.add(numberButtons[8]);
		buttonPanel.add(numberButtons[9]);
		buttonPanel.add(operationButtons[3]);
		
		buttonPanel.add(numberButtons[4]);
		buttonPanel.add(numberButtons[5]);
		buttonPanel.add(numberButtons[6]);
		buttonPanel.add(operationButtons[2]);
		
		buttonPanel.add(numberButtons[1]);
		buttonPanel.add(numberButtons[2]);
		buttonPanel.add(numberButtons[3]);
		buttonPanel.add(operationButtons[1]);
		
		buttonPanel.add(numberButtons[0]);
		buttonPanel.add(numberButtons[10]);
		buttonPanel.add(operationButtons[4]);
		buttonPanel.add(operationButtons[0]);
		
		frame.setLayout(new BorderLayout());
        frame.add(textEntry, BorderLayout.NORTH);
        frame.add(buttonPanel, BorderLayout.CENTER);
	}
	
	private class NumberButtonListener implements ActionListener
	{
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton button = (JButton) e.getSource();
            currInput += button.getText();
            textEntry.setText(currInput);
        }
	}

	public static void main(String args[])
	{
		SwingUtilities.invokeLater(() -> new main());
	}
	
}