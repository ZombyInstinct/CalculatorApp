package xyz.calculatorapp.test;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.InputMismatchException;

import javax.swing.*;

public class main{
	
	private JFrame frame;
	private JTextArea textEntry;
	private String currInput = "";
	private int currIndex  = 0;
	private double result = 0.0;
	private double value = 0.0;
	private boolean setNewEquation = true; //check for new eq
	private JButton[] numberButtons;
	private JButton[] operationButtons;
	private JButton[] functionButtons;
	private JPanel buttonPanel;
	private DoublyLinkedList equation;
	
	
	
	public main()
	{
		//creates JFrame
		frame = new JFrame("Calculator App");
		frame.setSize(500, 350);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(frame.DISPOSE_ON_CLOSE);
		
		//where the numbers appear
		Font textEntryFont = new Font("helvetica", Font.PLAIN, 30);
		textEntry = new JTextArea(3, 70);
		textEntry.setFont(textEntryFont);
		textEntry.setEditable(false);

		//array of buttons [0-9] and a decimal button
		numberButtons = new JButton[11];
		operationButtons = new JButton[5];
		functionButtons = new JButton[4];
		
		//array of operators including '='
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(5, 4));
		
		//populate all the buttons
		for (int i = 0; i < 10; i++)
		{
			numberButtons[i] = new JButton(""+i);
            numberButtons[i].addActionListener(new NumberButtonListener());
		}
		
		numberButtons[10] = new JButton("\u002E");
		numberButtons[10].addActionListener(new NumberButtonListener());
		
		operationButtons[0] = new JButton("\u2795");//addition
		operationButtons[0].addActionListener(new OperationButtonListener());
		operationButtons[1] = new JButton("\u2796");//subtraction
		operationButtons[1].addActionListener(new OperationButtonListener());
		operationButtons[2] = new JButton("\u2716");//multiplication
		operationButtons[2].addActionListener(new OperationButtonListener());
		operationButtons[3] = new JButton("\u2797");//division
		operationButtons[3].addActionListener(new OperationButtonListener());
		operationButtons[4] = new JButton("\u003D");//equals
		operationButtons[4].addActionListener(new OperationButtonListener());
		
		functionButtons[0] = new JButton("A/C");
		functionButtons[0].addActionListener(new FunctionButtonListener());
		functionButtons[1] = new JButton("Trig");
		functionButtons[1].addActionListener(new FunctionButtonListener());
		functionButtons[2] = new JButton("Mode");
		functionButtons[2].addActionListener(new FunctionButtonListener());
		functionButtons[3] = new JButton("\u232B");
		functionButtons[3].addActionListener(new FunctionButtonListener());
		
		//add buttons in specific order
		buttonPanel.add(functionButtons[0]);
		buttonPanel.add(functionButtons[1]);
		buttonPanel.add(functionButtons[2]);
		buttonPanel.add(functionButtons[3]);
		
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
        
        equation = new DoublyLinkedList();
	}
	
	private class NumberButtonListener implements ActionListener
	{
        @Override
        public void actionPerformed(ActionEvent e) {
			
			if (setNewEquation)
			{
				currInput = "";
				textEntry.setText(currInput);
				setNewEquation = false;
			}
			
            JButton button = (JButton) e.getSource();
            currInput += button.getText();
            textEntry.setText(currInput);
        }
	}
	
	private class OperationButtonListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) {
			JButton button = (JButton) e.getSource();
			//REDO --- perhaps full entry should by typed and decoded?

			
			if (setNewEquation)
			{
				currInput = "" + result;
				textEntry.setText(currInput);
				setNewEquation = false;
			}
			
			try {
				
				if (checkValidEntry())
				{
					equation.addNode(operationConversion(button.getText()), value);
				}
				else
				{
					equation.replaceLatestOperator(operationConversion(button.getText()));
				}
				
				if (equation.tail.operation == "=")
				{
					result = equation.calculate();
					textEntry.setText("= " + result);
					setNewEquation = true;
				}
				
				//equation.printList();
			} 
			catch (Exception e1) {
				JOptionPane.showMessageDialog(frame, "Invalid Entry");
			}
			
		}
		
		//determines if the entered value is valid
		private boolean checkValidEntry() throws Exception {
			
			//start with a number entry
			//TODO: check for trig entries and parentheses?

			String str = textEntry.getText().substring(currIndex, textEntry.getText().length());
			try {
				if (str == "")
				{
					return false;
				}
				//System.out.println(str);
				value = Double.parseDouble(str);
				currInput = "";
				textEntry.setText(currInput);
				currIndex = 0;
				return true;
			}
			catch (NumberFormatException e)
			{
				JOptionPane.showMessageDialog(frame, "Invalid Entry");
				return false;
			}
		}
		
		private String operationConversion(String unicodeText)
		{
			switch(unicodeText)
			{
			case "\u2795":
				return "+";
			case "\u2796":
				return "-";
			case "\u2716":
				return "x";
			case "\u2797":
				return "/";
			case "\u003D":
				return "=";
			default:
				return "?";
			}
		}
	}
	
	private class FunctionButtonListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) {
			JButton button = (JButton) e.getSource();
			
			switch (button.getText())
			{
			case "A/C": //all clear
				textEntry.setText("");
				currInput = "";
				result = value = 0;
				currIndex = 0;
				break;
			case "Trig":
				break;
			case "Mode":
				break;
			case "\u232B": //delete key (does not account for deletion of operators yet
				currInput = currInput.substring(0, currInput.length()-1);
				textEntry.setText(currInput);
				break;
			}
		}
	}

	public static void main(String args[])
	{
		SwingUtilities.invokeLater(() -> new main());
	}
	
}