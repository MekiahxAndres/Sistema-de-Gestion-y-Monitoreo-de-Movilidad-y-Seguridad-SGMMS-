package model.incident;

public class Incident implements Comparable<Incident> {

    private final String description;
    private final int severity; // 1 = menor, 3 = grave
    private final double x, y;

    public Incident(String description, int severity, double x, double y) {
        this.description = description;
        this.severity = severity;
        this.x = x;
        this.y = y;
    }

    public int getSeverity() { return severity; }
    public double getX() { return x; }
    public double getY() { return y; }
    public String getDescription() { return description; }

    @Override
    public int compareTo(Incident o) {
        return Integer.compare(o.severity, this.severity); // más grave primero
    }
}
