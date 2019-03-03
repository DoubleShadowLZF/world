package org.world.concurrencyinpractice.problem.b_object;

import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @Description 独立的状态变量
 * 将线程安全性委托给多个状态变量
 * 使用CopyOnWriteArrayList来保存监听器列表。它是一个线程安全的链表，
 * 特别适用于管理监听器列表。每个链表都是线程安全的，
 * 此外，由于各个状态之间不存在耦合关系。
 * @DAuthor Double
 */
public class VisualComponent {

	private final List<KeyListener> keyListeners = new CopyOnWriteArrayList<>();

	private final List<MouseListener> mouseListeners = new CopyOnWriteArrayList<>();

	public void addMouseListener(MouseListener listener) {
		mouseListeners.add(listener);
	}

	public void addKeyListener(KeyListener keyListener) {
		keyListeners.add(keyListener);
	}

	public void removeKeyListener(KeyListener keyListener) {
		keyListeners.remove(keyListener);
	}

	public void removeMouseListener(MouseListener mouseListener) {
		mouseListeners.remove(mouseListener);
	}
}
