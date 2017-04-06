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
		 // 메인 Task 백그라운드에서 스레드 실행
		@Override
		public Void doInBackground() {
			Random random = new Random();
			int progress = 0;
			/// progress 초기화
			setProgress(0);
			while (progress < 100) {
				// 최대 0.1초간 수면
				try {
					Thread.sleep(random.nextInt(1000));
				} catch (InterruptedException ignore) {
				}
				// 랜덤 progress 만들기
				progress += random.nextInt(10);
				setProgress(Math.min(progress, 100));
			}
			return null;
		}

		
		 //이벤트 디스패칭 스레드 실행  완료시
		 
		@Override
		public void done() {
			Toolkit.getDefaultToolkit().beep();
			setCursor(null); // 대기 커서 끄기
		}
	}

	public ProgressBarDemo2() {
		super(new BorderLayout());
		
		setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));// 커서 모양 변경
		
		// javax.swing.SwingWorker 인스턴스는 재사용 불가 그러므로 새로운 인터턴스가 필요함 
		task = new Task(); // 객체생성
		task.addPropertyChangeListener(this); //작업의 진행률 속성이 변경되면 호출됩니다.
		task.execute(); // 실행

		progressBar = new JProgressBar(0, 100);// 최소 ,최대
		progressBar.setValue(0); // 값
		progressBar.setStringPainted(true);

		JPanel panel = new JPanel();
		panel.add(progressBar);

		add(panel, BorderLayout.PAGE_START);
		

	}
//작업의 진행률 속성이 변경되면 호출됩니다.
	 
	public void propertyChange(PropertyChangeEvent evt) {
		if ("progress" == evt.getPropertyName()) {
			int progress = (Integer) evt.getNewValue();
			progressBar.setValue(progress);
		}
	}

	//GUI 생성 및 보기. 
	//모든 GUI 코드 에서처럼, 이것은 이벤트 디스패치 스레드에서 실행해야합니다.
	 
	static void createAndShowGUI() {
		// 윈도우 생성 및 설정
		JFrame frame = new JFrame("ProgressBarDemo");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// 콘텐츠 창 생성 및 설정
		JComponent newContentPane = new ProgressBarDemo2();
		newContentPane.setOpaque(true); // content panes must be opaque
		frame.setContentPane(newContentPane);

		// 윈도우 표시
		frame.pack();
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		// 이벤트 디스패치 스레드에 대한 작업을 예약합니다.
		//이 응용 프로그램의 GUI를 만들고 보여줍니다.
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});
	}
}