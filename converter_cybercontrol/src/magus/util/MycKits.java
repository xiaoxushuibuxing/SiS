// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   MycKits.java

package magus.util;

import java.awt.*;
import java.awt.event.*;
import java.util.Map;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableModel;

public class MycKits
{

	static Map renderingHints = null;
	public static final MouseListener toolButtonListener = new MouseAdapter() {

		public void mouseEntered(MouseEvent e)
		{
			AbstractButton button = (AbstractButton)e.getComponent();
			if (button.isEnabled())
				button.setBorderPainted(true);
		}

		public void mouseExited(MouseEvent e)
		{
			((AbstractButton)e.getComponent()).setBorderPainted(false);
		}

	};
	private static JPanel nfcPane = null;
	private static JTable nfcTable = null;
	private static JTextField nfcNameTxt = null;
	private static JComboBox nfcTypeCox = null;

	private MycKits()
	{
	}

	public static Map createQualityRenderingHints()
	{
		if (renderingHints == null)
		{
			RenderingHints r = new RenderingHints(null);
			r.put(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
			r.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			r.put(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
			r.put(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
			r.put(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
			r.put(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
			r.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
			r.put(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
			r.put(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			renderingHints = r;
		}
		return renderingHints;
	}

	public static void setToScreenCenter(Component com)
	{
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		com.setLocation((screen.width - com.getWidth()) / 2, (screen.height - com.getHeight()) / 2);
	}

	public static String showNetFileChoose(Component parentComponent, String title, Icon icon, String command, TableModel model)
	{
		JPanel message = new JPanel(new BorderLayout());
		JTable table = new JTable(model) {

			public boolean isCellEditable(int r, int c)
			{
				return false;
			}

		};
		table.setAutoResizeMode(0);
		table.setSelectionMode(0);
		table.setShowGrid(false);
		table.setCursor(Cursor.getPredefinedCursor(12));
		JComponent jc = null;
		message.add(jc = new JScrollPane(table));
		jc.setPreferredSize(new Dimension(350, 200));
		JPanel namePane = new JPanel();
		((FlowLayout)namePane.getLayout()).setAlignment(2);
		JLabel lab;
		namePane.add(lab = new JLabel("�ļ���(N)"));
		lab.setDisplayedMnemonic('n');
		class JTextFieldAndListSelectionEvent extends JTextField
			implements ListSelectionListener
		{

			public void valueChanged(ListSelectionEvent lisE)
			{
				if (lisE.getValueIsAdjusting())
				{
					return;
				} else
				{
					setText((String)MycKits.nfcTable.getValueAt(MycKits.nfcTable.getSelectedRow(), 0));
					return;
				}
			}

			public JTextFieldAndListSelectionEvent(int clu)
			{
				super(clu);
			}
		}

		JTextFieldAndListSelectionEvent txtAevt = new JTextFieldAndListSelectionEvent(30);
		namePane.add(nfcNameTxt = txtAevt);
		table.getSelectionModel().addListSelectionListener(txtAevt);
		JPanel typePane = new JPanel();
		((FlowLayout)typePane.getLayout()).setAlignment(2);
		typePane.add(lab = new JLabel("�ļ�����(T)"));
		lab.setDisplayedMnemonic('t');
		typePane.add(nfcTypeCox = new JComboBox(new Object[] {
			"SER�ļ� (*.ser)"
		}));
		nfcTypeCox.setPreferredSize(nfcNameTxt.getPreferredSize());
		JPanel bottom = new JPanel(new GridLayout(0, 1));
		bottom.add(namePane);
		bottom.add(typePane);
		message.add(bottom, "South");
		nfcTable = table;
		nfcPane = message;
		nfcTable.setRowSelectionInterval(0, 0);
		nfcNameTxt.requestFocusInWindow();
		Object options[] = {
			command, "ȡ��"
		};
		Object initialValue = null;
		int c = JOptionPane.showOptionDialog(parentComponent, nfcPane, title, -1, -1, icon, options, initialValue);
		String str;
		switch (c)
		{
		case 0: // '\0'
			str = nfcNameTxt.getText();
			break;

		default:
			str = null;
			break;
		}
		return str;
	}


}
