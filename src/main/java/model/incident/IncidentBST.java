package model.incident;

import java.util.ArrayList;
import java.util.List;

public class IncidentBST {

    private class Node {
        Incident incident;
        Node left, right;

        Node(Incident incident) {
            this.incident = incident;
        }
    }

    private Node root;

    public void insert(Incident incident) {
        root = insertRec(root, incident);
    }

    private Node insertRec(Node node, Incident incident) {
        if (node == null) return new Node(incident);

        if (incident.getSeverity() > node.incident.getSeverity()) {
            node.left = insertRec(node.left, incident);
        } else {
            node.right = insertRec(node.right, incident);
        }
        return node;
    }

    public Incident getMostSevere() {
        if (root == null) return null;
        Node current = root;
        while (current.left != null) {
            current = current.left;
        }
        return current.incident;
    }

    public void remove(Incident incident) {
        root = removeRec(root, incident);
    }

    private Node removeRec(Node node, Incident incident) {
        if (node == null) return null;

        if (incident.getSeverity() > node.incident.getSeverity()) {
            node.left = removeRec(node.left, incident);
        } else if (incident.getSeverity() < node.incident.getSeverity()) {
            node.right = removeRec(node.right, incident);
        } else if (incident.getX() == node.incident.getX() && incident.getY() == node.incident.getY()) {
            // Nodo encontrado
            if (node.left == null) return node.right;
            if (node.right == null) return node.left;

            Node minNode = findMin(node.right);
            node.incident = minNode.incident;
            node.right = removeRec(node.right, minNode.incident);
        } else {
            node.right = removeRec(node.right, incident);
        }

        return node;
    }

    private Node findMin(Node node) {
        while (node.left != null) node = node.left;
        return node;
    }

    public List<Incident> inOrder() {
        List<Incident> list = new ArrayList<>();
        inOrderRec(root, list);
        return list;
    }

    private void inOrderRec(Node node, List<Incident> list) {
        if (node != null) {
            inOrderRec(node.left, list);
            list.add(node.incident);
            inOrderRec(node.right, list);
        }
    }

    public boolean isEmpty() {
        return root == null;
    }

    public void clear() {
        root = null;
    }
}
