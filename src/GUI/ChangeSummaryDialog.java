package GUI;

import javax.swing.*;
import java.awt.*;

public class ChangeSummaryDialog extends JDialog {
    public ChangeSummaryDialog(Frame parent, String title, String message) {
        super(parent, title, true);
        setLocationRelativeTo(parent);
        setSize(400, 300);

        // Text area for showing the message
        JTextArea messageArea = new JTextArea(message);
        messageArea.setEditable(false);
        messageArea.setWrapStyleWord(true);
        messageArea.setLineWrap(true);

        // Scroll pane in case the message is long
        JScrollPane scrollPane = new JScrollPane(messageArea);

        // Adding components to the dialog
        add(scrollPane, BorderLayout.CENTER);

        // OK button to close the dialog
        JButton okButton = new JButton("OK");
        okButton.addActionListener(e -> dispose());
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(okButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Display the dialog
        setVisible(true);
    }
}
