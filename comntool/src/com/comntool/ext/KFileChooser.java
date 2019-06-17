package com.comntool.ext;

import java.awt.Component;

import java.awt.Dimension;

import java.awt.HeadlessException;

import java.awt.Point;

import java.awt.event.ComponentAdapter;

import java.awt.event.ComponentEvent;

import javax.swing.JComponent;
import javax.swing.JDialog;

import javax.swing.JFileChooser;

/**
 * 参考： https://kingjbx.iteye.com/blog/1097508
 */

public class KFileChooser extends JFileChooser {
	private Point p;
	private Dimension d;

	/**
	 * 见JFileChooser API的注释说明
	 * @param location 对话盒弹出的位置
	 * @param size 对话盒的大小
	 */

	public int showOpenDialog(Component parent,
			Point location, Dimension size) throws HeadlessException {
		p = location;
		d = size;
		return super.showOpenDialog(parent);

	}

	/**
	 * 
	 * 见JFileChooser API的注释说明
	 * @param location 对话盒弹出的位置
	 * @param size 对话盒的大小
	 * 
	 */

	public int showSaveDialog(Component parent,
			Point location, Dimension size) throws HeadlessException {
		p = location;
		d = size;
		return super.showSaveDialog(parent);

	}

	/**
	 * 见JFileChooser API的注释说明
	 * @param location 对话盒弹出的位置
	 * @param size 对话盒的大小
	 */
	public int showDialog(Component parent, String approveButtonText,
			Point location, Dimension size) {
		p = location;
		d = size;
		return super.showDialog(parent, approveButtonText);

	}

	/**
	 * 
	 * 取得对话盒的位置和大小，结果放在参数中，要求参数不是null
	 * @param p 记录对话盒的位置
	 * @param d 记录对话盒的大小
	 * 
	 */

	public void getLastLocationAndSize(Point p, Dimension d) {
		if (p != null) {
			p.x = this.p.x;
			p.y = this.p.y;
		}

		if (d != null) {
			d.width = this.d.width;
			d.height = this.d.height;
		}
	}

	protected JDialog createDialog(Component parent) throws HeadlessException {
		final JDialog dlg = super.createDialog(parent);
		// ComnViewSet.setFontDefaultDeep(this.getComponents());
		dlg.addComponentListener(new ComponentAdapter() {
			public void componentMoved(ComponentEvent e) {
				p = dlg.getLocation();
			}
			public void componentResized(ComponentEvent e) {
				d = dlg.getSize();
			}

		});

		if (p != null) {
			dlg.setLocation(p);
		}
		if (d != null && d.width > 0 && d.height > 0) {
			dlg.setSize(d);
		}
		return dlg;

	}

}
