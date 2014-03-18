package io.github.thefishlive.installer.log;

import java.io.Serializable;

import javax.swing.JOptionPane;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;

@Plugin(name = "MessageBox", category = "Core", elementType = "appender", printObject = true)
public class MessageBoxAppender extends AbstractAppender {

	protected MessageBoxAppender(String name, Filter filter, Layout<? extends Serializable> layout) {
		super(name, filter, layout);
	}

	@Override
	public void append(LogEvent event) {
		int type = JOptionPane.PLAIN_MESSAGE;

		if (event.getLevel() == Level.WARN) {
			type = JOptionPane.WARNING_MESSAGE;
		} else if (event.getLevel() == Level.ERROR || event.getLevel() == Level.FATAL) {
			type = JOptionPane.ERROR_MESSAGE;
		} else if (event.getLevel() == Level.INFO || event.getLevel() == Level.DEBUG) {
			type = JOptionPane.INFORMATION_MESSAGE;
		}

		JOptionPane.showMessageDialog(null, getLayout().toSerializable(event), "Installer", type);
	}

	@PluginFactory
	public static MessageBoxAppender createAppender(@PluginAttribute("name") String name, 
														@PluginElement("Layout") Layout<? extends Serializable> layout, 
														@PluginElement("Filters") Filter filter) {

		if (name == null) {
			LOGGER.error("No name provided for MessageBoxAppender");
			return null;
		}
		
		if (layout == null) {
			LOGGER.error("No layout provided for MessageBoxAppender");
			return null;
		}
		
		return new MessageBoxAppender(name, filter, layout);
	}
}
