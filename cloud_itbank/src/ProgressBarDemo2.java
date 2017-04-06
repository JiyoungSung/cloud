//455

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.beans.*;
import java.util.Random;


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.beans.*;
import java.util.Random;

public class ProgressBarDemo2 extends JPanel implements PropertyChangeListener {

	private JProgressBar progressBar;
	private JButton startButton;
	private JTextArea taskOutput;
	private Task task;

	class Task extends SwingWorker<Void, Void> {
		 // ���� Task ��׶��忡�� ������ ����
		@Override
		public Void doInBackground() {
			Random random = new Random();
			int progress = 0;
			/// progress �ʱ�ȭ
			setProgress(0);
			while (progress < 100) {
				// �ִ� 0.1�ʰ� ����
				try {
					Thread.sleep(random.nextInt(1000));
				} catch (InterruptedException ignore) {
				}
				// ���� progress �����
				progress += random.nextInt(10);
				setProgress(Math.min(progress, 100));
			}
			return null;
		}

		
		 //�̺�Ʈ ����Ī ������ ����  �Ϸ��
		 
		@Override
		public void done() {
			Toolkit.getDefaultToolkit().beep();
			setCursor(null); // ��� Ŀ�� ����
		}
	}

	public ProgressBarDemo2() {
		super(new BorderLayout());
		
		setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));// Ŀ�� ��� ����
		
		// javax.swing.SwingWorker �ν��Ͻ��� ���� �Ұ� �׷��Ƿ� ���ο� �����Ͻ��� �ʿ��� 
		task = new Task(); // ��ü����
		task.addPropertyChangeListener(this); //�۾��� ����� �Ӽ��� ����Ǹ� ȣ��˴ϴ�.
		task.execute(); // ����

		progressBar = new JProgressBar(0, 100);// �ּ� ,�ִ�
		progressBar.setValue(0); // ��
		progressBar.setStringPainted(true);

		JPanel panel = new JPanel();
		panel.add(progressBar);

		add(panel, BorderLayout.PAGE_START);
		

	}
//�۾��� ����� �Ӽ��� ����Ǹ� ȣ��˴ϴ�.
	 
	public void propertyChange(PropertyChangeEvent evt) {
		if ("progress" == evt.getPropertyName()) {
			int progress = (Integer) evt.getNewValue();
			progressBar.setValue(progress);
		}
	}

	//GUI ���� �� ����. 
	//��� GUI �ڵ� ����ó��, �̰��� �̺�Ʈ ����ġ �����忡�� �����ؾ��մϴ�.
	 
	static void createAndShowGUI() {
		// ������ ���� �� ����
		JFrame frame = new JFrame("ProgressBarDemo");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// ������ â ���� �� ����
		JComponent newContentPane = new ProgressBarDemo2();
		newContentPane.setOpaque(true); // content panes must be opaque
		frame.setContentPane(newContentPane);

		// ������ ǥ��
		frame.pack();
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		// �̺�Ʈ ����ġ �����忡 ���� �۾��� �����մϴ�.
		//�� ���� ���α׷��� GUI�� ����� �����ݴϴ�.
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});
	}
}