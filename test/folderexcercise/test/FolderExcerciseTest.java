package folderexcercise.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import folderexcercise.FolderExcercise;
import folderexcercise.FolderNode;
import folderexcercise.FolderProperty;

public class FolderExcerciseTest {

	@Test
	public void addToTreeTest() {
		FolderExcercise excercise = new FolderExcercise();
		FolderNode tree = new FolderNode("/");
		excercise.addToTree(tree, "/test/test2", FolderProperty.Read);
		FolderNode first = tree.getChild("test");
		assertFalse(first.hasProperty(FolderProperty.Read));
		assertTrue(first.getChild("test2").hasProperty(FolderProperty.Read));
	}
	
	@Test
	public void reachableNodesTest() {
		FolderExcercise excercise = new FolderExcercise();
		List<String> readable = new ArrayList<>();
		List<String> writable = new ArrayList<>();
		FolderNode reachable = excercise.reachableNodes(readable, writable);
		assertNull(reachable);
		readable.add("/");
		writable.add("/");
		reachable = excercise.reachableNodes(readable, writable);
		assertNotNull(reachable);
		readable.add("/rw");
		readable.add("/rw/rw");
		readable.add("/rw/r");
		readable.add("/rw/not_r");
		readable.add("/rw/not_r2");
		writable.add("/rw");
		writable.add("/rw/rw");
		writable.add("/rw/r/w");
		writable.add("/rw/r2/other/w");
		reachable = excercise.reachableNodes(readable, writable);
		FolderNode rw = reachable.getChild("rw");
		assertTrue(rw.hasProperty(FolderProperty.Read));
		assertTrue(rw.hasProperty(FolderProperty.Write));
		assertTrue(rw.hasChild("rw")); // no child but itself is writable
		assertTrue(rw.hasChild("r")); // has one writable child
		assertFalse(rw.hasChild("not_r")); // writable child unreachable
		assertFalse(rw.hasChild("not_r2")); // readable but no writable child
	}

}
