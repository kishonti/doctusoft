package folderexcercise;

import java.util.List;

public class FolderExcercise {
	public FolderNode reachableNodes(List<String> readable, List<String> writable) {
		FolderNode folders = new FolderNode("/");
		for (String path : readable) {
			addToTree(folders, path, FolderProperty.Read);
		}
		for (String path : writable) {
			addToTree(folders, path, FolderProperty.Write);
		}
		FolderNode reachable = new FolderNode("/");
		addReachableToTree(folders, reachable);
		if (reachable.getChildren().isEmpty() && !reachable.hasProperty(FolderProperty.Read)
				&& !reachable.hasProperty(FolderProperty.Write)) {
			return null;
		}
		return reachable;
	}

	public void addReachableToTree(FolderNode current, FolderNode result) {
		// must be readable, and must be writable or must have writable child
		if (!current.hasProperty(FolderProperty.Read)
				|| !current.hasProperty(FolderProperty.Write) && !current.hasChildWithProperty(FolderProperty.Write)) {
			return;
		}
		FolderNode next;
		if (!current.getName().equals("/")) {
			// copy except children
			FolderNode copy = new FolderNode(current.getName());
			copy.properties = current.properties;
			result.addChild(copy);
			next = result.getChild(current.getName());
		} else {
			// handling root
			result.properties = current.properties;
			next = result;
		}
		// adding relevant child nodes
		current.getChildren().stream().forEach(child -> addReachableToTree(child, next));
	}

	public void addToTree(FolderNode tree, String path, FolderProperty property) {
		if (path.equals("/")) {
			tree.setProperty(property);
		}
		String[] parts = path.split("\\/");
		String part;
		for (int i = 1; i < parts.length; i++) {
			part = parts[i];
			if (!tree.hasChild(part)) {
				tree.addChild(part);
			}
			tree = tree.getChild(part);
		}
		tree.setProperty(property);
	}

}
