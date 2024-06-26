/**
 * 
 */
package com.bsva.guavaImpl;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author jeremym
 * 
 */

public class PathEvents implements PathEventContext {
	private final List<PathEvent> pathEvents = new ArrayList<>();
	private final Path watchedDirectory;
	private final boolean isValid;

	PathEvents(boolean valid, Path watchedDirectory) {
		isValid = valid;
		this.watchedDirectory = watchedDirectory;
	}

	@Override
	public boolean isValid() {
		return isValid;
	}

	@Override
	public Path getWatchedDirectory() {
		return watchedDirectory;
	}

	@Override
	public List<PathEvent> getEvents() {
		return Collections.unmodifiableList(pathEvents);
	}

	public void add(PathEvent pathEvent) {
		pathEvents.add(pathEvent);
	}

}
