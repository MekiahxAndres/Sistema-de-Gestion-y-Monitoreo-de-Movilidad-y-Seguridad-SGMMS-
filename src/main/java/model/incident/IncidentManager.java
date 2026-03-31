package model.incident;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class IncidentManager {

    private final PriorityQueue<Incident> incidentQueue;
    private final List<Incident> allIncidents;

    public IncidentManager() {
        this.incidentQueue = new PriorityQueue<>();
        this.allIncidents = new ArrayList<>();
    }

    public void addIncident(Incident incident) {
        incidentQueue.add(incident);         // para el centro de monitoreo
        allIncidents.add(incident);          // para el panel de incidentes
    }

    public Incident getNextIncident() {
        return incidentQueue.poll();         // usado por MonitoringCenterController
    }

    public boolean hasIncidents() {
        return !incidentQueue.isEmpty();
    }

    public List<Incident> getAllIncidentsSorted() {
        List<Incident> sorted = new ArrayList<>(allIncidents);
        sorted.sort(Comparator.comparingInt(Incident::getSeverity).reversed());
        return sorted;
    }

    public boolean isDangerousZone(double x, double y) {
        for (Incident i : allIncidents) {
            if (Math.hypot(i.getX() - x, i.getY() - y) < 30) {
                return true;
            }
        }
        return false;
    }

    public Incident peekIncident() {
        return incidentQueue.peek();
    }
}
