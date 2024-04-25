/**
 * 
 */
package com.bsva.guavaImpl;

import java.nio.file.Path;
import java.util.List;

/**
 * @author jeremym
 * 
 */

public interface PathEventContext {
	boolean isValid();

	Path getWatchedDirectory();

	List<PathEvent> getEvents();
}