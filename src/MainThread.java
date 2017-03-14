import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JButton;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.GridLayout;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.BoxLayout;
import java.awt.CardLayout;
import net.miginfocom.swing.MigLayout;
import javax.swing.JList;
import java.awt.BorderLayout;
import javax.swing.JTextArea;
import javax.swing.JTable;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import org.eclipse.wb.swing.FocusTraversalOnArray;
import java.awt.Component;
import javax.swing.border.EmptyBorder;

public class MainThread extends JFrame {

	
	public static Dimension ScreenSize = Toolkit.getDefaultToolkit().getScreenSize();
	public JPanel variablePanel;
	
	private static MainThread mainThread = new MainThread(); 
	private CommonPanel commonPanel;
	

	private MainThread() {
		super("RMA");
		setSize(new Dimension(1280, 800));
		getContentPane().setLayout(new BorderLayout(0, 0));

		loadCommonPanel();
		loadAdvancedReplacementPanel();

	}
	
	public static MainThread getMainThread(){
		return mainThread;
	}
	
	public CommonPanel getCommonPanel(){
		return commonPanel;
	}
	

	private void loadCommonPanel() {

		commonPanel = new CommonPanel();

		getContentPane().add(commonPanel, BorderLayout.CENTER);

	}

	public void loadAdvancedReplacementPanel() {

		

		variablePanel = new AdvancedReplacementPanel();
		commonPanel.addVariablePanel(variablePanel);

	}
	
	public void removeVariablePanel(){
		
		//commmonPanel�� ������ �ִ� Jpanel�� Variable Panel�� �ö��ִ� ��Ȳ�̶�, CommonPanel ���ο��� ���� ����������Ѵ�. 
		commonPanel.removeVariablePanel(variablePanel);
		
	}

	public static void main(String[] args) {
		MainThread layout = getMainThread();
		layout.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		layout.setSize(1280, 800);
//		 layout.setResizable(false);
		layout.setVisible(true);

		
		layout.setLocation(ScreenSize.width / 2 - layout.getSize().width / 2, ScreenSize.height / 2 - layout.getSize().height / 2);
	}

}