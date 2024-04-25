/**
 * 
 */
package com.bsva.guavaImpl;

import com.google.common.eventbus.EventBus;
import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.google.inject.name.Names;

/**
 * @author jeremym
 * 
 */

public class DirectoryEventModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(EventBus.class).in(Singleton.class);
		bind(String.class).annotatedWith(Names.named("START_PATH")).toInstance(
				"test-files");
		bind(DirectoryEventWatcher.class).toProvider(
				DirectoryEventWatcherProvider.class);
	}

}