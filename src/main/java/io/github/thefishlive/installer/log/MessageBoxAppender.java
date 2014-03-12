package io.github.thefishlive.installer.log;

import javax.swing.JOptionPane;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.Level;
import org.apache.log4j.spi.LoggingEvent;

public class MessageBoxAppender extends AppenderSkeleton {

	@Override
	public void close() {
	}

	@Override
	public boolean requiresLayout() {
		return true;
	}

	@Override
	protected void append(LoggingEvent event) {
		int type = JOptionPane.PLAIN_MESSAGE;
		
		if (event.getLevel() == Level.WARN) {
			type = JOptionPane.WARNING_MESSAGE;
		} else if (event.getLevel() == Level.ERROR || event.getLevel() == Level.FATAL) {
			type = JOptionPane.ERROR_MESSAGE;
		} else if (event.getLevel() == Level.INFO || event.getLevel() == Level.DEBUG) {
			type = JOptionPane.INFORMATION_MESSAGE;
		}
		
		JOptionPane.showMessageDialog(null, layout.format(event), "Installer", type);
	}

}
