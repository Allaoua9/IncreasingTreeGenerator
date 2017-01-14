import Tree.Node;
import TreeGenerator.IncreasingTreeGenerator;

import java.math.BigInteger;

public class Main {

    public static void main(String[] args) {

        IncreasingTreeGenerator generator = new IncreasingTreeGenerator();
        generator.processCoeffiecients(4);
        try {
            printTree(generator.labelThis(generator.generateTree(7, BigInteger.valueOf(1))));
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
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
