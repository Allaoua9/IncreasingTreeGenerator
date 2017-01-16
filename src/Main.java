import Tree.Node;
import TreeGenerator.IncreasingTreeGenerator;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;

public class Main {

    public static void main(String[] args) {

        IncreasingTreeGenerator generator = new IncreasingTreeGenerator();
        generator.processCoeffiecients(4);
        try {
            Node node = generator.labelThis(generator.generateTree(14, new BigInteger("87178291300")));
            printTree(node);

            try{
                PrintWriter writer = new PrintWriter(System.getProperty("user.home")+
                        "\\Desktop\\new1.graphml", "UTF-8");
                writer.println(buildGraphMLString(node));
                writer.close();
            } catch (IOException e) {
                // do something
                System.exit(-1);
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static String buildGraphMLString(Node node) {

        String graphML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<graphml xmlns=\"http://graphml.graphdrawing.org/xmlns\"  \n" +
                "    xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n" +
                "    xsi:schemaLocation=\"http://graphml.graphdrawing.org/xmlns\n" +
                "     http://graphml.graphdrawing.org/xmlns/1.0/graphml.xsd\">\n";

        String keys = "<key attr.name=\"url\" attr.type=\"string\" for=\"node\" id=\"d3\"/>\n";
        StringBuilder structure = new StringBuilder();

        buildGraphMLTreeString(node, structure);

        graphML = graphML + keys ;

        graphML = graphML + "<graph edgedefault=\"directed\" id=\"G\">\n";

        graphML = graphML + structure.toString();

        graphML = graphML + "</graph>" + "</graphml>";


        return graphML;
    }


    public static void buildGraphMLTreeString(Node node, StringBuilder structure) {


        structure.append("<node id=\"n"+node.getValue()+"\">\n" +
                "\t\t<data key=\"d3\"><![CDATA["+node.getValue()+"]]></data>\n" +
                "\t</node>\n");


        for (Node child :
                node.getChildren()) {
            structure = structure.append("<edge source=\"n"+ node.getValue() +"\" target=\"n"+child.getValue()+"\"/>\n");
            buildGraphMLTreeString(child, structure);
        }
    }

    static int tab = 0;
    public static void printTree(Node node) {
        if(node==null) {
            System.out.print("#");
        }
        else {

            System.out.println("<<"+ node.getValue() +">> {");
            tab++;
            for(Node child : node.getChildren()) {

                for(int i=0; i<tab; i++) System.out.print("\t");
                printTree(child);
                System.out.println(",");
            }
            tab--;
            for(int i=0; i<tab; i++) System.out.print("\t");
            System.out.print("}");
        }
    }

}
