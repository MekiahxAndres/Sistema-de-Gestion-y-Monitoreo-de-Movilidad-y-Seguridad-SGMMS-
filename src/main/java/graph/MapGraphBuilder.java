package graph;

public class MapGraphBuilder {

    public static Graph createCityGraph() {
        Graph graph = new Graph();


        graph.addVertex("V00", 316, 386);
        graph.addVertex("V01", 798, 385);
        graph.addVertex("V02", 800, 225);
        graph.addVertex("V03", 1216, 221);
        graph.addVertex("V04", 1216, 383);
        graph.addVertex("V05", 1216, 639);
        graph.addVertex("V06", 1216, 895);
        graph.addVertex("V07", 1565, 383);
        graph.addVertex("V08", 1566, 609);
        graph.addVertex("V09", 1566, 894);
        graph.addVertex("V10", 1566, 1214);
        graph.addVertex("V11", 1566, 1345);
        graph.addVertex("V12", 1279, 1345);
        graph.addVertex("V13", 1087, 1345);
        graph.addVertex("V14", 801, 1345);
        graph.addVertex("V15", 1088, 893);
        graph.addVertex("V16", 799, 1213);
        graph.addVertex("V17", 799, 896);
        graph.addVertex("V18", 799, 641);
        graph.addVertex("V19", 320, 641);
        graph.addVertex("V20", 320, 895);
        graph.addVertex("V21", 320, 1215);
        graph.addVertex("V22", 320, 1470);
        graph.addVertex("V23", 799, 1470);


        graph.connect("V00", "V01");
        graph.connect("V00", "V19");

        graph.connect("V01", "V02");
        graph.connect("V01", "V18");

        graph.connect("V02", "V03");
        graph.connect("V03", "V04");
        graph.connect("V04", "V05");
        graph.connect("V04", "V07");
        graph.connect("V05", "V06");
        graph.connect("V05", "V18");

        graph.connect("V06", "V09");
        graph.connect("V06", "V15");

        graph.connect("V07", "V08");
        graph.connect("V08", "V09");
        graph.connect("V09", "V10");
        graph.connect("V10", "V11");
        graph.connect("V11", "V12");
        graph.connect("V12", "V13");
        graph.connect("V13", "V14");
        graph.connect("V13", "V15");

        graph.connect("V14", "V16");
        graph.connect("V14", "V23");

        graph.connect("V15", "V17");
        graph.connect("V16", "V17");
        graph.connect("V16", "V21");

        graph.connect("V17", "V18");
        graph.connect("V17", "V20");

        graph.connect("V18", "V19");

        graph.connect("V19", "V20");

        graph.connect("V20", "V21");
        graph.connect("V21", "V22");
        graph.connect("V22", "V23");

        return graph;
    }
}