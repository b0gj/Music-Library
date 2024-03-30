package GUI;

import javax.swing.*;
import java.awt.*;

public class ChangeSummaryDialog extends JDialog {
    public ChangeSummaryDialog(Frame parent, String title, String message) {
        super(parent, title, true);
        setLocationRelativeTo(parent);
        setSize(400, 300);

        JTextArea messageArea = new JTextArea(message);
        messageArea.setEditable(false);
        messageArea.setWrapStyleWord(true);
        messageArea.setLineWrap(true);

        JScrollPane scrollPane = new JScrollPane(messageArea);

        add(scrollPane, BorderLayout.CENTER);

        JButton okButton = new JButton("OK");
        okButton.addActionListener(e -> dispose());
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(okButton);
        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }
}
