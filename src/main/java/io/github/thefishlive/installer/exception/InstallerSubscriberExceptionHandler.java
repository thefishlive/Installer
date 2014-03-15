package io.github.thefishlive.installer.exception;

import io.github.thefishlive.installer.log.InstallerLogger;

import com.google.common.eventbus.SubscriberExceptionContext;
import com.google.common.eventbus.SubscriberExceptionHandler;

public class InstallerSubscriberExceptionHandler implements SubscriberExceptionHandler {

	@Override
	public void handleException(Throwable exception, SubscriberExceptionContext context) {
		InstallerLogger log = InstallerLogger.getLog();
		InstallerLogger.getUserlog().error("There was a error handling a event, please contact the developer");
		
		log.error("Error sending event " + context.getEvent().getClass().getName() + " to " + context.getSubscriber().getClass().getName());
		log.error("Exception: " + exception.getMessage());
		log.error("Stacktrace: ", exception);
	}

}
