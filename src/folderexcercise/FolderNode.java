package folderexcercise;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class FolderNode {
	private String name;
	private Map<String, FolderNode> children;
	private Set<FolderProperty> properties;

	public FolderNode(String name) {
		children = new HashMap<>();
		properties = new HashSet<>();
		this.name = name;
	}

	String getName() {
		return name;
	}

	public void addChild(FolderNode child) {
		children.put(child.getName(), child);
	}

	public void addChild(String child) {
		children.put(child, new FolderNode(child));
	}

	public FolderNode getChild(String child) {
		return children.get(child);
	}
	
	public Collection<FolderNode> getChildren() {
		return children.values();
	}

	public boolean hasChild(String child) {
		return children.containsKey(child);
	}

	public boolean hasChildWithProperty(FolderProperty property) {
		return children.entrySet().stream().anyMatch(child -> child.getValue().hasProperty(property));
	}

	public void setProperty(FolderProperty property) {
		properties.add(property);
	}

	public Set<FolderProperty> getProperties() {
		return properties;
	}

	public void setProperties(Set<FolderProperty> properties) {
		this.properties = properties;
	}

	public boolean hasProperty(FolderProperty property) {
		return properties.contains(property);
	}
}
