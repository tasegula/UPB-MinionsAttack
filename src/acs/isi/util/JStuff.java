package acs.isi.util;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Calendar;

import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import acs.isi.minions.MapWrapper;
import acs.isi.util.Utils.App;

import com.esri.core.map.TimeExtent;
import com.esri.core.map.TimeOptions.Units;
import com.esri.map.TimeAwareLayer;
import com.esri.toolkit.sliders.JTimeSlider;
import com.esri.toolkit.sliders.JTimeSlider.TimeMode;

public class JStuff {

	public static final Dimension elemDim = new Dimension(Utils.App.jWidth,
			Utils.App.jHeight);
	static Dimension controlDim = new Dimension(Utils.App.jWidth,
			Utils.App.width);

	// CONSTRUCTOR
	private JStuff() {
	}

	/**
	 * Creates a JTimeSlider.
	 *
	 * @param TimeAwareLayer
	 *            layer to which the time slider will be associated.
	 */
	// J-TIME SLIDER creator
	public static JTimeSlider createTimeSlider(final TimeAwareLayer layer,
			final String title, final int tick) {
		final JTimeSlider jTimeSlider = new JTimeSlider();
		jTimeSlider.setTitle(title);
		jTimeSlider.addLayer(layer);
		jTimeSlider.setTimeMode(TimeMode.TimeExtent);
		jTimeSlider.setPlaybackRate(tick);
		jTimeSlider.setVisible(false);
		return jTimeSlider;
	}

	// J-TIME SLIDER setter
	public static JTimeSlider setTimeSlider(final JTimeSlider timeSlider,
			final TimeAwareLayer layer) {
		timeSlider.setTimeExtent(layer.getTimeInfo().getTimeExtent(), 1,
				Units.Months);
		final Calendar calendar = layer.getTimeInfo().getTimeExtent()
				.getStartDate();
		timeSlider.setTimeIntervalStart(calendar);
		calendar.add(Calendar.MONTH, 1);
		timeSlider.setTimeIntervalEnd(calendar);
		timeSlider.setVisible(true);

		return timeSlider;
	}
	
	public static JTimeSlider setTimeSlider(final JTimeSlider timeSlider,
			final TimeAwareLayer layer,
			final TimeExtent interval) {
		timeSlider.setTimeExtent(layer.getTimeInfo().getTimeExtent(), 1,
				Units.Months);
		timeSlider.setTimeIntervalStart(interval.getStartDate());
		timeSlider.setTimeIntervalEnd(interval.getEndDate());
		timeSlider.setVisible(true);

		return timeSlider;
	}

	// J-LAYERED PANE
	public static JLayeredPane createContentPane() {
		final JLayeredPane contentPane = new JLayeredPane();
		contentPane.setBounds(100, 100, 1000, 700);
		contentPane.setLayout(new BorderLayout(0, 0));
		contentPane.setVisible(true);
		return contentPane;
	}

	// J-WINDOW
	public static JFrame createWindow(final String title) {
		final JFrame window = new JFrame(title);
		window.setBounds(Utils.App.windowRectangle);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.getContentPane().setLayout(new BorderLayout(0, 0));
		window.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(final WindowEvent windowEvent) {
				super.windowClosing(windowEvent);
				MapWrapper.map.dispose();
			}
		});
		return window;
	}

	// J-PANEL
	public static JPanel getBoxPanel(Dimension dim, int axis) {
		final JPanel boxPanel = new JPanel();
		final BoxLayout boxLayout = new BoxLayout(boxPanel, axis);
		boxPanel.setLayout(boxLayout);
		boxPanel.setSize(dim);
		final Color BG_COLOR = new Color(0, 0, 0, 80);
		boxPanel.setBackground(BG_COLOR);
		boxPanel.setDoubleBuffered(true);
		boxPanel.setBorder(new LineBorder(Color.GRAY, 1, false));
		boxPanel.setVisible(true);

		return boxPanel;
	}

	public static JPanel getGridPanel(Dimension dim) {
		final JPanel gridPanel = new JPanel(new GridBagLayout());
		gridPanel.setSize(dim);
		final Color BG_COLOR = new Color(0, 0, 0, 0);
		gridPanel.setBackground(BG_COLOR);
		gridPanel.setDoubleBuffered(true);
		gridPanel.setBorder(new LineBorder(Color.GRAY, 1, false));
		gridPanel.setVisible(true);

		return gridPanel;
	}

	// J-LABEL
	public static JLabel getLabel(final String text) {
		final JLabel label = new JLabel(text, JLabel.LEFT);
		// label.setBorder(BasicBorders.getProgressBarBorder());
		label.setForeground(Color.WHITE);
		label.setBackground(null);
		label.setAlignmentX(SwingConstants.LEFT);
		label.setMaximumSize(elemDim);
		return label;
	}

	// J-ComboBox
	public static <LayerWrapperType> JComboBox<LayerWrapperType> getComboBox(
			LayerWrapperType defaultItem, LayerWrapperType[] all) {
		final JComboBox<LayerWrapperType> comboBox = new JComboBox<>();
		comboBox.setModel(new DefaultComboBoxModel<>(all));
		comboBox.setSelectedItem(defaultItem);

		return comboBox;
	}

	// GRID-BAG-CONSTRAINS
	private static GridBagConstraints createGbc(int x, int y) {
		final Insets WEST_INSETS = new Insets(5, 0, 5, 5);
		final Insets EAST_INSETS = new Insets(5, 5, 5, 0);
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = x;
		gbc.gridy = y;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;

		gbc.anchor = (x == 0) ? GridBagConstraints.WEST
				: GridBagConstraints.EAST;
		gbc.fill = (x == 0) ? GridBagConstraints.BOTH
				: GridBagConstraints.HORIZONTAL;

		gbc.insets = (x == 0) ? WEST_INSETS : EAST_INSETS;
		gbc.weightx = (x == 0) ? 0.1 : 1.0;
		gbc.weighty = 1.0;
		return gbc;
	}
	
	static <LayerWrapperType> void setGridPanel(JPanel humanPanel,
			JComboBox<LayerWrapperType> chooseMin,
			JComboBox<LayerWrapperType> chooseMax) {

		humanPanel.add(getLabel("min"), createGbc(0, 0));
		humanPanel.add(chooseMin, createGbc(1, 0));
		humanPanel.add(getLabel("max"), createGbc(0, 1));
		humanPanel.add(chooseMax, createGbc(1, 1));
	}
}
