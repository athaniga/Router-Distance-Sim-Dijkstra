import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.*;
import java.io.File;

public class FilePicker extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
   // private JButton buttonCancel;
    private JFileChooser jfc;

    public FilePicker() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });



        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    public File onOK() {
        jfc = new JFileChooser();
        File routers = new File("No file selected");
        jfc.setFileFilter(new FileNameExtensionFilter("Text files", "txt"));
        int returnValue = jfc.showOpenDialog(this);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            routers = jfc.getSelectedFile();
            return routers;
        } else {
            buttonOK.setText("No File Selected");
        }
        return routers;
        //dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public static void main(String[] args) {
        FilePicker dialog = new FilePicker();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
