
package com.bsva.guavaImpl;

import java.nio.file.Path;
import java.nio.file.WatchEvent;

/**
 * @author jeremym
 * 
 */

public class PathEvent {
	private final Path eventTarget;
	private final WatchEvent.Kind type;

	PathEvent(Path eventTarget, WatchEvent.Kind type) {
		this.eventTarget = eventTarget;
		this.type = type;
	}

	public Path getEventTarget() {
		return eventTarget;
	}

	public WatchEvent.Kind getType() {
		return type;
	}
}